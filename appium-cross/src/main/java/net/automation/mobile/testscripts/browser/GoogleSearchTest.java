package net.automation.mobile.testscripts.browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;

import net.automation.mobile.testscripts.MobileWebTestCase;
import net.automation.mobile.util.AppiumConstants;

public class GoogleSearchTest extends MobileWebTestCase implements AppiumConstants {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String port;
	
	@Parameters({"port"})
	public GoogleSearchTest(String port) {
		this.port = port;
	}

	public void testGoogleSearch() {
		driver.get("http://www.google.com/");
	}
}
