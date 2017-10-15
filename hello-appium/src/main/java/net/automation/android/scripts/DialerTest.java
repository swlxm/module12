package net.automation.android.scripts;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import net.automation.android.util.AndroidConstants;

public class DialerTest extends AppiumTestCase {
	
	@BeforeClass
	@Parameters({"deviceName", "version", "url"})
	public void setUp(String deviceName, String version, String url) throws Exception {
		super.setUp(deviceName, version, "com.android.dialer", ".DialtactsActivity", url);
	}
	
	@Test
	public void testDialer() throws Exception {
        driver.findElementByAccessibilityId("dial pad").click();
//		driver.findElementById("com.android.dialer:id/dialpad_button").click();
        driver.findElementByAndroidUIAutomator("text(\"1\")").click();
        driver.findElementByAndroidUIAutomator("text(\"0\")").click();
        driver.findElementByAndroidUIAutomator("text(\"0\")").click();
        driver.findElementByAndroidUIAutomator("text(\"8\")").click();
        driver.findElementByAndroidUIAutomator("text(\"6\")").click();
        driver.findElementByAccessibilityId("dial").click();
        Thread.sleep(AndroidConstants.LONG_WAIT);
        driver.findElementByAccessibilityId("end").click();
	}
	
	@AfterClass
	public void tearDown() throws Exception {
		super.tearDown();
		
	}
}
