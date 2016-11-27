package com.excilys.mviegas.computer_database.selenium.errors;

import com.excilys.mviegas.computer_database.selenium.BaseSeleniumIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

/**
 * Test les pages d'erreurs
 *
 * Created by excilys on 15/04/16.
 */
public class PageErorIntegrationTest extends BaseSeleniumIntegrationTest {

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		authentication("admin", "admin");
	}

	@Test
	public void test404() throws Exception {
		check404("v/dashbo");
	}

	@Test
	public void test404_2() throws Exception {
		check404("404");
	}

	@Test
	public void test403() throws Exception {
		check404("403");
	}

	@Test
	public void test500() throws Exception {
		check404("500");
	}

	private void check404(String pUrl) throws Exception {
		openAndWait();
		openTarget(pUrl);

		WebElement webElement = driver.findElement(By.cssSelector("div.alert.alert-danger"));
		assertEquals("Error 404: Page not found. Too bad bitch!", webElement.getText());
		assertEquals("Computer Database", driver.getTitle());

		assertEquals("rgba(169, 68, 66, 1)", webElement.getCssValue("color"));
		assertEquals("rgba(242, 222, 222, 1)", webElement.getCssValue("background-color"));
		assertEquals("rgba(235, 204, 209, 1)", webElement.getCssValue("border-top-color"));
		assertEquals("rgba(235, 204, 209, 1)", webElement.getCssValue("border-bottom-color"));
		assertEquals("rgba(235, 204, 209, 1)", webElement.getCssValue("border-left-color"));
		assertEquals("rgba(235, 204, 209, 1)", webElement.getCssValue("border-right-color"));
		assertEquals("1px", webElement.getCssValue("border-top-width"));
		assertEquals("1px", webElement.getCssValue("border-bottom-width"));
		assertEquals("1px", webElement.getCssValue("border-left-width"));
		assertEquals("1px", webElement.getCssValue("border-right-width"));
		assertEquals("4px", webElement.getCssValue("border-top-left-radius"));
		assertEquals("4px", webElement.getCssValue("border-top-right-radius"));
		assertEquals("4px", webElement.getCssValue("border-bottom-right-radius"));
		assertEquals("4px", webElement.getCssValue("border-bottom-left-radius"));

		assert404();
	}
}
