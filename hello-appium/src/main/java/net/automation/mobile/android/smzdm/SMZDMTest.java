package net.automation.mobile.android.smzdm;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import net.automation.mobile.android.AndroidTestCase;
import net.automation.mobile.util.AppiumConstants;

public class SMZDMTest extends SMZDMTestCase {
	
	
	@Test
	public void testDialer() {
		WebElement el = null;
		try {
			Thread.sleep(5000);
			el = driver.findElementByAndroidUIAutomator("text(\"更新\")");
			driver.findElementByAndroidUIAutomator("text(\"否\")").click();
		} catch(Exception ex) {
			
		}
		try {
			el = driver.findElementById("close");
			el.click();
		} catch(Exception ex) {
			
		}
		try {
			el = driver.findElementByAccessibilityId("搜索");
			el.click();
		} catch(Exception ex) {
			try {
				el = driver.findElementById("action_search");
				el.click();
			} catch(Exception e) {
				
			}
		}
		driver.findElementByAndroidUIAutomator("text(\"家电好价汇总\")").sendKeys("macbook pro");;
		driver.findElementById("iv_search").click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<AndroidElement> list = driver.findElementsByXPath("//android.support.v7.widget.RecyclerView[@resource-id='com.smzdm.client.android:id/list']/android.widget.FrameLayout");
		int i = list.size();
		list.get(0).click();
	}
}
