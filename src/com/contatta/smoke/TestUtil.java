package com.contatta.smoke;

import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.apache.log4j.Logger;

public class TestUtil {
	private static Logger log4j = Logger.getLogger(TestUtil.class.getName());
	private static WebDriver driver ;
	private static WebElement element = null;
	private static String pageTitle = "";
	private static int z = 5;
	
	TestUtil(WebDriver drv, Logger logger, int snoozeValue){
		log4j = logger;
		driver = drv;
		z = snoozeValue;
		
	}
		
	void clear(String selector) {
		log4j.info("clicking " + selector);
		element = null;
		try {
			element = driver.findElement(By.cssSelector(selector));
		}
		catch (NoSuchElementException e) {
			log4j.error("didn't find " + selector);
			driver.quit();	
			System.exit(1);
		}
		catch(ElementNotVisibleException e){
    		log4j.error(selector + " is not Visible?");
    		log4j.error("...this exception was caught in TestUtil.");
    		driver.close();
    		System.exit(1);
    	}
		catch(Exception e){
			String line = "Some other kind of exception " 
					+ e.toString()
					+ "\n";
			
			log4j.error(line);
			driver.quit();	
			System.exit(1);
        }
		element.clear();
		this.snooze(z);
		
	}
	
	void click(String selector) {
		log4j.info("clicking " + selector);
		element = null;
		try {
			element = driver.findElement(By.cssSelector(selector));
		}
		catch (NoSuchElementException e) {
			log4j.error("didn't find " + selector);
			driver.quit();	
			System.exit(1);
		}
		catch(ElementNotVisibleException e){
    		log4j.error(selector + " is not Visible?");
    		log4j.error("...this exception was caught in TestUtil.");
    		driver.close();
    		System.exit(1);
    	}
		catch(Exception e){
			String line = "Some other kind of exception " 
					+ e.toString()
					+ "\n";
			
			log4j.error(line);
			driver.quit();	
			System.exit(1);
        }
		element.click();
		this.snooze(z);
		
	}

	String getAttribute(String selector, String name){
		log4j.info("clicking " + selector);
		element = null;
		try {
			element = driver.findElement(By.cssSelector(selector));
		}
		catch (NoSuchElementException e) {
			log4j.error("didn't find " + selector);
			driver.quit();	
			System.exit(1);
		}
		catch(ElementNotVisibleException e){
    		log4j.error(selector + " is not Visible?");
    		log4j.error("...this exception was caught in TestUtil.");
    		driver.close();
    		System.exit(1);
    	}
		catch(Exception e){
			String line = "Some other kind of exception " 
					+ e.toString()
					+ "\n";
			
			log4j.error(line);
			driver.quit();	
			System.exit(1);
        }
		return (element.getAttribute(name));
	}
	
	Iterator<WebElement> getGridIterator(String selector){
		List<WebElement> elements = null;
		Iterator<WebElement> iterator;
		try{
			elements = driver.findElements(By.cssSelector(selector));
			int size = elements.size();
			log4j.info("looks like there are " + size + " elements currently pulled in the dgrid");
			Assert.assertTrue(size>0);
			
		}
		catch(AssertionError e){
			log4j.error("Warning: Expected grid to contain some elements, it didn't");
			driver.quit();	
			System.exit(1);
		}
		catch (NoSuchElementException e) {
			log4j.error("didn't find " + selector);
			driver.quit();	
			System.exit(1);
		}
		catch(ElementNotVisibleException e){
    		log4j.error(selector + " is not Visible?");
    		log4j.error("...this exception was caught in TestUtil.");
    		driver.close();
    		System.exit(1);
    	}
		iterator = elements.iterator();
		return iterator;
	}
	
	String getText(String selector){
		log4j.info("clicking " + selector);
		element = null;
		try {
			element = driver.findElement(By.cssSelector(selector));
		}
		catch (NoSuchElementException e) {
			log4j.error("didn't find " + selector);
			driver.quit();	
			System.exit(1);
		}
		catch(ElementNotVisibleException e){
    		log4j.error(selector + " is not Visible?");
    		log4j.error("...this exception was caught in TestUtil.");
    		driver.close();
    		System.exit(1);
    	}
		catch(Exception e){
			String line = "Some other kind of exception " 
					+ e.toString()
					+ "\n";
			
			log4j.error(line);
			driver.quit();	
			System.exit(1);
        }
		return (element.getText());
	}
	
	boolean isTextPresent(String text) {
		if(driver.getPageSource().contains(text)) {
			log4j.info(text + " is present");
			return true;
		}
		log4j.info(text + " is not on the page");
		return false;
	}
	
	boolean isVisible(String selector) {
		try{
			if(driver.findElement(By.cssSelector(selector)).isDisplayed()){
				log4j.info(selector + " is displayed");
				return true;
			}
		}
		catch(NoSuchElementException e){
			log4j.error(selector + " isVisible threw " + e);
			log4j.error("we're handling it and just returning a false");
			return false;
		}
		catch(ElementNotVisibleException e){
    		log4j.error(selector + " is not Visible");
    		log4j.error("...wasn't expecting the element.isDisplayed method to throw this.");
    		return false;
    	}
		
		log4j.info(selector + " is not displayed (outside of try/catch)");
		return false;
	}
	
	void send(String selector, String keys) {
		log4j.info("sending " + keys + " to " + selector);
		try {
			element = driver.findElement(By.cssSelector(selector));
			element.sendKeys(keys);
		}
		catch (NoSuchElementException e) {
			log4j.warn("didn't find " + selector);
			driver.quit();	
			System.exit(1);
		}
		catch(ElementNotVisibleException e){
    		log4j.error(selector + " is not Visible?");
    		log4j.error("...this exception was caught in TestUtil.");
    		driver.close();
    		System.exit(1);
    	}
		catch(Exception e){
			String line = "Some other kind of exception " 
					+ e.toString()
					+ "\n";
			
			log4j.error(line);
			driver.quit();	
			System.exit(1);
        }
		
		this.snooze(z);
	}
	
	void snooze(int n){
		try{
        	Thread.sleep(n*1000);
        	}
        catch(Exception e){
        	log4j.error("sleep interrupted");
			driver.quit();	
			System.exit(1);
        	}	
	
	}
	
	void whereAmI(){
		pageTitle = driver.getTitle();
		log4j.info("we are at " + pageTitle);
	}


}
