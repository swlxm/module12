package net.automation.selenium.jenkins.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

public class RetryAnalyzer implements IRetryAnalyzer {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private int retryCount = 1;
	private int maxRetryCount = 1;
	
	/**
	 * Retry if failed.
	 */
    public boolean retry(ITestResult result) {
    	if(!result.isSuccess()) {
    		if(retryCount <= maxRetryCount) {
	    		logger.warn("Retry test {}.{}() {} times", this.getClass().getName(), result.getName(), retryCount);
	    		Reporter.setCurrentTestResult(result);
	    		Reporter.log("RetryCount=" + (retryCount + 1));
	    		retryCount++;
	            return true;
	        }
    	}
        return false;
    }
}