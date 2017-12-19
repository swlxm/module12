package net.automation.selenium.jenkins.pages;

import org.openqa.selenium.WebDriver;

import net.automation.selenium.jenkins.components.ContentBox;
import net.automation.selenium.jenkins.components.SideNav;
import net.automation.selenium.jenkins.components.TopNav;

/**
 * A class represents certain page, and defines some common methods to be used by test scripts.
 * @author Samuel
 *
 */
public class SomePage extends PageObject {

	private ContentBox contentBox = new ContentBox(driver);
	private SideNav sideNav = new SideNav(driver);
	private TopNav topNav = new TopNav(driver);
	
	public SomePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void commonFuncOnSomePage() {
		contentBox.commonFuncOnContentBox();
		sideNav.commonFuncOnSideNav();
		topNav.commonFuncOnTopNav();
	}
}
