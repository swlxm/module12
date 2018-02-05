package com.epam.automation.mobile.appium_training;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Utils {
	
	public static void sync(WebDriver driver, int timeout) {
		int i = 0;
		try {
			Thread.sleep(1000);
			while(driver.findElement(By.cssSelector(".emirates-ajaxloader")) != null && i < timeout) {
				Thread.sleep(1000);
				i++;
			}
			if(i == timeout) {
				throw new Exception("timeout");
			}
		} catch(Exception ex) {
			
		}
	}

}
