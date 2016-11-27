package com.excilys.mviegas.computer_database.selenium;

import com.excilys.mviegas.computer_database.utils.DatabaseUtils;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class RoleUserIntegrationTest extends BaseSeleniumIntegrationTest {

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		DatabaseUtils.resetDatabase();

		openAndWait();
	}

	@Test
	public void adminDashboard() throws Exception {
		authentication("admin", "admin");
		openTarget("dashboard");

		assertEquals("Computer Database", driver.getTitle());
		assertEquals("Application - Computer Database", driver.findElement(By.cssSelector("a.navbar-brand")).getText());
		assertEquals("574 Computers found", driver.findElement(By.id("homeTitle")).getText());
	}

	@Test
	public void adminAddComputer() throws Exception {
		authentication("admin", "admin");
		openTarget("addComputer");

		assertEquals("Computer Database", driver.getTitle());
		assertEquals("Add Computer", driver.findElement(By.cssSelector("h1")).getText());
	}

	@Test
	public void adminEditComputer() throws Exception {
		authentication("admin", "admin");
		open(getApplicationUrl()+"/editComputer.html?id=5");

		assertEquals("id: 5", driver.findElement(By.xpath("//section[@id='main']/div/div/div/div")).getText());
	}

	@Test
	public void defaultDashBoard() throws Exception {
		authentication("martin", "martin");
		openTarget("dashboard");

		assertEquals("Computer Database", driver.getTitle());
		assertEquals("Application - Computer Database", driver.findElement(By.cssSelector("a.navbar-brand")).getText());
		assertEquals("574 Computers found", driver.findElement(By.id("homeTitle")).getText());
	}

	@Test
	public void defaultAddComputer() throws Exception {
		authentication("martin", "martin");
		openTarget("addComputer");

		assert403();
	}

	@Test
	public void defaultEditComputer() throws Exception {
		authentication("martin", "martin");
		open(getApplicationUrl()+"/editComputer.html?id=5");
		assert403();
	}

	@Test
	public void uiAddbuttonAndDelete() throws Exception {
		authentication("martin", "martin");

		assertFalse(isElementPresent(By.id("addComputer")));
		assertFalse(isElementPresent(By.id("editComputer")));
	}

	@Test
	public void linkEditforAdminDefault() throws Exception {
		authentication("admin", "admin");

		assertTrue(isElementPresent(By.linkText("CM-2a")));
		driver.findElement(By.linkText("CM-2a")).click();

		assertThat(mWebDriver.getCurrentUrl(), Matchers.containsString("/editComputer.html?id"));
		assertThat(mWebDriver.getCurrentUrl(), not(Matchers.endsWith("/dashboard.html")));
	}

	@Test
	public void noLinkEditforRoleDefault() throws Exception {
		authentication("martin", "martin");

		assertFalse(isElementPresent(By.linkText("CM-2a")));

		assertThat(mWebDriver.getCurrentUrl(), not(Matchers.containsString("/editComputer.html?id")));
		assertThat(mWebDriver.getCurrentUrl(), Matchers.endsWith("/dashboard.html"));
	}

	@Test
	@Ignore
	public void requetePostDeleteFail() throws Exception {
		fail();
	}
}
