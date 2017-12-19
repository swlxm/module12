package net.automation.selenium.jenkins.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * A class to parse the property files stored in resources folder.
 * @author Samuel
 *
 */
public class PropertyParser {
	
	private Map<String , String> propertyMap;
	private Properties properties = new Properties();
	
	public PropertyParser(String fileName) {
		parse(fileName);
	}
	
	
	/**
	 * Return a string value according to the key.
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		String value = propertyMap.get(key);
		if(value == null)
			throw new RuntimeException("Can't find the value: " + value + " with key: " + key);
		return value;
	}
	
	/**
	 * Return a boolean value according to the key.
	 * @param key
	 * @return
	 */
	public boolean getPropertyAsBoolean(String key) {
		return Boolean.parseBoolean(propertyMap.get(key));
	}

	public int getPropertyAsInt(String key) {
		return Integer.parseInt(propertyMap.get(key));
	}

	private Map<String, String> parse(String fileName) {
		InputStream is = PropertyParser.class.getClassLoader().getResourceAsStream(fileName);
		try {
			properties.load(is);
			propertyMap = new HashMap<String, String>();
			for(String key:properties.stringPropertyNames()) {
				String value = properties.getProperty(key);
				propertyMap.put(key, value);
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		} finally {
			if(is != null)
				try {
					is.close();
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
		}
		return propertyMap;
	}
}
