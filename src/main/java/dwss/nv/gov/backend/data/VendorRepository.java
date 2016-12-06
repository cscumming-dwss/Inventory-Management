package dwss.nv.gov.backend.data;

import java.util.List;

import org.apache.deltaspike.data.api.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository(forEntity = Vendor.class)
public interface VendorRepository extends JpaRepository<Vendor, Integer> {

	List<Vendor> findByEntryStartsWithIgnoreCase(String Vendor);

	@Override
	Vendor findOne(Integer id);
}
