package example.test.backend.data;

import java.util.List;

import org.apache.deltaspike.data.api.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository(forEntity = Product.class)
public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByManufacturerStartsWithIgnoreCase(String manufacturer);

	@Override
	Product findOne(Integer id);
}
