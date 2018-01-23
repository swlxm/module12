package net.automation.mobile.ios;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import net.automation.mobile.util.AppiumConstants;

public class IOSTestCase implements AppiumConstants {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static IOSDriver<IOSElement> driver;
    
    public static int x_screen, y_screen;

    @BeforeSuite
    @Parameters({"port", "app_package", "app_activity"})
    public void setUp(String port, String app_package, String app_activity) {
    	try {
	        // set up appium
	        DesiredCapabilities capabilities = new DesiredCapabilities();
	        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
	        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, app_package);
	        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, app_activity);
	        driver = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:" + port + "/wd/hub"), capabilities);
	        sync(driver);
	        x_screen = driver.manage().window().getSize().width;
	        y_screen = driver.manage().window().getSize().height;
			Thread.sleep(SHORT_WAIT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @AfterSuite
    public void tearDown() throws Exception {
        driver.quit();
    }

	public void sync(IOSDriver<IOSElement> driver) {
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
	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if(result.getStatus() == 2) {
			String date = new Date().toString();
			date = date.replace(" ", "_");
			date = date.replace(":", "_");
			ITestNGMethod method = result.getMethod();
			String testClassName = result.getInstanceName();
			String screenshotRootPath = PROJECT_ROOT_PATH + FILE_SEPARATOR + "target\\surefire-reports\\screenshots";
			String screenshotName = method.getMethodName() + "_" + driver.getRemoteAddress().getPort() + "_" + date + ".png";
			try {
				File screenShotFolder = new File(screenshotRootPath);
				if (!screenShotFolder.exists()) {
					screenShotFolder.mkdir();
				}

				File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				File targetFile = new File(screenshotRootPath + FILE_SEPARATOR + testClassName, screenshotName);
				FileUtils.copyFile(screenshotFile, targetFile);
				System.setProperty("org.uncommons.reportng.escape-output", "false");
				
				String absolute = targetFile.getAbsolutePath();
				String screenShot = absolute.replace('\\','/');
				Reporter.setCurrentTestResult(result);
				Reporter.log("<a href=\"../Screenshots/" + testClassName + "/" + screenshotName + "\"><p align=\"left\">Error screenshot at " + date + "</p>");
//				Reporter.log("<a href=\"" + targetFile.getAbsoluteFile() + "\"><p align=\"left\">Error screenshot at " + new Date()+ "</p>");
				Reporter.log("<p><img width=\"1024\" src=\"" + targetFile.getAbsoluteFile()  + "\" alt=\"screenshot at " + date + "\"/></p></a><br />"); 
			} catch (Exception e) {
				System.out.println("An exception occured while taking screenshot " + e.getCause());
			}
		}
	}

}