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

	public class StatusUpdate {

		Logger log4j;
		DesiredCapabilities dCaps;
		Config cfg;
		WebDriver driver;
		WebElement element = null;
		String pageTitle = "";
		int z = 5;
		int maxGridLoopCount = 5;
		
		TestUtil util;
		
		@Before
		public void setUp() throws Exception {
			log4j = Logger.getLogger(StatusUpdate.class.getName());
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
			JavascriptExecutor js = (JavascriptExecutor) driver;
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
		public void run() throws Exception {
			//replace this with switch
			util.click(".quick-menu-secondary-item[data-command='noteAdd']");//open timeline
			util.send(".add-note-content .textarea-field-input","just some text for a note");
			util.click(".add-note-header .edit-save");
		}
		
		@After
		  public void tearDown() throws Exception {
			//we're done, close the browser
			log4j.info("I think we're done...........");
		    driver.quit();
		}

}
