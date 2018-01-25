package net.automation.mobile.testscripts.android;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import net.automation.mobile.testscripts.AppiumTestCase;
import net.automation.mobile.util.AppiumConstants;

public class DialerTest extends AppiumTestCase {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private AndroidDriver<AndroidElement> androidDriver = null;
	private String device, port;
	
	@Parameters({"device", "port"})
	public DialerTest(String device, String port) {
		this.device = device;
		this.port = port;
	}

	@Test
	public void testDialer() throws Exception {
		androidDriver = (AndroidDriver<AndroidElement>)driver;

        androidDriver.findElementByAccessibilityId("dial pad").click();
//		androidDriver.findElementById("com.android.dialer:id/dialpad_button").click();
        androidDriver.findElementByAndroidUIAutomator("text(\"1\")").click();
        androidDriver.findElementByAndroidUIAutomator("text(\"0\")").click();
        androidDriver.findElementByAndroidUIAutomator("text(\"0\")").click();
        androidDriver.findElementByAndroidUIAutomator("text(\"8\")").click();
        androidDriver.findElementByAndroidUIAutomator("text(\"6\")").click();
        androidDriver.findElementByAccessibilityId("dial").click();
        Thread.sleep(AppiumConstants.LONG_WAIT);
        androidDriver.findElementByAccessibilityId("end").click();
	}
	
}
