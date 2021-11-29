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
import com.smallcase.pages.FlipkartPage;
import com.smallcase.util.WaitUtils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FlipkartPageTest {

	FlipkartPage flipkartPage;
	final String itemName = "VU TV";

	WebDriver driver = null;
	WaitUtils waitUtils;

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

	public void clickElementAndSwitchDriverToNewTab(WebElement element) {
		Set<String> driverWindowHandlesSet = driver.getWindowHandles();
		element.click();
		waitUtils.webDriverWait.until(ExpectedConditions.numberOfWindowsToBe(driverWindowHandlesSet.size() + 1));
		Set<String> currentWindowHandles = driver.getWindowHandles();
		Set<String> newWindowHandle = Sets.difference(currentWindowHandles, driverWindowHandlesSet);
		driver.switchTo().window(Iterables.getOnlyElement(newWindowHandle));
	}

	@Given("We are on the landing page of Flipkart")
	public void we_are_on_the_landing_page_of_flipkart() {
		flipkartPage = new FlipkartPage(driver);
		driver.get(FlipkartPage.PAGE_URL);
		flipkartPage.waitForHomePageToLoad();
		assertEquals(driver.getCurrentUrl(), FlipkartPage.PAGE_URL);
	}

	@When("We search for an item.")
	public void we_search_for_an_item() {
		flipkartPage.clickCloseSignInDialogButton();
		flipkartPage.searchForAnItem(itemName);
		waitUtils.waitForTitleToLoad(itemName + FlipkartPage.SEARCH_RESULTS_PAGE_TITLE);
	}

	@Then("The search results get loaded.")
	public void the_search_results_get_loaded() {
		flipkartPage.waitForSearchResultsToLoad();
	}

	@When("We click on the First Item in the list")
	public void we_click_on_the_first_item_in_the_list() {
		// Used Actions class to move focus as a drop-down is getting auto opened and
		// intercepting the click.
		new Actions(driver).moveToElement(flipkartPage.returnSearchInputField()).perform();
		clickElementAndSwitchDriverToNewTab(flipkartPage.returnSearchResults().get(0));
	}

	@Then("The Product description page gets loaded")
	public void the_product_description_page_gets_loaded() {
		flipkartPage.waitForDescriptionPageToLoad();
	}

	@Then("Print Price of the Item.")
	public void print_price_of_the_item() {
		System.out.println(flipkartPage.returnPriceOnPageDescription());
	}

	@When("We add the product to cart")
	public void we_add_the_product_to_cart() {
		flipkartPage.clickAddToCartButton();
	}

	@Then("Flipkart automatically redirects to the Cart page.")
	public void flipkart_automatically_redirects_to_the_cart_page() {
		waitUtils.waitForTitleToLoad(FlipkartPage.CART_PAGE_URL);
	}

	@When("Increase Quantity by one.")
	public void increase_quantity_by_one() {
		flipkartPage.clickAddQuantityByOneButton();
	}

	@Then("Print the Price after addition of Quantity.")
	public void print_the_price_after_addition_of_quantity() {
		flipkartPage.waitForCartUpdate();
		System.out.println(flipkartPage.returnPriceOnCartPage());
	}

	@After
	public void afterTheTest() {
		driver.quit();
	}
}
