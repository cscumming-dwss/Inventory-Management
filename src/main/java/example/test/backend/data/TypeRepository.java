package example.test.backend.data;

import java.util.List;

import org.apache.deltaspike.data.api.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository(forEntity = Type.class)
public interface TypeRepository extends JpaRepository<Type, Integer> {

	List<Type> findByEntryStartsWithIgnoreCase(String Type);

	@Override
	Type findOne(Integer id);
}
