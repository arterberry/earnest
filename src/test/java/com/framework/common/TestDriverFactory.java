package com.framework.common;

import java.io.File;
import java.util.TreeMap;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Map.Entry;

import com.framework.common.PropertiesUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * TestDriverFactory class loads data resources in sharedData.csv or sharedData.xls. 
 *
 */
public class TestDriverFactory {

	final static int RUN_STATUS = 0;
	final static int TEST_TYPE = 1;
	final static int ENVIRONMENT = 2;
	final static int USERNAME = 3;
	final static int PASSWORD = 4;
	final static int VERTICAL = 5;
	final static int ACCOUNT_TYPE = 6;

	final static String SHARED_TEST_DATA_FILE = "src/test/resources/sharedData.xls";

	public static TestDriver createTestDriver(String env) {
		PropertiesUtil propertiesUtil = new PropertiesUtil();
		TestDriver testDriver = new TestDriver();
		testDriver.setTestUrl((String) propertiesUtil.getProperty(env + "Url"));
		return testDriver;
	}

	public static TestDriver createTestDriver() {
		return createTestDriver(System.getProperty("env"));
	}

	public static String[][] getTableArray(String xlFilePath, String sheetName,
			String tableName) throws Exception {
		String[][] tabArray = null;
		Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
		Sheet sheet = workbook.getSheet(sheetName);
		int startRow, startCol, endRow, endCol, ci, cj;
		Cell tableStart = sheet.findCell(tableName);
		startRow = tableStart.getRow();
		startCol = tableStart.getColumn();

		Cell tableEnd = sheet.findCell(tableName, startCol + 1, startRow + 1,
				100, 64000, false);
		endRow = tableEnd.getRow();
		endCol = tableEnd.getColumn();
		tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
		ci = 0;

		for (int i = startRow + 1; i < endRow; i++, ci++) {
			cj = 0;
			for (int j = startCol + 1; j < endCol; j++, cj++) {
				tabArray[ci][cj] = sheet.getCell(j, i).getContents();
			}
		}
		return (tabArray);
	}

	private static boolean isTestRunStatusYes(HSSFRow myRow){
		return myRow.getCell(RUN_STATUS).toString().equals("yes");
	}

	private static boolean isPositiveTest(HSSFRow myRow){
		return myRow.getCell(TEST_TYPE).toString().equals("+");
	}

	private static boolean isNegativeTest(HSSFRow myRow){
		return myRow.getCell(TEST_TYPE).toString().equals("-");
	}

	private static boolean isTestEnv(HSSFRow myRow, String env){
		return myRow.getCell(ENVIRONMENT).toString().equals(env);
	}

	private static Iterator<Row> getSharedTestDataFileIterator(){
		try{
			FileInputStream inp = new FileInputStream(SHARED_TEST_DATA_FILE);
			HSSFWorkbook myWorkBook = new HSSFWorkbook(new POIFSFileSystem(inp));
			HSSFSheet mySheet = myWorkBook.getSheetAt(0);

			return mySheet.rowIterator();
		
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Unable to open the Shared Test Data File.");
		}
		return null;
	}

	public static TreeMap<String, Vector<String>> getPositiveTestDataByVertical(String env) throws Exception{
		TreeMap<String, Vector<String>> testData = new TreeMap<String, Vector<String>>();
		Iterator<Row> rowIter = getSharedTestDataFileIterator();

		try{
			int numberOfDataPoints = 0;
			while(rowIter.hasNext()){
				HSSFRow myRow = (HSSFRow) rowIter.next();
				numberOfDataPoints = myRow.getLastCellNum();
				Iterator<org.apache.poi.ss.usermodel.Cell> cellIter = myRow.cellIterator();
				Vector<HSSFCell> cellStoreVector = new Vector<HSSFCell>(numberOfDataPoints);
				Vector<String> cellDataVector = new Vector<String>(numberOfDataPoints);
				if(isTestRunStatusYes(myRow) && isPositiveTest(myRow) && (isTestEnv(myRow, env) || isTestEnv(myRow, "*"))){
					while(cellIter.hasNext()){
						HSSFCell myCell = (HSSFCell) cellIter.next();
						cellStoreVector.addElement(myCell);
						cellDataVector.addElement(myCell.toString());
					}					
					cellDataVector.removeElementAt(RUN_STATUS);
					testData.put(cellStoreVector.get(VERTICAL).toString(), cellDataVector);
					cellStoreVector.clear();
				}
			}
		}catch (Exception e){
			e.printStackTrace(); 
			return null;
		}
		return testData;
	}
	
