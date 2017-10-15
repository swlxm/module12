package net.automation.android.scripts;

import java.net.URL;
import java.util.List;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import net.automation.android.util.AndroidConstants;

public class MMSTest extends AppiumTestCase {
	protected static AndroidDriver<AndroidElement> driver;
	
	@BeforeClass
	@Parameters({"deviceName", "version", "url"})
	public void setUp(String deviceName, String version, String url) throws Exception {
		super.setUp(deviceName, version, "com.android.mms", ".ui.ComposeMessageActivity", url);
	}
	
	@Test
	public void testMMS() throws Exception {
		int y = driver.manage().window().getSize().height;
		int x = driver.manage().window().getSize().width;
        driver.findElementById("up").click();
        Thread.sleep(AndroidConstants.MIDDLE_WAIT);
		TouchAction action = new TouchAction(driver);
		action.press((int)(x/2), (int)(y*0.9)).moveTo(0, -(int)(y*0.6)).release().perform();
		Thread.sleep(AndroidConstants.SHORT_WAIT);
		action = new TouchAction(driver);
		action.press((int)(x/2), (int)(y*0.2)).moveTo(0, (int)(y*0.6)).release().perform();
		Thread.sleep(AndroidConstants.SHORT_WAIT);
		List<AndroidElement> msgs = driver.findElementsById("from");
		action.longPress(msgs.get(1)).perform();
		driver.findElementById("delete").click();
        driver.findElementByAndroidUIAutomator("text(\"Delete\")").click();
        Thread.sleep(AndroidConstants.SHORT_WAIT);

	}
	
	@AfterClass
	public void teardown() {
		driver.quit();
		
	}
}
