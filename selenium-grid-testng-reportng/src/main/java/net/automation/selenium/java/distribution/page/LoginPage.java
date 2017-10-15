package net.automation.selenium.java.distribution.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.automation.selenium.java.distribution.util.AttributeTypeEnum;
import net.automation.selenium.java.distribution.util.Constants;
import net.automation.selenium.java.distribution.util.Action;
import net.automation.selenium.java.distribution.util.NGAssert;
import net.automation.selenium.java.distribution.util.PropertyParser;

/**
 * This class represent the login page.
 * @author Samuel
 *
 */
public class LoginPage extends PageObject {

	private WebElement loginLink, usernameField, passwordField, loginBtn, profileLink;
	
	private final String textLoginLink = "Sign in";
	private final String cssUsernameField = "input#login_field";
	private final String cssPasswordField = "input#password";
	private final String cssLoginBtn = "input[name='commit']";
	private final String cssError = "div#js-flash-container>div>div";
	private final String cssProfileLink = "summary[aria-label='View profile and more']>img";
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public LoginPage(WebDriver driver) {
		super(driver);
	}
	
	public void login(WebDriver driver, String url, String username) {
		loginLink = Action.findElement(driver, By.linkText(textLoginLink), 0);
		Action.clickElement(driver, loginLink);
		//input account
		usernameField = Action.findElement(driver, By.cssSelector(cssUsernameField), 0);
		Action.sendKeysToElement(driver, usernameField, username);
		String password = getPassword(username);
		passwordField = Action.findElement(driver, By.cssSelector(cssPasswordField), 0);
		Action.sendKeysToElement(driver, passwordField, password);
		logger.info("Login with user ID: {}", username);
		loginBtn = Action.findElement(driver, By.cssSelector(cssLoginBtn), 0);
		Action.clickElement(driver, loginBtn);
		
		//handle error
		Boolean hasError = Action.isDisplayed(driver, By.cssSelector(cssError), 1);
		if(hasError)
			throw new RuntimeException("We don't recognize this user id: {" + username + "} or password: {" + password + "}");
		
		//checkpoint
		profileLink = Action.findElement(driver, By.cssSelector(cssProfileLink), 0);
		String href = Action.getAttribute(profileLink, AttributeTypeEnum.ALT);
		NGAssert.assertEquals(href, "@" + username);
		logger.info("Login successfully");
	}
	
	private String getPassword(String username) {
		PropertyParser parser = new PropertyParser("config.properties");
		String password = parser.getProperty(username);
		return password;
	}
	

}
