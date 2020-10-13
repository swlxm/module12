package com.epam.automation;

import com.epam.automation.common.runner.BaseRunner;
import com.epam.automation.common.utils.Common;
import com.epam.automation.common.utils.JsonUtils;
import com.epam.automation.common.utils.ProxyUtils;
import io.restassured.mapper.TypeRef;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class ApplicationTest extends BaseRunner {

    private ApplicationApi applicationApi = ProxyUtils.create(ApplicationApi.class);
    private int id, rand;
    private String body = "{\n" +
            "  \"description\": \"string\",\n" +
            "  \"name\": \"string\",\n" +
            "  \"owner\": \"string\"\n" +
            "}";

    @BeforeTest
    public void before() {
        Common common = new Common();
        rand = common.getRandomNumber();
        body = JsonUtils.setjsonData(body, "name", "String" + rand);
        body = JsonUtils.setjsonData(body, "owner", "String" + rand);
        body = JsonUtils.setjsonData(body, "description", "String" + rand);

    }

    @Test
    public void testPost() {
        response = applicationApi.createAnApplication(body);
        response.then().statusCode(201);
        String location = response.header("location");
        String[] arr = location.split("/");
        id = Integer.parseInt(arr[arr.length - 1]);
        System.out.println(id);
    }

    @Test(dependsOnMethods = "testPost")
    public void testGet() {
        response = applicationApi.getAnApplication(id);
        response.then().statusCode(200);
//        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        assertThat(items.size(), equalTo(10));
        response.then().body("id", equalTo(id));
        response.then().body("description", equalTo("String" + rand));
        response.then().body("name", equalTo("String" + rand));
        response.then().body("owner", equalTo("String" + rand));
        response.prettyPrint();
    }

    @Test(dependsOnMethods = "testGet")
    public void testPut() {
        body = JsonUtils.setjsonData(body, "name", "String_" + rand);
        body = JsonUtils.setjsonData(body, "owner", "String_" + rand);
        body = JsonUtils.setjsonData(body, "description", "String_" + rand);
        body = JsonUtils.addjsonData(body, "id", id);
        response = applicationApi.putAnApplication(body);
        response.then().statusCode(200);
        response.then().body("id", equalTo(id));
        response.then().body("description", equalTo("String_" + rand));
        response.then().body("name", equalTo("String_" + rand));
        response.then().body("owner", equalTo("String_" + rand));
        response.prettyPrint();
    }

    @Test(dependsOnMethods = "testPut")
    public void testDelete() {
        response = applicationApi.deleteAnApplication(id);
        response.then().statusCode(204);
//        List<Map<String, Object>> items = response.as(new TypeRef<List<Map<String, Object>>>() {});
//        assertThat(items.size(), equalTo(10));
        response = applicationApi.getAnApplication(id);
        response.then().statusCode(404);
    }


}
