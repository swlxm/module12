package net.automation.selenium.java.distribution.scripts;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.automation.selenium.java.distribution.listener.EventListener;
import net.automation.selenium.java.distribution.page.LoginPage;
import net.automation.selenium.java.distribution.util.Constants;

/**
 * A basic class, all the test scripts should extend it.
 * @author Samuel
 *
 */
public class SeleniumTestCase  {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static WebDriver driver;
	
	protected void setUp(String browser, String remoteAddress, String url, String username) throws Exception {
		if(Constants.DISTRIBUTION)
			driver = initDriver(browser, remoteAddress);
		else
			driver = initDriver(browser);
		logger.info("Browser Initialization Completed: {}", browser.toUpperCase());
		driver.manage().window().maximize();
		driver.get(url);
		new LoginPage(driver).login(driver, url, username);
	}
	
	protected void tearDown() {
//			driver.quit();
	}
	
	private WebDriver initDriver(String browser, String remoteAddress) throws MalformedURLException {
		WebDriver driver;
		DesiredCapabilities capabilities;
		if(browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", Constants.PROJECT_ROOT_PATH + Constants.FILE_SEPARATOR + "drivers" + Constants.FILE_SEPARATOR + "chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--lang=" + "en-US");
			capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		} else if(browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", Constants.PROJECT_ROOT_PATH + Constants.FILE_SEPARATOR + "drivers" + Constants.FILE_SEPARATOR + "geckodriver.exe");
			capabilities = DesiredCapabilities.firefox();
		} else if(browser.equals("edge")) {
			System.setProperty("webdriver.edge.driver", Constants.PROJECT_ROOT_PATH + Constants.FILE_SEPARATOR + "drivers" + Constants.FILE_SEPARATOR + "MicrosoftWebDriver.exe");
			EdgeOptions options = new EdgeOptions();
			capabilities = DesiredCapabilities.edge();
			capabilities.setCapability(EdgeOptions.CAPABILITY, options);
		} else if(browser.equals("ie")) {
			System.setProperty("webdriver.ie.driver", Constants.PROJECT_ROOT_PATH + Constants.FILE_SEPARATOR + "drivers" + Constants.FILE_SEPARATOR + "IEDriverServer.exe");
//			InternetExplorerOptions options = new InternetExplorerOptions().requireWindowFocus();
			capabilities = DesiredCapabilities.internetExplorer();
		} else {
			throw new RuntimeException("Only chrome firefox edge and ie are supported, please check the spelling as well.");
		}
		driver = new RemoteWebDriver(new URL(remoteAddress), capabilities);
		EventListener listener = new EventListener();
		EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
		eventDriver.register(listener);
		return eventDriver;
	}
	
	private WebDriver initDriver(String browser) {
		WebDriver driver;
		if(browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", Constants.PROJECT_ROOT_PATH + Constants.FILE_SEPARATOR + "drivers" + Constants.FILE_SEPARATOR + "chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
    		options.addArguments("--lang=" + "en-US");
			driver = new ChromeDriver(options);
		} else if(browser.equals("edge")) {
			System.setProperty("webdriver.edge.driver", Constants.PROJECT_ROOT_PATH + Constants.FILE_SEPARATOR + "drivers" + Constants.FILE_SEPARATOR + "MicrosoftWebDriver.exe");
			EdgeOptions options = new EdgeOptions();
			driver = new EdgeDriver(options);
		} else if(browser.equals("ie")) {
			System.setProperty("webdriver.ie.driver", Constants.PROJECT_ROOT_PATH + Constants.FILE_SEPARATOR + "drivers" + Constants.FILE_SEPARATOR + "IEDriverServer.exe");
			InternetExplorerOptions options = new InternetExplorerOptions().requireWindowFocus();
			driver = new InternetExplorerDriver(options.merge(DesiredCapabilities.internetExplorer()));
		}
		else if(browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", Constants.PROJECT_ROOT_PATH + Constants.FILE_SEPARATOR + "drivers" + Constants.FILE_SEPARATOR + "geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();
			options.setProfile(new FirefoxProfile());
			driver = new FirefoxDriver(options);
		} else {
			throw new RuntimeException("Only chrome firefox edge and ie are supported, please check the spelling as well.");
		}
		EventListener listener = new EventListener();
		EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
		eventDriver.register(listener);
		return eventDriver;
	}
}
