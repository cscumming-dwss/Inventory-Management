package dwss.nv.gov.backend;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import dwss.nv.gov.backend.data.Asset;
import dwss.nv.gov.backend.data.AssetRepository;

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


