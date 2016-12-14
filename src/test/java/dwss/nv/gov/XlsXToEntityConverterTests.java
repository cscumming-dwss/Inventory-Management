package dwss.nv.gov;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vaadin.ui.Notification;

import dwss.nv.gov.XLSXToEntityConverter;
import dwss.nv.gov.backend.data.AssetRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
/*
 /   NEED to Add a mock data service and data generator to make repository go
  *   or you need to add applications property file so it connects. 
 */

public class XlsXToEntityConverterTests {

	@Autowired
	public AssetRepository pRepository = null;
	public File newFile = null;
	public XLSXToEntityConverter xlsxConvert = null;
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.newFile = new File("C:/Users/ccummings/Downloads/ReplacementApprovedEquipment2016-test.xlsx");
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testxlsxToEntityConverter() {
		xlsxConvert = new XLSXToEntityConverter();
		assertEquals(xlsxConvert.getxlsxInFile(), null);
		assertEquals(xlsxConvert.getEntityRepository(), null);
	}

	@Test
	public void testxlsxToEntityConverterFile() {
		xlsxConvert = new XLSXToEntityConverter(newFile);
		assertEquals(xlsxConvert.getxlsxInFile(), newFile);
		assertEquals(xlsxConvert.getEntityRepository(), null);
	}
	@Test
	public void testxlsxToEntityConverterFileJpaRepository() {
		xlsxConvert = new XLSXToEntityConverter(newFile, pRepository);
		assertEquals(xlsxConvert.getxlsxInFile(), newFile);
		assertEquals(xlsxConvert.getEntityRepository(), pRepository);
	}


	@Test
	public void testProcessFile() {
		try {
			xlsxConvert = new XLSXToEntityConverter(newFile);
			xlsxConvert.processFile();
		} catch (Exception e) {
		System.out.println("Unexpected File exception: Try again" + " " + e.toString());
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
