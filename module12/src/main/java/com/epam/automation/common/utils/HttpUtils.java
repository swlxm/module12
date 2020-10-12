package com.epam.automation.common.utils;

import com.epam.automation.common.GlobalVar;
import com.epam.automation.common.entity.TestStep;
import com.epam.automation.common.enums.HttpType;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE;

//import static GlobalVar.listenerUtils;

public class HttpUtils {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RestAssuredConfig restAssuredConfig;
    private Response response;
    private String baseURL;
    private String proxyurl;
    private int proxyport;


    /**
     * 构造方法
     */
    HttpUtils(String url) {
        baseURL = url;
        proxyurl = "10.72.1.205";
        proxyport = 8080;

        //RestAssured.proxy(proxyurl,proxyport);
        //logger.info("proxy host is "+proxyurl+" port is "+proxyport);
        // 根据需要进行设置
        restAssuredConfig = config()
            .jsonConfig(jsonConfig().numberReturnType(DOUBLE));
    }


    /**
     * 获取本次请求的URL，携带参数
     *
     * @param path
     * @param params
     */
    private String getRequestInfo(String path, Map<String, Object> params) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : params.keySet()) {
            stringBuilder.append(key).append("=").append(params.get(key)).append("&");
        }

        if (stringBuilder.length() >= 1 && stringBuilder.toString().endsWith("&")) {
            stringBuilder = new StringBuilder(stringBuilder.substring(0, stringBuilder.length() - 1));
        }

        return getRequestInfo(path) + "?" + stringBuilder;
    }

    /**
     * 获取本次请求的URL，不携带参数
     *
     * @param path
     */
    private String getRequestInfo(String path) {
        return RestAssured.baseURI + path;
    }

    /**
     * 获取本次请求的响应信息
     *
     * @param response
     */
    private String getResponseInfo(Response response) {

        // TODO - 此处容易抛异常
        if (response.contentType().contains("json")) {
            try {
                return "[" + response.statusCode() + "]" + response.jsonPath().get();
            }
            catch(Exception e){
                return "[" + response.statusCode() + "]" + response.htmlPath().get();
            }
        } else {
            return "[" + response.statusCode() + "]" + response.htmlPath().get();
        }
    }

    /**
     * 装载此次请求配置
     *
     * @param path
     */
    private RequestSpecification getRequestSpecification(String path) {
        RequestSpecification spec = new RequestSpecBuilder().setContentType("application/json").setBaseUri(baseURL).build();
        RestAssured.registerParser("text/plain", Parser.JSON);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (RestAssured.baseURI.startsWith("https://qa-zuul-b2c-instore.cn-xnp-cloud-pg.com.cn")) {
            return given().relaxedHTTPSValidation("SSL").headers(GlobalVar.HEADERS).spec(spec).cookies(GlobalVar.COOKIES).config(restAssuredConfig).basePath(path).trustStore("/truststore_javanet.jks", "Epam1234");
        } else if (RestAssured.baseURI.contains("https")) {
            return given().relaxedHTTPSValidation("SSL").headers(GlobalVar.HEADERS).spec(spec).cookies(GlobalVar.COOKIES).config(restAssuredConfig).basePath(path);
        } else {
            return given().headers(GlobalVar.HEADERS).spec(spec).cookies(GlobalVar.COOKIES).config(restAssuredConfig).basePath(path);
        }
    }

    /**
     * 携带参数的请求
     *
     * @param httpType
     * @param path
     * @param params
     */
    private Response request(HttpType httpType, String path, Map<String, Object> params, String strbody) {
        switch (httpType) {
            case GET:
                response = getRequestSpecification(path).params(params).get();
                break;
            case POST:
                if (!strbody.isEmpty())
                    response = getRequestSpecification(path).queryParams(params).body(strbody).post();
                else
                    response = getRequestSpecification(path).params(params).post();
                break;
            case DELETE:
                response = getRequestSpecification(path).params(params).delete();
                break;
            case PUT:
                if (!strbody.isEmpty())
                    response = getRequestSpecification(path).params(params).body(strbody).put();
                else
                    response = getRequestSpecification(path).params(params).put();
                break;
            default:
                throw new RuntimeException(String.format("暂不支持%s请求类型", httpType));
        }
        return response;
    }

    private Response request(HttpType httpType, String path, String params) {
        switch (httpType) {
            case GET:
                response = getRequestSpecification(path).body(params).get();
                break;
            case POST:
                response = getRequestSpecification(path).body(params).post();
                break;
            case PUT:
                response = getRequestSpecification(path).body(params).put();
                break;
            default:
                throw new RuntimeException(String.format("暂不支持%s请求类型", httpType));
        }
        return response;
    }

    /**
     * 不携带参数的请求
     *
     * @param httpType
     * @param path
     */
    private Response request(HttpType httpType, String path) {
        logger.info("[" + httpType.getValue() + "]" + getRequestInfo(path));
        try {
            switch (httpType) {
                case GET:
                    response = getRequestSpecification(path).get();
                    break;
                case POST:
                    response = getRequestSpecification(path).post();
                    break;
                case DELETE:
                    response = getRequestSpecification(path).delete();
                    break;
                default:
                    throw new RuntimeException(String.format("暂不支持%s请求类型", httpType));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return response;
    }

    /**
     * 请求
     *
     * @param testStep
     */
    public Response request(TestStep testStep) {
//        if (testStep.getPath().contains("authenticate"))
//            RestAssured.baseURI = GlobalVar.PG_INSTORE_AUTHENICATE_URL;
//        else {
//            RestAssured.baseURI = baseURL;
//        }
        logger.info("| [{}] | {} | ", testStep.getType().getValue(), getRequestInfo(testStep.getPath()));
        logger.debug("| [Body] | {} |", testStep.getBody().replaceAll("\r|\n|\t", ""));
        String params = testStep.getParams().toString();
        params = params.replace(", ", "&");
        logger.debug("| [Parameter] | {} |", params);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        if (!testStep.getParams().isEmpty()) {
            response = request(testStep.getType(), testStep.getPath(), testStep.getParams(), testStep.getBody());
        } else if (!testStep.getBody().isEmpty()) {
            response = request(testStep.getType(), testStep.getPath(), testStep.getBody());
        } else {
            response = request(testStep.getType(), testStep.getPath());
        }

        logger.trace(getResponseInfo(response));
        logger.info(" Http Code | [{}] | ", response.statusCode());
        stopWatch.stop();
        logger.info(" | timecost(ms) | [{}]", stopWatch.getTime());
        if(response.statusCode()!=200 && response.statusCode()!=201)
            logger.trace("[response] {} ", response.prettyPrint());
        return response;
    }
}

