package net.automation.selenium.java.distribution.util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Define some common actions to handle cross browsers testing.
 * @author Samuel
 *
 */
public class Action {

	private static final Logger logger = LoggerFactory.getLogger(Action.class);
	
	private static By by = null;
	
	public static void get(WebDriver driver, String url) {
		driver.get(url);
	}
	
	/**
	 * Return a web element or null if element not found.
	 * @param driver
	 * @param by
	 * @param timeout The default waiting time will be used if 0.
	 * @return Return a web element or null if element not found.
	 */
	public static WebElement findElement(WebDriver driver, By by, int timeout) {
		if(timeout == 0)
			timeout = Constants.DEFAULT_WAITING_TIME;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		WebElement element = null;
		try {
			element = wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch(TimeoutException ex) {
			if(driver.getClass().toString().endsWith("EdgeDriver")) {
	    		String b = by.toString();
	    		String js = "";
	    		if(b.contains("id:")) {
	    			String id = b.split(":")[1].trim();
	    			js = "return document.getElementById('" + id + "');";
	    		} else if(b.contains("name:")) {
	    			String name = b.split(":")[1].trim();
	    			js = "return document.getElementByName('" + name + "');";
	    		} else {
	    			String xpath = b.split(":")[1].trim();
	    			js = "return document.evaluate(\"" + xpath + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;";
	    		}
				element = (WebElement) ((JavascriptExecutor)driver).executeScript(js, "");
				((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
			} else {
				logger.info("Element not found: {}", by.toString());
				Action.by = by;
				element = null;
			}
		}
		return element;
	}
	
	
	/**
	 * The script will wait specified seconds before executing next statement.
	 * @param driver
	 * @param time
	 */
	public static void wait(WebDriver driver, int time) {
		try {
			Thread.sleep(time*1000);
		} catch(Exception ex) {}
	}

    /**
     * Used by PageFactory mode.
     * @param driver
     * @param element
     */
    public static void doubleClickElement(WebDriver driver, WebElement element) {
		if(element == null)
			throw new NoSuchElementException("Element not found: " + by.toString());
    	if(driver.getClass().toString().endsWith("FirefoxDriver")) {
    		element.click();
    		((JavascriptExecutor)driver).executeScript("var evt = new MouseEvent('dblclick', {'view':window, 'bubbles':true, 'cancelable':true}); arguments[0].dispatchEvent(evt);", element);
    	} else if(driver.getClass().toString().endsWith("EdgeDriver")) {
    		element.click();
    		((JavascriptExecutor)driver).executeScript("var evt = new MouseEvent('dblclick', {'view':window, 'bubbles':true, 'cancelable':true}); arguments[0].dispatchEvent(evt);", element);
    	} else {
    		Actions builder = new Actions(driver);
    		builder.doubleClick(element).build().perform();
    	}
	}
    
    /**
     * Used by PageFactory mode.
     * @param driver
     * @param element
     */
    public static void rightClickElement(WebDriver driver, WebElement element) {
		if(element == null)
			throw new NoSuchElementException("Element not found: " + by.toString());
    	if(driver.getClass().toString().endsWith("FirefoxDriver")) {
    		element.click();
    		((JavascriptExecutor)driver).executeScript("var evt = new MouseEvent('contextmenu', {'view':window, 'bubbles':true, 'cancelable':true}); arguments[0].dispatchEvent(evt);", element);
    	} else if(driver.getClass().toString().endsWith("EdgeDriver")) {
    		element.click();
    		((JavascriptExecutor)driver).executeScript("var evt = new MouseEvent('contextmenu', {'view':window, 'bubbles':true, 'cancelable':true}); arguments[0].dispatchEvent(evt);", element);
    	} else {
    		Actions actions = new Actions(driver);
    		actions.contextClick(element).perform();
    	}
	}
    
    /**
     * Used by PageFactory mode.
     * @param driver
     * @param element
     * @param keys
     */
    public static void sendKeysToElement(WebDriver driver, WebElement element, CharSequence keys) {
		if(element == null)
			throw new NoSuchElementException("Element not found: " + by.toString());
    	if(driver.getClass().toString().endsWith("FirefoxDriver"))
    		element.click();
    	element.sendKeys(keys);
    }

    /**
     * Used by PageFactory mode.
     * @param driver
     * @param element
     * @param keys
     */
    public static void sendKeysWithEnterToElement(WebDriver driver, WebElement element, CharSequence keys) {
		if(element == null)
			throw new NoSuchElementException("Element not found: " + by.toString());
    	if(driver.getClass().toString().endsWith("FirefoxDriver"))
    		element.click();
    	element.sendKeys(keys);
    	element.sendKeys(Keys.ENTER);
    }

    /**
     * Click the element when it present on page.
     * @param driver
     * @param element
     */
    public static void clickElement(WebDriver driver, WebElement element) {
		if(element == null)
			throw new NoSuchElementException("Element not found: " + by.toString());
   	if(driver.getClass().toString().endsWith("EdgeDriver"))
    		((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
    	else
    		element.click();
    }

    /**
     * Can be used to check if exception displayed on page, like error message.
     * @param driver
     * @param by
     * @param timeout
     * @return
     */
    public static boolean isDisplayed(WebDriver driver, By by, int timeout) {
		if(timeout == 0)
			timeout = Constants.DEFAULT_WAITING_TIME;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		WebElement element = null;
		Boolean isDisplayed = false;
		try {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			isDisplayed = element.isDisplayed();
		} catch(TimeoutException ex) {}
		return isDisplayed;
    }
    
    public static String getAttribute(WebElement element, AttributeTypeEnum attributeType) {
    	String val = "";
    	if(attributeType.getType().equalsIgnoreCase("text"))
    		val = element.getText();
    	else
    		val = element.getAttribute(attributeType.getType());
    	return val;
    }
}
