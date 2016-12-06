package example.test.backend.data;

import java.util.List;

import org.apache.deltaspike.data.api.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository(forEntity = Manufacturer.class)
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

	List<Manufacturer> findByEntryStartsWithIgnoreCase(String manufacturer);

	@Override
	Manufacturer findOne(Integer id);
}
