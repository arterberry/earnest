
package com.framework.tests.portal;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.framework.common.Base;
import com.framework.pages.JungleSockCheckoutPage;
import com.framework.pages.JungleSockPage;

/**
 * JungleSockTest
 * @author John Eric Arterberry<john.arterberry@gmail.com>
 */
public class JungleSockTest extends Base {
	
	// sock types 
	final private static int ZEBRA_SOCK = 1;	
	final private static int LION_SOCK = 2;
	final private static int ELEPHANT_SOCK = 3;
	final private static int GIRAFFE_SOCK = 4;
	
	// quantity and states
	final private static String ORDER_ZERO = "0";
	final private static String ORDER_ONE = "1";
	final private static String ORDER_EDGE = "99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999";
	final private static int NUMBER_OF_STATES = 50;
	final private static int CALIFORNIA_STATE = 5;
	final private static String EDGE_SUBTOTAL = "$Inf";
	
	// acceptance tests JungleSocks 
		
	@Test(groups = {"qa"})
	public void verifyEachStateTaxAtCheckout() throws InterruptedException {
		
		// Validate 50 states and their respective tax values 
		// using set and known quantities.
		
		double subtotalDblValue = 0;
		String subtotalStrValue = null;
		
		String state = null;		
		// checkout JungleSock page constructor / instance
		JungleSockCheckoutPage checkoutPage = JungleSockCheckoutPage.navigateTo(driver);
		
		// active JungleSock page constructor / instance 
		JungleSockPage activePage = JungleSockPage.navigateTo(driver);
		
		// active JungleSock page constructor / instance w/ environment URL (commented to test either one)
		// JungleSockPage activePage = JungleSockPage.navigateTo(driver, "qa");
		
		for(int i = 1; i <= NUMBER_OF_STATES; i++) {			
			Assert.assertTrue(activePage.isPageTitleCorrect());
			activePage.setQuantity(ORDER_ONE, ZEBRA_SOCK);
			activePage.setQuantity(ORDER_ONE, LION_SOCK);
			activePage.setQuantity(ORDER_ONE, ELEPHANT_SOCK);
			activePage.setQuantity(ORDER_ONE, GIRAFFE_SOCK);			
			state = activePage.getState(i);
			activePage.setState(state);
			activePage.clickCommit();
			
			// 1. confirm page change
			if(checkoutPage.isPageHeaderCorrect()) {
				
				// 2. check quantity of each purchase
				Assert.assertTrue(checkoutPage.isQuantitySet(ORDER_ONE, ZEBRA_SOCK));
				Assert.assertTrue(checkoutPage.isQuantitySet(ORDER_ONE, LION_SOCK));
				Assert.assertTrue(checkoutPage.isQuantitySet(ORDER_ONE, ELEPHANT_SOCK));
				Assert.assertTrue(checkoutPage.isQuantitySet(ORDER_ONE, GIRAFFE_SOCK));
				
				// 3. get the sub total
				subtotalDblValue = checkoutPage.getSubTotalDouble();
				subtotalStrValue = checkoutPage.getSubTotalString();
				
				// 4.check sub total
				Assert.assertTrue(checkoutPage.isSubTotalMatch("$" + subtotalStrValue));
				
				// 5. calculate tax for the state  
				Assert.assertTrue(checkoutPage.isTaxCorrect(state, subtotalDblValue));
				
				// 6. calculate grand total
				Assert.assertTrue(checkoutPage.isGrandTotalCorrect(state, subtotalDblValue));
			
			} else {
				
				// 7. else confirm error page appears
				Assert.assertTrue(checkoutPage.isPageErrorHeaderCorrect());
			}
			
			// return to activePage
			activePage.goBackRefresh(driver);
			
		}
		
		// free up static
		activePage = null;
		checkoutPage = null;
	}
	
	@Test(groups = {"qa"})
	public void verifyInvalidCheckout() throws InterruptedException {
		
		// Verify empty transaction error handling. 
		
		// checkout JungleSock page constructor / instance
		JungleSockCheckoutPage checkoutPage = JungleSockCheckoutPage.navigateTo(driver);
		
		// active JungleSock page constructor / instance 
		JungleSockPage activePage = JungleSockPage.navigateTo(driver);
		
		Assert.assertTrue(activePage.isPageTitleCorrect());
		activePage.clickCommit();
			
		Assert.assertTrue(checkoutPage.isPageErrorHeaderCorrect());
		
		// free up static
		activePage = null;
		checkoutPage = null;
	}	
	
	@Test(groups = {"qa"})
	public void verifyEdgeCaseCheckout() throws InterruptedException {
		
		// Verify edge case transaction error handling. 
		
		String state = null;
		
		// checkout JungleSock page constructor / instance
		JungleSockCheckoutPage checkoutPage = JungleSockCheckoutPage.navigateTo(driver);
		
		// active JungleSock page constructor / instance 
		JungleSockPage activePage = JungleSockPage.navigateTo(driver);
		
		Assert.assertTrue(activePage.isPageTitleCorrect());

		activePage.setQuantity(ORDER_EDGE, ZEBRA_SOCK);
		state = activePage.getState(CALIFORNIA_STATE);
		activePage.setState(state);
		activePage.clickCommit();
			
		Assert.assertTrue(checkoutPage.isSubTotalMatch(EDGE_SUBTOTAL));
		
		// free up static
		activePage = null;
		checkoutPage = null;
	}
	
	@Test(groups = {"qa"})
	public void verifyZeroCheckout() throws InterruptedException {
		
		// Verify zero entered into transaction and error handling. 
		double subtotalDblValue = 0;
		String subtotalStrValue = null;
		
		String state = null;
		
		// checkout JungleSock page constructor / instance
		JungleSockCheckoutPage checkoutPage = JungleSockCheckoutPage.navigateTo(driver);
		
		// active JungleSock page constructor / instance 
		JungleSockPage activePage = JungleSockPage.navigateTo(driver);
		
		Assert.assertTrue(activePage.isPageTitleCorrect());

		activePage.setQuantity(ORDER_ZERO, ZEBRA_SOCK);
		activePage.setQuantity(ORDER_ZERO, LION_SOCK);
		activePage.setQuantity(ORDER_ZERO, ELEPHANT_SOCK);
		activePage.setQuantity(ORDER_ZERO, GIRAFFE_SOCK);			

		state = activePage.getState(CALIFORNIA_STATE);
		activePage.setState(state);
		activePage.clickCommit();
		
		subtotalDblValue = checkoutPage.getSubTotalDouble();
		subtotalStrValue = checkoutPage.getSubTotalString();
		
		Assert.assertTrue(checkoutPage.isSubTotalMatch("$" + subtotalStrValue));
		  
		Assert.assertTrue(checkoutPage.isTaxCorrect(state, subtotalDblValue));
		
		Assert.assertTrue(checkoutPage.isGrandTotalCorrect(state, subtotalDblValue));
		
		// free up static
		activePage = null;
		checkoutPage = null;
	}	

}