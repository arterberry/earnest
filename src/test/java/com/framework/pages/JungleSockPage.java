package com.framework.pages;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement; 
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.framework.common.TestDriverFactory;

/**
 * JungleSockPage Objects
 * @author John Eric Arterberry<john.arterberry@gmail.com>
 *
 */
public class JungleSockPage {
	
	final private static String JS_PAGE_TITLE = "JungleSocks";

	private static StringBuffer verificationErrors = new StringBuffer();
	private WebDriver driver;
	
	// jungle socks header text
	@FindBy(xpath = "//h1")
	private WebElement junglesocksHeader;

	// jungle socks paragraph	
	@FindBy(xpath = "//p")
	private WebElement junglesocksSubtext;
	

	// zebra
	@FindBy(xpath = "//td")
	private WebElement zebraHeader;
	
	@FindBy(xpath = "//td[2]")
	private WebElement zebraPrice;
	
	@FindBy(xpath = "//td[3]")
	private WebElement zebraStock;
	
	@FindBy(xpath = "//td[4]/input")
	private WebElement zebraQuantity;
	
	
	// lion
	@FindBy(xpath = "//tr[3]/td")
	private WebElement lionHeader;
	
	@FindBy(xpath = "//tr[3]/td[2]")
	private WebElement lionPrice;
	
	@FindBy(xpath = "//tr[3]/td[3]")
	private WebElement lionStock;
	
	@FindBy(xpath = "//tr[3]/td[4]/input")
	private WebElement lionQuantity;

	
	// elephant
	@FindBy(xpath = "//tr[4]/td")
	private WebElement elephantHeader;
	
	@FindBy(xpath = "//tr[4]/td[2]")
	private WebElement elephantPrice;
	
	@FindBy(xpath = "//tr[4]/td[3]")
	private WebElement elephantStock;
	
	@FindBy(xpath = "//tr[4]/td[4]/input")
	private WebElement elephantQuantity;


	// giraffe
	@FindBy(xpath = "//tr[5]/td")
	private WebElement giraffeHeader;
	
	@FindBy(xpath = "//tr[5]/td[2]")
	private WebElement giraffePrice;
	
	@FindBy(xpath = "//tr[5]/td[3]")
	private WebElement giraffeStock;
	
	@FindBy(xpath = "//tr[5]/td[4]/input")
	private WebElement giraffeQuantity;
	

	// state selector
	@FindBy(xpath = "//select[@name='state']")
	private WebElement stateSelector;
	
	// state hash map data
	private static HashMap<Integer, String> stateMap = new HashMap<Integer, String>();
		
	// commit
	@FindBy(xpath = "//input[@name='commit']")
	private WebElement commitButton;	

	// driver
	public JungleSockPage(WebDriver driver) {
		this.driver = driver;
	}
	
	// Supporting methods for managing data
	
	/**
	 * Builds hash map of states.
	 */
	private static void buildStates() {
		stateMap.put(1, "Alabama");
		stateMap.put(2, "Alaska");
		stateMap.put(3, "Arizona");
		stateMap.put(4, "Arkansas");
		stateMap.put(5, "California");
		stateMap.put(6, "Colorado");
		stateMap.put(7, "Connecticut");
		stateMap.put(8, "Delaware");
		stateMap.put(9, "Florida");
		stateMap.put(10, "Georgia");
		stateMap.put(11, "Hawaii");
		stateMap.put(12, "Idaho");
		stateMap.put(13, "Illinois");
		stateMap.put(14, "Indiana");
		stateMap.put(15, "Iowa");
		stateMap.put(16, "Kansas");
		stateMap.put(17, "Kentucky");
		stateMap.put(18, "Louisiana");
		stateMap.put(19, "Maine");
		stateMap.put(20, "Maryland");
		stateMap.put(21, "Massachusetts");
		stateMap.put(22, "Michigan");
		stateMap.put(23, "Minnesota");
		stateMap.put(24, "Mississippi");
		stateMap.put(25, "Missouri");
		stateMap.put(26, "Montana");
		stateMap.put(27, "Nebraska");
		stateMap.put(28, "Nevada");
		stateMap.put(29, "New Hampshire");
		stateMap.put(30, "New Jersey");
		stateMap.put(31, "New Mexico");
		stateMap.put(32, "New York");
		stateMap.put(33, "North Carolina");
		stateMap.put(34, "North Dakota");
		stateMap.put(35, "Ohio");
		stateMap.put(36, "Oklahoma");
		stateMap.put(37, "Oregon");
		stateMap.put(38, "Pennsylvania");
		stateMap.put(39, "Rhode Island");
		stateMap.put(40, "South Carolina");
		stateMap.put(41, "South Dakota");
		stateMap.put(42, "Tennessee");
		stateMap.put(43, "Texas");
		stateMap.put(44, "Utah");
		stateMap.put(45, "Vermont");
		stateMap.put(46, "Virginia");
		stateMap.put(47, "Washington");
		stateMap.put(48, "West Virginia");
		stateMap.put(49, "Wisconsin");
		stateMap.put(50, "Wyoming");		
		
	}
	
