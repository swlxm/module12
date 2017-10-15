package net.automation.android.scripts;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import net.automation.android.util.AndroidConstants;

public class AppiumTestCase {
    public static AndroidDriver<AndroidElement> driver;
    protected static int x_screen, y_screen;

    protected void setUp(String deviceName, String version, String appPackage, String appActivity, String url) throws Exception {
        // set up appium
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, version);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
        driver = new AndroidDriver<AndroidElement>(new URL(url), capabilities);
        sync(driver);
        x_screen = driver.manage().window().getSize().width;
        y_screen = driver.manage().window().getSize().height;
        Thread.sleep(AndroidConstants.SHORT_WAIT);
    }

    protected void tearDown() throws Exception {
        driver.quit();
    }

	public void sync(AndroidDriver<AndroidElement> driver) throws Exception {
		try {
			driver.findElementByClassName("android.widget.ProgressBar");
		} catch(Exception ex) {
			return;
		}
		while(true) {
			try {
				driver.findElementByClassName("android.widget.ProgressBar");
			} catch(Exception ex) {
				return;
			}
		}
	}
	

}
