package net.automation.mobile.testscripts.android;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import net.automation.mobile.testscripts.AppiumTestCase;
import net.automation.mobile.util.AppiumConstants;

public class SwitchAppTest extends AppiumTestCase implements SettingsConstants {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private AndroidDriver<AndroidElement> androidDriver = null;
	private String device, port;
	
	@Parameters({"device", "port"})
	public SwitchAppTest(String device, String port) {
		this.device = device;
		this.port = port;
	}

	@Test
	public void testSwitchApp() throws Exception {
		androidDriver = (AndroidDriver<AndroidElement>)driver;

		
		TouchAction action = new TouchAction(androidDriver);
		androidDriver.longPressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
        Thread.sleep(AppiumConstants.SHORT_WAIT);
        
        AndroidElement el = androidDriver.findElementByClassName("android.widget.ScrollView");
		List<MobileElement> frames = el.findElementsByAndroidUIAutomator("childSelector(new UiSelector().className(\"android.widget.FrameLayout\"))");
		MobileElement frame = frames.get(frames.size()-2);
		MobileElement frametitle = frame.findElementByClassName("android.widget.TextView");
		String title = frametitle.getText();
		frametitle.click();
        Thread.sleep(MIDDLE_WAIT);
		androidDriver.pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
		Thread.sleep(SHORT_WAIT);
        el = androidDriver.findElementByClassName("android.widget.ScrollView");
		frames = el.findElementsByAndroidUIAutomator("childSelector(new UiSelector().className(\"android.widget.FrameLayout\"))");
		frame = frames.get(frames.size()-2);
		frametitle = frame.findElementByClassName("android.widget.TextView");
		title = frametitle.getText();
		int y_frame = frame.getLocation().y;
		int y_offset = frame.getSize().height;
		action.press((int)(x_screen/2), y_frame + 10).moveTo(0, y_offset).release().perform();
        Thread.sleep(MIDDLE_WAIT);
        el = androidDriver.findElementByClassName("android.widget.ScrollView");
		frames = el.findElementsByAndroidUIAutomator("childSelector(new UiSelector().className(\"android.widget.FrameLayout\"))");
		frame = frames.get(0);
		frametitle = frame.findElementByClassName("android.widget.TextView");
		title = frametitle.getText();
		frametitle.click();
	}
	
}
