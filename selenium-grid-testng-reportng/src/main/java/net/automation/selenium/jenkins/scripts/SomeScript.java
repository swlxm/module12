package net.automation.selenium.jenkins.scripts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import net.automation.selenium.jenkins.pages.SomePage;
import net.automation.selenium.jenkins.util.NGAssert;

public class SomeScript extends SeleniumTestCase {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@BeforeClass
	@Parameters({"browser", "url", "username"})
	public void setUp(String browser, String url, String username) {
		super.setUp(browser, url, username);
	}
	
	@Test
	public void testXXX() {
		SomePage somePage = new SomePage(driver);
		somePage.commonFuncOnSomePage();
		NGAssert.assertTrue(true);
	}

}
