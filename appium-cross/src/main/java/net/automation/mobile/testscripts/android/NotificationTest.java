package net.automation.mobile.testscripts.android;

import java.net.URL;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import net.automation.mobile.testscripts.AppiumTestCase;
import net.automation.mobile.util.AppiumConstants;

public class NotificationTest extends AppiumTestCase implements SettingsConstants {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private AndroidDriver<AndroidElement> androidDriver = null;
	private String device, port;
	
	@Parameters({"device", "port"})
	public NotificationTest(String device, String port) {
		this.device = device;
		this.port = port;
	}

	@Test
	public void testSettings() throws Exception {
		
		androidDriver = (AndroidDriver<AndroidElement>)driver;

		androidDriver.openNotifications();
		Thread.sleep(SHORT_WAIT);
		androidDriver.findElementByAccessibilityId("Open quick settings.").click();        
		Thread.sleep(SHORT_WAIT);
		AndroidElement seek = androidDriver.findElementByAndroidUIAutomator("text(\"Display brightness\")");
        TouchAction action = new TouchAction(androidDriver);
        action.press(seek).moveTo(200, 0).waitAction(Duration.ofSeconds(SHORT_WAIT)).moveTo(-400, 0).waitAction(Duration.ofSeconds(SHORT_WAIT)).release().perform();
        
        //switch bluetooth
		List<AndroidElement> buttons = androidDriver.findElementsByClassName("android.widget.Button");
		for(AndroidElement button:buttons) {
			String desc = button.getAttribute("name");
			Reporter.log(desc, true);
			if(desc.startsWith("Bluetooth")) {
				button.click();
				if(desc.startsWith("Bluetooth on")) {
					androidDriver.findElementByAndroidUIAutomator("text(\"ON\")").click();;
				}
				if(desc.startsWith("Bluetooth off")) {
					androidDriver.findElementByAndroidUIAutomator("text(\"DONE\")").click();;
				}
				break;
			}
		}
		androidDriver.longPressKeyCode(AndroidKeyCode.BACK);
		androidDriver.longPressKeyCode(AndroidKeyCode.BACK);
	}
}
