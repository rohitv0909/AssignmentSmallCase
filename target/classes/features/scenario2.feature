Feature: Smallcase QA Engineer Assignment

  @Scenario2
  Scenario: Compare price of an item on both Flipkart and Amazon.
    Given Again we are on the landing page of Flipkart
    When We search for an item again
		Then we wait for the search results get loaded.
		When again we click on the First Item in the list
		Then we wait for the Product description page gets loaded
		And we Print Price from the Flipkart description.
		When We add the product to the cart
		Then Flipkart automatically redirect to the Cart page.
		Then Print the Price from cart page of Flipkart.
		Given Now we are on the landing page of Amazon
		When We search for same item again on Amazon
		Then we wait for search results get loaded.
		When again we click on the same item in the list
		Then we wait for the Product details page to gets loaded
		And we Print Price from the Amazon description
		When We add that product to the cart
		Then Amazon automatically redirect to the Cart page.
		Then Print the Price from cart page of Amazon.
		And Print the name of website which has cheaper price