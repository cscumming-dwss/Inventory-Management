package dwss.nv.gov;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.NumberRenderer;

import org.vaadin.gridutil.GridUtil;
import org.vaadin.gridutil.cell.*;
import org.vaadin.gridutil.converter.SimpleStringConverter;
import org.vaadin.gridutil.renderer.*;
import org.vaadin.gridutil.renderer.EditDeleteButtonValueRenderer.EditDeleteButtonClickListener;

import dwss.nv.gov.backend.data.Asset;



/**
 * Grid of items (orignal code copied from Vaadin is very fragile - particularly the html and the codes relation to it, only slight changes can cause
 * it to fail to display without any straightforward method of debugging it : hence be very careful updating it) 
 * handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
public class AssetGrid extends Grid {

	private BooleanToStringConverter booleanToStringConverter = new BooleanToStringConverter(); 
	private GridCellFilter filter;
	private boolean ignoreCase = true;
	private boolean onlyMatchPrefix = true;

    public AssetGrid(VaadinUI ui) {
        setSizeFull();

        setSelectionMode(SelectionMode.SINGLE);
        
        setContainerDataSource(
                new BeanItemContainer(Asset.class, ui.getAssetRepository().findAll()));        
        setColumnOrder("id", "barCode", "propertyTag", "serialCode", "dateEntered", "office", "description",
                "assetType","assetModel","manufacturer","unit","comments","historyLog","vendor","dateReceived","purchaseOrder","budgetCode",
                "verifiedDate","computerRelated","excessed","locationCode","repApproved","itemReplaced","inventoryDate","isEquipment","heatTicket");
        
        removeColumn("showComments");
        removeColumn("comments");
        removeColumn("operator");
        removeColumn("tech");
        removeColumn("LU");
        removeColumn("historyLog");
        removeColumn("locationType");
        
        
        filter = new GridCellFilter(this);
        
        filter.setNumberFilter("id");
        filter.setNumberFilter("barCode");
        filter.setNumberFilter("propertyTag");
        filter.setNumberFilter("serialCode");
        filter.setDateFilter("dateEntered");
        filter.setTextFilter("office", true, false,"CO");
        filter.setTextFilter("description", true, false);
        filter.setTextFilter("assetType", ignoreCase, onlyMatchPrefix);
        filter.setTextFilter("assetModel", ignoreCase, onlyMatchPrefix);
        filter.setTextFilter("manufacturer", ignoreCase, onlyMatchPrefix);
        filter.setTextFilter("unit", ignoreCase, onlyMatchPrefix);
        filter.setTextFilter("vendor", ignoreCase, onlyMatchPrefix);
        filter.setDateFilter("dateReceived");
        filter.setTextFilter("purchaseOrder", ignoreCase, onlyMatchPrefix);
        filter.setTextFilter("budgetCode", ignoreCase, onlyMatchPrefix);
        filter.setDateFilter("verifiedDate");
        filter.setBooleanFilter("computerRelated");
        filter.setBooleanFilter("excessed");
        filter.setTextFilter("locationCode", ignoreCase, onlyMatchPrefix);
        filter.setDateFilter("repApproved");
        filter.setBooleanFilter("itemReplaced");
        filter.setDateFilter("inventoryDate");
        filter.setBooleanFilter("isEquipment");
        filter.setNumberFilter("heatTicket", "0", "999999999");
        
        //add renderers on grid so they display correctly ISSUE #15
        getColumn("cost").setConverter(new DollarConverter());
        getColumn("dateEntered").setRenderer(new DateRenderer(" %1$tm/%1$td/%1$ty", Locale.US));
        getColumn("dateReceived").setRenderer(new DateRenderer(" %1$tm/%1$td/%1$ty", Locale.US));
        getColumn("verifiedDate").setRenderer(new DateRenderer(" %1$tm/%1$td/%1$ty", Locale.US));
        getColumn("repApproved").setRenderer(new DateRenderer(" %1$tm/%1$td/%1$ty", Locale.US));
        getColumn("inventoryDate").setRenderer(new DateRenderer(" %1$tm/%1$td/%1$ty", Locale.US));
        getColumn("heatTicket").setRenderer(new NumberRenderer(new DecimalFormat("########")));
        getColumn("id").setRenderer(new NumberRenderer(new DecimalFormat("########")));
        getColumn("computerRelated").setConverter(booleanToStringConverter).setRenderer(new HtmlRenderer());

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


    private BeanItemContainer<Asset> getContainer() {
        return (BeanItemContainer<Asset>) super.getContainerDataSource();
    }

    @Override
    public Asset getSelectedRow() throws IllegalStateException {
        return (Asset) super.getSelectedRow();
    }

    public void setAssets(Collection<Asset> assets) {
        getContainer().removeAllItems();
        getContainer().addAll(assets);
    }

    public void refresh(Asset asset) {
        // We avoid updating the whole table through the backend here so we can
        // get a partial update for the grid
        BeanItem<Asset> item = getContainer().getItem(asset);
        if (item != null) {
            // Updated product
            MethodProperty p = (MethodProperty) item.getItemProperty("id");
            p.fireValueChange();
        } else {
            // New product
            getContainer().addBean(asset);
        }
    }

    public void remove(Asset asset) {
        getContainer().removeItem(asset);
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
