package example.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.ibm.icu.text.DateFormat;
import com.vaadin.ui.Notification;

import example.test.backend.data.Asset;

@Component
public class CsvToEntityConverter {

private File csvInFile = null;
private JpaRepository  entityRepository = null;
private Asset asset = null;
private static List<String> processingfieldNames = null;
private static List<String> fieldNames = null;
private Properties columnProps = new Properties();
private int numberColumns = 0;



public CsvToEntityConverter() {
	super();
}
	

public CsvToEntityConverter(File csvInFile, JpaRepository inRepo) {
	super();
	this.csvInFile = csvInFile;
	this.entityRepository = inRepo;
}

public CsvToEntityConverter(File csvInFile) {
	super();
	this.csvInFile = csvInFile;
}

public File getCsvInFile() {
	return csvInFile;
}

public void setCsvInFile(File csvInFile) {
	this.csvInFile = csvInFile;
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


public int processFile() throws IOException {

	processingfieldNames = new ArrayList<String>();
	
	//Create a asset so we can check the column names and introspect the fields
	asset = new Asset();

	CSVParser parser = CSVParser.parse(csvInFile, Charset.defaultCharset(), CSVFormat.EXCEL);
	
	//First get all the CSV Records
	List<CSVRecord> rList = parser.getRecords();
	Iterator<CSVRecord> it = rList.iterator();

	
	//Get the Header record (not an excel official header record just the first row)
	CSVRecord headerRecord = (CSVRecord)it.next();
	numberColumns = headerRecord.size();
	
	//Get column names
	List<String> CSVcolumnNameList = buildHeaderColumnList(headerRecord);
	
	//Get the field names for asset so we can check if column headers are correct
	fieldNames = buildEntityFieldNameList(asset);
	
	//Build a property table to use for building setter methods
	columnProps = buildColumnProperties(CSVcolumnNameList);
	
	//Now get the rest of the rows - in each iteration it is assumed that each record has the same columns
	processCSVRecords(it);

	
	

	
	return -1;
}


public List<String> buildHeaderColumnList(CSVRecord hRec) {
	int numberColumns = hRec.size();
	List<String> hRecList = new ArrayList<String>(numberColumns);
	
	//Grab the column names and put them in an Array
	for (int i = 0; i < numberColumns; i++) {
		hRecList.add(hRec.get(i));					
	}
	
	
	return hRecList;
}


public Properties buildColumnProperties(List<String> CSVPropertyNames) {
	Properties columnProps = new Properties();

	try {

	for (String string : CSVPropertyNames){
			String key = checkCSVColumnsValid(string);
			processingfieldNames.add(key);
			columnProps.setProperty(key, "");
	}
	
	} catch (NoSuchFieldException e) {
		System.out.println(e);
		Notification.show("Invalid Field name", "Check File of valid column Names" , null);
		// throw (Exception e) - should pass it on....
	}

	return columnProps;
}




public List<String> buildEntityFieldNameList(Asset p) {
	
	List<String> theFieldList = new ArrayList<String>();		
	// Get Asset and get the field names
	Field[] pfields = asset.getClass().getDeclaredFields();

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



public String checkCSVColumnsValid(String columnName) throws NoSuchFieldException {

	for (String string : fieldNames){
		if (string.equalsIgnoreCase(columnName)) {
			// add a cap to first char so setter will be built correctly
			String s1 = string.substring(0,1).toUpperCase();
			String capped = s1 + string.substring(1);
			return capped;
		} 
		
	}
	
	throw new NoSuchFieldException(columnName);
	//We didn't find the name
 
}


public void processCSVRecords(Iterator<CSVRecord> itRec) {
	
	while (itRec.hasNext()) {
		CSVRecord theRec = (CSVRecord) itRec.next();
		
		addPropertyValues(theRec);
		
		callSetters(asset, columnProps);
    	entityRepository.save(asset);
    	
    	asset = new Asset();
		}

}

public void addPropertyValues(CSVRecord theRec) {
	int numberColumns = theRec.size();
	
	for (int i = numberColumns ; i > 0; i--) {
		 String recColumnValue = theRec.get(i-1);
		 columnProps.setProperty(processingfieldNames.get(i-1), recColumnValue);
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
//		key = props.getProperty(fieldNames.get(i));;
		key = processingfieldNames.get(i);
		try {
			
			Class[] args1 = new Class[1];
			args1[0] = String.class;
			
			String methodName = new String("set" + key);
			Method method = findMatchingSetterMethod(c,methodName);

			//get all the class methods; find matching methodname and then get the param type
			//Method method = c.getMethod("set" + key, args1);
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
			default: /// default is string 
				method.invoke(p, props.getProperty(key));

				break;
			}
			
			//method.invoke(p, props.getProperty(key));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
	result = prime * result + ((csvInFile == null) ? 0 : csvInFile.hashCode());
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
	CsvToEntityConverter other = (CsvToEntityConverter) obj;
	if (csvInFile == null) {
		if (other.csvInFile != null)
			return false;
	} else if (!csvInFile.equals(other.csvInFile))
		return false;
	return true;
}

@Override
public String toString() {
	return "CsvToEntityConverter [csvInFile=" + csvInFile + "]";
}


	
}
