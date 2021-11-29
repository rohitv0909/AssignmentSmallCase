package com.smallcase.pages;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.smallcase.util.WaitUtils;

public class AmazonPage {

	public final static String LANDING_PAGE_TITLE = "Online Shopping site in India: Shop Online for Mobiles, "
			+ "Books, Watches, Shoes and More - Amazon.in";
	public static String SEARCH_RESULTS_PAGE_TITLE = "Amazon.in : ";
	public final static String PAGE_URL = "https://www.amazon.in/";
	public final static String CART_PAGE_URL = "Amazon.in Shopping Cart";
	
	WaitUtils waitUtils;

	@FindBy(id = "twotabsearchtextbox")
	private WebElement searchInputField;
	
	@FindBy(css = "div.a-section.a-spacing-none h2 a")
	private List<WebElement> searchResultsList;
	
	@FindBy(id = "priceblock_ourprice")
	private WebElement priceOnPageDescription;

	@FindBy(id = "add-to-cart-button")
	private WebElement addToCartButton;

	@FindBy(id = "attach-desktop-sideSheet")
	private WebElement sideCartmenu;
	
	@FindBy(css = "#attach-view-cart-button-form input")
	private WebElement goToCartPagebutton;
	
	@FindBy(css = "#sc-subtotal-amount-buybox span.sc-price")
	private WebElement totalPriceOnCartPage;
	
	public AmazonPage(WebDriver driver) {
		this.waitUtils = new WaitUtils(new WebDriverWait(driver, 30));
		PageFactory.initElements(driver, this);
	}

	public void waitForHomePageToLoad() {
		waitUtils.waitForTitleToLoad(LANDING_PAGE_TITLE);
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
	
	public void goToCartPage() {
		waitUtils.waitForElementToBeVisible(goToCartPagebutton);
		goToCartPagebutton.click();
	}
	
	public String returnPriceOnPageDescription() {
		String price = priceOnPageDescription.getText();
		price = price.substring(1).replaceAll(",","");
		return price;
	}
	
	public String returnPriceOnCartPage() {
		String price = totalPriceOnCartPage.getText();
		price = price.substring(1).replaceAll(",","");
		return price;
	}
}
