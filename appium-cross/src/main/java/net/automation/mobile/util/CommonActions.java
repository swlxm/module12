package net.automation.mobile.util;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Keyboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class CommonActions {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void tap(MobileElement element) {
		element.click();
		try {
			Thread.sleep(AppiumConstants.SHORT_WAIT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void swipe(AppiumDriver<? extends MobileElement> driver, MobileElement element, Enum<DirectionEnum> direction, int duration) {
		TouchAction<?> t_action = new TouchAction<>(driver);
		Dimension d = driver.manage().window().getSize();
		int heigh = d.height;
		int width = d.width;
		Point center = element.getCenter();
		int x = center.getX();
		int y = center.getY();
		switch(direction.name()) {
			case "UP":
				t_action.press(PointOption.point(x, y)).moveTo(PointOption.point(0, -duration)).release().perform();
				break;
			case "DOWN":
				t_action.press(PointOption.point(x, y)).moveTo(PointOption.point(0, duration)).release().perform();
				break;
			case "LEFT":
				t_action.press(PointOption.point(x, y)).moveTo(PointOption.point(-duration, 0)).release().perform();
				break;
			case "RIGHT":
				t_action.press(PointOption.point(x, y)).moveTo(PointOption.point(duration, 0)).release().perform();
				break;
		}
	}

	public static void sendKeys(MobileElement element, CharSequence keys) {
		element.clear();
		element.sendKeys(keys);
		
		try {
			Thread.sleep(AppiumConstants.SHORT_WAIT);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendKeys(Keyboard keyboard, CharSequence keys) {
		keyboard.sendKeys(keys);
		
		try {
			Thread.sleep(AppiumConstants.MIDDLE_WAIT);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
