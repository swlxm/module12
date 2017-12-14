package net.automation.android.scripts.settings;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import net.automation.android.scripts.AppiumTestCase;

public class SettingsTestCase extends AppiumTestCase {

	@BeforeTest
	@Parameters({"port"})
	public void setUp(String port) throws Exception {
		super.setUp(port, "com.android.settings", ".Settings");
	}
	
	@AfterTest
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
