package com.excilys.mviegas.speccdb.selenium.nojs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Tests2 {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	@Test
	public void testA() throws Exception {
		driver.get(baseUrl + "/speccdb/views/addComputer.jsp");

		driver.findElement(By.id("btnSubmit")).click();

		driver.findElement(By.id("btnSubmit")).click();

		driver.findElement(By.id("searchsubmit")).click();

		driver.findElement(By.linkText("MacBook Pro 15.4 inch")).click();
	}

	/**
	 * Test d'un enregistrement avec une date < Timestamp
	 *
	 * @throws Exception
	 */
	@Test
	public void test11() throws Exception {
		driver.get(baseUrl + "/speccdb/views/addComputer.jsp");

		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("UnNom");
		driver.findElement(By.id("introducedDate")).click();
		driver.findElement(By.className("ui-datepicker-title")).isDisplayed();
		driver.findElement(By.id("introducedDate")).clear();
		driver.findElement(By.id("introducedDate")).sendKeys("01/01/1111");
		driver.findElement(By.id("btnSubmit")).click();
	}

	@Test
	public void testName() throws Exception {
		driver.get(baseUrl + "/speccdb/views/addComputer.jsp");
		assertEquals("Computer Database", driver.getTitle());
		try {
			assertEquals("", driver.findElement(By.id("name")).getAttribute("value"));
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		try {
			assertEquals("", driver.findElement(By.id("name")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		String valueName = driver.findElement(By.id("name")).getText();
		try {
			assertEquals("Computer name Computer's Name is compulsory.", driver.findElement(By.cssSelector("div.form-group.")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if ("Computer's Name is compulsory.".equals(driver.findElement(By.cssSelector("span.help-block")).getText())) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}

		for (int second = 0;; second++) {
			if (second >= 60) fail("timeout");
			try { if (isElementPresent(By.cssSelector("span.help-block"))) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}

		boolean aaaa = isElementPresent(By.cssSelector("span.help-block"));


	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
