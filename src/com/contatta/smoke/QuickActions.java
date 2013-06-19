package com.contatta.smoke;

import com.contatta.smoke.Config;
import com.contatta.smoke.TestUtil;
import org.apache.log4j.Logger;
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

public class QuickActions {

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
			log4j = Logger.getLogger(QuickActions.class.getName());
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
		
		@Test
		public void composeMessage() throws Exception {
		    
		    util.click(".navigation-primary .icon-contatta");
		    util.click(".navigation-quick-item[data-command='mail']");
		    String title = js.executeScript("return document.title").toString();
		    log4j.info(title);
		    util.click(".compose-message-header .button[data-action='cancelEntry']");
		}
		
		@Test
		public void statusUpdate() throws Exception {
		    
		    util.click(".navigation-primary .icon-contatta");
		    util.click(".navigation-quick-item[data-command='note']");
		    String title = js.executeScript("return document.title").toString();
		    log4j.info(title);
		    util.send(".add-note-content .textarea-field-input","a note from the quick action test");
			util.click(".add-note-header .edit-save");
		}
		
		@Test
		public void newContact() throws Exception {
				

		    util.click(".navigation-primary .icon-contatta");
		    util.click(".navigation-quick-item[data-command='user']");
		    String title = js.executeScript("return document.title").toString();
		    log4j.info(title);
		    util.click(".edit-cancel");
		}
		
		@Test
		public void newCompany() throws Exception {
		    
		    util.click(".navigation-primary .icon-contatta");
		    util.click(".navigation-quick-item[data-command='briefcase']");
		    String title = js.executeScript("return document.title").toString();
		    log4j.info(title);
		    util.click(".edit-cancel");
		}
		
		@AfterClass
		  public static void tearDown() throws Exception {
			//we're done, close the browser
			log4j.info("I think we're done...........");
		    driver.quit();
		}

}
