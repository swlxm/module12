package com.epam.automation.common.listeners;

import com.epam.automation.common.utils.ConfigUtils;
import org.testng.ITestResult;
import org.testng.util.RetryAnalyzerCount;


public class TestngRetry extends RetryAnalyzerCount {

    private static final int count = Integer.valueOf(ConfigUtils.getProperty("retryTimes", "0"));

    public TestngRetry() {
        super.setCount(count);
    }

    @Override
    public boolean retryMethod(ITestResult result) {
        return true;
    }
}

