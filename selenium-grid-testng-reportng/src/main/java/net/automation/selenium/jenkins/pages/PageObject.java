package net.automation.selenium.jenkins.pages;

import org.openqa.selenium.WebDriver;

/**
 * all the page classes should extends this basic class.
 * @author Samuel
 *
 */
public class PageObject {

	protected WebDriver driver;
	
	public PageObject(WebDriver driver) {
		this.driver = driver;
	}
}
