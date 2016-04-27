package com.excilys.mviegas.speccdb.selenium.errors;

import com.excilys.mviegas.speccdb.selenium.BaseSeleniumTest;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

/**
 * Created by excilys on 15/04/16.
 */
public class Page404Test extends BaseSeleniumTest {
	@Test
	public void test() throws Exception {
		openAndWait();

		openTarget("v/dashbo");

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
