package com.excilys.mviegas.speccdb.selenium;

import com.excilys.mviegas.speccdb.concurrency.ThreadLocals;
import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao;
import com.excilys.mviegas.speccdb.persistence.jdbc.DatabaseManager;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.sql.Connection;

import static org.junit.Assert.*;

public class AddComputerTest extends BaseSeleniumTest {

	private Connection mConnection;
	private ComputerDao mComputerDao = ComputerDao.INSTANCE;

	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		ThreadLocals.CONNECTIONS.remove();
		DatabaseManager.releaseConnection(mConnection);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		openAndWait();
		mWebDriver.findElement(By.id("addComputer")).click();

		mConnection = DatabaseManager.getConnection();
		ThreadLocals.CONNECTIONS.set(mConnection);
	}

	@Test
	public void start() throws Exception {

		assertEquals("Application - Computer Database", driver.findElement(By.linkText("Application - Computer Database")).getText());
		assertEquals("Application - Computer Database", driver.findElement(By.cssSelector("div.container")).getText());

		// assert Base
		assertEquals("Computer Database", driver.getTitle());
		assertEquals("Add Computer", driver.findElement(By.cssSelector("h1")).getText());

		// Assert Form
		assertEquals("", driver.findElement(By.id("name")).getText());
		assertEquals("", driver.findElement(By.id("name")).getAttribute("value"));
		assertEquals("Computer name", driver.findElement(By.cssSelector("label.control-label")).getText());
		assertEquals("Computer's Name is compulsory.", driver.findElement(By.cssSelector("span.help-block")).getText());
		assertFalse(isElementPresent(By.id("name-error")));


		assertEquals("Introduced date", driver.findElement(By.xpath("//section[@id='main']/div/div/div/form/fieldset/div[2]/label")).getText());
		assertEquals("", driver.findElement(By.id("introducedDate")).getText());
		assertEquals("", driver.findElement(By.id("introducedDate")).getAttribute("value"));

		assertEquals("Discontinued date", driver.findElement(By.xpath("//section[@id='main']/div/div/div/form/fieldset/div[3]/label")).getText());
		assertEquals("", driver.findElement(By.id("discontinuedDate")).getAttribute("value"));
		assertEquals("", driver.findElement(By.id("discontinuedDate")).getText());

		assertEquals("0", driver.findElement(By.id("companyId")).getAttribute("value"));
//		assertEquals("-- Apple Inc. Thinking Machines RCA Netronics Tandy Corporation Commodore International MOS Technology Micro Instrumentation and Telemetry Systems IMS Associates, Inc. Digital Equipment Corporation Lincoln Laboratory Moore School of Electrical Engineering IBM Amiga Corporation Canon Nokia Sony OQO NeXT Atari Acorn computer Timex Sinclair Nintendo Sinclair Research Ltd Xerox Hewlett-Packard Zemmix ACVS Sanyo Cray Evans & Sutherland E.S.R. Inc. OMRON BBN Technologies Lenovo Group ASUS Amstrad Sun Microsystems Texas Instruments HTC Corporation Research In Motion Samsung Electronics", driver.findElement(By.id("companyId")).getText());
		assertEquals("Company", driver.findElement(By.xpath("//section[@id='main']/div/div/div/form/fieldset/div[4]/label")).getText());
		assertEquals("Application - Computer Database", driver.findElement(By.cssSelector("div.container")).getText());
	}

	/**
	 * Test de validation champ vide pour nom
	 *
	 * @throws Exception
	 */
	@Test
	public void nameEmpty() throws Exception {
		int n = mComputerDao.size();

		assertFalse(isElementPresent(By.id("name-error")));

		mWebDriver.findElement(By.id("btnSubmit")).click();

		assertEquals("This field is required.", driver.findElement(By.id("name-error")).getText());

		assertEquals(n, mComputerDao.size());
		assertThat(mWebDriver.getCurrentUrl(), Matchers.endsWith("/addComputer.jsp"));
	}

	/**
	 * Test de validation champ vide pour nom
	 *
	 * @throws Exception
	 */
	@Test
	public void onlyName() throws Exception {
		int n = mComputerDao.size();

		// assert Base
		assertEquals("Computer Database", driver.getTitle());
		assertEquals("Add Computer", driver.findElement(By.cssSelector("h1")).getText());

		WebElement element = driver.findElement(By.id("name"));
		assertEquals("", element.getText());
		assertEquals("", element.getAttribute("value"));

		element.clear();
		element.sendKeys("UnNom");

		assertFalse(isElementPresent(By.id("name-error")));


		mWebDriver.findElement(By.id("btnSubmit")).click();

		assertFalse(isElementPresent(By.id("name-error")));

		assertEquals(n+1, mComputerDao.size());

		assertTrue(mWebDriver.getCurrentUrl().endsWith("dashboard.jsp"));

		WebElement webElement = driver.findElement(By.xpath("//section[@id='main']/div"));
		assertEquals("×\nComputer successfully added into Database", webElement.getText());
		assertTrue(webElement.getAttribute("class").contains("alert"));
		assertTrue(webElement.getAttribute("class").contains("alert-success"));
	}

	/**
	 * Test de validation champ vide pour nom
	 */
	@Test
	@Ignore
	public void wrongIntroducedDate() throws Exception {
		int n = mComputerDao.size();

		// assert Base
		assertEquals("Computer Database", driver.getTitle());
		assertEquals("Add Computer", driver.findElement(By.cssSelector("h1")).getText());

		WebElement element = driver.findElement(By.id("name"));
		assertEquals("", element.getText());
		assertEquals("", element.getAttribute("value"));

		element.clear();
//		element.sendKeys("UnNom");

		assertFalse(isElementPresent(By.id("name-error")));


		mWebDriver.findElement(By.id("btnSubmit")).click();

		assertFalse(isElementPresent(By.id("name-error")));

		assertEquals(n+1, mComputerDao.size());

		assertTrue(mWebDriver.getCurrentUrl().endsWith("dashboard.jsp"));

		WebElement webElement = driver.findElement(By.xpath("//section[@id='main']/div"));
		assertEquals("×\nComputer successfully added into Database", webElement.getText());
		assertTrue(webElement.getAttribute("class").contains("alert"));
		assertTrue(webElement.getAttribute("class").contains("alert-success"));
	}

	@Test
	@Ignore
	/**
	 * Test d'une date après aujourd'hui
	 */
	public void wrongDiscontinuedDate1() throws Exception {


	}

	@Test
	@Ignore
	/**
	 * Test d'une date avent date début
	 */
	public void wrongDiscontinuedDate2() throws Exception {


	}

	@Test
	public void addMoreComplete() throws Exception {

		openAndWait();
		driver.findElement(By.linkText("100")).click();
		driver.findElement(By.linkText("4")).click();
		driver.findElement(By.linkText("6")).click();
		assertFalse(isElementPresent(By.xpath("//tbody[@id='results']/tr[75]")));
		mWebDriver.findElement(By.id("addComputer")).click();


		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("Nouvel Ordinateur");
		driver.findElement(By.id("introducedDate")).click();
		driver.findElement(By.linkText("8")).click();
		new Select(driver.findElement(By.id("companyId"))).selectByVisibleText("NeXT");
		driver.findElement(By.id("btnSubmit")).click();
		driver.findElement(By.linkText("100")).click();
		driver.findElement(By.linkText("4")).click();
		driver.findElement(By.linkText("6")).click();

//		 04/05/2016
		assertEquals("Nouvel Ordinateur", driver.findElement(By.xpath("//tbody[@id='results']/tr[75]/td[2]")).getText());
		assertEquals("2016-08-04", driver.findElement(By.xpath("//tbody[@id='results']/tr[75]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[75]/td[4]")).getText());
		assertEquals("NeXT", driver.findElement(By.xpath("//tbody[@id='results']/tr[75]/td[5]")).getText());
	}
}
