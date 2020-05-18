package com.w2a.APITestingFramework.utilities;

import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.annotations.DataProvider;

import com.w2a.APITestingFramework.setUp.BaseTest;
//import com.w2a.utilities.Constants;
//import com.w2a.utilities.ExcelReader;

public class DataUtil extends BaseTest{

	@DataProvider(name="dp",parallel=true)
	public static Object[][] getData(Method m) {
		
		int rows = excel.getRowCount(config.getProperty("testDataSheetName"));
		System.out.println("Total rows are: " + rows);
		
		String testName = m.getName();
		System.out.println("Test name is: " + testName);

		// find from which row does the test case start
		int testCaseRowNum = 1;
		for (testCaseRowNum = 1; testCaseRowNum <= rows; testCaseRowNum++) {

			String testCaseName = excel.getCellData(config.getProperty("testDataSheetName"), 0, testCaseRowNum);
			if (testCaseName.equalsIgnoreCase(testName))
				break;
		}
		System.out.println("Test Case starts from row number: " + testCaseRowNum);

		// find how many test-data-rows are there in each test
		int dataStartRowNum = testCaseRowNum + 2;
		// must leave one blank row after end of each TestCase data in excel
		int testRows = 0; 

		while (!excel.getCellData(config.getProperty("testDataSheetName"), 0, dataStartRowNum + testRows).equals("")) {
			testRows++;
		}
		System.out.println("Total test data-rows for the " + testName + " are : " + testRows);

		// find how many test-data-columns are there in each test
		int colStartColNum = testCaseRowNum + 1;
		int testCols = 0;

		while (!excel.getCellData(config.getProperty("testDataSheetName"), testCols, colStartColNum).equals("")) {
			testCols++;
		}
		System.out.println("Total test data-columns for the " + testName + " are : " + testCols);

		// Now print the data
		Object[][] data = new Object[testRows][1];
		
		int i=0;
		for (int rNum = dataStartRowNum; rNum < (dataStartRowNum + testRows); rNum++) {

			Hashtable<String,String> table = new Hashtable<String,String>();

			for (int cNum = 0; cNum < testCols; cNum++) {
				String testData = excel.getCellData(config.getProperty("testDataSheetName"), cNum, rNum);
				String colName = excel.getCellData(config.getProperty("testDataSheetName"), cNum, colStartColNum);
				table.put(colName, testData);

			}
			data[i][0] = table;
			i++;
		}
		return data;
	}
}
