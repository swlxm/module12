package net.automation.android.scripts;

import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import net.automation.android.util.AndroidConstants;

public class SplitUITest extends AppiumTestCase {
	
	@BeforeClass
	@Parameters({"deviceName", "version", "url"})
	public void setUp(String deviceName, String version, String url) throws Exception {
		if(version.startsWith("7.1.1"))
			super.setUp(deviceName, version, "com.google.android.dialer", ".extensions.GoogleDialtactsActivity", url);
		else
			super.setUp(deviceName, version, "com.android.dialer", ".DialtactsActivity", url);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testSettings() throws Exception {
		//dial
		
		Assert.assertNotNull(driver.findElementByAccessibilityId("拨号键盘"), "Start dial activity successfully.");
        driver.findElementByAccessibilityId("拨号键盘").click();
        driver.findElementByAndroidUIAutomator("text(\"1\")").click();
        driver.findElementByAndroidUIAutomator("text(\"0\")").click();
        driver.findElementByAndroidUIAutomator("text(\"0\")").click();
        driver.findElementByAndroidUIAutomator("text(\"8\")").click();
        driver.findElementByAndroidUIAutomator("text(\"6\")").click();
//	        driver.findElementByAccessibilityId("dial").click();
//        Thread.sleep(AndroidConstants.LONG_WAIT);
//        driver.findElementByAccessibilityId("End Call").click();
		Thread.sleep(AndroidConstants.SHORT_WAIT);
		
		//split ui
		driver.startActivity("com.android.settings", ".Settings");
		Assert.assertNotNull(driver.findElementByAndroidUIAutomator("text(\"设置\")"), "Start setting activity successfully.");
    	TouchAction action = new TouchAction(driver);
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
        Thread.sleep(AndroidConstants.SHORT_WAIT);
        
        AndroidElement scrollView = driver.findElementByClassName("android.widget.ScrollView");
		List<MobileElement> frames = scrollView.findElementsByAndroidUIAutomator("childSelector(new UiSelector().className(\"android.widget.FrameLayout\"))");
		MobileElement lastFrame = frames.get(frames.size()-2);
		MobileElement firstFrame = frames.get(0);
		MobileElement frametitle = lastFrame.findElementByClassName("android.widget.TextView");
		String title = frametitle.getText();
		Reporter.log("Drag and drop app " + title + " to split the screen.", true);
		if(x_screen < y_screen) {
			int y_offset = frametitle.getLocation().y - firstFrame.getLocation().y + firstFrame.getLocation().y*3/4;
			action.longPress(frametitle).waitAction(2000).moveTo(0, -y_offset).waitAction(2000).release().perform();
		} else {
			action.longPress(frametitle).waitAction(2000).moveTo(-x_screen*2/5, 0).waitAction(2000).release().perform();
		}
        Thread.sleep(AndroidConstants.SHORT_WAIT);
        
        action = new TouchAction(driver);
        if(x_screen < y_screen)
        	action.tap(x_screen/2, y_screen*3/4).release().perform();
        else
        	action.tap(x_screen*3/4, y_screen/2).release().perform();
        Thread.sleep(AndroidConstants.MIDDLE_WAIT);
	}
	
	@AfterClass
	public void teardown() {
		driver.quit();
		
	}
}
