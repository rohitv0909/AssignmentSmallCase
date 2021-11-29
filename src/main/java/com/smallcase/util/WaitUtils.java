package com.smallcase.util;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {
	
	public WebDriverWait webDriverWait;
	
	public WaitUtils(WebDriverWait webDriverWait) {
		this.webDriverWait = webDriverWait;
	}
	
	public void waitForTitleToLoad(String title) {
		webDriverWait.until(ExpectedConditions.titleIs(title));
	}
	
	public void waitForElementToBeClickable(WebElement element) {
		webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void waitForElementToBeVisible(WebElement element) {
		webDriverWait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForAllElementsToBeVisible(List<WebElement> elements) {
		webDriverWait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}
}
