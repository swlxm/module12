package net.automation.android.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class CommonActions {

	public static void click(AppiumDriver<?> driver, WebElement element) {
		element.click();
	}
	
	public static WebElement findElement(AppiumDriver<?> driver, By by) {
		WebElement element = driver.findElement(by);
		return element;
	}
}
