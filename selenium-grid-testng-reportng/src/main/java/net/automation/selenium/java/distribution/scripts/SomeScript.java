package net.automation.selenium.java.distribution.scripts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import net.automation.selenium.java.distribution.page.SomePage;
import net.automation.selenium.java.distribution.util.NGAssert;

public class SomeScript extends SeleniumTestCase {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@BeforeClass
	@Parameters({"browser", "remoteAddress", "url", "username"})
	public void setUp(String browser, String remoteAddress, String url, String username) throws Exception {
		super.setUp(browser, remoteAddress, url, username);
	}
	
	@Test
	public void testXXX() {
		SomePage somePage = new SomePage(driver);
		somePage.commonFuncOnSomePage();
		NGAssert.assertTrue(true);
	}

}
