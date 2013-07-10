package com.contatta.smoke;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.contatta.smoke.Config;
import com.contatta.smoke.TestUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

public class AddContact {

		static Logger log4j;
		static DesiredCapabilities dCaps;
		static Config cfg;
		static WebDriver driver;
		static WebElement element = null;
		static JavascriptExecutor js;
		static String pageTitle = "";
		static int z = 5;
		static int maxGridLoopCount = 5;
		
		//for now, define some data that should probably come from a file
		static String first = "Tweety";
		static String last = "Bird";
		static String email = "tweety@acmelabs.com";
		static String phoneType = "mobile";
		static String phone = "666-555-4444";
		static String company = "ACME Labs";
		static String title = "Recon";
		static String status = "new";
		static String type = "prospect";
		static String tag = "VIP";
		
		
		static TestUtil util;
		
		@BeforeClass
		public static void setUp() throws Exception {
			log4j = Logger.getLogger(AddContact.class.getName());
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
		public void newContact() throws Exception {
		    
			driver.findElement(By.cssSelector(".navigation-primary .icon-contatta")).click();
			driver.findElement(By.cssSelector(".navigation-quick-actions .icon-user")).click();
		    String title = js.executeScript("return document.title").toString();
		    log4j.info(title);
		    
			Assert.assertTrue("edit cancel button is visible assertion", 
					util.isVisible(".edit-cancel"));
			Assert.assertTrue("edit save button is visible assertion", 
					util.isVisible(".edit-save"));
			
			// email
			
			String selector = ".email-collection .p-type .select-input";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			driver.findElement(By.cssSelector(".select-item[data-id='contact.emailType.work']")).click();
			util.snooze(z);
			
			selector = ".p-email .text-field-input";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).sendKeys(email);
			util.snooze(z);
			
			// name
			
			selector = ".p-firstName .text-field-input";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).sendKeys(first);
			util.snooze(z);
			
			selector = ".p-lastName .text-field-input";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).sendKeys(last);
			util.snooze(z);
			
			// add primary phone
			
			selector = ".phone-collection .p-type .select-input";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			selector = ".select-item[data-id='contact.phoneType." + phoneType + "']";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			
			selector = ".p-phone .text-field-input";;
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).sendKeys(phone);
			util.snooze(z);
			
			//company name
			
			selector = ".p-company-name .select-input";
			Assert.assertTrue(util.isVisible(selector));	
		    driver.findElement(By.cssSelector(selector)).click();
		    driver.findElement(By.cssSelector(selector)).sendKeys(company);
		    driver.findElement(By.cssSelector(selector)).sendKeys(Keys.RETURN);
		    util.snooze(z);
		    
			//title
		    
		    selector = ".p-position .text-field-input";
			Assert.assertTrue(util.isVisible(selector));	
		    driver.findElement(By.cssSelector(selector)).click();
		    driver.findElement(By.cssSelector(selector)).sendKeys(title);
		    driver.findElement(By.cssSelector(selector)).sendKeys(Keys.RETURN);
		    util.snooze(z);
						
			// status  remember to change to .p-status .select-input?
			
		    selector = ".extra-section .edit-row:nth-child(1) .select-input";
			Assert.assertTrue(util.isVisible(selector));	
		    driver.findElement(By.cssSelector(selector)).click();
		    util.snooze(z);
		    driver.findElement(By.cssSelector(".select-item[data-id='contact.status." + status + "']")).click();
		    util.snooze(z);
		    
		    
		    //type - consider using  .extra-section .p-type .select-input
		    
		    selector = ".extra-section .edit-row:nth-child(2) .select-input";
			Assert.assertTrue(util.isVisible(selector));	
		    driver.findElement(By.cssSelector(selector)).click();util.snooze(z);
			driver.findElement(By.cssSelector(".select-item[data-id='contact.type." + type + "']")).click();
			util.snooze(z);
			
		    
		    //tag
		    
		    selector = ".tag-field .select-input"; 
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).sendKeys(tag);
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


			//save
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
