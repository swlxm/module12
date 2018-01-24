package net.automation.mobile.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Parameters;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public class AppiumDriverBuilder {

	public static AppiumDriver<? extends MobileElement> build(String platform, String port, String app_package, String app_activity) throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities();

		if(platform.equalsIgnoreCase("android")) {
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");	//MobileBrowserType.CHROME
			capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, app_package);
			capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, app_activity);
			return new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);
		}
		if(platform.equalsIgnoreCase("ios"))
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");	//MobileBrowserType.SAFARI
			capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, app_package);
			capabilities.setCapability(IOSMobileCapabilityType.APP_NAME, app_activity);
			return new IOSDriver<IOSElement>(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);
	}
}
