package example.test.backend;

import java.io.Serializable;
import java.util.Collection;

import example.test.backend.data.Asset;
import example.test.backend.data.Category;
import example.test.backend.data.Product;
import example.test.backend.MockDataService;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public abstract class DataService implements Serializable {

    public abstract Collection<Product> getAllProducts();

    public abstract Collection<Asset> getAllAssets();

    public abstract Collection<Category> getAllCategories();

    public abstract void updateProduct(Product p);

    public abstract void updateAsset(Asset a);

    public abstract void deleteProduct(int productId);

    public abstract void deleteAsset(int assetId);

    public abstract Asset getAssetById(int assetId);

    public abstract Product getProductById(int productId);

    public static DataService get() {
        return MockDataService.getInstance();
    }

}
