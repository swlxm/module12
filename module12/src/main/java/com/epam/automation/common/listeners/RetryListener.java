package com.epam.automation.common.listeners;

import org.testng.*;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

public class RetryListener extends TestListenerAdapter implements IAnnotationTransformer {
    @SuppressWarnings("rawtypes")
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        IRetryAnalyzer retry = annotation.getRetryAnalyzer();
        if (retry == null) {
            annotation.setRetryAnalyzer(TestngRetry.class);
        }
    }

    public void onFinish(ITestContext testContext) {
        ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
        Set<Integer> passedTestIds = new HashSet<Integer>();
        for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) {
            passedTestIds.add(getId(passedTest));
        }
        Set<Integer> failedTestIds = new HashSet<Integer>();
        for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) {
            int failedTestId = getId(failedTest);
            if (failedTestIds.contains(failedTestId) || passedTestIds.contains(failedTestId)) {
                testsToBeRemoved.add(failedTest);
            } else {
                failedTestIds.add(failedTestId);
            }
        }
        for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator(); iterator.hasNext(); ) {
            ITestResult testResult = iterator.next();
            if (testsToBeRemoved.contains(testResult)) {
                iterator.remove();
            }
        }
    }

    private int getId(ITestResult result) {
        int id = result.getTestClass().getName().hashCode();
        id = id + result.getMethod().getMethodName().hashCode();
        id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
        return id;
    }

}

