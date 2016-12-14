package dwss.nv.gov.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dwss.nv.gov.backend.data.User;
import dwss.nv.gov.backend.data.UserRepository;

@Service("UserDetailsServiceImpl")
@ComponentScan(basePackageClasses = UserRepository.class)
 
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repo.findByUsername(username);
		
		if (null == user) {
			throw new UsernameNotFoundException("No user present with username: " + username);
		} else {
			return (UserDetails) user;
		}	
	}

}
