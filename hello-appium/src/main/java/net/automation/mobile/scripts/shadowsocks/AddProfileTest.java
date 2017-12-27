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

public class AddProfileTest extends AppiumTestCase {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@BeforeTest
	@Parameters("port")
	public void setUp(String port) throws Exception {
		super.setUp(port, "com.github.shadowsocks", ".MainActivity");
	}
	
	@AfterTest
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test(dataProvider="profile", dataProviderClass=AppiumDataProvider.class)
	public void testAddProfile(String profileName, String server, String remotePort, String pwd, String encyptMethod) throws AppiumException {
		try {
			driver.findElementByXPath("//android.widget.TextView[@text='" + server + ":" + remotePort + "']");
			logger.error("The profile [" + server + ":" + remotePort + "] already exists.");
			throw new AppiumException("The profile [" + server + ":" + remotePort + "] already exists.");
		} catch(NoSuchElementException ex) {
		}
		driver.findElementByAccessibilityId("Add Profile").click();
		driver.findElementByXPath("//android.widget.TextView[@text='Manual Settings']").click();
		driver.findElementByXPath("//android.widget.TextView[@text='Profile Name']").click();
		driver.findElementByXPath("//android.widget.EditText[@resource-id='android:id/edit']").sendKeys(profileName);
		driver.findElementByXPath("//android.widget.Button[@text='OK']").click();
		driver.findElementByXPath("//android.widget.TextView[@text='Server']").click();
		AndroidElement serverEdit = driver.findElementByXPath("//android.widget.EditText[@resource-id='android:id/edit']");
		serverEdit.clear();
		serverEdit.sendKeys(server);
		driver.findElementByXPath("//android.widget.Button[@text='OK']").click();
		driver.findElementByXPath("//android.widget.TextView[@text='Remote Port']").click();
		Keyboard keyboard = driver.getKeyboard();
		keyboard.sendKeys(remotePort);
		driver.findElementByXPath("//android.widget.Button[@text='OK']").click();
		driver.findElementByXPath("//android.widget.TextView[@text='Password']").click();
		AndroidElement pwdEdit = driver.findElementByXPath("//android.widget.EditText[@resource-id='android:id/edit']");
		pwdEdit.clear();
		pwdEdit.sendKeys(pwd);
		driver.findElementByXPath("//android.widget.Button[@text='OK']").click();
		driver.findElementByXPath("//android.widget.TextView[@text='Encrypt Method']").click();
		AndroidElement listView = driver.findElementByClassName("android.widget.ListView");
		TouchAction t_action = new TouchAction(driver);
		t_action.press(listView).moveTo(0, 200).release().perform();
		driver.findElementByXPath("//android.widget.CheckedTextView[@text='" + encyptMethod + "']").click();
		driver.findElementByAccessibilityId("Apply").click();
		AndroidElement checkpoint = driver.findElementByXPath("//android.widget.TextView[@text='" + server + ":" + remotePort + "']");
		Assert.assertNotNull(checkpoint);
		logger.info("Create new profile successfully [" + profileName + server + remotePort + encyptMethod + "]");
	}
}
