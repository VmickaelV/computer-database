package com.excilys.mviegas.computer_database.selenium;

import com.excilys.mviegas.computer_database.persistence.IComputerDao;
import com.excilys.mviegas.computer_database.utils.DatabaseUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans-front.xml"})
public class DeleteComputerTest extends BaseSeleniumTest {

	private Connection mConnection;

	@Autowired
	private IComputerDao mComputerDao;

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		authentication("admin", "admin");

		DatabaseUtils.resetDatabase();

		openAndWait();
	}

	/**
	 * Test de validation champ vide pour nom
	 *
	 * @throws Exception
	 */
	@Test
	public void delete() throws Exception {
		int n = mComputerDao.size();

		assertEquals("BDD non réinitialisé", "574 Computers found", driver.findElement(By.id("homeTitle")).getText());
		driver.findElement(By.id("editComputer")).click();
		driver.findElement(By.xpath("(//input[@name='cb'])[2]")).click();
		driver.findElement(By.xpath("(//input[@name='cb'])[5]")).click();
		driver.findElement(By.xpath("(//input[@name='cb'])[5]")).click();
		driver.findElement(By.xpath("(//input[@name='cb'])[5]")).click();
		driver.findElement(By.xpath("(//input[@name='cb'])[7]")).click();
		driver.findElement(By.xpath("(//input[@name='cb'])[8]")).click();
		assertEquals("MacBook Pro 15.4 inch", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[2]")).getText());
		assertEquals("CM-2a", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[2]")).getText());
		assertEquals("CM-200", driver.findElement(By.xpath("//tbody[@id='results']/tr[3]/td[2]")).getText());
		assertEquals("CM-5", driver.findElement(By.xpath("//tbody[@id='results']/tr[5]/td[2]")).getText());
		assertEquals("Thinking Machines", driver.findElement(By.xpath("//tbody[@id='results']/tr[5]/td[5]")).getText());
		assertEquals("Thinking Machines", driver.findElement(By.xpath("//tbody[@id='results']/tr[3]/td[5]")).getText());
		assertEquals("Apple IIe", driver.findElement(By.xpath("//tbody[@id='results']/tr[7]/td[2]")).getText());
		assertEquals("Apple IIc", driver.findElement(By.xpath("//tbody[@id='results']/tr[8]/td[2]")).getText());
		assertEquals("Apple IIGS", driver.findElement(By.xpath("//tbody[@id='results']/tr[9]/td[2]")).getText());
		assertEquals("Apple IIc Plus", driver.findElement(By.xpath("//tbody[@id='results']/tr[10]/td[2]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[7]/td[5]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[8]/td[5]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[9]/td[5]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[10]/td[5]")).getText());

		new Actions(driver).moveToElement(driver.findElement(By.id("homeTitle"))).perform();
		driver.findElement(By.xpath("//a[@id='deleteSelected']/i")).click();
		assertTrue(closeAlertAndGetItsText().matches("^Are you sure you want to delete the selected computers[\\s\\S]$"));

		assertEquals("Apple IIc Plus", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[2]")).getText());
		assertEquals("Apple II Plus", driver.findElement(By.xpath("//tbody[@id='results']/tr[7]/td[2]")).getText());
		assertEquals("Apple III", driver.findElement(By.xpath("//tbody[@id='results']/tr[8]/td[2]")).getText());
		assertEquals("Apple Lisa", driver.findElement(By.xpath("//tbody[@id='results']/tr[9]/td[2]")).getText());
		assertEquals("CM-2", driver.findElement(By.xpath("//tbody[@id='results']/tr[10]/td[2]")).getText());
		assertEquals("Apple Inc.", driver.findElement(By.xpath("//tbody[@id='results']/tr/td[5]")).getText());
		assertEquals("Thinking Machines", driver.findElement(By.xpath("//tbody[@id='results']/tr[2]/td[5]")).getText());
		assertEquals("Thinking Machines", driver.findElement(By.xpath("//tbody[@id='results']/tr[3]/td[5]")).getText());
		assertEquals("Apple Inc.", driver.findElement(By.xpath("//tbody[@id='results']/tr[4]/td[5]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[5]/td[5]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[5]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[7]/td[5]")).getText());
		assertEquals("Apple Inc.", driver.findElement(By.xpath("//tbody[@id='results']/tr[8]/td[5]")).getText());
		assertEquals("Apple Inc.", driver.findElement(By.xpath("//tbody[@id='results']/tr[9]/td[5]")).getText());
		assertEquals("Thinking Machines", driver.findElement(By.xpath("//tbody[@id='results']/tr[10]/td[5]")).getText());

		assertEquals("570 Computers found", driver.findElement(By.id("homeTitle")).getText());

		WebElement webElement = driver.findElement(By.xpath("//section[@id='main']/div"));
		assertEquals("×\nComputer successfully deleted from the Database", webElement.getText());

		assertEquals("rgba(60, 118, 61, 1)", webElement.getCssValue("color"));
		assertEquals("rgba(223, 240, 216, 1)", webElement.getCssValue("background-color"));
		assertEquals("rgba(214, 233, 198, 1)", webElement.getCssValue("border-top-color"));
		assertEquals("rgba(214, 233, 198, 1)", webElement.getCssValue("border-bottom-color"));
		assertEquals("rgba(214, 233, 198, 1)", webElement.getCssValue("border-left-color"));
		assertEquals("rgba(214, 233, 198, 1)", webElement.getCssValue("border-right-color"));
		assertEquals("1px", webElement.getCssValue("border-top-width"));
		assertEquals("1px", webElement.getCssValue("border-bottom-width"));
		assertEquals("1px", webElement.getCssValue("border-left-width"));
		assertEquals("1px", webElement.getCssValue("border-right-width"));
		assertEquals("4px", webElement.getCssValue("border-top-left-radius"));
		assertEquals("4px", webElement.getCssValue("border-top-right-radius"));
		assertEquals("4px", webElement.getCssValue("border-bottom-right-radius"));
		assertEquals("4px", webElement.getCssValue("border-bottom-left-radius"));

		assertEquals(n-4, mComputerDao.size());

	}

	@Test
	@Ignore
	public void deleteWithRefresh() throws Exception {
		driver.navigate().refresh();

		assertFalse(isElementPresent(By.xpath("//section[@id='main']/div")));

	}
}
