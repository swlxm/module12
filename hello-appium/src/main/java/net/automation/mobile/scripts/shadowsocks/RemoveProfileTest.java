package net.automation.mobile.scripts.shadowsocks;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import net.automation.mobile.scripts.AppiumTestCase;
import net.automation.mobile.util.AppiumDataProvider;

public class RemoveProfileTest extends AppiumTestCase {


	@BeforeTest
	@Parameters("port")
	public void setUp(String port) throws Exception {
		super.setUp(port, "com.github.shadowsocks", ".MainActivity");
	}
	
	@AfterTest
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test(dataProvider="profile", dataProviderClass=AppiumDataProvider.class)
	public void testRemoveProfile() {
		
	}
}
