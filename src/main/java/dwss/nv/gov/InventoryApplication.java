package dwss.nv.gov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import dwss.nv.gov.backend.data.Asset;
import dwss.nv.gov.backend.data.AssetRepository;


@SpringBootApplication
public class InventoryApplication {

	private static final Logger log = LoggerFactory.getLogger(InventoryApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

/*	@Bean
	public CommandLineRunner loadData(AssetRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Asset(1,"12342","295961","BRBHLC1","DELL" ));
			repository.save(new Asset(2,"12341", "297001","JFDVRC1","DELL"));
			repository.save(new Asset(3,"12055", "N/A","HZ67434074E966",""));
			repository.save(new Asset(4,"12343", "304669","C3ZYRF1","DELL"));
			repository.save(new Asset(5, "12301", "340677","5CB40402SY","HP"));
			// fetch all products
			log.info("Products found with findAll():");
			log.info("-------------------------------");
			for (Asset product : repository.findAll()) {
				log.info(product.toString());
			}
			log.info("");

			// fetch an individual product by ID
			Asset product = repository.findOne(Integer.valueOf(5));
			log.info("Asset found with findOne(1):");
			log.info("--------------------------------");
			log.info(product.toString());
			log.info("");

			// fetch products by last name
			log.info("Asset found with findByManufacturerStartsWithIgnoreCase('Tablet'):");
			log.info("--------------------------------------------");
			for (Asset tablet : repository
					.findByManufacturerStartsWithIgnoreCase("DELL")) {
				log.info(tablet.toString());
			}
			log.info("");
		};
	}*/	
}
