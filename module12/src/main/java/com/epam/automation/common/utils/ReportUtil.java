package com.epam.automation.common.utils;

import org.testng.Reporter;

import java.util.Calendar;

public class ReportUtil {
    private static String reportName = "Auto Test Report";

    private static String splitTimeAndMsg = "===";

    public static void log(String msg) {
        long timeMillis = Calendar.getInstance().getTimeInMillis();
        Reporter.log(timeMillis + splitTimeAndMsg + msg, true);
    }

    public static String getReportName() {
        return reportName;
    }

    public static void setReportName(String reportName) {
        if (!reportName.isEmpty()) {
            ReportUtil.reportName = reportName;
        }
    }

    public static String getSpiltTimeAndMsg() {
        return splitTimeAndMsg;
    }
}