	public static TreeMap<String, Vector<String>> getPositiveTestDataByUsername(String env) throws Exception{
		TreeMap<String, Vector<String>> testData = new TreeMap<String, Vector<String>>();
		Iterator<Row> rowIter = getSharedTestDataFileIterator();
		
		try{
			int numberOfDataPoints = 0;
			while(rowIter.hasNext()){
				HSSFRow myRow = (HSSFRow) rowIter.next();
				numberOfDataPoints = myRow.getLastCellNum();
				Iterator<org.apache.poi.ss.usermodel.Cell> cellIter = myRow.cellIterator();
				Vector<HSSFCell> cellStoreVector = new Vector<HSSFCell>(numberOfDataPoints);
				Vector<String> cellDataVector = new Vector<String>(numberOfDataPoints);
				if(isTestRunStatusYes(myRow) && isPositiveTest(myRow) && (isTestEnv(myRow, env) || isTestEnv(myRow, "*"))){
					while(cellIter.hasNext()){
						HSSFCell myCell = (HSSFCell) cellIter.next();
						cellStoreVector.addElement(myCell);
						cellDataVector.addElement(myCell.toString());					
					}					
					cellDataVector.removeElementAt(RUN_STATUS);
					testData.put(cellStoreVector.get(USERNAME).toString(), cellDataVector);
					cellStoreVector.clear();
				}
			}
		}catch (Exception e){
			e.printStackTrace(); 
			return null;
		}
		return testData;
	}
	
	public static Object[][] getPositiveCreds() throws Exception {
		try{
			TreeMap<String, Vector<String>> testData = TestDriverFactory.getPositiveTestDataByUsername(System.getProperty("env"));
			Object[][] myData = new Object[testData.size()][2];
			int i = 0;
			for(Entry<String, Vector<String>> entry  : testData.entrySet()){
				for(int j=0; j<2; j++){
					myData[i][j] = entry.getValue().get(j+2);
				}
				i++;
			}
			return myData;
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("unable to define data");
		}
		return null;
	}

	public static TreeMap<String, Vector<String>> getNegativeTestDataByUsername(String env) throws Exception{
		TreeMap<String, Vector<String>> testData = new TreeMap<String, Vector<String>>();
		Iterator<Row> rowIter = getSharedTestDataFileIterator();
		
		try{
			int numberOfDataPoints = 0;
			while(rowIter.hasNext()){
				HSSFRow myRow = (HSSFRow) rowIter.next();
				numberOfDataPoints = myRow.getLastCellNum();
				Iterator<org.apache.poi.ss.usermodel.Cell> cellIter = myRow.cellIterator();
				Vector<HSSFCell> cellStoreVector = new Vector<HSSFCell>(numberOfDataPoints);
				Vector<String> cellDataVector = new Vector<String>(numberOfDataPoints);
				if(isTestRunStatusYes(myRow) && isNegativeTest(myRow) && (isTestEnv(myRow, env) || isTestEnv(myRow, "*"))){
					while(cellIter.hasNext()){
						HSSFCell myCell = (HSSFCell) cellIter.next();
						cellStoreVector.addElement(myCell);
						cellDataVector.addElement(myCell.toString());					
					}					
					cellDataVector.removeElementAt(RUN_STATUS);
					testData.put(cellStoreVector.get(USERNAME).toString(), cellDataVector);
					cellStoreVector.clear();
				}
			}
		}catch (Exception e){
			e.printStackTrace(); 
			return null;
		}
		return testData;
	}	

	public static Object[][] getNegativeCreds() throws Exception {
		try{
			TreeMap<String, Vector<String>> testData = TestDriverFactory.getNegativeTestDataByUsername(System.getProperty("env"));
			Object[][] myData = new Object[testData.size()][2];
			int i = 0;
			for(Entry<String, Vector<String>> entry  : testData.entrySet()){
				for(int j=0; j<2; j++){
					myData[i][j] = entry.getValue().get(j+2);
				}
				i++;
			}
			return myData;
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("unable to define data");
		}
		return null;
	}
}
