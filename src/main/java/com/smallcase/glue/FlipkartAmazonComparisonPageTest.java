package com.smallcase.glue;

import static org.testng.Assert.assertEquals;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.smallcase.pages.AmazonPage;
import com.smallcase.pages.FlipkartPage;
import com.smallcase.util.WaitUtils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FlipkartAmazonComparisonPageTest {

	FlipkartPage flipkartPage;
	AmazonPage amazonPage;
	final String itemName = "Iphone 11 64 GB Red";
	double flipkartPrice = -1;
	double amazonPrice = -1;

	WebDriver driver = null;
	WaitUtils waitUtils;

	public void clickElementAndSwitchDriverToNewTab(WebElement element) {
		Set<String> driverWindowHandlesSet = driver.getWindowHandles();
		element.click();
		waitUtils.webDriverWait.until(ExpectedConditions.numberOfWindowsToBe(driverWindowHandlesSet.size() + 1));
		Set<String> currentWindowHandles = driver.getWindowHandles();
		Set<String> newWindowHandle = Sets.difference(currentWindowHandles, driverWindowHandlesSet);
		driver.switchTo().window(Iterables.getOnlyElement(newWindowHandle));
	}

	@SuppressWarnings("deprecation")
	@Before
	public void beforeTheTest() {
		WebDriverManager.chromedriver().version("96.0.4664.45").setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("enable-automation");
		driver = new ChromeDriver(options);
		waitUtils = new WaitUtils(new WebDriverWait(driver, 30));
	}

	@Given("Again we are on the landing page of Flipkart")
	public void again_we_are_on_the_landing_page_of_flipkart() {
		flipkartPage = new FlipkartPage(driver);
		driver.get(FlipkartPage.PAGE_URL);
		flipkartPage.waitForHomePageToLoad();
		assertEquals(driver.getCurrentUrl(), FlipkartPage.PAGE_URL);
	}

	@When("We search for an item again")
	public void we_search_for_an_item_again() {
		flipkartPage.clickCloseSignInDialogButton();
		flipkartPage.searchForAnItem(itemName);
		waitUtils.waitForTitleToLoad(itemName + FlipkartPage.SEARCH_RESULTS_PAGE_TITLE);
	}

	@Then("we wait for the search results get loaded.")
	public void we_wait_for_the_search_results_get_loaded() {
		flipkartPage.waitForSearchResultsToLoad();
	}

	@When("again we click on the First Item in the list")
	public void again_we_click_on_the_first_item_in_the_list() {
		// Used Actions class to move focus as a drop-down is getting auto opened and
		// intercepting the click.
		new Actions(driver).moveToElement(flipkartPage.returnSearchInputField()).perform();
		clickElementAndSwitchDriverToNewTab(flipkartPage.returnSearchResults().get(0));
	}

	@Then("we wait for the Product description page gets loaded")
	public void we_wait_for_the_product_description_page_gets_loaded() {
		flipkartPage.waitForDescriptionPageToLoad();
	}

	@Then("we Print Price from the Flipkart description.")
	public void we_print_price_from_the_flipkart_description() {
		System.out.println(Double.parseDouble(flipkartPage.returnPriceOnPageDescription()));
	}

	@When("We add the product to the cart")
	public void we_add_the_product_to_the_cart() {
		flipkartPage.clickAddToCartButton();
	}

	@Then("Flipkart automatically redirect to the Cart page.")
	public void flipkart_automatically_redirect_to_the_cart_page() {
		waitUtils.waitForTitleToLoad(FlipkartPage.CART_PAGE_URL);
	}

	@Then("Print the Price from cart page of Flipkart.")
	public void print_the_price_from_cart_page_of_flipkart() {
		flipkartPrice = Double.parseDouble(flipkartPage.returnPriceOnCartPage());
		System.out.println(flipkartPrice);
	}

	@Given("Now we are on the landing page of Amazon")
	public void now_we_are_on_the_landing_page_of_amazon() {
		amazonPage = new AmazonPage(driver);
		driver.get(AmazonPage.PAGE_URL);
		amazonPage.waitForHomePageToLoad();
		assertEquals(driver.getCurrentUrl(), AmazonPage.PAGE_URL);
	}

	@When("We search for same item again on Amazon")
	public void we_search_for_same_item_again_on_amazon() {
		amazonPage.searchForAnItem(itemName);
		waitUtils.waitForTitleToLoad(AmazonPage.SEARCH_RESULTS_PAGE_TITLE + itemName);
	}

	@Then("we wait for search results get loaded.")
	public void we_wait_for_search_results_get_loaded() {
		amazonPage.waitForSearchResultsToLoad();
	}

	@When("again we click on the same item in the list")
	public void again_we_click_on_the_same_item_in_the_list() {
		clickElementAndSwitchDriverToNewTab(amazonPage.returnSearchResults().get(0));
	}

	@Then("we wait for the Product details page to gets loaded")
	public void we_wait_for_the_product_details_page_to_gets_loaded() {
		amazonPage.waitForDescriptionPageToLoad();
	}

	@Then("we Print Price from the Amazon description")
	public void we_print_price_from_the_amazon_description() {
		System.out.println(Double.parseDouble(amazonPage.returnPriceOnPageDescription()));
	}

	@When("We add that product to the cart")
	public void we_add_that_product_to_the_cart() {
		amazonPage.clickAddToCartButton();
	}

	@Then("Amazon automatically redirect to the Cart page.")
	public void amazon_automatically_redirect_to_the_cart_page() {
		amazonPage.goToCartPage();
		waitUtils.waitForTitleToLoad(AmazonPage.CART_PAGE_URL);
	}

	@Then("Print the Price from cart page of Amazon.")
	public void print_the_price_from_cart_page_of_amazon() {
		amazonPrice = Double.parseDouble(amazonPage.returnPriceOnCartPage());
		System.out.println(amazonPrice);
	}

	@Then("Print the name of website which has cheaper price")
	public void print_the_name_of_website_which_has_cheaper_price() {
		if (flipkartPrice != -1 && amazonPrice != -1) {
			if (flipkartPrice < amazonPrice) {
				System.out.println("Flipkart is providing " + itemName + " at a cheaper price => " + flipkartPrice);
			} else if (flipkartPrice > amazonPrice) {
				System.out.println("Amazon is providing " + itemName + " at a cheaper price => " + amazonPrice);
			} else {
				System.out.println("Both Amazon and FLipkart are providing " + itemName + " at the same price => " + amazonPrice);
			}
		}
	}

	@After
	public void afterTheTest() {
		driver.quit();
	}
}
