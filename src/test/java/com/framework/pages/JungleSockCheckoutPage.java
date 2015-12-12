package com.framework.pages;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * JungleSockCheckoutPage Objects
 * @author John Eric Arterberry<john.arterberry@gmail.com>
 *
 */
public class JungleSockCheckoutPage {
	
	final private static String JS_CHECKOUT_PAGE_TITLE = "JungleSocks";	
	final private static String JS_CHECKOUT_PAGE_HEADER ="Please Confirm Your Order";
	final private static String JS_CHECKOUT_PAGE_ERROR_HEADER = "We're sorry, but something went wrong.";
	
	private static StringBuffer verificationErrors = new StringBuffer();
	private WebDriver driver;
	
	
	// jungle socks header text
	@FindBy(xpath = "//h1")
	private WebElement junglesocksHeader;
	
	// zebra
	@FindBy(xpath = "//td")
	private WebElement zebraHeader;
	
	@FindBy(xpath = "//td[2]")
	private WebElement zebraPrice;
	
	@FindBy(xpath = "//td[3]")
	private WebElement zebraQuantity;
	

	// lion
	@FindBy(xpath = "//tr[3]/td")
	private WebElement lionHeader;
	
	@FindBy(xpath = "//tr[3]/td[2]")
	private WebElement lionPrice;
	
	@FindBy(xpath = "//tr[3]/td[3]")
	private WebElement lionQuantity;

	
	// elephant
	@FindBy(xpath = "//tr[4]/td")
	private WebElement elephantHeader;
	
	@FindBy(xpath = "//tr[4]/td[2]")
	private WebElement elephantPrice;
	
	@FindBy(xpath = "//tr[4]/td[3]")
	private WebElement elephantQuantity;	
	
	
	// giraffe
	@FindBy(xpath = "//tr[5]/td")
	private WebElement giraffeHeader;
	
	@FindBy(xpath = "//tr[5]/td[2]")
	private WebElement giraffePrice;
	
	@FindBy(xpath = "//tr[5]/td[3]")
	private WebElement giraffeQuantity;	
		
	
	// sub_total
	@FindBy(xpath = "//td[@id='subtotal']")
	private WebElement subtotalValue;
	
	// taxes
	@FindBy(xpath = "//td[@id='taxes']")
	private WebElement taxValue;
	
	// total
	@FindBy(xpath = "//td[@id='total']")
	private WebElement totalValue;
	
	// error header
	@FindBy(xpath = "//h1")
	private WebElement errorHeader;
	
	
	// Supporting methods for managing data
	
	/**
	 * Confirm the SubTotal is a match.
	 * 
	 * @param amt
	 * @return boolean
	 */
	public boolean isSubTotalMatch(String amt) {		
		try {
			return subtotalValue.getText().equals(amt);
		} catch (Error e) {
			verificationErrors.append(e.toString());
			return false;
		}
	}
	
	/**
	 * Get SubTotal on page, to account for any price change.
	 * Removes symbol.
	 * 
	 * @return string
	 */
	public String getSubTotalString(){
		return subtotalValue.getText().substring(1);		
	}

	/**
	 * Get SubTotal on page, to account for any price change.
	 * Removes symbol
	 *  
	 * @return double
	 */
	public double getSubTotalDouble(){
		return Double.parseDouble(subtotalValue.getText().substring(1));		
	}

	
	/**
	 * Is quantity field set for WebElement with specific test data?
	 * 
	 * @param qty
	 * @param obj
	 * @return
	 */
	public boolean isQuantitySet(String qty, int num) {
		
		WebElement obj = null;
		
		switch(num) {			
		case 1 : obj = this.zebraQuantity;
			break;
		case 2 : obj = this.lionQuantity;
			break;
		case 3 : obj = this.elephantQuantity;
			break;
		case 4 : obj = this.giraffeQuantity;
			break;				
		}
		
		try {
			return obj.getText().equals(qty);
		} catch (Error e) {
			verificationErrors.append(e.toString());
			return false;
		}
	}
	
	
	/**
	 * Calculate if the tax is correct.
	 * 
	 * @param state
	 * @param subtotal
	 * @return boolean
	 */
	public boolean isTaxCorrect(String state, double subtotal) {
		
		double tax = 0;
		double calcTax = 0;
		
		// format
		NumberFormat formatTax = new DecimalFormat("#0.00");
		
		// assign specific tax values per state
		if(state == "California") {
			tax = 0.08;
		} else if (state == "Minnesota") {
			tax = 0;
		} else if(state == "New York") {
			tax = 0.06;
		} else if(state == "North Dakota") {
			tax = 0.10;
		} else {
			tax = 0.05;
		}
		
		calcTax = subtotal * tax;
		try {
			return taxValue.getText().equals("$"+formatTax.format(calcTax));
		} catch (Error e) {
			verificationErrors.append(e.toString());
			return false;
		}		
	}
	
	
	public boolean isGrandTotalCorrect(String state, double subtotal) {
		
		double tax = 0;
		double calcTax = 0;
		double calcTotal = 0;
		
		// format
		NumberFormat formatTax = new DecimalFormat("#0.00");
		
		// assign specific tax values per state
		if(state == "California") {
			tax = 0.08;
		} else if (state == "Minnesota") {
			tax = 0;
		} else if(state == "New York") {
			tax = 0.06;
		} else if(state == "North Dakota") {
			tax = 0.10;
		} else {
			tax = 0.05;
		}
		
		calcTax = subtotal * tax;
		calcTotal = subtotal + calcTax;
		
		try {
			return totalValue.getText().equals("$"+formatTax.format(calcTotal));
		} catch (Error e) {
			verificationErrors.append(e.toString());
			return false;
		}		
	}	
	
		
	/**
	 * Constructor / driver for JungleSockCheckoutPage page.
	 * 
	 * @param driver
	 * @return JungleSockCheckout class
	 */
	public static JungleSockCheckoutPage navigateTo(WebDriver driver) throws InterruptedException {
		//driver.get("https://jungle-socks.herokuapp.com/checkout/create");
		return PageFactory.initElements(driver, JungleSockCheckoutPage.class);
	}
	
	
	/**
	 * Confirm page title is correct.
	 * 
	 * @return boolean
	 */
	public boolean isPageTitleCorrect() {
		if (driver.getTitle().contains(JS_CHECKOUT_PAGE_TITLE)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Confirm header is correct.
	 * 
	 * @return boolean
	 */
	public boolean isPageHeaderCorrect() {
		if(JS_CHECKOUT_PAGE_HEADER.equals(junglesocksHeader.getText())) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Confirm header is correct.
	 * 
	 * @return boolean
	 */
	public boolean isPageErrorHeaderCorrect() {
		if(JS_CHECKOUT_PAGE_ERROR_HEADER.equals(errorHeader.getText())) {
			return true;
		} else {
			return false;
		}
	}	
}
