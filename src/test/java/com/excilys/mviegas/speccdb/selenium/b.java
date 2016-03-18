//package com.excilys.mviegas.speccdb.selenium;
//
//import com.thoughtworks.selenium.Selenium;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.WebDriver;
//import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Tests;
//import static org.junit.Assert.*;
//import java.util.regex.Pattern;
//import static org.apache.commons.lang3.StringUtils.join;
//
//public class b {
//	private Selenium selenium;
//
//	@Before
//	public void setUp() throws Exception {
//		WebDriver mWebDriver = new FirefoxDriver();
//		String mBaseUrl = "http://localhost:8080/";
//		selenium = new WebDriverBackedSelenium(mWebDriver, mBaseUrl);
//	}
//
//	@Test
//	public void testB() throws Exception {
//		selenium.open("/speccdb/views/dashboard.jsp");
//		selenium.click("id=searchsubmit");
//		selenium.waitForPageToLoad("30000");
//		selenium.click("link=MacBook Pro 15.4 inch");
//		selenium.waitForPageToLoad("30000");
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		selenium.stop();
//	}
//}
