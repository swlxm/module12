package net.automation.mobile.testscripts.browser;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import net.automation.mobile.testscripts.AppiumTestCase;
import net.automation.mobile.util.AppiumAssert;
import net.automation.mobile.util.AppiumDataProvider;
import net.automation.mobile.util.AppiumException;
import net.automation.mobile.util.CommonActions;
import net.automation.mobile.util.DirectionEnum;

public class SearchTest extends AppiumTestCase {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String device, port;
	
	@Parameters({"device", "port"})
	public SearchTest(String device, String port) {
		this.device = device;
		this.port = port;
	}
	
	public void testSearch() throws AppiumException {
		driver.get("http://www.google.com/");
		driver.findElementById("lst-ib").sendKeys("appium");
		driver.findElementById("tsbb").click();
		TouchAction action = new TouchAction(driver);
		action.press(500, 1000).moveTo(-100, 0).release().perform();
		List<WebElement> links = (List<WebElement>) driver.findElementsByTagName("a");
		for(WebElement el:links) {
			if(el.getAttribute("href").equals("http://appium.io/")) {
				el.click();
				break;
			}
		}
		WebElement button = driver.findElementByXPath("//a[text()='Download Appium']");
		AppiumAssert.assertNotNull(button);
	}

}
