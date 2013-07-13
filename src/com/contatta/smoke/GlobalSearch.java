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

public class GlobalSearch {

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
			log4j = Logger.getLogger(GlobalSearch.class.getName());
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
		public void pause() throws Exception {
			log4j.info("this is supposed to pause before each test");
			util.snooze(z);
		}
		
		/*
		@Test
		public void findContactWithEnter() throws Exception {
		    
			log4j.info(".....findContact");
			String target = cfg.getProperty("targetContact");
			log4j.info("target is " + target);
			driver.findElement(By.cssSelector(".navigation-item .icon-search")).click();
			log4j.info("clicked search icon");
			driver.findElement(By.cssSelector(".search-input")).sendKeys(target);
			log4j.info("searching on " + target);
			util.snooze(z);
			driver.findElement(By.cssSelector(".search-input")).sendKeys(Keys.ENTER);
			log4j.info("enter key");
			driver.findElement(By.cssSelector(".search-input")).sendKeys(Keys.ENTER);
			log4j.info("enter key");
			util.snooze(z);
		    String title = js.executeScript("return document.title").toString();
		    log4j.info("expecting " + title + " to include " + target);
		    Assert.assertTrue(title.contains(target));
		    
		}
		
		@Test
		public void findCompanyWithEnter() throws Exception {
		    
			log4j.info(".....findCompany");
			String target = cfg.getProperty("targetCompany");
			log4j.info("target is " + target);
			driver.findElement(By.cssSelector(".navigation-item .icon-search")).click();
			log4j.info("clicked search icon");
			driver.findElement(By.cssSelector(".search-input")).sendKeys(target);
			log4j.info("searching on " + target);
			util.snooze(z);
			driver.findElement(By.cssSelector(".search-input")).sendKeys(Keys.ENTER);
			log4j.info("enter key");
			driver.findElement(By.cssSelector(".search-input")).sendKeys(Keys.ENTER);
			log4j.info("enter key");
			util.snooze(z);
		    String title = js.executeScript("return document.title").toString();
		    log4j.info("expecting " + title + " to include " + target);
		    Assert.assertTrue(title.contains(target));

		}
		
		
		@Test
		public void findNoteWithEnter() throws Exception {
			
			log4j.info(".....findNote");
			String target = "Here's another note";
			log4j.info("target is " + target);
			driver.findElement(By.cssSelector(".navigation-item .icon-search")).click();
			log4j.info("clicked search icon");
			driver.findElement(By.cssSelector(".search-input")).sendKeys(target + Keys.ENTER);
			log4j.info("searching on " + target);
			util.snooze(z);
			//driver.findElement(By.cssSelector(".search-input")).sendKeys(Keys.ENTER);
			//log4j.info("enter key");
			element = util.getElementList(".navigation-search-item-content").get(0);
			element.click();
			util.snooze(z);
		    String title = js.executeScript("return document.title").toString();
		    log4j.info("expecting " + title + " to include " + target);
		    Assert.assertTrue(title.contains(target));
		    
		}
		*/
		
		
		@Test
		public void findContactInDropdown() throws Exception {
		    
			log4j.info(".....findContact");
			String target = cfg.getProperty("targetContact");
			log4j.info("target is " + target);
			driver.findElement(By.cssSelector(".navigation-item .icon-search")).click();
			log4j.info("clicked search icon");
			driver.findElement(By.cssSelector(".search-input")).sendKeys(target);
			log4j.info("searching on " + target);
			util.snooze(z);
			//driver.findElement(By.cssSelector(".search-input")).sendKeys(Keys.ENTER);
			//log4j.info("enter key");
			element = util.getElementList(".navigation-search-item-content").get(0);
			element.click();
			util.snooze(z);
		    String title = js.executeScript("return document.title").toString();
		    log4j.info("expecting " + title + " to include " + target);
		    Assert.assertTrue(title.contains(target));
		    
		}
		
		@Test
		public void findCompanyInDropdown() throws Exception {
		    
			log4j.info(".....findCompany");
			String target = cfg.getProperty("targetCompany");
			log4j.info("target is " + target);
			driver.findElement(By.cssSelector(".navigation-item .icon-search")).click();
			log4j.info("clicked search icon");
			driver.findElement(By.cssSelector(".search-input")).sendKeys(target + Keys.ENTER);
			log4j.info("searching on " + target);
			util.snooze(z);
			//driver.findElement(By.cssSelector(".search-input")).sendKeys(Keys.ENTER);
			//log4j.info("enter key");
			element = util.getElementList(".navigation-search-item-content").get(0);
			element.click();
			util.snooze(z);
		    String title = js.executeScript("return document.title").toString();
		    log4j.info("expecting " + title + " to include " + target);
		    Assert.assertTrue(title.contains(target));

		}
		
		
		@Test
		public void findNoteInDropdown() throws Exception {
			
			log4j.info(".....findNote");
			String target = "Here's another note";
			log4j.info("target is " + target);
			driver.findElement(By.cssSelector(".navigation-item .icon-search")).click();
			log4j.info("clicked search icon");
			driver.findElement(By.cssSelector(".search-input")).sendKeys(target + Keys.ENTER);
			log4j.info("searching on " + target);
			util.snooze(z);
			//driver.findElement(By.cssSelector(".search-input")).sendKeys(Keys.ENTER);
			//log4j.info("enter key");
			element = util.getElementList(".navigation-search-item-content").get(0);
			element.click();
			util.snooze(z);
		    String title = js.executeScript("return document.title").toString();
		    log4j.info("expecting " + title + " to include " + target);
		    Assert.assertTrue(title.contains(target));
		    
		}
		
		
		@After
		public void closeTab() throws Exception {
			driver.findElement(By.cssSelector(".dijitClosable .dijitTabCloseButton")).click();
			util.snooze(z);
		}
		
		@AfterClass
		  public static void tearDown() throws Exception {
			//we're done, close the browser
			log4j.info("I think we're done...........");
		    driver.quit();
		}

}
