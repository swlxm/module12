package net.automation.mobile.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.AppiumDriver;

public class CommonActions {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void click(AppiumDriver<?> driver, WebElement element) {
		element.click();
	}
	
	public static WebElement findElement(AppiumDriver<?> driver, By by) {
		WebElement element = driver.findElement(by);
		return element;
	}
}
