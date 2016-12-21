package dwss.nv.gov;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.vaadin.addons.ExportExcelComponentConfiguration;
import org.vaadin.addons.ExportExcelConfiguration;
import org.vaadin.addons.ExportExcelSheetConfiguration;
import org.vaadin.addons.ExportToExcelUtility;
import org.vaadin.addons.ExportType;
import org.vaadin.addons.builder.ExportExcelComponentConfigurationBuilder;
import org.vaadin.addons.builder.ExportExcelConfigurationBuilder;
import org.vaadin.addons.builder.ExportExcelSheetConfigurationBuilder;
import org.vaadin.addons.formatter.BooleanColumnFormatter;
import org.vaadin.addons.formatter.ColumnFormatter;
import org.vaadin.addons.formatter.PrefixColumnFormatter;
import org.vaadin.addons.formatter.SuffixColumnFormatter;
import org.vaadin.easyuploads.UploadField;
import org.vaadin.easyuploads.UploadField.FieldType;
import org.vaadin.easyuploads.UploadField.StorageMode;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
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
import com.vaadin.ui.Grid.SelectionModel;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import dwss.nv.gov.backend.data.Asset;
import dwss.nv.gov.backend.data.Manufacturer;

/**
 * A view for performing create-read-update-delete operations on products.
 *
 * See also {@link AssetCrudLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
public class AssetCrudView extends CssLayout implements View {

    public static final String VIEW_NAME = "Inventory";
    private VaadinUI ui;
    private AssetGrid grid;
    private AssetForm form;

    private AssetCrudLogic viewLogic = new AssetCrudLogic(this);
    private Button addAsset;
    private Button exportGrid;
    private UploadField uplDoc;    

    public AssetCrudView(VaadinUI ui) {
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

    	exportGrid = new Button("Export");
    	exportGrid.setResponsive(true);
        exportGrid.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        exportGrid.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	exportGrid();
            }
        });
        
        uplDoc = new UploadField(StorageMode.FILE);
        uplDoc.setFieldType(FieldType.FILE);
        uplDoc.setFileDeletesAllowed(false);
        uplDoc.setButtonCaption("Import");
        uplDoc.addStyleName(ValoTheme.BUTTON_QUIET);
        
        uplDoc.addValueChangeListener(new Property.ValueChangeListener() {
        	@Override
        	public void valueChange(Property.ValueChangeEvent event) {
        		importToGrid();
        	}
        });
        

        
        addAsset = new Button("New Asset");
        addAsset.addStyleName(ValoTheme.BUTTON_PRIMARY);
        addAsset.setIcon(FontAwesome.PLUS_CIRCLE);
        addAsset.setResponsive(true);
        addAsset.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                viewLogic.newProduct();
            }
        });

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setSpacing(true);
        topLayout.setWidth("50%");
        topLayout.addComponent(addAsset);
        topLayout.addComponent(exportGrid);
        topLayout.addComponent(uplDoc);
        topLayout.setComponentAlignment(addAsset, Alignment.MIDDLE_RIGHT);
        topLayout.setComponentAlignment(exportGrid, Alignment.MIDDLE_RIGHT);
        topLayout.setComponentAlignment(uplDoc, Alignment.MIDDLE_RIGHT);
        topLayout.setExpandRatio(addAsset,1);
        topLayout.setExpandRatio(exportGrid,2);
        topLayout.setExpandRatio(uplDoc, 3);
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
    	addAsset.setEnabled(enabled);
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

    
    public void refreshAsset(List<Asset> assets) {
    	Iterator<Asset> it = assets.iterator();
    	Asset iAsset = null;
    	boolean select = true;
    	
    	while (it.hasNext()){
    		iAsset = (Asset)it.next();
    	    grid.refresh(iAsset);
    	}
    	if (iAsset != null)
    		if (select) {
    			grid.scrollTo(iAsset);
//    			selectRow(iAsset);
    			select = false;
    		}

    }

    public void removeAsset(Asset asset) {
        grid.remove(asset);
    }

    
    public void exportGrid() {
        /* Configuring Components */
    	
        XSSFCellStyle hcellStyle = new  XSSFCellStyle(new StylesTable());
        hcellStyle.setAlignment(HorizontalAlignment.CENTER);
        hcellStyle.setFillForegroundColor(new XSSFColor(new Color(228, 234, 238)));
        hcellStyle.setFillPattern(XSSFCellStyle.NO_FILL);


        ExportExcelComponentConfiguration componentConfig1 = 
        		new ExportExcelComponentConfigurationBuilder().withGrid(grid) //Your Table or component goes here
                                                              .withVisibleProperties(grid.getVisibleColumns())
                                                           //   .withTableContentStyle(columnCellStyle)
                                                           //   .withTableHeaderStyle(hcellStyle)
                                                              .withColumnHeaderKeys(grid.getHeaderColumns())
                                                              .withBooleanFormattingProperties(new ArrayList<String>(Arrays.asList("computerRelated")))
                                                              .withIntegerFormattingProperties(new ArrayList<String>(Arrays.asList("cost","heatTicket")))
                                                              .withDateFormattingProperties(new ArrayList<String>(Arrays.asList("dateEntered",
                                                            		  															"dateReceived",
                                                            		  															"verifiedDate",
                                                            		  															"repApproved",
                                                            		  															"inventoryDate")))
                                                              .build();
        
        /* Configuring Sheets */
        ArrayList<ExportExcelComponentConfiguration> componentList1 = new ArrayList<ExportExcelComponentConfiguration>();
        componentList1.add(componentConfig1);

        
        ExportExcelSheetConfigurationBuilder builder = new ExportExcelSheetConfigurationBuilder();
        builder.withSheetName("DWSS IT Assets");
        builder.withComponentConfigs(componentList1);
        builder.withIsHeaderSectionAdded(Boolean.FALSE);
        builder.withIsHeaderSectionRequired(Boolean.FALSE);
        builder.withDateFormat("MM-dd-yyyy");
        builder.withHeaderValueStyle(hcellStyle);
        builder.withIsDefaultSheetTitleRequired(false);
        builder.withIsDefaultGeneratedByRequired(false);
        
        ExportExcelSheetConfiguration sheetConfig1 = builder.build();
        sheetConfig1.setReportTitle("");
        UI.getCurrent().setLocale(Locale.US);
