package example.test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.NumberRenderer;

import example.test.backend.data.Product;

/**
 * Grid of items (orignal code copied from Vaadin is very fragile - particularly the html and the codes relation to it, only slight changes can cause
 * it to fail to display without any straightforward method of debugging it : hence I have not refactored) 
 * handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class ProductGrid extends Grid {

	private BooleanToStringConverter booleanToStringConverter = new BooleanToStringConverter(); 

    public ProductGrid(VaadinUI ui) {
        setSizeFull();

        setSelectionMode(SelectionMode.SINGLE);
        
        //BeanItemContainer<Product> container = new BeanItemContainer<Product>(
        //        Product.class);
        setContainerDataSource(
                new BeanItemContainer(Product.class, ui.getProductRepository().findAll()));        
        //setContainerDataSource(container);
//        setColumnOrder("id", "productName", "barCode", "propertyTag", "serialCode", "price", "availability",
//                "stockCount");
        setColumnOrder("barCode", "propertyTag", "serialCode", "dateEntered", "office", "description",
                "assetType","assetModel","manufacturer","unit","comments","historyLog","vendor","dateReceived","purchaseOrder","budgetCode",
                "verifiedDate","computerRelated","excessed","locationCode","repApproved","itemReplaced","inventoryDate","isEquipment","heatTicket");
        
        removeColumn("id");
        removeColumn("showComments");
        removeColumn("comments");
        removeColumn("operator");
        removeColumn("tech");
        removeColumn("LU");
        removeColumn("historyLog");
        removeColumn("locationType");
        
/*
        // Show empty stock as "-"
        getColumn("stockCount").setConverter(new StringToIntegerConverter() {
            @Override
            public String convertToPresentation(Integer value,
                    java.lang.Class<? extends String> targetType, Locale locale)
                    throws Converter.ConversionException {
                if (value == 0) {
                    return "-";
                }

                return super.convertToPresentation(value, targetType, locale);
            };
        });
		*/
        // Add " $" automatically after price
        //add renderers on grid so they display correctly ISSUE #15
        getColumn("cost").setConverter(new DollarConverter());
        getColumn("dateEntered").setRenderer(new DateRenderer(" %1$tm/%1$td/%1$ty", Locale.US));
        getColumn("dateReceived").setRenderer(new DateRenderer(" %1$tm/%1$td/%1$ty", Locale.US));
        getColumn("verifiedDate").setRenderer(new DateRenderer(" %1$tm/%1$td/%1$ty", Locale.US));
        getColumn("repApproved").setRenderer(new DateRenderer(" %1$tm/%1$td/%1$ty", Locale.US));
        getColumn("inventoryDate").setRenderer(new DateRenderer(" %1$tm/%1$td/%1$ty", Locale.US));
        getColumn("heatTicket").setRenderer(new NumberRenderer(new DecimalFormat("########")));
        getColumn("computerRelated").setConverter(booleanToStringConverter).setRenderer(new HtmlRenderer());

        // Show categories as a comma separated list
       // getColumn("category").setConverter(new CollectionToStringConverter());

        // Align columns using a style generator and theme rule until #15438
        setCellStyleGenerator(new CellStyleGenerator() {

            @Override
            public String getStyle(CellReference cellReference) {
                if (cellReference.getPropertyId().equals("price")
                        || cellReference.getPropertyId().equals("stockCount")) {
                    return "align-right";
                }
                return null;
            }
        });
    }

    /**
     * Filter the grid based on a search string that is searched for in the
     * product name, availability and category columns.
     *
     * @param filterString
     *            string to look for
     */
    public void setFilter(String filterString) {
        getContainer().removeAllContainerFilters();
        if (filterString.length() > 0) {
            SimpleStringFilter nameFilter = new SimpleStringFilter(
                    "manufacturer", filterString, true, false);
            SimpleStringFilter availabilityFilter = new SimpleStringFilter(
                    "barCode", filterString, true, false);
            SimpleStringFilter categoryFilter = new SimpleStringFilter(
                    "propertyTag", filterString, true, false);
            getContainer().addContainerFilter(
                    new Or(nameFilter, availabilityFilter, categoryFilter));
        }

    }

    private BeanItemContainer<Product> getContainer() {
        return (BeanItemContainer<Product>) super.getContainerDataSource();
    }

    @Override
    public Product getSelectedRow() throws IllegalStateException {
        return (Product) super.getSelectedRow();
    }

    public void setProducts(Collection<Product> products) {
        getContainer().removeAllItems();
        getContainer().addAll(products);
    }

    public void refresh(Product product) {
        // We avoid updating the whole table through the backend here so we can
        // get a partial update for the grid
        BeanItem<Product> item = getContainer().getItem(product);
        if (item != null) {
            // Updated product
            MethodProperty p = (MethodProperty) item.getItemProperty("id");
            p.fireValueChange();
        } else {
            // New product
            getContainer().addBean(product);
        }
    }

    public void remove(Product product) {
        getContainer().removeItem(product);
    }
    
    public Object[] getVisibleColumns() {
        List<Column> theCols = this.getColumns();
        List<Column> visCols = new ArrayList<Column>();
        
        for (Grid.Column gcolumn : theCols) {
        	if (!gcolumn.isHidden()) {
        		visCols.add(gcolumn);
        	}
        }
    	return visCols.toArray();
    }
    
    public String[] getHeaderColumns() {
    	List<String> visCols = new ArrayList<String>();

    	//Remove hidden columns
        for (Grid.Column gcolumn : this.getColumns()) {
        	if (!gcolumn.isHidden()) {
        		visCols.add(gcolumn.getHeaderCaption());
        	}
        }
    	    	
//    	return new String[] {"barCode", "propertyTag", "serialCode", "dateEntered", "office", "description",
//                "assetType","assetModel","manufacturer","unit","comments","historyLog","vendor","dateReceived","purchaseOrder","budgetCode",
//                "verifiedDate","computerRelated","excessed","locationCode","repApproved","itemReplaced","inventoryDate","isEquipment","heatTicket"};    	
    	return visCols.toArray(new String[0]);
    }
}
