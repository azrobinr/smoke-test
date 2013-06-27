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

import java.util.List;
import java.util.Iterator;
//import org.hamcrest.*;
//import org.testng.annotations.*;
//import java.util.concurrent.TimeUnit;

public class CompanyDetail {

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
			driver.get(cfg.getProperty("url"));
			util.snooze(z);
			util.send(".login-form .login-field[name='username']",cfg.getProperty("usr"));
		    util.send(".login-form .login-field[name='password']",cfg.getProperty("pwd"));
		    util.click(".login-form-container .form-login");
		    util.whereAmI();
		    log4j.info(js.executeScript("return document.title"));
		}
		
		@Before
		public void naviateToCompanyDetailView() throws Exception {
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
			log4j.info("just clicked " + name + " from list view, the page title is " + pageTitle);
			
		}
		
		@Test
		public void getLink() throws Exception {
			String selector = ".p-url";
			Assert.assertTrue(util.isVisible(selector));
			String url = driver.findElement(By.cssSelector(selector)).getText();
			log4j.info("Company URL is " + url);
			Assert.assertTrue(url.contains("http:"));
		}
		
		@Test
		public void getPhoneNum() throws Exception {
			String selector = ".p-phone";
			Assert.assertTrue(util.isVisible(selector));
			String num = driver.findElement(By.cssSelector(selector)).getText();
			log4j.info("Company phone number  is " + num);
			Assert.assertTrue(num.length()>9);
			Assert.assertTrue(num.contains("-"));
		}
		
		@Test
		public void addNote() throws Exception {
		    // set status
			String selector = ".action-bar-item .icon-note";
			Assert.assertTrue(util.isVisible(selector));
		    driver.findElement(By.cssSelector(selector)).click();
		    util.snooze(z);
		    pageTitle = driver.getTitle();
		    log4j.info("just clicked add note, the page title is "
	        		+ pageTitle);
		    Assert.assertTrue(pageTitle.toLowerCase().contains("add note"));
		    util.snooze(z);

		    String s = "Here's another note that Selenium is writing about " + name;
		    selector = ".add-note-content .textarea-field-input";
		    Assert.assertTrue(util.isVisible(selector));
		    driver.findElement(By.cssSelector(selector)).sendKeys(s);
		    util.snooze(z);
		    selector = ".contatta-note-add .edit-content .edit-save";
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