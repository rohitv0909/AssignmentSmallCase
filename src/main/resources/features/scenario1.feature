Feature: Smallcase QA Engineer Assignment

  @Scenario1
  Scenario: Open Flipkart, search for an item, add to cart and print its price.
    Given We are on the landing page of Flipkart
    When We search for an item.
		Then The search results get loaded.
		When We click on the First Item in the list
		Then The Product description page gets loaded
		And Print Price of the Item.
		When We add the product to cart
		Then Flipkart automatically redirects to the Cart page.
		When Increase Quantity by one.
		Then Print the Price after addition of Quantity.