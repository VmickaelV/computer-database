package com.excilys.mviegas.speccdb.selenium;

import com.excilys.mviegas.speccdb.DatabaseUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

/**
 * Created by excilys on 15/04/16.
 */
public class SpringBugFixRegressionTest extends BaseSeleniumTest {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		openAndWait();
	}

	@Test
	@Ignore("bug dans le tri")
	public void name() throws Exception {
		DatabaseUtils.resetDatabase();
		driver.findElement(By.linkText("2")).click();
		driver.findElement(By.linkText("4")).click();
		driver.findElement(By.linkText("Introduced date")).click();
		driver.findElement(By.id("searchbox")).clear();
		driver.findElement(By.id("searchbox")).sendKeys("apple");
		driver.findElement(By.id("searchsubmit")).click();

		driver.findElement(By.linkText("Computer name")).click();

		assertEquals("Computer Database", driver.getTitle());
		assertEquals("13 Computers found", driver.findElement(By.id("homeTitle")).getText());
		assertTrue(isElementPresent(By.id("homeTitle")));

		assertEquals(10, driver.findElements(By.xpath("//tbody[@id='results']/tr")).size());

		// Assert Header Table
		WebElement headerElement = driver.findElement(By.linkText("Computer name"));
		assertEquals("Computer name", headerElement.getText());
		assertEquals("glyphicon glyphicon-sort-by-attributes-alt", headerElement.findElement(By.xpath("span[2]")).getAttribute("class"));
		assertEquals("glyphicon glyphicon-chevron-up", headerElement.findElement(By.xpath("span[1]")).getAttribute("class"));

		headerElement = driver.findElement(By.linkText("Introduced date"));
		assertEquals("Introduced date", headerElement.getText());
		assertEquals("", headerElement.findElement(By.cssSelector("span.glyphicon.glyphicon-sort-by-attributes")).getText());
		assertFalse(isElementPresent(headerElement, By.cssSelector("span.glyphicon.glyphicon-chevron-up")));

		headerElement = driver.findElement(By.linkText("Discontinued date"));
		assertEquals("Discontinued date", headerElement.getText());
		assertEquals("", headerElement.findElement(By.cssSelector("span.glyphicon.glyphicon-sort-by-attributes")).getText());
		assertFalse(isElementPresent(headerElement, By.cssSelector("span.glyphicon.glyphicon-chevron-up")));

		headerElement = driver.findElement(By.linkText("Company"));
		assertEquals("Company", headerElement.getText());
		assertEquals("", headerElement.findElement(By.cssSelector("span.glyphicon.glyphicon-sort-by-attributes")).getText());
		assertFalse(isElementPresent(headerElement, By.cssSelector("span.glyphicon.glyphicon-chevron-up")));


		// Assert Size Search
		assertEquals("10", driver.findElement(By.linkText("10")).getText());
		assertThat(driver.findElement(By.linkText("10")).getAttribute("class").split(" "), org.hamcrest.Matchers.hasItemInArray("active"));
		assertThat(driver.findElement(By.linkText("50")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("100")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));

		// Asserts Contents
		assertEquals("Apple I", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[2]")).getText());
		assertEquals("Apple II", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[2]")).getText());
		assertEquals("Apple II Plus", driver.findElement(By.xpath("//tbody[@id='results']/tr[3]/td[2]")).getText());
		assertEquals("Apple IIc Plus", driver.findElement(By.xpath("//tbody[@id='results']/tr[5]/td[2]")).getText());
		assertEquals("Apple IIGS", driver.findElement(By.xpath("//tbody[@id='results']/tr[7]/td[2]")).getText());
		assertEquals("1976-04-01", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[3]")).getText());
		assertEquals("1977-04-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[3]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[4]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[10]/td[3]")).getText());
		assertEquals("Apple Inc.", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[5]")).getText());
		assertEquals("Apple Inc.", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[5]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[5]")).getText());

		assertEquals("Apple Lisa", driver.findElement(By.xpath("//tbody[@id='results']/tr[10]/td[2]")).getText());
		assertEquals("Apple III Plus", driver.findElement(By.xpath("//tbody[@id='results']/tr[9]/td[2]")).getText());
		assertEquals("Apple III", driver.findElement(By.xpath("//tbody[@id='results']/tr[8]/td[2]")).getText());
		assertEquals("1980-05-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[8]/td[3]")).getText());
		assertEquals("1983-12-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[9]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[10]/td[3]")).getText());
		assertEquals("1984-04-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[8]/td[4]")).getText());
		assertEquals("1984-04-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[9]/td[4]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[10]/td[4]")).getText());
		assertEquals("Apple Inc.", driver.findElement(By.xpath("//tbody[@id='results']/tr[8]/td[5]")).getText());
		assertEquals("Apple Inc.", driver.findElement(By.xpath("//tbody[@id='results']/tr[9]/td[5]")).getText());
		assertEquals("Apple Inc.", driver.findElement(By.xpath("//tbody[@id='results']/tr[10]/td[5]")).getText());


	}
}
