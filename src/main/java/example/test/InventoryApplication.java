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
			repository.save(new Product(1,"12342","295961","BRBHLC1","DELL" ));
			repository.save(new Product(2,"12341", "297001","JFDVRC1","DELL"));
			repository.save(new Product(3,"12055", "N/A","HZ67434074E966",""));
			repository.save(new Product(4,"12343", "304669","C3ZYRF1","DELL"));
			repository.save(new Product(5, "12301", "340677","5CB40402SY","HP"));
			// fetch all products
			log.info("Products found with findAll():");
			log.info("-------------------------------");
			for (Product product : repository.findAll()) {
				log.info(product.toString());
			}
			log.info("");

			// fetch an individual product by ID
			Product product = repository.findOne(Integer.valueOf(5));
			log.info("Product found with findOne(1):");
			log.info("--------------------------------");
			log.info(product.toString());
			log.info("");

			// fetch products by last name
			log.info("Product found with findByManufacturerStartsWithIgnoreCase('Tablet'):");
			log.info("--------------------------------------------");
			for (Product tablet : repository
					.findByManufacturerStartsWithIgnoreCase("DELL")) {
				log.info(tablet.toString());
			}
			log.info("");
		};
	}	
}
