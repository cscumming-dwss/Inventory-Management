package example.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import example.test.backend.data.Product;
import example.test.backend.data.ProductRepository;


@SpringBootApplication
public class InventoryApplication {

	private static final Logger log = LoggerFactory.getLogger(InventoryApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(ProductRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Product(1,"PC", "1"));
			repository.save(new Product(2,"DSU-CSE", "3"));
			repository.save(new Product(3,"PRINTER", "30"));
			repository.save(new Product(4,"PRINTER", "87"));
			repository.save(new Product(5, "PRINTER", "120"));
			// fetch all products
			log.info("Products found with findAll():");
			log.info("-------------------------------");
			for (Product product : repository.findAll()) {
				log.info(product.toString());
			}
			log.info("");

			// fetch an individual product by ID
			Product product = repository.findOne(Integer.valueOf(1));
			log.info("Product found with findOne(1):");
			log.info("--------------------------------");
			log.info(product.toString());
			log.info("");

			// fetch products by last name
			log.info("Product found with findByLastNameStartsWithIgnoreCase('Bauer'):");
			log.info("--------------------------------------------");
			for (Product tablet : repository
					.findByProductNameStartsWithIgnoreCase("Tablet")) {
				log.info(tablet.toString());
			}
			log.info("");
		};
	}	
}
