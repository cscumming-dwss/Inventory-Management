package dwss.nv.gov;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.persistence.Id;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.RecordFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Notification;

import dwss.nv.gov.backend.data.Asset;
import dwss.nv.gov.backend.data.AssetRepository;

@Component
public class XLSXToEntityConverter {

private File xlsxInFile = null;
//private File csvInFile = null;
private JpaRepository  entityRepository = null;
private Asset asset = null;
private static List<String> processingfieldNames = null;
private static List<String> fieldNames = null;
private Properties columnProps = new Properties();
private int numberColumns = 0;



public XLSXToEntityConverter() {
	super();
}
	
public XLSXToEntityConverter(File xlsxInFile, JpaRepository<?,?> inRepo) {
	super();
	this.xlsxInFile = xlsxInFile;
	this.entityRepository = inRepo;
}

public XLSXToEntityConverter(File xlsxInFile) {
	super();
	this.xlsxInFile = xlsxInFile;
}

public File getxlsxInFile() {
	return xlsxInFile;
}

public void setxlsxInFile(File xlsxInFile) {
	this.xlsxInFile = xlsxInFile;
}

public JpaRepository getEntityRepository() {
	return entityRepository;
}

public void setEntityRepository(JpaRepository entityRepository) {
	this.entityRepository = entityRepository;
}


public Asset getAsset() {
	return asset;
}


public void setAsset(Asset asset) {
	if (asset == null){
		this.asset = new Asset();
	} else {
		this.asset = asset;
	}
}


public List<String> getFieldNames() {
	return fieldNames;
}


public void setFieldNames(List<String> fieldNameList) {
		this.fieldNames = fieldNameList;
		
	// Get Asset and get the field names
	Field[] pfields = asset.getClass().getDeclaredFields();

	List<Field> flist = Arrays.asList(pfields);
	
	int numFields = flist.size();

	Iterator<Field> fIter = flist.iterator();
	String getName = new String();
	//build string list of field names
	while (fIter.hasNext()) {
		Field theField = (Field) fIter.next();
		//System.out.println(theField.getName());
		getName = theField.getName();
//		fieldNames.add(theField.getName());
		this.fieldNames.add(getName);
	}	

}


public List<Asset> processFile() throws IOException {

	processingfieldNames = new ArrayList<String>();
	
	//CSVParser parser = CSVParser.parse(csvInFile, Charset.defaultCharset(), CSVFormat.EXCEL);
	XSSFWorkbook workBook = new XSSFWorkbook(new FileInputStream(xlsxInFile));
	
	//get sheet - add some checking later.
	Iterator<Row> it = workBook.getSheetAt(0).rowIterator();
	
	//First get all the CSV Records
//	List<CSVRecord> rList = parser.getRecords();
	//Iterator<CSVRecord> it = rList.iterator();

	
	//Get the Header record (not an excel official header record just the first row)
	XSSFRow headerRecord = (XSSFRow)it.next();
	numberColumns = headerRecord.getPhysicalNumberOfCells();
	
	//Get column names
	List<String> XSSFcolumnNameList = buildHeaderColumnList(headerRecord);
	
	//Get the field names for asset so we can check if column headers are correct
	fieldNames = buildEntityFieldNameList(new Asset());
	
	//Build a property table to use for building setter methods
	columnProps = buildColumnProperties(XSSFcolumnNameList);
	
	//Now get the rest of the rows - in each iteration it is assumed that each record has the same columns

	return 	processRows(it);
}


public List<String> buildHeaderColumnList(XSSFRow headerRecord)  {

	List<String> hRecList = new ArrayList<String>(numberColumns);
	int CELL_TYPE_DATE = 42;
	//get cell iterator
	//   at some point: for each cell, enum = getCellTypeEnum()
	//                  switch based on enum
	//for right now getRawvalue(); 
	
	Iterator<Cell> itCell = headerRecord.cellIterator();
	while (itCell.hasNext()) {
		XSSFCell theCell = (XSSFCell) itCell.next();
		//System.out.println(theField.getName());
		
		
		switch (theCell.getCellType()) { 
		
			case Cell.CELL_TYPE_STRING: 
				hRecList.add((theCell.getStringCellValue())); 
				break; 
/*			case Cell.CELL_TYPE_NUMERIC: 
				if (DateUtil.isCellDateFormatted(theCell)) {
					System.out.print(theCell.getDateCellValue() + "\t");
				} else {
					System.out.print(theCell.getNumericCellValue() + "\t");
				}
				break; 
			case Cell.CELL_TYPE_BOOLEAN: 
				System.out.print(theCell.getBooleanCellValue() + "\t"); 
				break; 
			case Cell.CELL_TYPE_BLANK: 
				System.out.print(theCell.getBooleanCellValue() + "\t"); 
				break; 
*/			default :
				throw new RecordFormatException("Invalid Type for Column Name in XLSX Document, must be STRING type " + theCell.getCellType());
		}
	}
	
	return hRecList;
}


public Properties buildColumnProperties(List<String> XSSFPropertyNames) {
	Properties columnProps = new Properties();
	String key = new String();

	try {

	for (String string : XSSFPropertyNames){
			key = checkXSSFColumnsValid(string);
			processingfieldNames.add(key);
			columnProps.setProperty(key, "");
	}
	
	} catch (NoSuchFieldException e) {
		System.out.println(e);
		Notification.show("Invalid Field name", "Check Documenation for valid column Names" + " " + key , null);
		// throw (Exception e) - should pass it on....
	}

	return columnProps;
}




public List<String> buildEntityFieldNameList(Asset p) {
	
	List<String> theFieldList = new ArrayList<String>();		
	// Get Asset and get the field names
	Field[] pfields = p.getClass().getDeclaredFields();

	List<Field> flist = Arrays.asList(pfields);
			
	Iterator<Field> fIter = flist.iterator();
	String getName = new String();

	//build string list of field names
	while (fIter.hasNext()) {
		Field theField = (Field) fIter.next();
		//System.out.println(theField.getName());
		getName = theField.getName();
//		fieldNames.add(theField.getName());
		theFieldList.add(getName);
	}	
			
	return theFieldList;
}



public String checkXSSFColumnsValid(String columnName) throws NoSuchFieldException {

	for (String string : fieldNames){
		if (string.equalsIgnoreCase(columnName)) {
			// add a cap to first char so setter will be built correctly
			String s1 = string.substring(0,1).toUpperCase();
			String capped = s1 + string.substring(1);
			return capped;
		} 
		
	}
	
	throw new NoSuchFieldException(columnName + " is invalid. Valid Names: " + fieldNames);
	//We didn't find the name
 
}


public List<Asset> processRows(Iterator<Row> it) {
	List<Asset> assetList = new ArrayList<Asset>();
	String inID = null;
	Integer inInt = 0;
	
	
	while (it.hasNext()) {
		XSSFRow theRow = (XSSFRow) it.next();
		addPropertyValues(theRow);
		
		if (columnProps.containsKey("Id")) {
		
			inID = columnProps.getProperty("Id");
			if (inID != null || !inID.equals("")) {
				//see if it is in the database
				inInt = tryParse(inID);
				if (inInt != null)
					asset = ((AssetRepository) entityRepository).findOne(inInt.intValue());
				
				if (asset == null)
					asset = new Asset();
			}	
			
		} else {
			asset = new Asset();
		}
		
		callSetters(asset, columnProps);

		assetList.add(asset);
	}

	return assetList;
}

public static Integer tryParse(String text) {
	  try {
	    return Integer.parseInt(text);
	  } catch (NumberFormatException e) {
	    return null;
	  }
	}


public void addPropertyValues(XSSFRow inRow) {
	
	Iterator<Cell> itCell = inRow.cellIterator();
	
	//Formatters for dates and numbers so they can be presented as string
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	DataFormatter fmt = new DataFormatter();

	int i  = 0;
	
	while (itCell.hasNext()) {
		XSSFCell theCell = (XSSFCell) itCell.next();
		
		
		switch (theCell.getCellType()) { 
		
			case Cell.CELL_TYPE_STRING: 
				columnProps.setProperty(processingfieldNames.get(i),theCell.getStringCellValue()); 
				break; 
			case Cell.CELL_TYPE_NUMERIC: 
				if (DateUtil.isCellDateFormatted(theCell)) {
					columnProps.setProperty(processingfieldNames.get(i),df.format(theCell.getDateCellValue()));
				} else {
					columnProps.setProperty(processingfieldNames.get(i),fmt.formatCellValue(theCell));
				}
				break; 
			default :
				columnProps.setProperty(processingfieldNames.get(i),fmt.formatCellValue(theCell)); 
				break; 
		}
		i++;
	}

	
}


/*
 * isNameInTable
 * @param String inName
 * @returns boolean
 * 
 * method to compare in coming string to Entity (Table) field name list items ignoring case
 * might want to return internal name and its caseSensitivites so that method construction goes ok
 * 
 */
public boolean isNameInTable(String inName) {
	//yeah I know, bad.
	this.setFieldNames(new ArrayList<String>());
	
	for (String string : fieldNames){
		if (string.equalsIgnoreCase(inName)) {
			return true;
		}
	}
	
	return false;
}


/*
 * callSetters() using reflection and the property table constructs the 
 */
public static void callSetters(Asset p, Properties props) {
//public static void callSetters() {
	String key = new String();
	Class c = p.getClass();
	
	for (int i= props.size()-1; i >= 0 ; i--) {
		key = processingfieldNames.get(i);
		
		if (key.equals("id"))
			continue;
		else {
		try {
			
			Class[] args1 = new Class[1];
			args1[0] = String.class;
			
			String methodName = new String("set" + key);
			Method method = findMatchingSetterMethod(c,methodName);

			//get all the class methods; find matching methodname and then get the param type
			Class parms[] = method.getParameterTypes();
			
			//Just do the ones we need for now - should do all base types
			switch (parms[0].getName()) {
			
			case "java.math.BigDecimal":
				method.invoke(p,new BigDecimal(props.getProperty(key)));
				break;
			case "java.lang.Boolean":
				method.invoke(p, new Boolean(props.getProperty(key)));
				break;
			case "java.util.Date" : 
				// could use DateFormat but for now will use the deprecated one its easy
				//DateFormat df = DateFormat.getInstance();
				//Date tempDate = df.parse(props.getProperty(key));
				//method.invoke(p, tempDate);
				method.invoke(p, new Date(props.getProperty(key)));
				break;
			case "java.lang.Integer" :
				method.invoke(p, new Integer(props.getProperty(key)));
				break;
			case "java.lang.String" :
				method.invoke(p, props.getProperty(key));
				break;
			case "int" :
				int tempInt = Integer.parseInt(props.getProperty(key));
				method.invoke(p, tempInt);
				break;
			default: /// default is string 
				method.invoke(p, props.getProperty(key));

				break;
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		}
	}
	
}


public static Method findMatchingSetterMethod(Class c, String methodName) {
		
	Method[] methods = c.getMethods();
	
	for (Method method : methods){
		if (method.getName().equals(methodName)) {
			return method;
		}
	}
	
	return null;
}


@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((xlsxInFile == null) ? 0 : xlsxInFile.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	XLSXToEntityConverter other = (XLSXToEntityConverter) obj;
	if (xlsxInFile == null) {
		if (other.xlsxInFile != null)
			return false;
	} else if (!xlsxInFile.equals(other.xlsxInFile))
		return false;
	return true;
}

@Override
public String toString() {
	return "XLSXToEntityConverter [xlsxInFile=" + xlsxInFile + "]";
}


	
}
