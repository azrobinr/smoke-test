package com.contatta.smoke;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.contatta.smoke.Config;
import com.contatta.smoke.TestUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.junit.*;
//import org.hamcrest.*;
//import org.testng.annotations.*;
//import java.util.concurrent.TimeUnit;

public class ComposeEmail {

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
			log4j = Logger.getLogger(ComposeEmail.class.getName());
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
			else if(cfg.getProperty("driver").toLowerCase().contains("safari")){
				log4j.info("Safari driver selected");
				driver = new SafariDriver();
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
		public void composeMessage() throws Exception {
		    
			util.click(".quick-menu-secondary-item[data-command='mailCompose']");
		    
		    List<WebElement> recipients = null;
		    try {
		    	recipients = driver.findElements(By.cssSelector(".compose-recipient-field .entity-lookup-item"));
			}
			catch (NoSuchElementException e) {
				log4j.error("empty textbox list searching for subject, exiting contacts test");
				driver.close();
				System.exit(1);
			}
			catch (Exception e){
				log4j.error("exception thrown searching for subject field, "
	    			  + e.getMessage());
				driver.close();
				System.exit(1);
			} 
		    int size = recipients.size();
		    log4j.info("found " + size + " text-field-inputs in compose window");
			element = recipients.get(0);
			element.sendKeys("xenophilias@yab-yum.org");
			util.snooze(z);
		    
		    // just trying to grab the last text field in the list and stuff in a subject
		      
			List<WebElement> textboxes = null;
			try {
				textboxes = driver.findElements(By.cssSelector(".compose-message-fields .edit-row .text-field-input"));
			}
			catch (NoSuchElementException e) {
				log4j.error("empty textbox list searching for subject, exiting contacts test");
				driver.close();
				System.exit(1);
			}
			catch (Exception e){
				log4j.error("exception thrown searching for subject field, "
	    			  + e.getMessage());
				driver.close();
				System.exit(1);
			} 
			
			size = textboxes.size();
			log4j.info("found " + size + " text-field-inputs in compose window");
			element = textboxes.get(size-1);
			element.sendKeys("Selenium spam");
			util.snooze(z);
	      
			//add a link
			driver.findElement(By.cssSelector(".dijitEditorIconCreateLink")).click();
			driver.findElement(By.cssSelector(".dijitInputInner[id='contatta_fields_RichEditor_0_0_urlInput']")).sendKeys("http://www.mit.edu");
			driver.findElement(By.cssSelector(".dijitInputInner[id='contatta_fields_RichEditor_0_0_textInput']")).sendKeys("a school");
			driver.findElement(By.cssSelector(".dijitReset[id='contatta_fields_RichEditor_0_0_setButton']")).click();
			
			//add an image
			driver.findElement(By.cssSelector(".dijitEditorIconInsertImage")).click();
			driver.findElement(By.cssSelector(".dijitInputInner[id='contatta_fields_RichEditor_0_1_urlInput']")).sendKeys(
					"https://upload.wikimedia.org/wikipedia/commons/3/3d/MIT_Main_Campus_Aerial.jpg");
			//http://licensingbook.com/wp-content/uploads/2013/05/Email-Marketing-SPAM-Law.jpg
			driver.findElement(By.cssSelector(".dijitReset[id='contatta_fields_RichEditor_0_1_setButton']")).click();
				
			// click the italic button
			driver.findElement(By.cssSelector(".dijitEditorIconItalic")).click();
		    
			String handle = driver.getWindowHandle();
			log4j.info("saved window handle " + handle);
			driver.switchTo().frame("contatta_fields_RichEditor_0_iframe");
			try{
				WebElement editable = driver.switchTo().activeElement();
				editable.sendKeys("Here's an email from Selenium for Xenophilias");
				
			}
			catch(NoSuchElementException e) {
				log4j.info("didn't find the activeElement in the iFrame");
			}
					
		    //send(".dijitEditorIFrame","Here's an email from Selenium for " + detailName);
			util.snooze(z);
		    driver.switchTo().window(handle);
		    
		    driver.findElement(By.cssSelector(".contatta-mail-compose .send-button:nth-child(2) .dropdown-button-primary")).click();
		    log4j.info("attempted to click the send button, " + element.toString());
		    util.snooze(z);
		}
		
		
		@AfterClass
		  public static void tearDown() throws Exception {
			//we're done, close the browser
			log4j.info("I think we're done...........");
		    driver.quit();
		}

}
