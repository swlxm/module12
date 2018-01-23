package net.automation.mobile.android.settings;

import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import net.automation.mobile.android.AndroidTestCase;
import net.automation.mobile.util.AppiumConstants;

public class NotificationTest extends AndroidTestCase implements SettingsConstants {
	
	@BeforeClass
	@Parameters({"port"})
	public void setUp(String port) throws Exception {
		super.setUp(port, APP_PACKAGE, APP_ACTIVITY);
	}
	
	@AfterClass
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testSettings() throws Exception {
		driver.openNotifications();
		Thread.sleep(SHORT_WAIT);
		driver.findElementByAccessibilityId("Open quick settings.").click();        
		Thread.sleep(SHORT_WAIT);
		AndroidElement seek = driver.findElementByAndroidUIAutomator("text(\"Display brightness\")");
        TouchAction action = new TouchAction(driver);
        action.press(seek).moveTo(200, 0).waitAction(Duration.ofSeconds(SHORT_WAIT)).moveTo(-400, 0).waitAction(Duration.ofSeconds(SHORT_WAIT)).release().perform();
        
        //switch bluetooth
		List<AndroidElement> buttons = driver.findElementsByClassName("android.widget.Button");
		for(AndroidElement button:buttons) {
			String desc = button.getAttribute("name");
			Reporter.log(desc, true);
			if(desc.startsWith("Bluetooth")) {
				button.click();
				if(desc.startsWith("Bluetooth on")) {
					driver.findElementByAndroidUIAutomator("text(\"ON\")").click();;
				}
				if(desc.startsWith("Bluetooth off")) {
					driver.findElementByAndroidUIAutomator("text(\"DONE\")").click();;
				}
				break;
			}
		}
		driver.longPressKeyCode(AndroidKeyCode.BACK);
		driver.longPressKeyCode(AndroidKeyCode.BACK);
	}
}
