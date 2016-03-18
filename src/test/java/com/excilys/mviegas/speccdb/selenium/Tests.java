package com.excilys.mviegas.speccdb.selenium;

import com.excilys.mviegas.speccdb.BaseTest;
import org.junit.Test;
import org.openqa.selenium.*;

import static org.junit.Assert.*;

public class Tests extends BaseTest {
	@org.junit.Test
	public void testA() throws Exception {
		mWebDriver.get(mBaseUrl + "/speccdb/views/addComputer.jsp");
		mWebDriver.findElement(By.id("btnSubmit")).click();

		mWebDriver.findElement(By.id("searchsubmit")).click();

		mWebDriver.findElement(By.linkText("MacBook Pro 15.4 inch")).click();
	}

	@Test
	public void testName() throws Exception {
		mWebDriver.get(mBaseUrl + "/speccdb/views/addComputer.jsp");
		mWebDriver.findElement(By.id("name")).clear();
		mWebDriver.findElement(By.id("name")).sendKeys("UnNom");
		mWebDriver.findElement(By.id("introducedDate")).click();
		assertTrue(mWebDriver.findElement(By.className("ui-datepicker-title")).isDisplayed());
		mWebDriver.findElement(By.id("introducedDate")).clear();
		mWebDriver.findElement(By.id("introducedDate")).sendKeys("01/01/1111");
		mWebDriver.findElement(By.id("btnSubmit")).click();

		assertEquals("Computer Database", mWebDriver.getTitle());

	}
}
