package com.excilys.mviegas.speccdb.selenium.errors;

import com.excilys.mviegas.speccdb.selenium.BaseSeleniumTest;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

/**
 * Created by excilys on 15/04/16.
 */
public class PageErorTest extends BaseSeleniumTest {
	@Test
	public void test404() throws Exception {
		openAndWait();
		openTarget("v/dashbo");
		check404();
	}

	@Test
	public void test404_2() throws Exception {
		openAndWait();
		openTarget("404");
		check404();
	}

	@Test
	public void test403() throws Exception {
		openAndWait();
		openTarget("403");
		check404();
	}

	@Test
	public void test500() throws Exception {
		openAndWait();
		openTarget("500");
		check404();
	}



	private void check404() throws Exception {


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
