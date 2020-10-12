package com.epam.automation.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.epam.automation.common.GlobalVar;

import java.time.Instant;
import java.time.ZonedDateTime;

public class JsonUtils {
    public static final Instant NOW = Instant.now();

    public static String setjsonData(String jsonString, String key, Object value) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        if (jsonObject.get(key) != null)
            jsonObject.put(key, value);
        return jsonObject.toJSONString();
    }

    public static String putjsonData(String jsonString, String key, Object value) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        jsonObject.putIfAbsent(key, value);
        return jsonObject.toJSONString();
    }

    public static String setJsonArrayData(String jsonString, String ArrayKey, int index, String key, Object value) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        JSONArray jsarr = jsonObject.getJSONArray(ArrayKey);
        if (jsarr.toArray().length > index) {
            JSONObject ao = jsarr.getJSONObject(index);
            if (ao.get(key) != null) {
                ao.put(key, value);
            }
        }

        return jsonObject.toJSONString();
    }

    public static String addjsonData(String jsonString, String key, Object value) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        if (jsonObject.get(key) == null)
            jsonObject.put(key, value);
        return jsonObject.toJSONString();
    }

    public static String removejsonData(String jsonString, String key) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        if (jsonObject.get(key) == null)
            jsonObject.remove(key);
        return jsonObject.toJSONString();
    }

    public static String getDataValueByKey(String jsonString, String key) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        if (jsonObject.get(key) != null)
            return jsonObject.get(key).toString();
        else
            return "";
    }

    /*
    public static String GetJsonStr(String JsonName)
    {
        String JsonStr="";
        switch(JsonName) {
            case "QueryReturnJson":
                JsonStr= QueryReturnJson;
                break;
            case "ReturnJson":
                JsonStr= ReturnJson;
                break;
            case "ACCOUNT_JSON":
                JsonStr= ACCOUNT_JSON;
                break;
            case "PostJson":
                JsonStr= PostJson;
                break;
            case "TagPostJson":
                JsonStr=TagPostJson;
                break;
            default:
                JsonStr = "";
                break;
        }
        return JsonStr;
    }
    */
    public static String initJson(String jsonStr) {
        String updatedJsonStr = "";
        updatedJsonStr = setjsonData(jsonStr, "beginPurchaseDate", ZonedDateTime.now());
        updatedJsonStr = setjsonData(updatedJsonStr, "endPurchaseDate", ZonedDateTime.now());
        updatedJsonStr = setjsonData(updatedJsonStr, "purchaseDate", ZonedDateTime.now());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return setjsonData(updatedJsonStr, "returnedDate", ZonedDateTime.now());
    }

    //@Test
    public void testSetjsonData() {
        //String newJson=setjsonData(QueryReturnJson,"memberId","00101290335");
        //JSONObject jsonObject=JSON.parseObject(newJson);
        //assertTrue(jsonObject.getString("memberId").equals("00101290335"));
        //newJson=setjsonData(QueryReturnJson,"MEMBER_ID","00101290335");
        //jsonObject=JSON.parseObject(newJson);
        //assertTrue(jsonObject.getString("memberId").equals("00101290336"));

        //jsonObject = JSON.parseObject(initJson(PostJson));
        //System.out.println(jsonObject.getString("purchaseDate").toString());
        //System.out.println(jsonObject.getString("returnedDate").toString());

    }

}
