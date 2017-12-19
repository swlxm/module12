package net.automation.selenium.jenkins.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import net.automation.selenium.jenkins.scripts.SeleniumTestCase;
import net.automation.selenium.jenkins.util.Constants;

public class TestListener extends TestListenerAdapter {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private WebDriver driver;
	
	/**
	 * Take screenshot if test failed.
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		super.onTestFailure(result);
		String testClassName = result.getMethod().toString().split("\\.")[0];
		String screenShotName = result.getName().toString().trim() + ".png";

		logger.error("***** Error {}.{}() test has failed *****", testClassName, result.getName().toString().trim());

		driver = SeleniumTestCase.driver;


		if (driver != null) {
			String screenShotPath = takeScreenShot(driver, testClassName, screenShotName);
			logger.info("Screenshot can be found in {}", screenShotPath);
		}
	}
	
    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);

        // List of test results which we will delete later
        ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
        // collect all id's from passed test
        Set<Integer> passedTestIds = new HashSet<Integer>();
        for (ITestResult passedTest : testContext.getPassedTests()
                .getAllResults()) {
            logger.info("PassedTests = " + passedTest.getName());
            passedTestIds.add(getId(passedTest));
        }

        Set<Integer> failedTestIds = new HashSet<Integer>();
        for (ITestResult failedTest : testContext.getFailedTests()
                .getAllResults()) {
            logger.info("failedTest = " + failedTest.getName());
            int failedTestId = getId(failedTest);

            // if we saw this test as a failed test before we mark as to be
            // deleted
            // or delete this failed test if there is at least one passed
            // version
            if (failedTestIds.contains(failedTestId)
                    || passedTestIds.contains(failedTestId)) {
                testsToBeRemoved.add(failedTest);
            } else {
                failedTestIds.add(failedTestId);
            }
        }

        // finally delete all tests that are marked
        for (Iterator<ITestResult> iterator = testContext.getFailedTests()
                .getAllResults().iterator(); iterator.hasNext();) {
            ITestResult testResult = iterator.next();
            if (testsToBeRemoved.contains(testResult)) {
                logger.info("Remove repeat Fail Test: " + testResult.getName());
                iterator.remove();
            }
        }

    }

    private int getId(ITestResult result) {
        int id = result.getTestClass().getName().hashCode();
        id = id + result.getMethod().getMethodName().hashCode();
        id = id
                + (result.getParameters() != null ? Arrays.hashCode(result
                        .getParameters()) : 0);
        return id;
    }
    
    private String takeScreenShot(WebDriver driver, String testName, String screenShotName) {
		String screenShotFolderPath = "test-output" + Constants.FILE_SEPARATOR + "screenshots";
		try {
			File screenShotFolder = new File(screenShotFolderPath);
			if (!screenShotFolder.exists()) {
				screenShotFolder.mkdir();
			}

			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File targetFile = new File(screenShotFolderPath + Constants.FILE_SEPARATOR + testName, screenShotName);
			FileUtils.copyFile(screenshotFile, targetFile);

			return targetFile.getAbsolutePath();
		} catch (Exception e) {
			System.out.println("An exception occured while taking screenshot " + e.getCause());
			return null;
		}
	}
}
