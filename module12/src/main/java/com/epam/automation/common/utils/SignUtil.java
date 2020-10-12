package com.epam.automation.common.utils;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class SignUtil {
    private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

    public static String createSign(String api_key, String nonce_str, String timestamp) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, Object> params = new HashMap<String, Object>();
        // apim auth info
        final String secret = "563fe3b3b4b4483191923bb1d4c55863";
        params.put("api_key", api_key);
        params.put("nonce_str", nonce_str);
        params.put("timestamp", timestamp);
        String sign = sign(params, secret);
        return sign;
    }

    public static String generatorSign(Map<String, Object> params, String body) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String secret = ConfigUtils.getSecret();
        if (!body.isEmpty()) {
            params.put("body", body);
        }
        String sign = sign(params, secret);
        logger.info("api_key = {}", params.get("api_key"));
        logger.info("nonce_str = {}", params.get("nonce_str"));
        logger.info("timestamp = {}", params.get("timestamp"));
        logger.info("Sign = {}", sign);
        if (!body.isEmpty()) {
            params.remove("body");
        }
        return sign;
    }

    private static String sign(Map<String, Object> params, String
        secret) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        List<String> paramList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            paramList.add(entry.getKey() + "=" + entry.
                getValue());
        }
        Collections.sort(paramList);
        StringBuilder sb = new StringBuilder();
        sb.append(secret);
        for (String paramStr : paramList) {
            sb.append(paramStr);
            sb.append("&");
        }
        sb.replace(sb.length() - 1, sb.length(), secret);
        MessageDigest instance = MessageDigest.getInstance("SHA-256");
        byte[] bytes = instance.digest(sb.toString().getBytes("UTF-8"));
        //logger.info("sign id " + Hex.encodeHexString(bytes).toUpperCase());
        return Hex.encodeHexString(bytes).toUpperCase();
    }

    private static String signature(String subscriptionKey, String subscriptionSecret, String isPublic, String timestamp) {
        String[] str = {subscriptionKey, subscriptionSecret, timestamp, isPublic};
        Arrays.sort(str); // 字典序排序
        String rawStr = str[0] + str[1] + str[2] + str[3];
        BigInteger sha = null;
        byte[] inputData = rawStr.getBytes();
        String signature = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(inputData);
            sha = new BigInteger(1, messageDigest.digest());
            signature = sha.toString(16);
        } catch (Exception e) {
            //
        }
        return signature;
    }

    public static void main(String[] args) {
        logger.info(signature("instore-is123", "563fe3b3b4b4483191923bb1d4c55863", "" + true, "636816015732251071"));
    }
}
