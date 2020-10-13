package com.epam.automation;

import com.epam.automation.common.runner.BaseRunner;
import com.epam.automation.common.utils.ProxyUtils;
import io.restassured.mapper.TypeRef;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SmokeCheck extends BaseRunner {

    private SmokeCheckApi smokeCheckApi = ProxyUtils.create(SmokeCheckApi.class);


    @BeforeTest
    public void before() {
    }

    @Test
    public void testStatusCode() {
        response = smokeCheckApi.getUsers();
        response.then().statusCode(200);
        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
        assertThat(items.size(), greaterThan(0));
        response.prettyPrint();
    }

    @Test
    public void testBody() {
        response = smokeCheckApi.getUsers();
        response.then().statusCode(200);
        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
        assertThat(items.size(), equalTo(10));
        response.prettyPrint();
    }

    @Test
    public void testHeader() {
        response = smokeCheckApi.getUsers();
        response.then().statusCode(200);
        assertThat(response.headers().hasHeaderWithName("Content-Type"), equalTo(true));
        String contentType = response.header("Content-Type");
        assertThat(contentType, equalTo("application/json; charset=utf-8"));
    }

}
