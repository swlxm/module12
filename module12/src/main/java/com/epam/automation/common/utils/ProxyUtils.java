package com.epam.automation.common.utils;

import com.epam.automation.common.annotation.*;
import com.epam.automation.common.entity.TestStep;
import com.epam.automation.common.enums.HttpType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProxyUtils {
    private static Logger logger = LoggerFactory.getLogger(ProxyUtils.class);

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz) {

        // 获取接口上的HOST
        Annotation annotation = clazz.getAnnotation(Server.class);
        if (annotation == null) {
            throw new RuntimeException(String.format("接口类%s未配置@SERVER注解",
                clazz.getName()));
        }

        String host = ConfigUtils.getHostAddress().trim() + clazz.getAnnotation(Server.class).value().toString();
         /*
        switch (clazz.getAnnotation(SERVER.class).value()){
            case GlobalVar.DOUBAN_MOVIE_SERVER:
                host = GlobalVar.DOUBAN_MOVIE_SERVER_URL;
                break;
            case GlobalVar.PG_MICROSERVICE_TRANSACTIONS_SERVER:
                host = GlobalVar.USER_URL;
                break;
            case GlobalVar.PG_MICROSERVICE_PRODUCTS_SERVER:
                host = GlobalVar.PG_INSTORE_PRODUCTS_URL;
                break;
            default:
                throw new RuntimeException(String.format("未查找到接口类%s配置的@HOST(%s)注解中的%s接口服务器地址",
                        clazz.getName(),
                        clazz.getAnnotation(SERVER.class).value(),
                        clazz.getAnnotation(SERVER.class).value()));
        }
        */


        HttpUtils httpUtils = new HttpUtils(host);

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
            new Class[]{clazz},
            new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    // 方法上的注释及对应的值
                    Annotation[] annotations = method.getAnnotations();
                    if (annotations.length == 0) {
                        throw new RuntimeException(String.format("%s方法未配置请求类型注解，如@POST、@GET等",
                            method.getName()));
                    }

                    HttpType httpType;
                    String path;
                    String description;
                    String Body = "";

                    // 当前只需要解析一个注解
                    if (annotations[0] instanceof Post) {
                        httpType = HttpType.POST;
                        path = ((Post) annotations[0]).path();
                        description = ((Post) annotations[0]).description();
                    } else if (annotations[0] instanceof Put) {
                        httpType = HttpType.PUT;
                        path = ((Put) annotations[0]).path();
                        description = ((Put) annotations[0]).description();
                    } else if (annotations[0] instanceof Delete) {
                        httpType = HttpType.DELETE;
                        path = ((Delete) annotations[0]).path();
                        description = ((Delete) annotations[0]).description();
                    } else if (annotations[0] instanceof Get) {
                        httpType = HttpType.GET;
                        path = ((Get) annotations[0]).path();
                        description = ((Get) annotations[0]).description();
                    } else {
                        throw new RuntimeException(String.format("暂不支持%s方法配置的请求类型注解%s",
                            method.getName(),
                            annotations[0].annotationType()));
                    }

                    // 方法上参数对应的注解
                    Annotation[][] parameters = method.getParameterAnnotations();
                    Integer length = parameters.length;
                    TestStep testStep = new TestStep();
                    testStep.setBody("");
                    boolean removeId = false;
                    if (length != 0) {
                        Map<String, Object> map = new HashMap<>();
                        for (Integer i = 0; i < length; i++) {
                            // 参数注解类型
                            Annotation[] annos = parameters[i];
                            if (annos.length == 0) {
                                throw new RuntimeException(String.format("方法%s中缺少参数注解，如@Param",
                                    method.getName()));
                            }

                            if (annos[0] instanceof Param) {
                                String param = args[i].toString();
                                if (!param.isEmpty())
                                    map.put((((Param) annos[0]).value()), args[i]);
                            } else if (annos[0] instanceof PathVariable) {
                                path = path.replaceFirst("\\{\\}", args[i].toString());

                            } else if (annos[0] instanceof com.epam.automation.common.annotation.Body) {
                                Body = args[i].toString();
                            } else {
                                throw new RuntimeException(String.format("暂不支持方法%s中配置的参数注解%s",
                                    method.getName(),
                                    annos[0].annotationType()));
                            }
                        }
                        if (ConfigUtils.getAzureAPIM().trim().equals("true")) {
                            map.put("api_key", ConfigUtils.getApiKey());
                            map.put("nonce_str", ConfigUtils.getNonceStr());
                            map.put("timestamp", TimeUtils.getLocalFormatTime());
                            String signstr = SignUtil.generatorSign(map, Body);
                            map.put("sign", signstr);
                        }
                        testStep.setParams(map);
                        testStep.setBody(Body);
                    } else {
                        Map<String, Object> map = new HashMap<>();
                        if (ConfigUtils.getAzureAPIM().trim().equals("true")) {
                            map.put("api_key", ConfigUtils.getApiKey());
                            map.put("nonce_str", ConfigUtils.getNonceStr());
                            map.put("timestamp", TimeUtils.getLocalFormatTime());
                            String signstr = SignUtil.generatorSign(map, "");
                            map.put("sign", signstr);
                        }
                        testStep.setParams(map);
                    }

                    testStep.setType(httpType);
                    testStep.setPath(path);

                    logger.info("[" + path + "]" + description);
                    return httpUtils.request(testStep);
                }
            });
    }
}
