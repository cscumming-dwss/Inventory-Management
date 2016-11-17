package example.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public class CsvToEntityConverter {

private File csvInFile = null;
private JpaRepository  entityRepository = null;


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


public int processFile() throws IOException {
	CSVParser parser = CSVParser.parse(csvInFile, Charset.defaultCharset(), CSVFormat.EXCEL);
	Map hMap = parser.getHeaderMap();
	
/*	StringBuilder sb = new StringBuilder();
	
	Iterator it = hMap.values().iterator();
	
	while (it.hasNext()) {
		System.out.println(it.next().toString());
	}
*/	
	List<CSVRecord> rList = parser.getRecords();
	
	Iterator it = rList.iterator();

	//Jump over column names for now
	//but should save the names and position order and then use that when iterating over the records to set the values for fields
	//and at the end save the record
	
	//ie  for each record  
	// Product newP = new Product();
	// newP.setBarCode(theRec.get(--positionOFBarCode Column);
	// similar to edit form logic
	
	CSVRecord headerRecord = (CSVRecord)it.next();
	int numberColumns = headerRecord.size();
	
	while (it.hasNext()) {
		CSVRecord theRec = (CSVRecord) it.next();
		for (int i = numberColumns; i > 0; i--)
			System.out.println(theRec.get(i));
			//
	}
	

	return -1;
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
