package com.excilys.mviegas.speccdb.selenium;

import com.excilys.mviegas.speccdb.utils.DatabaseUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;

public class LoginTest extends BaseSeleniumTest {

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
	private void assertPageLogin() {
		assertPageLogin("", "", false);
	}
	private void assertPageLogin(String pLogin, String pPassword, boolean pAlert) {
		assertPageLogin(pLogin, pPassword, pAlert, "/login.html");
	}

	private void assertPageLogin(String pLogin, String pPassword, boolean pAlert, String pEndUrl) {
		assertThat(mWebDriver.getCurrentUrl(), Matchers.endsWith(pEndUrl));

		assertEquals("Application - Computer Database", driver.findElement(By.linkText("Application - Computer Database")).getText());
		assertEquals("Application - Computer Database", driver.findElement(By.cssSelector("div.container")).getText());

		// assert Base
		assertEquals("Computer Database", driver.getTitle());

		// Assert Form
		assertEquals("Authentication", driver.findElement(By.id("homeTitle")).getText());
		assertEquals(pLogin, driver.findElement(By.id("username")).getAttribute("value"));
		assertEquals(pPassword, driver.findElement(By.id("password")).getAttribute("value"));
		assertFalse(isElementPresent(By.id("name-error")));
		if (!pAlert) {
			assertFalse(isElementPresent(By.cssSelector(".alert")));
		}
	}

	private void assertCssAlertError(WebElement pWebElement) {
		assertEquals("rgba(169, 68, 66, 1)", pWebElement.getCssValue("color"));
		assertEquals("rgba(242, 222, 222, 1)", pWebElement.getCssValue("background-color"));
		assertEquals("rgba(235, 204, 209, 1)", pWebElement.getCssValue("border-top-color"));
		assertEquals("rgba(235, 204, 209, 1)", pWebElement.getCssValue("border-bottom-color"));
		assertEquals("rgba(235, 204, 209, 1)", pWebElement.getCssValue("border-left-color"));
		assertEquals("rgba(235, 204, 209, 1)", pWebElement.getCssValue("border-right-color"));
		assertEquals("1px", pWebElement.getCssValue("border-top-width"));
		assertEquals("1px", pWebElement.getCssValue("border-bottom-width"));
		assertEquals("1px", pWebElement.getCssValue("border-left-width"));
		assertEquals("1px", pWebElement.getCssValue("border-right-width"));
		assertEquals("4px", pWebElement.getCssValue("border-top-left-radius"));
		assertEquals("4px", pWebElement.getCssValue("border-top-right-radius"));
		assertEquals("4px", pWebElement.getCssValue("border-bottom-right-radius"));
		assertEquals("4px", pWebElement.getCssValue("border-bottom-left-radius"));
	}

	private void assertCssSuccessError(WebElement pWebElement) {
		assertEquals("rgba(60, 118, 61, 1)", pWebElement.getCssValue("color"));
		assertEquals("rgba(223, 240, 216, 1)", pWebElement.getCssValue("background-color"));
		assertEquals("rgba(214, 233, 198, 1)", pWebElement.getCssValue("border-top-color"));
		assertEquals("rgba(214, 233, 198, 1)", pWebElement.getCssValue("border-bottom-color"));
		assertEquals("rgba(214, 233, 198, 1)", pWebElement.getCssValue("border-left-color"));
		assertEquals("rgba(214, 233, 198, 1)", pWebElement.getCssValue("border-right-color"));
		assertEquals("1px", pWebElement.getCssValue("border-top-width"));
		assertEquals("1px", pWebElement.getCssValue("border-bottom-width"));
		assertEquals("1px", pWebElement.getCssValue("border-left-width"));
		assertEquals("1px", pWebElement.getCssValue("border-right-width"));
		assertEquals("4px", pWebElement.getCssValue("border-top-left-radius"));
		assertEquals("4px", pWebElement.getCssValue("border-top-right-radius"));
		assertEquals("4px", pWebElement.getCssValue("border-bottom-right-radius"));
		assertEquals("4px", pWebElement.getCssValue("border-bottom-left-radius"));
	}

	@Test
	public void start() throws Exception {
		assertPageLogin("", "", false);
	}

