package net.automation.selenium.jenkins.scripts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import net.automation.selenium.jenkins.pages.LoginPage;
import net.automation.selenium.jenkins.util.NGDataProvider;

public class LoginTest extends SeleniumTestCase {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String url, username;
	
	@BeforeClass
	@Parameters({"browser", "url", "username"})
	public void setUp(String browser, String url, String username) {
		super.setUp(browser, url, username);
		this.url = url;
		this.username = username;
	}
	
	@Test(dataProvider="login", dataProviderClass=NGDataProvider.class)
	public void login(String username) {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(driver, url, username);
	}

	@Test
	public void login() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(driver, url, username);
	}
}
