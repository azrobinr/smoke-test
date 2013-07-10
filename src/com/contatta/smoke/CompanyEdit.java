package com.contatta.smoke;

import com.contatta.smoke.Config;
import com.contatta.smoke.TestUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
//import org.hamcrest.*;
//import org.testng.annotations.*;
//import java.util.concurrent.TimeUnit;

public class CompanyEdit {

		static Config cfg;
		static DesiredCapabilities dCaps;
		static JavascriptExecutor js;
		static Logger log4j;
		static TestUtil util;
		static WebDriver driver;
		static WebElement element = null;
		
		static String name = "";
		static String pageTitle = "";
		
		static int z = 5;
		static int maxGridLoopCount = 5;
		
		
		@BeforeClass
		public static void setUp() throws Exception {
			log4j = Logger.getLogger(CompanyDetail.class.getName());
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
		
		@Before
		public void naviateToCompanyEditView() throws Exception {
			//open company list view
			driver.findElement(By.cssSelector(".quick-menu-primary-item .icon-briefcase")).click();
			util.snooze(z);
			pageTitle = driver.getTitle();
			log4j.info("just clicked Company icon from home view, the page title is "
					+ pageTitle);
			util.snooze(z);
			List<WebElement> messages = driver.findElements(By.cssSelector(".contatta-company-grid .dgrid-row"));
			int size = messages.size();
			log4j.info("looks like there are " + size + " elements currently pulled in the company dgrid");
			Assert.assertTrue(size>0);
			Iterator<WebElement> iterator = messages.iterator();
		
			iterator.next().click();
			util.snooze(z);
			//get the company name
		    element = driver.findElement(By.cssSelector(".p-name"));
		    name = element.getText();
			pageTitle = driver.getTitle();
			log4j.info("just clicked company from list view, the page title is " + pageTitle);
			log4j.info("looks like the name of the company is " + name);
			driver.findElement(By.cssSelector(".detail-content .detail-edit-button")).click();;
		    util.snooze(z);
		    log4j.info("just clicked edit from detail view for " + name);
			
		}
		
		@Test
		public void setStatus() throws Exception {
		    // set status
			Assert.assertTrue("edit delete button is visible assertion", 
					util.isVisible(".edit-delete"));
			Assert.assertTrue("edit cancel button is visible assertion", 
					util.isVisible(".edit-cancel"));
			Assert.assertTrue("edit save button is visible assertion", 
					util.isVisible(".edit-save"));
			Assert.assertTrue("status picklist is visible assertion", 
					util.isVisible(".p-status .select-input"));	
		    driver.findElement(By.cssSelector(".p-status .select-input")).click();
		    util.snooze(z);
		    driver.findElement(By.cssSelector(".select-item[data-id='company.status.new']")).click();
		    util.snooze(z);
		    driver.findElement(By.cssSelector(".edit-content .edit-save")).click();
		    util.snooze(z);
		}
		
		@Test
		public void setType() throws Exception {
		    
			Assert.assertTrue("edit delete button is visible assertion", 
					util.isVisible(".edit-delete"));
			Assert.assertTrue("edit cancel button is visible assertion", 
					util.isVisible(".edit-cancel"));
			Assert.assertTrue("edit save button is visible assertion", 
					util.isVisible(".edit-save"));
			Assert.assertTrue("type picklist is visible assertion", 
					util.isVisible(".extra-section .p-type .select-input"));	
			driver.findElement(By.cssSelector(".extra-section .p-type .select-input")).click();
			util.snooze(z);
			driver.findElement(By.cssSelector(".select-item[data-id='company.type.prospect']")).click();
			util.snooze(z);
			driver.findElement(By.cssSelector(".edit-content .edit-save")).click();
		    util.snooze(z);
		}
		
		
		@Test
		public void setSource() throws Exception {
			Assert.assertTrue("edit delete button is visible assertion", 
					util.isVisible(".edit-delete"));
			Assert.assertTrue("edit cancel button is visible assertion", 
					util.isVisible(".edit-cancel"));
			Assert.assertTrue("edit save button is visible assertion", 
					util.isVisible(".edit-save"));	
			Assert.assertTrue("source picklist is visible assertion", 
					util.isVisible(".p-source .select-input"));	
			driver.findElement(By.cssSelector(".p-source .select-input")).click();
			util.snooze(z);
			driver.findElement(By.cssSelector(".select-item[data-id='company.source.referral']")).click();
			util.snooze(z);
			driver.findElement(By.cssSelector(".edit-content .edit-save")).click();
		    util.snooze(z);
		}
		
		
		@After
		public void closeTab() throws Exception {
			
		    driver.findElement(By.cssSelector(".dijitClosable .dijitTabCloseIcon")).click();
		    util.snooze(z);
		    
		}
		
		@AfterClass
		  public static void tearDown() throws Exception {
			//we're done, close the browser
			log4j.info("I think we're done...........");
		    driver.quit();
		}

}
