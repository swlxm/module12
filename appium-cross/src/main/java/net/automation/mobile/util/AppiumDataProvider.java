package net.automation.mobile.util;

import org.testng.annotations.DataProvider;

/**
 * A class to provide test data to be used by testng with DataProvider annotation.
 * @author Samuel
 *
 */
public class AppiumDataProvider {

	@DataProvider(name="profile")
	public static Object[][] profileData() throws Exception {
		return ExcelParser.getInstance().getTestData("Profiles.xlsx");
	}
}
