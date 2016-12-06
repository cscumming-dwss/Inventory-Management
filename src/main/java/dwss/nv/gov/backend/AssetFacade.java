package example.test.backend;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;


import example.test.backend.data.Asset;


import example.test.backend.data.AssetRepository;

/**
 *
 * @author Ccummings
 */
@Stateless
public class AssetFacade {
    
    @Inject AssetRepository repo;

    public void save(Asset entity) {
        repo.save(entity);
    }

    public List<Asset> findByManufactuerStartsWithIgnoreCase(String value) {
        return repo.findByManufacturerStartsWithIgnoreCase(value);
    }

    public  List<Asset> findAll(Integer id) {
        return repo.findAll();
    }
       
}


