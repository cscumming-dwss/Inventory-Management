package dwss.nv.gov;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.vaadin.addons.ExportExcelComponentConfiguration;
import org.vaadin.addons.ExportExcelConfiguration;
import org.vaadin.addons.ExportExcelSheetConfiguration;
import org.vaadin.addons.ExportToExcelUtility;
import org.vaadin.addons.ExportType;
import org.vaadin.addons.builder.ExportExcelComponentConfigurationBuilder;
import org.vaadin.addons.builder.ExportExcelConfigurationBuilder;
import org.vaadin.addons.builder.ExportExcelSheetConfigurationBuilder;
import org.vaadin.easyuploads.UploadField;
import org.vaadin.easyuploads.UploadField.FieldType;
import org.vaadin.easyuploads.UploadField.StorageMode;
import org.vaadin.resetbuttonfortextfield.ResetButtonForTextField;

import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionModel;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import dwss.nv.gov.backend.data.Asset;
import dwss.nv.gov.backend.data.Manufacturer;

/**
 * A view for performing create-read-update-delete operations on products.
 *
 * See also {@link SampleCrudLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
public class SampleCrudView extends CssLayout implements View {

    public static final String VIEW_NAME = "Inventory";
    private VaadinUI ui;
    private AssetGrid grid;
    private AssetForm form;

    private SampleCrudLogic viewLogic = new SampleCrudLogic(this);
    private Button newProduct;
    private Button exportGrid;
    private UploadField uplDoc;    

    public SampleCrudView(VaadinUI ui) {
    	this.ui = ui;
        setSizeFull();
        addStyleName("crud-view");

        grid = new AssetGrid(ui);
        grid.setColumnReorderingAllowed(true);
        List<Grid.Column> colList = grid.getColumns();
        for (Grid.Column tempCol : colList) {
        	tempCol.setHidable(true);
        }
        
        grid.addSelectionListener(new SelectionListener() {

            @Override
            public void select(SelectionEvent event) {
                viewLogic.rowSelected(grid.getSelectedRow());
            }
        });

        HorizontalLayout topLayout = createTopBar();
        
        form = new AssetForm(viewLogic);
        // to be implemented getting offices codes from database
        //form.setCategories(DataService.get().getAllCategories());

        VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.addComponent(topLayout);
        barAndGridLayout.addComponent(grid);
        barAndGridLayout.setMargin(true);
        barAndGridLayout.setSpacing(true);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.setExpandRatio(grid, 1);
        barAndGridLayout.setStyleName("crud-main-layout");

        addComponent(barAndGridLayout);
        addComponent(form);

        viewLogic.init();
    }

    public HorizontalLayout createTopBar() {
        TextField filter = new TextField();
        filter.setStyleName("filter-textfield");
        filter.setInputPrompt("Filter");
        ResetButtonForTextField.extend(filter);
        filter.setImmediate(true);
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                grid.setFilter(event.getText());
            }
        });

        exportGrid = new Button("Export");
        exportGrid.addStyleName(ValoTheme.BUTTON_DANGER);
        exportGrid.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                /* Configuring Components */
                Collection c = grid.getContainerDataSource().getContainerPropertyIds();
                ExportExcelComponentConfiguration componentConfig1 = 
                		new ExportExcelComponentConfigurationBuilder().withGrid(grid) //Your Table or component goes here
                                                                      .withVisibleProperties(grid.getVisibleColumns())
                                                                      .withColumnHeaderKeys(grid.getHeaderColumns())
                                                                      .withBooleanFormattingProperties(new ArrayList<String>(Arrays.asList("computerRelated")))
                                                                      .withIntegerFormattingProperties(new ArrayList<String>(Arrays.asList("cost","heatTicket")))
                                                                      .withDateFormattingProperties(new ArrayList<String>(Arrays.asList("dateEntered",
                                                                    		  															"dateReceived",
                                                                    		  															"verifiedDate",
                                                                    		  															"repApproved",
                                                                    		  															"inventoryDate")))
//                                                                      .withColumnFormatters(new ArrayList<String>(Arrays.asList(a)))
//                                                                    .withColumnHeaderKeys(this.grid.getColumnHeaders()) 
                                                                      .build();
                /* Configuring Sheets */
                ArrayList<ExportExcelComponentConfiguration> componentList1 = new ArrayList<ExportExcelComponentConfiguration>();
                componentList1.add(componentConfig1);

                ExportExcelSheetConfiguration sheetConfig1 = new ExportExcelSheetConfigurationBuilder().withSheetName("DWSS IT Assets")
                                                                                                       .withComponentConfigs(componentList1)
                                                                                                       .withIsHeaderSectionRequired(Boolean.FALSE)
                                                                                                       .build();

                ui.getCurrent().setLocale(Locale.US);
