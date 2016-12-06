package example.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Notification;

import example.test.backend.data.AssetRepository;

public class CsvTOEntityConverterTests {

	@Autowired
	public AssetRepository pRepository = null;
	public File newFile = null;
	public CsvToEntityConverter csvConvert = null;
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.newFile = new File("C:/Users/ccummings/Downloads/ReplacementApprovedEquipment2016.csv");
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCsvToEntityConverter() {
		csvConvert = new CsvToEntityConverter();
		assertEquals(csvConvert.getCsvInFile(), null);
		assertEquals(csvConvert.getEntityRepository(), null);
	}

	@Test
	public void testCsvToEntityConverterFileJpaRepository() {
		csvConvert = new CsvToEntityConverter(newFile, pRepository);
		assertEquals(csvConvert.getCsvInFile(), newFile);
		assertEquals(csvConvert.getEntityRepository(), pRepository);
	}

	@Test
	public void testCsvToEntityConverterFile() {
		csvConvert = new CsvToEntityConverter(newFile);
		assertEquals(csvConvert.getCsvInFile(), newFile);
		assertEquals(csvConvert.getEntityRepository(), null);
	}

	@Test
	public void testProcessFile() {
		try {
			csvConvert.processFile();
		} catch (Exception e) {
		Notification.show("Unexpected File exception: Try again");
		System.out.println(e.getMessage() + " File error!");
		}

		
		

		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testIsNameInTable() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testCallSetters() {
		fail("Not yet implemented"); // TODO
	}

}
