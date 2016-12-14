package dwss.nv.gov.backend.data;

import java.util.List;

import org.apache.deltaspike.data.api.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository(forEntity = User.class)
public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByUsernameStartsWithIgnoreCase(String User);

	User findByUsername(String User);
	
	@Override
	User findOne(Integer id);
}
