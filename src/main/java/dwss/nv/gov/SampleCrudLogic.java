package dwss.nv.gov;

import java.io.Serializable;
import java.util.List;

import com.vaadin.server.Page;

import dwss.nv.gov.backend.data.Asset;
import dwss.nv.gov.backend.data.Manufacturer;
import dwss.nv.gov.backend.data.ManufacturerRepository;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the product editor form and the data source, including
 * fetching and saving products.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class SampleCrudLogic implements Serializable {

    private SampleCrudView view;

    public SampleCrudLogic(SampleCrudView simpleCrudView) {
        view = simpleCrudView;
    }

    public void init() {
        editAsset(null);
        // Hide and disable if not admin
        if (!VaadinUI.get().getAccessControl().isUserInRole("admin")) {
            view.setNewProductEnabled(false);
        }

        view.showAssets(VaadinUI.get().getAssetRepository().findAll());
        view.showManufacturers(VaadinUI.get().getManufacturerRepository().findAll());
        view.showVendors(VaadinUI.get().getVendorRepository().findAll());
        view.showTypes(VaadinUI.get().getTypeRepository().findAll());
        //view.showBudgetCodes(VaadinUI.get().getAssetRepository().findAllBudgetCodes());
       
    }

    public void cancelProduct() {
        setFragmentParameter("");
        view.clearSelection();
        view.editAsset(null);
    }

    /**
     * Update the fragment without causing navigator to change view
     */
    private void setFragmentParameter(String productId) {
        String fragmentParameter;
        if (productId == null || productId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = productId;
        }

        Page page = VaadinUI.get().getPage();
        page.setUriFragment("!" + SampleCrudView.VIEW_NAME + "/"
                + fragmentParameter, false);
    }

    public void enter(String productId) {
        if (productId != null && !productId.isEmpty()) {
            if (productId.equals("new")) {
                newProduct();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    int pid = Integer.parseInt(productId);
                    Asset asset = findAsset(pid);
                    view.selectRow(asset);
                } catch (NumberFormatException e) {
                }
            }
        }
    }

    private Asset findAsset(int productId) {
        return VaadinUI.get().getAssetRepository().getOne(productId);
    }

    public void saveAsset(Asset asset) {
    	VaadinUI.get().getAssetRepository().save(asset);
    	view.showSaveNotification(asset.getBarCode() + " ("
                + asset.getId() + ") updated");
        view.clearSelection();
        view.editAsset(null);
        view.refreshAsset(asset);
        setFragmentParameter("");
    }

    public void deleteAsset(Asset asset) {
    	VaadinUI.get().getAssetRepository().delete(asset.getId());
        view.showSaveNotification(asset.getBarCode() + " ("
                + asset.getId() + ") removed");

        view.clearSelection();
        view.editAsset(null);
        view.removeAsset(asset);
        setFragmentParameter("");
    }

    public void editAsset(Asset asset) {
        if (asset == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(asset.getId() + "");
        }
        view.editAsset(asset);
    }

    public void newProduct() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editAsset(new Asset());
    }

    public void rowSelected(Asset asset) {
        if (VaadinUI.get().getAccessControl().isUserInRole("admin")) {
            view.editAsset(asset);
        }
    }
}
