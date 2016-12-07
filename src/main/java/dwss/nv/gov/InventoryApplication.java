package dwss.nv.gov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)

public class InventoryApplication {

	private static final Logger log = LoggerFactory.getLogger(InventoryApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

}