//        sheetConfig1.setDateFormat(" %1$tm/%1$td/%1$ty");
        sheetConfig1.setDateFormat("MM/dd/yyyy");
        /* Configuring Excel */
        ArrayList<ExportExcelSheetConfiguration> sheetList = new ArrayList<ExportExcelSheetConfiguration>();
        sheetList.add(sheetConfig1);
        ExportExcelConfiguration config1 = new ExportExcelConfigurationBuilder().withSheetConfigs(sheetList)
                .build();

        ExportToExcelUtility<Asset> exportToExcelUtility = new ExportToExcelUtility<Asset>(UI.getCurrent(), config1, Asset.class);
        exportToExcelUtility.setSourceUI(UI.getCurrent());
        exportToExcelUtility.setResultantExportType(ExportType.XLSX);

        exportToExcelUtility.export();    	
    }
    
    public void importToGrid() {
		showSaveNotification("File Uploaded " + uplDoc.getValue());
		File newfile = (File)uplDoc.getValue();
		XLSXToEntityConverter xlsxConvert = new XLSXToEntityConverter(newfile, ui.getAssetRepository());
		Container.Indexed ci = grid.getContainerDataSource();
		
		try {
			List<Asset> assetList = xlsxConvert.processFile();
			VaadinUI.get().getAssetRepository().save(assetList);
			grid.clearSortOrder();
			grid.getFilter().clearAllFilters();
			
			for (Asset asset : assetList){
				if (ci.getItem(asset)== null) {
					ci.addItem(asset);
				} else {
					ci.removeItem(asset);
					ci.addItem(asset);
				}
			}        				
    		grid.scrollToEnd();
			
		} catch (Exception e) {
			showError(e.getMessage());
			System.out.println(e.getMessage() + " Error processing Import! " + e);
			
		}
		uplDoc.clear();
		uplDoc.requestRepaint();
	}
 
    public HashMap buildColumnFormaters() {
	    HashMap<Object, ColumnFormatter> columnFormatters = new HashMap<>();
	
	    // Suffix Formatter provided
	    columnFormatters.put("cost", new PrefixColumnFormatter("$"));
	
	    // Boolean Formatter provided
	    columnFormatters.put("active", new BooleanColumnFormatter("Yes", "No"));
	
	    // Custom Formatting also possible
	    columnFormatters.put("catalogue", new ColumnFormatter() {
	
	        @Override
	        public Object generateCell(final Object value, final Object itemId, final Object columnId) {
	            return (value != null) ? ((String) value).toLowerCase() : null;
	        }
	    });
	    
        return columnFormatters;
    }
}
