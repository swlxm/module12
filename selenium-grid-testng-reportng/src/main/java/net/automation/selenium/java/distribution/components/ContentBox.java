package net.automation.selenium.java.distribution.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.automation.selenium.java.distribution.util.AttributeTypeEnum;
import net.automation.selenium.java.distribution.util.Action;
import net.automation.selenium.java.distribution.util.NGAssert;

/**
 * The common functions related this page will be defined here
 * all the web element should be initial by DriverUtil.findElement method and do more operation if not null
 * @author Samuel
 *
 */
public class ContentBox implements IContentBox {

	private WebDriver driver;
	
	public ContentBox(WebDriver driver) {
		this.driver = driver;
	}

	//TODO
	
	public void commonFuncOnContentBox() {
//		private WebElement el = Action.findElement(driver, By.xpath(xpath), 0);
//		Action.clickElement(driver, el);
	}
}
