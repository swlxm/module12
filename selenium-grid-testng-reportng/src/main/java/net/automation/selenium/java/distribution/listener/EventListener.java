package net.automation.selenium.java.distribution.listener;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventListener extends AbstractWebDriverEventListener {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void beforeClickOn(WebElement el, WebDriver driver) {
		String xpath = el.toString().split("->")[1].trim();
		logger.info("Click element: {}", xpath);
	}
}
