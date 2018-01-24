package net.automation.mobile.testscripts.browser;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import net.automation.mobile.testscripts.AppiumTestCase;
import net.automation.mobile.util.AppiumDataProvider;
import net.automation.mobile.util.AppiumException;
import net.automation.mobile.util.CommonActions;
import net.automation.mobile.util.DirectionEnum;

public class LoginTest extends AppiumTestCase {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String device, port;
	
	@Parameters({"device", "port"})
	public LoginTest(String device, String port) {
		this.device = device;
		this.port = port;
	}
	
	@Test(dataProvider="login", dataProviderClass=AppiumDataProvider.class)
	public void testLogin(String id, String password) throws AppiumException {
		driver.get("http://www.baidu.com/");
		driver.findElementById("login").click();
		driver.findElementByLinkText("立即登录").click();
		driver.findElementById("login-username").sendKeys(id);
		driver.findElementById("login-password").sendKeys(password);
		driver.findElementById("login-submit").click();
	}

}
