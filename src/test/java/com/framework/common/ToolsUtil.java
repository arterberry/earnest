package com.framework.common;

import org.testng.Reporter;
//import com.framework.pages.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.sql.*;

/**
 * ToolsUtil class is a set of utility methods to implement within test or page classes.
 *  
 */
public class ToolsUtil {
	
	private static final String FOLDER_NAME = "target/screenshots/";
			
	/**
	 * Create screen capture of application under test.
	 * @param driver
	 * @param pagename
	 * @throws IOException
	 */
	public void setScreenCapture(FirefoxDriver driver, String pagename) throws IOException{
		 FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE), new File(FOLDER_NAME + pagename));		
	}
	
	
	/*
	 * @param long milliseconds
	 * @return void
	 * Sets wait time before executing a transaction.
	 */
	public void setWaitTime(long milliSec) throws InterruptedException{
		Thread.sleep(milliSec);
	}

	
	/*
	 * @param String message
	 * @return void
	 * Writes log to TestNG report
	 */		
	public void setTestLog(String message){
		Reporter.log(message);
	}
	
	/*
	 * @param FirefoxDriver driver, String url, long time, String elementName
	 * @return WebElement
	 * Returns the WebElement under test with an implicit wait time, if it is not readily available.   
	 */			
	public WebElement setImplicitWait(FirefoxDriver driver, String url, long time, String elementName){
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
		driver.get(url);
		WebElement DynamicElement = driver.findElement(By.id(elementName));
		return DynamicElement;
	}
	
	/*
	 * @param FirefoxDriver driver, String url, long time, String elementName
	 * @return WebElement
	 * Returns the WebElement under test with an explicit wait condition applied, before proceeding ahead with code execution.  
	 */		
	public WebElement setExplicitWait(FirefoxDriver driver, String url, long time, String elementName){
		final String idName = elementName;
		driver.get(url);
		WebElement DynamicElement = (new WebDriverWait(driver, time)).until(new ExpectedCondition<WebElement>(){
			@Override
			public WebElement apply(WebDriver d) {
				return d.findElement(By.id(idName));
			}});
		return DynamicElement;
	}
	
	/*
	 * @param  String filename
	 * @return 
	 * Executes an external application   
	 */		
	public void runExec(String filename){
		try	{
		 Runtime rt = Runtime.getRuntime() ;
		 Process p = rt.exec(filename) ;
		 p.destroy() ;
		}catch(Exception e){
			System.out.println(e);
		}	
	}

	/*
	 * @param  String dbURL, String dbName, String user, String password
	 * @return Connection
	 * Connects to an external JDBC location to allow SQL queries.  
	 */			
	public Connection getJDBCConnection(String dbURL, String dbName, String user, String password){
		Connection connect = null;
		String dbDriver = "com.mysql.jdbc.Driver";
		try {
			Class.forName(dbDriver).newInstance();
			connect = DriverManager.getConnection(dbURL + dbName, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connect;		
	}

	
	/*
	 * @param  Connection
	 * @return 
	 * Closes the connection to an external JDBC.  
	 */		
	public void closeJDBCConnection(Connection connect){
		try {
			connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	

	
	
	
}
