package com.epam.automation.common;

import java.util.HashMap;
import java.util.Map;

public class GlobalVar {

    public static final String CONFIGFILENAME = "application.properties";
    public static final String USER_URL = "";

    // 失败重试，等于2，则失败重试1次，共执行2次
    public final static Integer RETRY_COUNTS = 1;
    public static Map<String, String> COOKIES = new HashMap<>();
    public static Map<String, Object> HEADERS = new HashMap<>();


    public static long SLEEP_LONG = 1000*60;

    public static final String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjdXJyZW50Q291bnRlciI6IiIsInVzZXJfbmFtZSI6IjgyNTAiLCJzY29wZSI6WyJvYXV0aCJdLCJicmFuZElkIjoib2xheSIsIm1hcmtldENvZGUiOiJjbiIsImxvZ2luQ291bnRlciI6IiIsImV4cCI6MTU1NTc1MjMwMSwianRpIjoiNmI1OTJmNDktNGYyZi00MDllLTk5ODYtODBjNjFhMmFlNWY4IiwiYWNjb3VudCI6IlpIRU5HLVlZLTEiLCJjbGllbnRfaWQiOiJjbGllbnQtaWQifQ.Lu_iesbQP69Oky9uTIP8tDogzIH2ExB8J4qyAFs37Pc";

}
