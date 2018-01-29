package net.automation.mobile.testscripts;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import net.automation.mobile.util.AppiumConstants;
import net.automation.mobile.util.AppiumDriverBuilder;

public class MobileWebTestCase implements AppiumConstants {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static WebDriver driver = null;
	
	@BeforeSuite
    @Parameters({"platform", "port"})
    public void setUp(String platform, String port) {
    	try {
	        // set up appium
	        driver = AppiumDriverBuilder.buildWebDriver(platform, port);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @AfterSuite
    public void tearDown() throws Exception {
        driver.quit();
    }

    @SuppressWarnings("unchecked")
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if(result.getStatus() == 2) {
			String date = new Date().toString();
			date = date.replace(" ", "_");
			date = date.replace(":", "_");
			ITestNGMethod method = result.getMethod();
			String testClassName = result.getInstanceName();
			String screenshotRootPath = PROJECT_ROOT_PATH + FILE_SEPARATOR + "target\\surefire-reports\\screenshots";
			String screenshotName = method.getMethodName() + "_" + ((AppiumDriver<? extends MobileElement>) driver).getRemoteAddress().getPort() + "_" + date + ".png";
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