	/**
	 * Test de validation champ vide pour nom
	 *
	 * @throws Exception
	 */
	@Test
	public void nameAndPassordEmpty() throws Exception {
		assertPageLogin("", "", false);

		driver.findElement(By.id("btnSubmit")).click();

		assertPageLogin("", "", false);
	}

	/**
	 * Test de validation champ vide pour nom
	 *
	 * @throws Exception
	 */
	@Test
	public void passwordEmpty() throws Exception {
		assertPageLogin("", "", false);

		driver.findElement(By.id("username")).sendKeys("login");

		driver.findElement(By.id("btnSubmit")).click();

		assertPageLogin("login", "", false);
	}

	@Test
	public void nameEmpty() throws Exception {
		assertPageLogin("", "", false);

		driver.findElement(By.id("password")).sendKeys("lo");

		driver.findElement(By.id("btnSubmit")).click();

		assertPageLogin("", "lo", false);
	}

	/**
	 * Test de validation champ vide pour nom
	 *
	 * @throws Exception
	 */
	@Test
	public void usernameDontExists() throws Exception {
		assertPageLogin("", "", false);

		driver.findElement(By.id("username")).sendKeys("a");
		driver.findElement(By.id("password")).sendKeys("a");
		driver.findElement(By.id("btnSubmit")).click();

		assertPageLogin("", "", true, "/login.html?error");

		WebElement element = driver.findElement(By.className("alert"));
		assertThat(element.getAttribute("class"), Matchers.containsString("alert-danger"));
		assertEquals("×\nBad credentials", element.getText());
		assertCssAlertError(element);
	}

	@Test
	public void wrongCredentials() throws Exception {
		assertPageLogin("", "", false);

		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("adm");
		driver.findElement(By.id("btnSubmit")).click();

		assertPageLogin("", "", true, "/login.html?error");

		WebElement element = driver.findElement(By.cssSelector(".alert"));
		assertThat(element.getAttribute("class"), Matchers.containsString("alert-danger"));
		assertEquals("×\nBad credentials", element.getText());
		assertCssAlertError(element);
	}

	@Test
	public void loginSuccessfull() throws Exception {
		assertPageLogin("", "", false);

		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.id("btnSubmit")).click();

		// Copié de Tests#start

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
	public void pageLoginNoMoreAccessible() throws Exception {
		assertPageLogin("", "", false);

		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.id("btnSubmit")).click();

		openTarget("login");

		assert403();
	}

	@Test
	public void logoutAnonymous() throws Exception {
		driver.get(getApplicationUrl()+"/logout");

		assertPageLogin();
	}

	@Test
	public void logoutGetRoleDefault() throws Exception {
		authentication("admin", "admin");

		System.out.println(getApplicationUrl()+"/logout");
		driver.get(getApplicationUrl()+"/logout");

		assert404();
	}

	@Test
	public void logout() throws Exception {
		authentication("admin", "admin");

		driver.findElement(By.id("btnLogout")).click();

		assertPageLogin("", "", true, "/login.html?logout");

		List<WebElement> elements = driver.findElements(By.className("alert"));
		assertEquals(1, elements.size());

		WebElement element = elements.get(0);
		assertThat(element.getAttribute("class"), Matchers.containsString("alert-success"));
		assertEquals("×\nSuccessfull logout", element.getText());
		assertCssSuccessError(element);
	}

	@Test
	public void reallyLogout() throws Exception {
		authentication("admin", "admin");

		driver.findElement(By.id("btnLogout")).click();

		assertPageLogin("", "", true, "/login.html?logout");

		openAndWait();

		assertPageLogin();
	}

	@Test
	public void testCrsf1() throws Exception {
		((JavascriptExecutor) driver).executeScript("$(\'[name=_csrf\').remove()");

		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.id("btnSubmit")).click();

		assert403();
	}

	@Test
	public void testCrsf2() throws Exception {
		((JavascriptExecutor) driver).executeScript("$(\'[name=_csrf\').remove()");

		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("adm");
		driver.findElement(By.id("btnSubmit")).click();

		assert403();
	}

	@Test
	public void error404needsLogin() throws Exception {
		openTarget("zaeopirzeprioze");

		assertPageLogin();

	}
}