//                sheetConfig1.setDateFormat(" %1$tm/%1$td/%1$ty");
                sheetConfig1.setDateFormat("mm/dd/yyyy");
                /* Configuring Excel */
                ArrayList<ExportExcelSheetConfiguration> sheetList = new ArrayList<ExportExcelSheetConfiguration>();
                sheetList.add(sheetConfig1);

                ExportExcelConfiguration config1 = new ExportExcelConfigurationBuilder().withGeneratedBy("DWSS Inventory ")
                                                                                        .withSheetConfigs(sheetList)
                                                                                        .build();

                ExportToExcelUtility<Asset> exportToExcelUtility = new ExportToExcelUtility<Asset>(ui.getCurrent(), config1, Asset.class);
                exportToExcelUtility.setSourceUI(ui.getCurrent());
                exportToExcelUtility.setResultantExportType(ExportType.XLSX);
                exportToExcelUtility.export();
            }
        });
        
        uplDoc = new UploadField(StorageMode.FILE);
        uplDoc.setFieldType(FieldType.FILE);
        
        uplDoc.setButtonCaption("Import");
        uplDoc.addStyleName(ValoTheme.BUTTON_QUIET);
        
        uplDoc.addValueChangeListener(new Property.ValueChangeListener() {
        	@Override
        	public void valueChange(Property.ValueChangeEvent event) {
        		Notification.show("File Uplaoaded" + uplDoc.getValue() + ' ' + uplDoc.isEmpty());
        		File newfile = (File)uplDoc.getValue();
        		CsvToEntityConverter csvConvert = new CsvToEntityConverter(newfile, ui.getAssetRepository());
        		
        		try {
        			csvConvert.processFile();
        		} catch (Exception e) {
        		Notification.show("Unexpected File exception: Try again");
        		System.out.println(e.getMessage() + " File error!");
        		}
        	}
        });
        

        
        newProduct = new Button("New Asset");
        newProduct.addStyleName(ValoTheme.BUTTON_PRIMARY);
        newProduct.setIcon(FontAwesome.PLUS_CIRCLE);
        newProduct.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                viewLogic.newProduct();
            }
        });

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setSpacing(true);
        topLayout.setWidth("100%");
        topLayout.addComponent(filter);
        topLayout.addComponent(newProduct);
        topLayout.addComponent(exportGrid);
        topLayout.addComponent(uplDoc);
        topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
        topLayout.setExpandRatio(filter, 1);
        topLayout.setStyleName("top-bar");
        return topLayout;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        viewLogic.enter(event.getParameters());
    }

    public void showError(String msg) {
        Notification.show(msg, Type.ERROR_MESSAGE);
    }

    public void showSaveNotification(String msg) {
        Notification.show(msg, Type.TRAY_NOTIFICATION);
    }

    public void setNewProductEnabled(boolean enabled) {
        newProduct.setEnabled(enabled);
    }

    public void clearSelection() {
        grid.getSelectionModel().reset();
    }

    public void selectRow(Asset row) {
        ((SelectionModel.Single) grid.getSelectionModel()).select(row);
    }

    public Asset getSelectedRow() {
        return grid.getSelectedRow();
    }

    public void editAsset(Asset asset) {
        if (asset != null) {
            form.addStyleName("visible");
            form.setEnabled(true);
        } else {
            form.removeStyleName("visible");
            form.setEnabled(false);
        }
        form.editAsset(asset);
    }

    public void showAssets(Collection<Asset> assets) {
        grid.setAssets(assets);
    }

    public void showManufacturers(Collection<Manufacturer> manufacturers) {
        form.setManufacturers(manufacturers);
    }
    public void showVendors(Collection<dwss.nv.gov.backend.data.Vendor> vendors) {
        form.setVendors(vendors);
    }
    public void showTypes(Collection<dwss.nv.gov.backend.data.Type> types) {
        form.setTypes(types);
    }


    
    public void refreshAsset(Asset asset) {
        grid.refresh(asset);
        grid.scrollTo(asset);
    }

    public void removeAsset(Asset asset) {
        grid.remove(asset);
    }

}
