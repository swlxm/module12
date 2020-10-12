package com.epam.automation.common.utils;

import com.epam.automation.common.GlobalVar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.Properties;

public class ConfigUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
    private static Properties props;
    private static String filename_ = GlobalVar.CONFIGFILENAME;

    synchronized static private void loadProps(String fileName) {
        logger.info("start load properties file.......");
        logger.info(fileName);
        props = new Properties();
        InputStream in = null;
        try {
            //第一种，通过类加载器进行获取properties文件流
            in = ConfigUtils.class.getClassLoader().getResourceAsStream(fileName);
            //第二种，通过类进行获取properties文件流
            //in = ConfigUtils.class.getResourceAsStream(fileName);
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error(fileName + "file not find");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("jdbc.properties file stream close exception");
            }
        }
        logger.info("load properties file complete...........\n");
    }

    public static void setProperty(String fileName, String keyname, String keyvalue) {
        URL url = ConfigUtils.class.getResource("/" + fileName);
        if (null == props) {
            loadProps(fileName); //必须在输出流实例化之前加载
        }
        OutputStream fos = null;
        try {
            // 打开文件的方式默认是覆盖，就是一旦执行了下面这句，那么原有文件中的内容被清空
            // 所以在要先加载loadProps(fileName); 否则文件原来的数据会丢失
            fos = new FileOutputStream(url.getFile());

            props.setProperty(keyname, keyvalue);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            props.store(fos, "Update '" + keyname + "' value");
        } catch (IOException e) {
            logger.error("update property file error:" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getProperty(String fileName, String key, String defaultValue) {
        if (null == props) {
            loadProps(fileName);
        }
        return props.getProperty(key, defaultValue);
    }

    public static Properties getAll(String fileName) {
        logger.info("file name：" + fileName);
        logger.info("props:" + props);
        if (null == props) {
            loadProps(fileName);
        } else {
            if (!props.propertyNames().equals(fileName)) {
                loadProps(fileName);
            }
        }
        return props;
    }

    public static String getApiKey() {
        return getProperty(filename_, "api_key", "instore-is123");
    }

    public static String getNonceStr() {
        return getProperty(filename_, "nonce_str", "EPAM-is123");
    }

    public static String getSecret() {
        return getProperty(filename_, "secret", "563fe3b3b4b4483191923bb1d4c55863");
    }

    public static String getAzureAPIM() {
        return getProperty(filename_, "AzureAPIM", "false");
    }

    public static String getProperty(String key) {
        return getProperty(filename_, key,"");
    }

    public static String getProperty(String key,String defaultValue) {
        return getProperty(filename_, key,defaultValue);
    }

    public static String getSubscriptionKey() {
        return getProperty(filename_, "subscriptionKey", "ac71fab6d89643b3b89196b2575d5167");
    }

    public static String getHostAddress() {
        return getProperty(filename_, "hostAddress", "localhost");
    }

    public static String getToken() {
        return getProperty(filename_, "token", "");
    }

}
