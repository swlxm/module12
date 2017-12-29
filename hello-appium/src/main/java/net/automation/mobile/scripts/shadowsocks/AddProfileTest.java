package net.automation.mobile.scripts.shadowsocks;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Keyboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import javafx.scene.input.KeyCode;
import net.automation.mobile.scripts.AppiumTestCase;
import net.automation.mobile.util.AppiumConstants;
import net.automation.mobile.util.AppiumDataProvider;
import net.automation.mobile.util.AppiumException;
import net.automation.mobile.util.CommonActions;
import net.automation.mobile.util.DirectionEnum;

public class AddProfileTest extends AppiumTestCase implements ShadowsocksConstants {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String device, port;
	
	@Parameters({"device", "port"})
	public AddProfileTest(String device, String port) {
		this.device = device;
		this.port = port;
	}
	
	@Test(dataProvider="profile", dataProviderClass=AppiumDataProvider.class)
	public void testAddProfile(String profileName, String server, String remotePort, String pwd, String encyptMethod) throws AppiumException {
		try {
			driver.findElementByXPath("//android.widget.TextView[@text='" + server + ":" + remotePort + "']");
			logger.error("The profile [" + server + ":" + remotePort + "] on device [" + device + "] already exists.");
			//The profile does already exist, throws exception
			throw new AppiumException("The profile [" + server + ":" + remotePort + "] on device [" + device + "] already exists.");
		} catch(NoSuchElementException ex) {
			logger.info("Creating new profile [" + server + ":" + remotePort + "] on device [" + device + "] ...");
		}
		CommonActions.tap(driver.findElementByAccessibilityId(content_desc_add_profile));
		CommonActions.tap(driver.findElementByXPath(xpath_textview_manual_settings));
		CommonActions.tap(driver.findElementByXPath(xpath_textview_profile_name));
		CommonActions.sendKeys(driver.findElementByXPath(xpath_textedit_edit), profileName);
		CommonActions.tap(driver.findElementByXPath(xpath_button_ok));
		CommonActions.tap(driver.findElementByXPath(xpath_textview_server));
		CommonActions.sendKeys(driver.findElementByXPath(xpath_textedit_edit), server);
		CommonActions.tap(driver.findElementByXPath(xpath_button_ok));
		CommonActions.tap(driver.findElementByXPath(xpath_textview_remote_port));
		CommonActions.sendKeys(driver.getKeyboard(), remotePort);
		CommonActions.tap(driver.findElementByXPath(xpath_button_ok));
		CommonActions.tap(driver.findElementByXPath(xpath_textview_password));
		CommonActions.sendKeys(driver.findElementByXPath(xpath_textedit_edit), pwd);
		CommonActions.tap(driver.findElementByXPath(xpath_button_ok));
		CommonActions.tap(driver.findElementByXPath(xpath_textview_encrypt_method));
		AndroidElement listView = driver.findElementByClassName("android.widget.ListView");
		int duration = y_screen/5;
		CommonActions.swipe(driver, listView, DirectionEnum.DOWN, duration);
//		TouchAction t_action = new TouchAction(driver);
//		t_action.press(listView).moveTo(0, 200).release().perform();
		CommonActions.tap(driver.findElementByXPath("//android.widget.CheckedTextView[@text='" + encyptMethod + "']"));
		CommonActions.tap(driver.findElementByAccessibilityId(content_desc_apply));
		AndroidElement checkpoint = driver.findElementByXPath("//android.widget.TextView[@text='" + server + ":" + remotePort + "']");
		Assert.assertNotNull(checkpoint);
		logger.info("Create new profile successfully [" + profileName + server + remotePort + encyptMethod + "]");
	}
}
