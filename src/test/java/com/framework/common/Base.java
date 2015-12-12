package com.framework.common;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Base abstract class
 *
 */
public abstract class Base {

	public FirefoxDriver driver;
	protected PropertiesUtil properties;
	protected int timeout;

	@BeforeClass(alwaysRun = true)
	public void setup() {
		driver = new FirefoxDriver();
		this.properties = new PropertiesUtil();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		this.driver.close();
		this.driver.quit();
	}

	@SuppressWarnings("unused")
	private Object executeJavascript(WebDriver driver, String script) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(script);
	}

}
