package example.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import example.test.backend.data.Manufacturer;
import example.test.backend.data.Product;
import example.test.backend.data.Vendor;

/**
 * A form for editing a single product.
 *  This class does some conversions and implements handlers and listeners
 *  the actual layout is handled by the productFormDesign.html since it is basically a static form
 * Using responsive layouts, the form can be displayed either sliding out on the
 * side of the view or filling the whole screen - see the theme for the related
 * CSS rules.
 * Note: you can only override the CSS by creating and compiling your own Vaadin theme to override the Valo Theme defaults
 * 
 *  setContainerDataSource(
                new BeanItemContainer(Product.class, ui.getProductRepository().findAll()));    
 */
public class ProductForm extends ProductFormDesign {

    private SampleCrudLogic viewLogic;
    private BeanFieldGroup<Product> fieldGroup;
    private BeanFieldGroup<Manufacturer> mfieldGroup;
    private BeanFieldGroup<Vendor> vfieldGroup;

    public ProductForm(SampleCrudLogic sampleCrudLogic) {
        super();
        addStyleName("product-form");
        setResponsive(true);
        //Experiment with setting size
        setSizeFull();
        viewLogic = sampleCrudLogic;

        cost.setConverter(new DollarConverter());
        
        verifiedDate.setResponsive(true);
        
//   Comment out - unless we need an example for a combo box  10/21/16
//        for (Availability s : Availability.values()) {
//            availability.addItem(s);
//        }

        fieldGroup = new BeanFieldGroup<Product>(Product.class);
        mfieldGroup = new BeanFieldGroup<Manufacturer>(Manufacturer.class);
        vfieldGroup = new BeanFieldGroup<Vendor>(Vendor.class);
        
        
        fieldGroup.bindMemberFields(this);

        dateEntered.setDateFormat("     MM/dd/yyyy");
        //mfieldGroup.bind(this.manufacturer, "entry");
        //vfieldGroup.bind(this.vendor, "entry");
        //System.out.println(verifiedDate.getStyleName());
        
        // perform validation and enable/disable buttons while editing
        ValueChangeListener valueListener = new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                formHasChanged();
            }
        };
        for (Field f : fieldGroup.getFields()) {
            f.addValueChangeListener(valueListener);
        }

        fieldGroup.addCommitHandler(new CommitHandler() {

            @Override
            public void preCommit(CommitEvent commitEvent)
                    throws CommitException {
            }

            @Override
            public void postCommit(CommitEvent commitEvent)
                    throws CommitException {
            	Product product = fieldGroup.getItemDataSource().getBean();
            	viewLogic.saveProduct(product);
                //DataService.get().updateProduct(
                //        fieldGroup.getItemDataSource().getBean());
            }
        });

        save.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    fieldGroup.commit();

                    // only if validation succeeds
                    Product product = fieldGroup.getItemDataSource().getBean();
                    viewLogic.saveProduct(product);
                } catch (CommitException e) {
                    Notification n = new Notification(
                            "Please re-check the fields", Type.ERROR_MESSAGE);
                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                }
            }
        });

        cancel.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                viewLogic.cancelProduct();
            }
        });

        delete.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Product product = fieldGroup.getItemDataSource().getBean();
                viewLogic.deleteProduct(product);
            }
        });
    }

/*    public void setCategories(Collection<Category> categories) {
        category.setOptions(categories);
    }
*/
    public void setManufacturers(Collection<Manufacturer> manufacturers) {
    	manufacturer.setNullSelectionAllowed(false);
    	List<String> entries = new ArrayList<String>();
    	manufacturers.forEach(e -> entries.add(e.getEntry()));
    	manufacturer.addItems(entries);

    	//manufacturer.setContainerDataSource(new BeanItemContainer<Manufacturer>(Manufacturer.class, entries));
    	BeanItemContainer bic = new BeanItemContainer<Manufacturer>(Manufacturer.class, manufacturers);
    	
    	//manufacturer.setContainerDataSource(new BeanItemContainer<Manufacturer>(Manufacturer.class, manufacturers));
    	Collection propertyids = bic.getContainerPropertyIds();
    }

    public void setVendors(Collection<Vendor> vendors) {
  	Collection<String> entries = new ArrayList<String>();
  	vendors.forEach(e -> entries.add(e.getEntry()));
  	vendor.addItems(entries);
  }
    
    public void setTypes(Collection<example.test.backend.data.Type> types) {
  	Collection<String> entries = new ArrayList<String>();
  	types.forEach(e -> entries.add(e.getEntry()));
  	assetType.addItems(entries);
  }

    
    
    public void editProduct(Product product) {
    	
    	if (product == null) {
            product = new Product();
            
        }
        fieldGroup.setItemDataSource(new BeanItem<Product>(product));
        
//        mfieldGroup.setItemDataSource(new BeanItem<Manufacturer>(manufacturer));

        // before the user makes any changes, disable validation error indicator
        // of the product name field (which may be empty) *substitute serialcode maybe we don't need this field
        barCode.setValidationVisible(false);

        // Scroll to the top
        // As this is not a Panel, using JavaScript
        String scrollScript = "window.document.getElementById('" + getId()
                + "').scrollTop = 0;";
        Page.getCurrent().getJavaScript().execute(scrollScript);
    }

    private void formHasChanged() {
        // show validation errors after the user has changed something
        //productName.setValidationVisible(true);

        // only products that have been saved should be removable
        boolean canRemoveProduct = false;
        int idValue = 0;
        BeanItem<Product> item = fieldGroup.getItemDataSource();
        if (item != null) {
            Product product = item.getBean();
            idValue = product.getId();
            if (idValue <= 0)
            	canRemoveProduct = false;
        }
        delete.setEnabled(canRemoveProduct);
    }
}
