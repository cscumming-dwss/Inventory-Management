package example.test.backend;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;


import example.test.backend.data.Product;


import example.test.backend.data.ProductRepository;

/**
 *
 * @author Ccummings
 */
@Stateless
public class ProductFacade {
    
    @Inject ProductRepository repo;

    public void save(Product entity) {
        repo.save(entity);
    }

    public List<Product> findByProductNameStartsWithIgnoreCase(String value) {
        return repo.findByProductNameStartsWithIgnoreCase(value);
    }

    public  List<Product> findAll(Integer id) {
        return repo.findAll();
    }
       
}


