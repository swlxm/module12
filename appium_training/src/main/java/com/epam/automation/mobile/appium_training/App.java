package com.epam.automation.mobile.appium_training;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.util.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.touch.offset.PointOption;

/**
 * Hello world!
 *
 */
public class App 
{
	private WebDriver driver;

	@BeforeClass
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		capabilities.setCapability(AndroidMobileCapabilityType.BROWSER_NAME, MobileBrowserType.CHROME);
		driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	}
	
	@Test
	public void testFlightBooking() throws Exception {
        driver.get("http://www.emirates.com/");
        clickBookingFlight();
        driver.findElement(By.xpath("//span[text()='One way']")).click();
        driver.findElement(By.id("departureId")).click();
        driver.findElement(By.id("autocompleteId_fromCity")).sendKeys("shanghai");
        selectCity("shanghai");
        driver.findElement(By.id("arrivalId")).click();
        driver.findElement(By.id("autocompleteId_toCity")).sendKeys("male");
        selectCity("male");
        driver.findElement(By.xpath("//span[text()='Select date']")).click();
        driver.findElement(By.linkText("14")).click();
        driver.findElement(By.xpath("//div[text()='See results']")).click();
        Utils.sync(driver, 10);
        selectFlight();
        driver.findElement(By.cssSelector("#mab_cbs2_cmf")).click();
        
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
	private void clickBookingFlight() {
		driver.findElement(By.id("hom_hom_bookFlt")).click();
//        driver.findElement(By.xpath("//div[@title='Book A Flight']")).click();
//        driver.findElement(By.cssSelector("#hom_hom_bookFlt")).click();
//        driver.findElement(By.cssSelector("div[title='Book A Flight']")).click();
//        driver.findElement(By.cssSelector(".custom-font.link-alt.cf.pl-32.pr-0")).click();
		Utils.sync(driver, 10);
	}
	
	private void selectCity(String cityName) {
		List<WebElement> cities = driver.findElements(By.xpath("//li[@ng-repeat='item in airportList track by $index'][@role='button']"));
		int size = cities.size();
		for(WebElement city:cities) {
			WebElement el = city.findElement(By.xpath(".//strong"));
			String text = el.getText();
			if(text.trim().toLowerCase().startsWith(cityName.toLowerCase())) {
				el.click();
				break;
			}
		}
	}
	
	private void selectFlight() {
		WebElement containerList = driver.findElement(By.id("sortContainer"));
		WebElement container = containerList.findElement(By.xpath("./div[1]"));
		String index = container.getAttribute("id").split("_")[1];
		container.click();
		Utils.sync(driver, 10);
		driver.findElement(By.id("compareAccordianDiv_" + index)).click();
		
	}
	
}
