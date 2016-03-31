package com.excilys.mviegas.speccdb.selenium;


import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.lift.Matchers;

import java.util.NoSuchElementException;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

@Ignore
public class Tests extends com.excilys.mviegas.speccdb.selenium.BaseSeleniumTest {

	@org.junit.Test
	public void testA() throws Exception {
		mWebDriver.get(getTargetUrl("addComputer"));

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

	/**
	 * Test de validation champ vide pour nom
	 *
	 * @throws Exception
	 */
	@Test
	public void nameEmpty() throws Exception {

		int n = ComputerDao.INSTANCE.size();
		mWebDriver.get(mBaseUrl + "/speccdb/views/addComputer.jsp");

		assertEquals("Computer Database", driver.getTitle());
		assertEquals("", driver.findElement(By.id("name")).getText());
		assertEquals("", driver.findElement(By.id("name")).getAttribute("value"));

		try {
			assertThat(driver.findElement(By.id("name-error")), not(Matchers.displayed()));
			fail();
		} catch (NoSuchElementException ignored) {

		}

		mWebDriver.findElement(By.id("btnSubmit")).click();

		assertEquals("This field is required.", driver.findElement(By.id("name-error")).getText());

		assertEquals(n, ComputerDao.INSTANCE.size());
	}

	/**
	 * Test de validation champ vide pour nom
	 *
	 * @throws Exception
	 */
	@Test
	public void onlyName() throws Exception {

		int n = ComputerDao.INSTANCE.size();
		mWebDriver.get(mBaseUrl + "/speccdb/views/addComputer.jsp");

		assertEquals("Computer Database", driver.getTitle());
		WebElement element = driver.findElement(By.id("name"));
		assertEquals("", element.getText());
		assertEquals("", element.getAttribute("value"));


		element.clear();
		element.sendKeys("UnNom");


		try {
			assertThat(driver.findElement(By.id("name-error")), not(Matchers.displayed()));
			fail();
		} catch (NoSuchElementException ignored) {

		}

		mWebDriver.findElement(By.id("btnSubmit")).click();

		try {
			assertThat(driver.findElement(By.id("name-error")), not(Matchers.displayed()));
			fail();
		} catch (NoSuchElementException ignored) {

		}

		assertEquals(n+1, ComputerDao.INSTANCE.size());

		assertTrue(mWebDriver.getCurrentUrl().endsWith("dashboard.jsp"));

		assertEquals("× Computer successfully added into Database", driver.findElement(By.xpath("//section[@id='main']/div")).getText());

	}
}
