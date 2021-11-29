package com.smallcase.pages;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.smallcase.util.WaitUtils;

public class FlipkartPage {

	public final static String LANDING_PAGE_TITLE = "Online Shopping Site for Mobiles, Electronics, "
			+ "Furniture, Grocery, Lifestyle, Books & More. Best Offers!";
	public static String SEARCH_RESULTS_PAGE_TITLE = "- Buy Products Online at Best Price in India - All Categories | Flipkart.com";
	public final static String PAGE_URL = "https://www.flipkart.com/";
	public final static String CART_PAGE_URL = "Shopping Cart | Flipkart.com";

	WaitUtils waitUtils;

	@FindBy(css = "input._3704LK")
	private WebElement searchInputField;

	@FindBy(css = "button._2KpZ6l._2doB4z")
	private WebElement closeSignInDialogButton;

	@FindBy(css = "div._2kHMtA a")
	private List<WebElement> searchResultsList;

	@FindBy(css = "div._30jeq3._16Jk6d")
	private WebElement priceOnPageDescription;

	@FindBy(css = "button._2KpZ6l._2U9uOA._3v1-ww")
	private WebElement addToCartButton;

	@FindBy(css = "button._23FHuj:nth-of-type(2)")
	private WebElement addQuantityByOne;

	@FindBy(css = "div._1dqRvU span")
	private WebElement totalPriceOnCartPage;
	
	@FindBy(css = "div._2sKwjB")
	private WebElement quantityUpdatedBanner;

	public FlipkartPage(WebDriver driver) {
		this.waitUtils = new WaitUtils(new WebDriverWait(driver, 30));
		PageFactory.initElements(driver, this);
	}

	public void waitForHomePageToLoad() {
		waitUtils.waitForTitleToLoad(LANDING_PAGE_TITLE);
		waitUtils.waitForElementToBeVisible(closeSignInDialogButton);
	}

	public void clickCloseSignInDialogButton() {
		closeSignInDialogButton.click();
	}

	public void waitForSearchResultsToLoad() {
		waitUtils.waitForAllElementsToBeVisible(searchResultsList);
	}
	
	public WebElement returnSearchInputField() {
		return searchInputField;
	}

	public void searchForAnItem(String item) {
		searchInputField.sendKeys(item);
		searchInputField.sendKeys(Keys.ENTER);
	}
	
	public List<WebElement> returnSearchResults() {
		return searchResultsList;
	}
	
	public void waitForDescriptionPageToLoad() {
		waitUtils.waitForElementToBeVisible(addToCartButton);
	}
	
	public void clickAddToCartButton() {
		addToCartButton.click();
	}
	
	public String returnPriceOnPageDescription() {
		String price = priceOnPageDescription.getText();
		price = price.substring(1).replaceAll(",","");
		return price;
	}
	
	public void clickAddQuantityByOneButton() {
		addQuantityByOne.click();
	}
	
	public void waitForCartUpdate() {
		waitUtils.waitForElementToBeVisible(quantityUpdatedBanner);
	}
	
	public String returnPriceOnCartPage() {
		String price = totalPriceOnCartPage.getText();
		price = price.substring(1).replaceAll(",","");
		return price;
	}
 }
