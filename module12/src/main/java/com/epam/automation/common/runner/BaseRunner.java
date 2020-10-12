package com.epam.automation.common.runner;

import com.epam.automation.common.GlobalVar;
import com.epam.automation.common.utils.ConfigUtils;
import com.epam.automation.common.utils.TokenUtils;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import static com.epam.automation.common.GlobalVar.HEADERS;
//@Listeners({ AutoTestListener.class, RetryListener.class })
public class BaseRunner {

    protected static final Logger logger = LoggerFactory.getLogger(BaseRunner.class);
    protected Response response;

    @BeforeClass
    public static void beforeClass() {
        HEADERS.put("Accept", "application/json");
        logger.info("Accept = {}", "application/json");
        if (ConfigUtils.getAzureAPIM().trim().equals("true")) {
            HEADERS.put("Ocp-Apim-Subscription-Key", ConfigUtils.getSubscriptionKey().trim());
            logger.info("Ocp-Apim-Subscription-Key = {}", ConfigUtils.getSubscriptionKey().trim());
            String tokenId_bc = GlobalVar.token;
            logger.info("Authorization = {} ", tokenId_bc);
            HEADERS.put("Authorization", tokenId_bc);
            HEADERS.put("Auth-Type", "c2");
            logger.info("Auth-Type = c2");
        } else {
            String tokenId = TokenUtils.generateToken(60*24*30);
            logger.info("Authorization = {}", tokenId);
            HEADERS.put("Authorization", tokenId);
        }
    }

    @AfterTest
    public static void afterClass() {
    }

}
