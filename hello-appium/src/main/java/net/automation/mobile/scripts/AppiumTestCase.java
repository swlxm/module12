package net.automation.mobile.scripts;

import java.io.File;
import java.net.URL;
import java.util.concurrent.locks.ReentrantLock;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import net.automation.mobile.util.AppiumConstants;

public class AppiumTestCase implements AppiumConstants {
    public AndroidDriver<AndroidElement> driver;
    protected int x_screen, y_screen;

    protected void setUp(String port, String appPackage, String appActivity) {
    	try {
	        // set up appium
	        DesiredCapabilities capabilities = new DesiredCapabilities();
	        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
	        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
	        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
	        driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);
	        sync(driver);
	        x_screen = driver.manage().window().getSize().width;
	        y_screen = driver.manage().window().getSize().height;
			Thread.sleep(SHORT_WAIT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    protected void tearDown() throws Exception {
        driver.quit();
    }

	public void sync(AndroidDriver<AndroidElement> driver) {
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
