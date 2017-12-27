package net.automation.mobile.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.Format;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class to parse a excel file to get the test data, it will be used by MyDataProvider class.
 * @author Samuel
 *
 */
public class ExcelParser {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String[][] data = null;
	private XSSFWorkbook wb = null;
	
	private static ExcelParser parser = new ExcelParser();
	
	public static ExcelParser getInstance() {
		return parser;
	}
	
	/**
	 * Return the test data from excel file.
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public String[][] getTestData(String fileName) throws Exception {
		String root = System.getProperty("user.dir");
		fileName = root + "\\testdata\\" + fileName;
		if(fileName.endsWith(".xlsx")) {
			InputStream is = new FileInputStream(fileName);
			wb = new XSSFWorkbook(is);
			logger.debug("Got test data from excel file: {}", fileName);
		} else
			throw new RuntimeException("Test data file " + fileName + " is not a valid excel file");
		XSSFSheet sheet = wb.getSheetAt(0);
		int row = sheet.getLastRowNum();
		XSSFRow rowObj = sheet.getRow(0);
		int col = rowObj.getLastCellNum();
		data = new String[row][col];
		int i = 1;
		while(i <= row) {
			rowObj = sheet.getRow(i);
			int j = 0;
			while(j < col) {
				XSSFCell cell = rowObj.getCell(j);
				Enum<?> cellType = cell.getCellTypeEnum();
				if(cellType.name().equals("NUMERIC"))
					cell.setCellType(CellType.STRING);
				data[i-1][j] = cell.getStringCellValue();
				j++;
			}
			i++;
		}
		return data;
	}
	
}
