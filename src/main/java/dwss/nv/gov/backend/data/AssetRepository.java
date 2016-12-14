package dwss.nv.gov.backend.data;

import java.util.List;

import org.apache.deltaspike.data.api.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository(forEntity = Asset.class)
public interface AssetRepository extends JpaRepository<Asset, Integer> {

	List<Asset> findByManufacturerStartsWithIgnoreCase(String manufacturer);
	Asset findByBarCode(String barcode);

	@Override
	Asset findOne(Integer id);
}
