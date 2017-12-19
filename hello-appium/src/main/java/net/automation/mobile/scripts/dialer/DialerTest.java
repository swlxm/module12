package net.automation.mobile.scripts.dialer;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import net.automation.mobile.scripts.AppiumTestCase;
import net.automation.mobile.util.AppiumConstants;

public class DialerTest extends DialerTestCase {
	
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
        Thread.sleep(AppiumConstants.LONG_WAIT);
        driver.findElementByAccessibilityId("end").click();
	}
	
}
