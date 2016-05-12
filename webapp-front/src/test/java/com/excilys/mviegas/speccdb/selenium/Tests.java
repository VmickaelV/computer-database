package com.excilys.mviegas.speccdb.selenium;

import com.excilys.mviegas.speccdb.persistence.jdbc.DatabaseManager;
import com.excilys.mviegas.speccdb.utils.DatabaseUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class Tests extends com.excilys.mviegas.speccdb.selenium.BaseSeleniumTest {
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		Connection connection = DatabaseManager.getConnection();
		DatabaseUtils.resetDatabase(connection);
		DatabaseManager.releaseConnection(connection);
	}

	private Connection mConnection;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		authentication("admin", "admin");
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void start() throws Exception {
		openAndWait();

		assertEquals("Computer Database", driver.getTitle());

		assertEquals("Application - Computer Database", driver.findElement(By.cssSelector("a.navbar-brand")).getText());

		assertEquals("574 Computers found", driver.findElement(By.id("homeTitle")).getText());

		assertEquals("", driver.findElement(By.id("searchbox")).getAttribute("value"));
		assertEquals("", driver.findElement(By.id("searchbox")).getText());

		// Assert contenu
		assertEquals("MacBook Pro 15.4 inch", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[2]")).getText());
		assertEquals("", driver.findElement(By.cssSelector("span.glyphicon.glyphicon-sort-by-attributes")).getText());
		assertEquals("CM-2a", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[2]")).getText());
		assertEquals("CM-200", driver.findElement(By.xpath("//tbody[@id='results']/tr[3]/td[2]")).getText());
		assertEquals("CM-5e", driver.findElement(By.xpath("//tbody[@id='results']/tr[4]/td[2]")).getText());
		assertEquals("CM-5", driver.findElement(By.xpath("//tbody[@id='results']/tr[5]/td[2]")).getText());
		assertEquals("MacBook Pro", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[2]")).getText());
		assertEquals("Apple IIc", driver.findElement(By.xpath("//tbody[@id='results']/tr[8]/td[2]")).getText());
		assertEquals("Apple IIGS", driver.findElement(By.xpath("//tbody[@id='results']/tr[9]/td[2]")).getText());

		// Assert Header Table
		WebElement headerElement = driver.findElement(By.linkText("Computer name"));
		assertEquals("Computer name", headerElement.getText());
		assertEquals("", headerElement.findElement(By.cssSelector("span.glyphicon.glyphicon-sort-by-attributes")).getText());
		assertFalse(isElementPresent(headerElement, By.cssSelector("span.glyphicon.glyphicon-chevron-u")));

		headerElement = driver.findElement(By.linkText("Introduced date"));
		assertEquals("Introduced date", headerElement.getText());
		assertEquals("", headerElement.findElement(By.cssSelector("span.glyphicon.glyphicon-sort-by-attributes")).getText());
		assertFalse(isElementPresent(headerElement, By.cssSelector("span.glyphicon.glyphicon-chevron-u")));

		headerElement = driver.findElement(By.linkText("Discontinued date"));
		assertEquals("Discontinued date", headerElement.getText());
		assertEquals("", headerElement.findElement(By.cssSelector("span.glyphicon.glyphicon-sort-by-attributes")).getText());
		assertFalse(isElementPresent(headerElement, By.cssSelector("span.glyphicon.glyphicon-chevron-u")));

		headerElement = driver.findElement(By.linkText("Company"));
		assertEquals("Company", headerElement.getText());
		assertEquals("", headerElement.findElement(By.cssSelector("span.glyphicon.glyphicon-sort-by-attributes")).getText());
		assertFalse(isElementPresent(headerElement, By.cssSelector("span.glyphicon.glyphicon-chevron-u")));

		// Assert Size Search
		assertEquals("10", driver.findElement(By.linkText("10")).getText());
		assertThat(driver.findElement(By.linkText("10")).getAttribute("class").split(" "), org.hamcrest.Matchers.hasItemInArray("active"));
		assertThat(driver.findElement(By.linkText("50")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("100")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));

		assertFalse(isElementPresent(By.cssSelector("span.glyphicon.glyphicon-chevron-up")));
	}

	@Test
	public void switchPage1() throws Exception {
		openAndWait();

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

		assertEquals(10+1, driver.findElements(By.tagName("tr")).size());

		assertEquals("10", driver.findElement(By.linkText("10")).getText());
		assertThat(driver.findElement(By.linkText("10")).getAttribute("class").split(" "), org.hamcrest.Matchers.hasItemInArray("active"));
		assertThat(driver.findElement(By.linkText("50")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("100")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));

		assertThat(driver.findElement(By.linkText("1")).findElement(By.xpath("..")).getAttribute("class").split(" "), (org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("2")).findElement(By.xpath("..")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("3")).findElement(By.xpath("..")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("4")).findElement(By.xpath("..")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("4")).findElement(By.xpath("..")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("»")).findElement(By.xpath("..")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertFalse(isElementPresent(By.linkText("5")));
		assertFalse(isElementPresent(By.linkText("6")));
		assertFalse(isElementPresent(By.linkText("7")));
		assertFalse(isElementPresent(By.linkText("0")));
		assertFalse(isElementPresent(By.linkText("«")));


	}

	@Test
	public void switchPage2() throws Exception {
		openAndWait();

		driver.findElement(By.linkText("2")).click();

		assertEquals("Computer Database", driver.getTitle());

		assertEquals("Application - Computer Database", driver.findElement(By.cssSelector("a.navbar-brand")).getText());

		assertEquals("574 Computers found", driver.findElement(By.id("homeTitle")).getText());

		assertEquals("", driver.findElement(By.id("searchbox")).getAttribute("value"));
		assertEquals("", driver.findElement(By.id("searchbox")).getText());

		assertEquals("Apple II Plus", driver.findElement(By.linkText("Apple II Plus")).getText());
		assertEquals("1980-05-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[3]")).getText());
		assertEquals("Apple Inc.", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[5]")).getText());
		assertEquals("1983-12-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[7]/td[3]")).getText());
		assertEquals("1977-01-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[10]/td[3]")).getText());
		assertTrue(isElementPresent(By.linkText("COSMAC ELF")));


		assertEquals("10", driver.findElement(By.linkText("10")).getText());
		assertThat(driver.findElement(By.linkText("10")).getAttribute("class").split(" "), org.hamcrest.Matchers.hasItemInArray("active"));
		assertThat(driver.findElement(By.linkText("50")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("100")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));

		assertThat(driver.findElement(By.linkText("1")).findElement(By.xpath("..")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("2")).findElement(By.xpath("..")).getAttribute("class").split(" "), (org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("3")).findElement(By.xpath("..")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("4")).findElement(By.xpath("..")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("4")).findElement(By.xpath("..")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("5")).findElement(By.xpath("..")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("»")).findElement(By.xpath("..")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertTrue(isElementPresent(By.linkText("«")));

		assertFalse(isElementPresent(By.linkText("6")));
		assertFalse(isElementPresent(By.linkText("7")));
		assertFalse(isElementPresent(By.linkText("0")));
	}

	@Test
	public void switchLastPage() throws Exception {
		mWebDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

		driver.get(getApplicationUrl() + "/dashboard.html?size=50&page=12");

		assertEquals("Computer Database", driver.getTitle());

		assertEquals("Application - Computer Database", driver.findElement(By.cssSelector("a.navbar-brand")).getText());

		assertEquals("574 Computers found", driver.findElement(By.id("homeTitle")).getText());

		assertTrue(isElementPresent(By.linkText("«")));
		assertFalse(isElementPresent(By.linkText("»")));
	}

	@Test
	public void switchPagePlusSize() throws Exception {
		openAndWait();

		driver.findElement(By.linkText("4")).click();
		driver.findElement(By.linkText("7")).click();
		driver.findElement(By.linkText("9")).click();
		driver.findElement(By.linkText("50")).click();

		assertEquals("Computer Database", driver.getTitle());
		assertEquals("Application - Computer Database", driver.findElement(By.cssSelector("a.navbar-brand")).getText());
		assertEquals("574 Computers found", driver.findElement(By.id("homeTitle")).getText());

		assertEquals("DRTE Computer", driver.findElement(By.linkText("DRTE Computer")).getText());
		assertEquals("PowerEdge", driver.findElement(By.linkText("PowerEdge")).getText());
		assertEquals("Apple Network Server", driver.findElement(By.linkText("Apple Network Server")).getText());
		assertEquals("Goodyear MPP", driver.findElement(By.linkText("Goodyear MPP")).getText());
		assertEquals("Macintosh 128K technical details", driver.findElement(By.linkText("Macintosh 128K technical details")).getText());
		assertEquals("DRTE Computer", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[2]")).getText());
		assertEquals("PowerEdge", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[2]")).getText());
		assertEquals("Apple Network Server", driver.findElement(By.xpath("//tbody[@id='results']/tr[3]/td[2]")).getText());
		assertEquals("Goodyear MPP", driver.findElement(By.xpath("//tbody[@id='results']/tr[4]/td[2]")).getText());
		assertEquals("Macintosh 128K technical details", driver.findElement(By.xpath("//tbody[@id='results']/tr[5]/td[2]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[3]/td[3]")).getText());
		assertEquals("2002-01-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[9]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[5]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[5]/td[5]")).getText());
		assertEquals("IBM", driver.findElement(By.xpath("//tbody[@id='results']/tr[9]/td[5]")).getText());
		assertEquals("Amstrad", driver.findElement(By.xpath("//tbody[@id='results']/tr[11]/td[5]")).getText());
		assertEquals("Sharp Mebius NJ70A", driver.findElement(By.xpath("//tbody[@id='results']/tr[45]/td[2]")).getText());
		assertEquals("HTC Snap", driver.findElement(By.xpath("//tbody[@id='results']/tr[46]/td[2]")).getText());
		assertEquals("Commodore Educator 64", driver.findElement(By.xpath("//tbody[@id='results']/tr[47]/td[2]")).getText());
		assertEquals("Amiga 1500", driver.findElement(By.xpath("//tbody[@id='results']/tr[48]/td[2]")).getText());
		assertEquals("HTC Corporation", driver.findElement(By.xpath("//tbody[@id='results']/tr[46]/td[5]")).getText());
		assertEquals("Commodore International", driver.findElement(By.xpath("//tbody[@id='results']/tr[47]/td[5]")).getText());
		assertEquals("Commodore International", driver.findElement(By.xpath("//tbody[@id='results']/tr[48]/td[5]")).getText());
		assertEquals("Commodore International", driver.findElement(By.xpath("//tbody[@id='results']/tr[49]/td[5]")).getText());
		assertEquals("Commodore International", driver.findElement(By.xpath("//tbody[@id='results']/tr[50]/td[5]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[46]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[47]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[48]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[49]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[50]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[46]/td[4]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[47]/td[4]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[48]/td[4]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[49]/td[4]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[50]/td[4]")).getText());


		assertEquals(50, driver.findElements(By.xpath("//tbody[@id='results']/tr")).size());

		assertThat(driver.findElement(By.linkText("6")).findElement(By.xpath("..")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("8")).findElement(By.xpath("..")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("9")).findElement(By.xpath("..")).getAttribute("class").split(" "), (org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("11")).findElement(By.xpath("..")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("12")).findElement(By.xpath("..")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertTrue(isElementPresent(By.linkText("«")));
		assertTrue(isElementPresent(By.linkText("»")));

		assertFalse(isElementPresent(By.linkText("5")));
		assertFalse(isElementPresent(By.linkText("3")));
		assertFalse(isElementPresent(By.linkText("1")));
		assertFalse(isElementPresent(By.linkText("0")));
		assertFalse(isElementPresent(By.linkText("13")));
		assertFalse(isElementPresent(By.linkText("15")));

		assertThat(driver.findElement(By.linkText("10")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("50")).getAttribute("class").split(" "), (org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("100")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));


	}

	@Test
	public void autreCombinaison() throws Exception {
		openAndWait();

		driver.findElement(By.linkText("3")).click();
		driver.findElement(By.linkText("100")).click();

		assertEquals("Computer Database", driver.getTitle());
		assertEquals("Application - Computer Database", driver.findElement(By.cssSelector("a.navbar-brand")).getText());
		assertEquals("574 Computers found", driver.findElement(By.id("homeTitle")).getText());

		assertEquals(100, driver.findElements(By.xpath("//tbody[@id='results']/tr")).size());

		assertThat(driver.findElement(By.linkText("10")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("50")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("100")).getAttribute("class").split(" "), (org.hamcrest.Matchers.hasItemInArray("active")));
	}

	@Test
	public void autreCombinaison2() throws Exception {
		openAndWait();

		driver.findElement(By.linkText("4")).click();
		driver.findElement(By.linkText("6")).click();
		driver.findElement(By.linkText("100")).click();

		assertEquals("Computer Database", driver.getTitle());
		assertEquals("Application - Computer Database", driver.findElement(By.cssSelector("a.navbar-brand")).getText());
		assertEquals("574 Computers found", driver.findElement(By.id("homeTitle")).getText());

		assertEquals(74, driver.findElements(By.xpath("//tbody[@id='results']/tr")).size());

		assertThat(driver.findElement(By.linkText("10")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("50")).getAttribute("class").split(" "), not(org.hamcrest.Matchers.hasItemInArray("active")));
		assertThat(driver.findElement(By.linkText("100")).getAttribute("class").split(" "), (org.hamcrest.Matchers.hasItemInArray("active")));
	}

	@Test
	public void autrecombinaison3() throws Exception {
		openAndWait();

		driver.findElement(By.id("searchbox")).clear();
		driver.findElement(By.id("searchbox")).sendKeys("apple");
		driver.findElement(By.id("searchsubmit")).click();

		assertEquals("Computer Database", driver.getTitle());
		assertEquals("13 Computers found", driver.findElement(By.id("homeTitle")).getText());
		assertTrue(isElementPresent(By.id("homeTitle")));

		assertEquals(10, driver.findElements(By.xpath("//tbody[@id='results']/tr")).size());
	}

	@Test
	public void autrecombinaison4() throws Exception {
		openAndWait();

		driver.findElement(By.id("searchbox")).clear();
		driver.findElement(By.id("searchbox")).sendKeys("apple");
		driver.findElement(By.id("searchsubmit")).click();

		driver.findElement(By.linkText("50")).click();

		assertEquals("Computer Database", driver.getTitle());
		assertEquals("13 Computers found", driver.findElement(By.id("homeTitle")).getText());
		assertTrue(isElementPresent(By.id("homeTitle")));

		assertEquals(13, driver.findElements(By.xpath("//tbody[@id='results']/tr")).size());
	}

	@Ignore("Le temps de trouvé une solution pour trier différemment")
	@Test
	public void autrecombinaison5() throws Exception {
		openAndWait();

		Connection connection = DatabaseManager.getConnection();
		DatabaseUtils.resetDatabase(connection);
		DatabaseManager.releaseConnection(connection);

		driver.findElement(By.id("searchbox")).clear();
		driver.findElement(By.id("searchbox")).sendKeys("apple");
		driver.findElement(By.id("searchsubmit")).click();

		driver.findElement(By.linkText("50")).click();

		driver.findElement(By.linkText("Computer name")).click();

		assertEquals("Computer Database", driver.getTitle());
		assertEquals("13 Computers found", driver.findElement(By.id("homeTitle")).getText());
		assertTrue(isElementPresent(By.id("homeTitle")));

		assertEquals(13, driver.findElements(By.xpath("//tbody[@id='results']/tr")).size());

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

		// Asserts Contents
		assertEquals("Apple I", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[2]")).getText());
		assertEquals("Apple II", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[2]")).getText());
		assertEquals("Apple II Plus", driver.findElement(By.xpath("//tbody[@id='results']/tr[3]/td[2]")).getText());
		assertEquals("Apple IIc Plus", driver.findElement(By.xpath("//tbody[@id='results']/tr[5]/td[2]")).getText());
		assertEquals("Apple IIGS", driver.findElement(By.xpath("//tbody[@id='results']/tr[7]/td[2]")).getText());
		assertEquals("Apple Network Server", driver.findElement(By.xpath("//tbody[@id='results']/tr[11]/td[2]")).getText());
		assertEquals("Apple Newton", driver.findElement(By.xpath("//tbody[@id='results']/tr[12]/td[2]")).getText());
		assertEquals("Apple PenLite", driver.findElement(By.xpath("//tbody[@id='results']/tr[13]/td[2]")).getText());
		assertEquals("1976-04-01", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[3]")).getText());
		assertEquals("1977-04-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[3]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[4]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[10]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[11]/td[3]")).getText());
		assertEquals("1993-01-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[12]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[13]/td[3]")).getText());
		assertEquals("Apple Inc.", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[5]")).getText());
		assertEquals("Apple Inc.", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[5]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[5]")).getText());
		assertEquals("Apple Inc.", driver.findElement(By.xpath("//tbody[@id='results']/tr[10]/td[5]")).getText());
		assertEquals("Apple Inc.", driver.findElement(By.xpath("//tbody[@id='results']/tr[12]/td[5]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[13]/td[5]")).getText());
	}

	@Test
	public void autrecombinaison6() throws Exception {
		openAndWait();

		driver.findElement(By.linkText("Discontinued date")).click();
		driver.findElement(By.linkText("Discontinued date")).click();

		assertEquals("Computer Database", driver.getTitle());
		assertEquals("574 Computers found", driver.findElement(By.id("homeTitle")).getText());
		assertTrue(isElementPresent(By.id("homeTitle")));

		assertEquals(10, driver.findElements(By.xpath("//tbody[@id='results']/tr")).size());

		// Assert Header Table
		WebElement headerElement = driver.findElement(By.linkText("Computer name"));
		assertEquals("Computer name", headerElement.getText());
		assertEquals("glyphicon glyphicon-sort-by-attributes", headerElement.findElement(By.xpath("span[1]")).getAttribute("class"));
		assertFalse(isElementPresent(headerElement, By.cssSelector("span.glyphicon.glyphicon-chevron-up")));

		headerElement = driver.findElement(By.linkText("Introduced date"));
		assertEquals("Introduced date", headerElement.getText());
		assertEquals("", headerElement.findElement(By.cssSelector("span.glyphicon.glyphicon-sort-by-attributes")).getText());
		assertFalse(isElementPresent(headerElement, By.cssSelector("span.glyphicon.glyphicon-chevron-up")));

		headerElement = driver.findElement(By.linkText("Discontinued date"));
		assertEquals("Discontinued date", headerElement.getText());
		assertEquals("glyphicon glyphicon-sort-by-attributes", headerElement.findElement(By.xpath("span[2]")).getAttribute("class"));
		assertEquals("glyphicon glyphicon-chevron-down", headerElement.findElement(By.xpath("span[1]")).getAttribute("class"));

		headerElement = driver.findElement(By.linkText("Company"));
		assertEquals("Company", headerElement.getText());
		assertEquals("", headerElement.findElement(By.cssSelector("span.glyphicon.glyphicon-sort-by-attributes")).getText());
		assertFalse(isElementPresent(headerElement, By.cssSelector("span.glyphicon.glyphicon-chevron-up")));

		// Assert Content
		assertEquals("2011-03-02", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[4]")).getText());
		assertEquals("2009-01-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[4]")).getText());
		assertEquals("2006-08-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[3]/td[4]")).getText());
		assertEquals("1998-01-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[8]/td[4]")).getText());
		assertEquals("1998-01-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[8]/td[4]")).getText());
		assertEquals("1996-01-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[9]/td[4]")).getText());
		assertEquals("1995-01-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[10]/td[4]")).getText());


	}
}
