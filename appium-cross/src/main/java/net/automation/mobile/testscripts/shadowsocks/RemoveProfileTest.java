package net.automation.mobile.testscripts.shadowsocks;

import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import net.automation.mobile.testscripts.AppiumTestCase;
import net.automation.mobile.util.AppiumAssert;
import net.automation.mobile.util.AppiumDataProvider;
import net.automation.mobile.util.AppiumException;
import net.automation.mobile.util.CommonActions;

public class RemoveProfileTest extends AppiumTestCase implements ShadowsocksConstants {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String device, port;
	
	@Parameters({"device", "port"})
	public RemoveProfileTest(String device, String port) {
		this.device = device;
		this.port = port;
	}

	@Test(dataProvider="profile", dataProviderClass=AppiumDataProvider.class)
	public void testRemoveProfile(String profileName, String server, String remotePort, String pwd, String encyptMethod) throws AppiumException {
		try {
			driver.findElementByXPath("//android.widget.TextView[@text='" + server + ":" + remotePort + "']");
		} catch(NoSuchElementException ex) {
			//The profile doesn't exist, throw exception
			logger.error("The profile [" + server + ":" + remotePort + "] on device [" + device + "] is not found.");
			throw new AppiumException("The profile [" + server + ":" + remotePort + "] on device [" + device + "] is not found.");
		}
		logger.info("Removing profile [" + server + ":" + remotePort + "] on device [" + device + "] ...");
		CommonActions.tap(driver.findElementByXPath("//android.widget.TextView[@text='" + server + ":" + remotePort + "']/../..//android.widget.ImageView[@resource-id='com.github.shadowsocks:id/edit']"));
		CommonActions.tap(driver.findElementByAccessibilityId(content_desc_remove));
		CommonActions.tap(driver.findElementByXPath(xpath_button_yes));
		try {
			driver.findElementByXPath("//android.widget.TextView[@text='" + server + ":" + remotePort + "']");
		} catch(NoSuchElementException ex) {
			//The profile doesn't exist, throw exception
			AppiumAssert.assertTrue(true, "Remove profile [" + server + ":" + remotePort + "] on device [" + device + "] successfully.");
			return;
		}
		throw new AppiumException("The profile [" + server + ":" + remotePort + "] on device [" + device + "] still exists.");
	}
}
