package com.contatta.smoke;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.contatta.smoke.Config;
import com.contatta.smoke.TestUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.*;
//import org.hamcrest.*;
//import org.testng.annotations.*;
//import java.util.concurrent.TimeUnit;

public class AddCompany {

		static Logger log4j;
		static DesiredCapabilities dCaps;
		static Config cfg;
		static WebDriver driver;
		static WebElement element = null;
		static JavascriptExecutor js;
		static String pageTitle = "";
		static int z = 5;
		static int maxGridLoopCount = 5;
		
		static TestUtil util;
		
		@BeforeClass
		public static void setUp() throws Exception {
			log4j = Logger.getLogger(AddCompany.class.getName());
			dCaps = new DesiredCapabilities();
			dCaps.setJavascriptEnabled(true);
			cfg = new Config();
			
			
			if(cfg.getProperty("driver").toLowerCase().contains("phantom")){
				log4j.info("PhantomJS driver selected");
				driver = new PhantomJSDriver(dCaps);
			}
			else if(cfg.getProperty("driver").toLowerCase().contains("chrome")){
				log4j.info("Chrome driver selected");
				driver = new ChromeDriver();
			}
			else{
				log4j.info("FireFox driver selected");
				driver = new FirefoxDriver();
			}
			
			z = Integer.parseInt(cfg.getProperty("zzz")); // sValue to snooze in seconds
		    maxGridLoopCount = Integer.parseInt(cfg.getProperty("loop"));
			String target = cfg.getProperty("targetContact");
			log4j.info("target property returned as " + target);
			util = new TestUtil(driver, log4j, z);
			js = (JavascriptExecutor) driver;
			driver.manage().window().maximize();
			
			//login
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
			String formattedDate = sdf.format(date);
			log4j.info("Test beginning at " +formattedDate);
			log4j.info("URL is " + cfg.getProperty("url"));
			driver.get(cfg.getProperty("url"));
			util.snooze(z);
			util.send(".login-form .login-field[name='username']",cfg.getProperty("usr"));
		    util.send(".login-form .login-field[name='password']",cfg.getProperty("pwd"));
		    util.click(".login-form-container .form-login");
		    util.whereAmI();
		    log4j.info(js.executeScript("return document.title"));
		}
		
		@Test
		public void newCompany() throws Exception {
		    
			driver.findElement(By.cssSelector(".navigation-primary .icon-contatta")).click();
			driver.findElement(By.cssSelector(".navigation-quick-item[data-command='briefcase']")).click();
		    String title = js.executeScript("return document.title").toString();
		    log4j.info(title);
		    
			Assert.assertTrue("edit cancel button is visible assertion", 
					util.isVisible(".edit-cancel"));
			Assert.assertTrue("edit save button is visible assertion", 
					util.isVisible(".edit-save"));
			
			// url
			
			String selector = ".p-url .text-field-input";
			String text = "http://www.foo.bar";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).sendKeys(text);
			util.snooze(z);
			
			// name
			
			selector = ".p-name .text-field-input";
			text = "Foo Inc.";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).sendKeys(text);
			util.snooze(z);
			
			// add primary phone
			
			selector = ".phone-collection .p-type .select-input";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			selector = ".select-item[data-id='company.phoneType.main']";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			
			selector = ".p-phone .text-field-input";
			text = "444-555-6666";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).sendKeys(text);
			util.snooze(z);
			
			// add second phone
			
			selector = ".phone-collection .collection-add-button";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			
			
			selector = ".phone-collection .collection-row:nth-child(2) .p-type .select-input";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			selector = ".phone-collection .collection-row:nth-child(2) .select-item[data-id='company.phoneType.other']";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			
			selector = ".phone-collection .collection-row:nth-child(2) .p-phone .text-field-input";
			text = "444-555-7777";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).sendKeys(text);
			util.snooze(z);
			
			// email
			
			selector = ".email-collection .p-type .select-input";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			driver.findElement(By.cssSelector(".select-item[data-id='company.emailType.work']")).click();
			util.snooze(z);
			
			selector = ".p-email .text-field-input";
			text = "info@foo.bar";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).sendKeys(text);
			util.snooze(z);
			
			// add second email
			
			selector = ".email-collection .collection-add-button";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			
			selector = ".email-collection .collection-row:nth-child(2) .p-type .select-input";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			selector = ".email-collection .collection-row:nth-child(2) .select-item[data-id='company.emailType.other']";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			
			selector = ".email-collection .collection-row:nth-child(2) .p-email .text-field-input";
			text = "sales@foo.bar";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).sendKeys(text);
			util.snooze(z);
			
			// status
			
			Assert.assertTrue(util.isVisible(".p-status .select-input"));	
		    driver.findElement(By.cssSelector(".p-status .select-input")).click();
		    util.snooze(z);
		    driver.findElement(By.cssSelector(".select-item[data-id='company.status.new']")).click();
		    util.snooze(z);
		    driver.findElement(By.cssSelector(".edit-content .edit-save")).click();
		    util.snooze(z);
		    
		    
		    //type
		    
		    Assert.assertTrue(util.isVisible(".extra-section .p-type .select-input"));	
			driver.findElement(By.cssSelector(".extra-section .p-type .select-input")).click();
			util.snooze(z);
			driver.findElement(By.cssSelector(".select-item[data-id='company.type.prospect']")).click();
			util.snooze(z);
			driver.findElement(By.cssSelector(".edit-content .edit-save")).click();
		    util.snooze(z);
		    
		    //tag
		    
		    selector = ".tag-field .select-input";
			text = "VIP";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).sendKeys(text);
			util.snooze(z);
			
			//account number
			
			selector = ".extra-section .edit-row .text-field-input";
			text = "bada55";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).sendKeys(text);
			util.snooze(z);
			
			//stars
			
			selector = ".rating-star[data-star='3']";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			
			//bookmark
			
			selector = ".bookmark-toggle";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			
			// note (need an array)
			/*
			selector = ".bookmark-toggle";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			*/


			driver.findElement(By.cssSelector(".edit-content .edit-save")).click();
		    util.snooze(z);
		    
		}
		
		@AfterClass
		  public static void tearDown() throws Exception {
			//we're done, close the browser
			log4j.info("I think we're done...........");
		    driver.quit();
		}

}
