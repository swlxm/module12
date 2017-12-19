package net.automation.selenium.jenkins.util;

import org.testng.annotations.DataProvider;

/**
 * A class to provide test data to be used by testng with DataProvider annotation.
 * @author Samuel
 *
 */
public class NGDataProvider {

	@DataProvider(name="login")
	public static Object[][] loginData() throws Exception {
		return ExcelParser.getInstance().getTestData("LoginData.xlsx");
	}
}