	/**
	 * Set quantity for named WebElement.
	 * 
	 * @param qty
	 * @param obj
	 * @throws InterruptedException
	 */
	public void setQuantity(String qty, int num) throws InterruptedException {		
		WebElement obj = null;
		try {			
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
			if(obj != null) { 
				obj.clear();
				obj.sendKeys(qty);
			}			
			
		} catch  (Error e) {
			verificationErrors.append(e.toString());						
		}
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
			return obj.getAttribute("value").equals(qty);
		} catch (Error e) {
			verificationErrors.append(e.toString());
			return false;
		}
	}
	
	/**
	 * Is quantity value numerical?
	 * 
	 * @param obj 
	 * @return boolean
	 */
	public boolean isQuantityNumerical(int num) {
		
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
			String qty = obj.getAttribute("value");
			NumberFormat formatter = NumberFormat.getInstance();
			ParsePosition pos = new ParsePosition(0);
			formatter.parse(qty, pos);
			return qty.length() == pos.getIndex();		
		} catch (Error e) {
			verificationErrors.append(e.toString());
			return false;
		}
	}
	
	/**
	 * Set the state.
	 * @param state
	 */
	public void setState(String state) {
		this.stateSelector.click();
		this.stateSelector.sendKeys(state);
		this.stateSelector.click();
	}
	
	
	public String getState(int inc) {
		return stateMap.get(inc);
	}
	
	/**
	 * Commit JungleSock order.
	 */
	public void clickCommit() {
		commitButton.click();
	}
	
	/**
	 * Confirm page title is correct.
	 * 
	 * @return boolean
	 */
	public boolean isPageTitleCorrect() {
		if (driver.getTitle().contains(JS_PAGE_TITLE)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Navigate to JungleSock page with environment setting (URL).
	 * 
	 * @param driver
	 * @param env
	 * @return JungleSockPage
	 */
	public static JungleSockPage navigateTo(WebDriver driver, String env) {
		driver.get(TestDriverFactory.createTestDriver(env).getTestUrl());
		buildStates();
		return PageFactory.initElements(driver, JungleSockPage.class);
	}

	/**
	 * Navigate to JungleSock page.
	 * 
	 * @param driver
	 * @return JungleSockPage
	 */
	public static JungleSockPage navigateTo(WebDriver driver) {
		driver.get("https://jungle-socks.herokuapp.com/");
		buildStates();
		return PageFactory.initElements(driver, JungleSockPage.class);
	}
	
	/**
	 * Return to JungleSock page.
	 * 
	 * @param driver
	 */
	public void goBack(WebDriver driver) {
		driver.navigate().back();
	}

	/**
	 * Return to JungleSock page, then refresh that page
	 * 
	 * @param driver
	 */	
	public void goBackRefresh(WebDriver driver) {
		driver.navigate().back();
		driver.navigate().refresh();
	}

}
