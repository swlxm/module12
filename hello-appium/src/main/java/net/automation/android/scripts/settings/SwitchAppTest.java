package net.automation.android.scripts.settings;

import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import net.automation.android.scripts.AppiumTestCase;
import net.automation.android.util.AndroidConstants;

public class SwitchAppTest extends SettingsTestCase {
	
	@Test
	public void testSwitchApp() throws Exception {
    	TouchAction action = new TouchAction(driver);
		driver.longPressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
        Thread.sleep(AndroidConstants.SHORT_WAIT);
        
        AndroidElement el = driver.findElementByClassName("android.widget.ScrollView");
		List<MobileElement> frames = el.findElementsByAndroidUIAutomator("childSelector(new UiSelector().className(\"android.widget.FrameLayout\"))");
		MobileElement frame = frames.get(frames.size()-2);
		MobileElement frametitle = frame.findElementByClassName("android.widget.TextView");
		String title = frametitle.getText();
		frametitle.click();
        Thread.sleep(AndroidConstants.MIDDLE_WAIT);
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_APP_SWITCH);
		Thread.sleep(AndroidConstants.SHORT_WAIT);
        el = driver.findElementByClassName("android.widget.ScrollView");
		frames = el.findElementsByAndroidUIAutomator("childSelector(new UiSelector().className(\"android.widget.FrameLayout\"))");
		frame = frames.get(frames.size()-2);
		frametitle = frame.findElementByClassName("android.widget.TextView");
		title = frametitle.getText();
		int y_frame = frame.getLocation().y;
		int y_offset = frame.getSize().height;
		action.press((int)(x_screen/2), y_frame + 10).moveTo(0, y_offset).release().perform();
        Thread.sleep(AndroidConstants.MIDDLE_WAIT);
        el = driver.findElementByClassName("android.widget.ScrollView");
		frames = el.findElementsByAndroidUIAutomator("childSelector(new UiSelector().className(\"android.widget.FrameLayout\"))");
		frame = frames.get(0);
		frametitle = frame.findElementByClassName("android.widget.TextView");
		title = frametitle.getText();
		frametitle.click();
	}
	
}
