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

public class ContactEdit {

	static Config cfg;
	static DesiredCapabilities dCaps;
	static JavascriptExecutor js;
	static Logger log4j;
	static TestUtil util;
	static WebDriver driver;
	static WebElement element = null;
	
	static String name = "";
	static String pageTitle = "";
	static String status = "active";
	
	static String address = "1060 W Addison";
	static String city = "Chicago";
	static String state = "IL";
	static String zip = "60613";
	static String country = "USA";
	
	static int z = 5;
	static int maxGridLoopCount = 5;
		
		@BeforeClass
		public static void setUp() throws Exception {
			log4j = Logger.getLogger(ContactEdit.class.getName());
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
		public void naviateToContactDetailView() throws Exception {
			//open company list view
			driver.findElement(By.cssSelector(".quick-menu-primary-item .icon-user")).click();
			util.snooze(z);
			pageTitle = driver.getTitle();
			log4j.info("just clicked Contact icon from home view, the page title is "
					+ pageTitle);
			util.snooze(z);
			List<WebElement> messages = driver.findElements(By.cssSelector(".contatta-contact-grid .dgrid-row"));
			int size = messages.size();
			log4j.info("looks like there are " + size + " elements currently pulled in the contact dgrid");
			Assert.assertTrue(size>0);
			Iterator<WebElement> iterator = messages.iterator();
		
			iterator.next().click();
			util.snooze(z);
			//get the contact name
		    element = driver.findElement(By.cssSelector(".p-name"));
		    name = element.getText();
			pageTitle = driver.getTitle();
			log4j.info("just clicked " + name + " from list view, the page title is " + pageTitle);
			driver.findElement(By.cssSelector(".detail-content .detail-edit-button")).click();;
		    util.snooze(z);
		    log4j.info("just clicked edit from detail view for " + name);
			
		}
		
		@Test
		public void setRating() throws Exception {
			String selector = ".rating-star[data-star='5']";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			selector = ".combined-left .edit-save";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
					
		}
		
		@Test
		public void setAddress() throws Exception {
			//address
			
			String selector = ".address-collection .collection-add-button";
			
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
			selector = ".address-collection .textarea-field-input";
			
			//when writing this section, I was getting two textarea-field-input matches
			//and now I'm not.  So, now we just clear and fill it directly
			
			//List<WebElement> addressFields = util.getElementList(selector);
			//element = addressFields.get(2);
			element = driver.findElement(By.cssSelector(selector));
			element.clear();
			element.sendKeys(address);
			util.snooze(z);
			selector = ".address-collection .text-field-input";
			List<WebElement> addressFields2 = util.getElementList(selector);
			element = addressFields2.get(0);
			element.clear();
			element.sendKeys(city);
			util.snooze(z);
			element = addressFields2.get(1);
			element.clear();
			element.sendKeys(state);
			util.snooze(z);
			element = addressFields2.get(2);
			element.clear();
			element.sendKeys(zip);
			util.snooze(z);
			element = addressFields2.get(3);
			element.clear();
			element.sendKeys(country);
			util.snooze(z);
			selector = ".combined-left .edit-save";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
			util.snooze(z);
		    					
		}
		
		@Test
		public void setStatus() throws Exception {
			// status  remember to change to .p-status .select-input?
		
		    String selector = ".extra-section .edit-row:nth-child(1) .select-input";
			Assert.assertTrue(util.isVisible(selector));	
		    element = driver.findElement(By.cssSelector(selector));
		    element.click();
		    util.snooze(z);
		    element = driver.findElement(By.cssSelector(".select-item[data-id='contact.status." + status + "']"));
		    element.click();
		    util.snooze(z);
		    selector = ".combined-left .edit-save";
			Assert.assertTrue(util.isVisible(selector));	
			driver.findElement(By.cssSelector(selector)).click();
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
