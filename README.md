Earnest Acceptance Test
=======================

The following tests provide a quick validation of the JungleSocks application. The code is based on my open source test framework hosted on GitHub (a work in progress) - which I used for my project for charities, Pledgerunner.

<hr>

### Test Logic Class (Test package)
JungleSockTest

### Test Cases / Methods 
* verifyEachStateTaxAtCheckout - completes order form and validates checkout, including tariffs, for all 50 states.
* verifyEdgeCaseCheckout - edge acceptance test for validating error handling.
* verifyInvalidCheckout - checking invalid checkout and error handling
* verifyZeroCheckout - adding zero items to checkout and confirming usability.

### Page Object Model (Page package)
JungleSockCheckout<br>
JungleSockPage

### Test Results
[pwd]/TestFrameworks/target/surefire-reports/index.html

### Run tests
* `mvn clean install` - or - `mvn test`

<hr>

### Notes / Observations
* Functional: Prevent or inform user they have exceeding stock quantity in the order form, IF they attempt to order more than what is listed.
* Suggestion: In Checkout, display state where order is from (or maybe not - for purpose of exercise).
* Cosmetic: Should the quantity change after an order has been placed? 
* Cosmetic: Lock button if user hasn’t made entry into any of the fields, preventing access to checkout.
* Cosmetic: Prevent user from entering spaces - user can type "1 0 0" for 100. Result: Only "1" is used.
* Edge Case: Prevent excessive text entry. Set limit.
* Code: Add an ID or name attribute to the form element.

### Nice To Have Test Cases / Ideas
* Test multiple browsers with acceptance test cases. I'm adding a way to switch out drivers associated with different browsers.
* More invalid text entry cases - but this would have been duplication for this exercise.
* My first attempt at this involved building my tests with PhantomJS and CasperJS - but I ran into issues with xPath and the form. This would be a perfect candidate for building a lightweight test framework in that technology.  




 


