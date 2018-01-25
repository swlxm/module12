package net.automation.mobile.testscripts.android;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Capabilities;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import io.appium.java_client.remote.MobilePlatform;
import net.automation.mobile.testscripts.AppiumTestCase;
import net.automation.mobile.util.AppiumConstants;
import net.automation.mobile.util.AppiumDriverBuilder;

public class SplitUITest extends AppiumTestCase {
	
	private AndroidDriver<AndroidElement> androidDriver;

	@SuppressWarnings("unchecked")
	@BeforeClass
	@Parameters({"port"})
	public void setUp(String port) throws Exception {
		Capabilities capa = driver.getCapabilities();
		String version = capa.getVersion();
		if(version.startsWith("7.1.1"))
			androidDriver = (AndroidDriver<AndroidElement>) AppiumDriverBuilder.build(MobilePlatform.ANDROID, port, "com.google.android.dialer", ".extensions.GoogleDialtactsActivity");
		else
			androidDriver = (AndroidDriver<AndroidElement>) AppiumDriverBuilder.build(MobilePlatform.ANDROID, port, "com.android.dialer", ".DialtactsActivity");
	}
	
	@SuppressWarnings({ "unchecked" })
	@Test
	public void testSettings() throws Exception {
		//dial
		androidDriver = (AndroidDriver<AndroidElement>)driver;
		Assert.assertNotNull(driver.findElementByAccessibilityId("拨号键盘"), "Start dial activity successfully.");
        androidDriver.findElementByAccessibilityId("拨号键盘").click();
        androidDriver.findElementByAndroidUIAutomator("text(\"1\")").click();
        androidDriver.findElementByAndroidUIAutomator("text(\"0\")").click();
        androidDriver.findElementByAndroidUIAutomator("text(\"0\")").click();
        androidDriver.findElementByAndroidUIAutomator("text(\"8\")").click();
        androidDriver.findElementByAndroidUIAutomator("text(\"6\")").click();
//	        driver.findElementByAccessibilityId("dial").click();
//        Thread.sleep(AndroidConstants.LONG_WAIT);
//        driver.findElementByAccessibilityId("End Call").click();
		Thread.sleep(AppiumConstants.SHORT_WAIT);
		
		//split ui
		Activity activity = new Activity("com.android.settings", ".Settings");
		activity.setAppWaitPackage("com.android.settings");
		activity.setAppWaitActivity(".Settings");
		androidDriver.startActivity(activity);
		Assert.assertNotNull(androidDriver.findElementByAndroidUIAutomator("text(\"设置\")"), "Start setting activity successfully.");
    	TouchAction action = new TouchAction(androidDriver);
		androidDriver.pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
        Thread.sleep(AppiumConstants.SHORT_WAIT);
        
        AndroidElement scrollView = androidDriver.findElementByClassName("android.widget.ScrollView");
		List<MobileElement> frames = scrollView.findElementsByAndroidUIAutomator("childSelector(new UiSelector().className(\"android.widget.FrameLayout\"))");
		MobileElement lastFrame = frames.get(frames.size()-2);
		MobileElement firstFrame = frames.get(0);
		MobileElement frametitle = lastFrame.findElementByClassName("android.widget.TextView");
		String title = frametitle.getText();
		Reporter.log("Drag and drop app " + title + " to split the screen.", true);
		if(x_screen < y_screen) {
			int y_offset = frametitle.getLocation().y - firstFrame.getLocation().y + firstFrame.getLocation().y*3/4;
			action.longPress(frametitle).waitAction(Duration.ofSeconds(MIDDLE_WAIT)).moveTo(0, -y_offset).waitAction(Duration.ofSeconds(MIDDLE_WAIT)).release().perform();
		} else {
			action.longPress(frametitle).waitAction(Duration.ofSeconds(MIDDLE_WAIT)).moveTo(-x_screen*2/5, 0).waitAction(Duration.ofSeconds(MIDDLE_WAIT)).release().perform();
		}
        Thread.sleep(AppiumConstants.SHORT_WAIT);
        
        action = new TouchAction(androidDriver);
        if(x_screen < y_screen)
        	action.tap(x_screen/2, y_screen*3/4).release().perform();
        else
        	action.tap(x_screen*3/4, y_screen/2).release().perform();
        Thread.sleep(AppiumConstants.MIDDLE_WAIT);
	}
}
