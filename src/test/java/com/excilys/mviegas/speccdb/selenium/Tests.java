package com.excilys.mviegas.speccdb.selenium;

import com.excilys.mviegas.speccdb.persist.jdbc.ComputerDao;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.lift.Matchers;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class Tests extends BaseSeleniumTest {

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void start() throws Exception {
		mWebDriver.get(getApplicationUrl());

		assertEquals("Computer Database", driver.getTitle());

		assertEquals("Application - Computer Database", driver.findElement(By.cssSelector("a.navbar-brand")).getText());

		assertEquals("574 Computers found", driver.findElement(By.id("homeTitle")).getText());

		assertEquals("", driver.findElement(By.id("searchbox")).getAttribute("value"));
		assertEquals("", driver.findElement(By.id("searchbox")).getText());

		assertEquals("MacBook Pro 15.4 inch", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[2]")).getText());
		assertEquals("", driver.findElement(By.cssSelector("span.glyphicon.glyphicon-sort-by-attributes")).getText());
		assertEquals("CM-2a", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[2]")).getText());
		assertEquals("CM-200", driver.findElement(By.xpath("//tbody[@id='results']/tr[3]/td[2]")).getText());
		assertEquals("CM-5e", driver.findElement(By.xpath("//tbody[@id='results']/tr[4]/td[2]")).getText());
		assertEquals("CM-5", driver.findElement(By.xpath("//tbody[@id='results']/tr[5]/td[2]")).getText());
		assertEquals("MacBook Pro", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[2]")).getText());
		assertEquals("Apple IIc", driver.findElement(By.xpath("//tbody[@id='results']/tr[8]/td[2]")).getText());
		assertEquals("Apple IIGS", driver.findElement(By.xpath("//tbody[@id='results']/tr[9]/td[2]")).getText());

		assertEquals("10", driver.findElement(By.linkText("10")).getText());
		assertThat(driver.findElement(By.linkText("10")).getAttribute("class").split(" "), org.hamcrest.Matchers.hasItemInArray("active"));
		assertThat(driver.findElement(By.linkText("50")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("100")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
	}

	@Test
	public void switchPage1() throws Exception {
		mWebDriver.get(getApplicationUrl());
		driver.findElement(By.linkText("1")).click();

		assertEquals("Computer Database", driver.getTitle());

		assertEquals("Application - Computer Database", driver.findElement(By.cssSelector("a.navbar-brand")).getText());

		assertEquals("574 Computers found", driver.findElement(By.id("homeTitle")).getText());

		assertEquals("", driver.findElement(By.id("searchbox")).getAttribute("value"));
		assertEquals("", driver.findElement(By.id("searchbox")).getText());

		assertEquals("MacBook Pro 15.4 inch", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[2]")).getText());
		assertEquals("", driver.findElement(By.cssSelector("span.glyphicon.glyphicon-sort-by-attributes")).getText());
		assertEquals("CM-2a", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[2]")).getText());
		assertEquals("CM-200", driver.findElement(By.xpath("//tbody[@id='results']/tr[3]/td[2]")).getText());
		assertEquals("CM-5e", driver.findElement(By.xpath("//tbody[@id='results']/tr[4]/td[2]")).getText());
		assertEquals("CM-5", driver.findElement(By.xpath("//tbody[@id='results']/tr[5]/td[2]")).getText());
		assertEquals("MacBook Pro", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[2]")).getText());
		assertEquals("Apple IIc", driver.findElement(By.xpath("//tbody[@id='results']/tr[8]/td[2]")).getText());
		assertEquals("Apple IIGS", driver.findElement(By.xpath("//tbody[@id='results']/tr[9]/td[2]")).getText());

		assertEquals("10", driver.findElement(By.linkText("10")).getText());
		assertThat(driver.findElement(By.linkText("10")).getAttribute("class").split(" "), org.hamcrest.Matchers.hasItemInArray("active"));
		assertThat(driver.findElement(By.linkText("50")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("100")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));

		assertThat(driver.findElement(By.linkText("1")).getAttribute("class").split(" "), (org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("2")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("3")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("4")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
	}

	@Test
	public void switchPage2() throws Exception {
		mWebDriver.get(getApplicationUrl());
		driver.findElement(By.linkText("3")).click();

		assertEquals("Computer Database", driver.getTitle());

		assertEquals("Application - Computer Database", driver.findElement(By.cssSelector("a.navbar-brand")).getText());

		assertEquals("574 Computers found", driver.findElement(By.id("homeTitle")).getText());

		assertEquals("", driver.findElement(By.id("searchbox")).getAttribute("value"));
		assertEquals("", driver.findElement(By.id("searchbox")).getText());

		assertEquals("MacBook Pro 15.4 inch", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[2]")).getText());
		assertEquals("", driver.findElement(By.cssSelector("span.glyphicon.glyphicon-sort-by-attributes")).getText());
		assertEquals("CM-2a", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[2]")).getText());
		assertEquals("CM-200", driver.findElement(By.xpath("//tbody[@id='results']/tr[3]/td[2]")).getText());
		assertEquals("CM-5e", driver.findElement(By.xpath("//tbody[@id='results']/tr[4]/td[2]")).getText());
		assertEquals("CM-5", driver.findElement(By.xpath("//tbody[@id='results']/tr[5]/td[2]")).getText());
		assertEquals("MacBook Pro", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[2]")).getText());
		assertEquals("Apple IIc", driver.findElement(By.xpath("//tbody[@id='results']/tr[8]/td[2]")).getText());
		assertEquals("Apple IIGS", driver.findElement(By.xpath("//tbody[@id='results']/tr[9]/td[2]")).getText());

		assertEquals("10", driver.findElement(By.linkText("10")).getText());
		assertThat(driver.findElement(By.linkText("10")).getAttribute("class").split(" "), org.hamcrest.Matchers.hasItemInArray("active"));
		assertThat(driver.findElement(By.linkText("50")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("100")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
	}



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
