package com.excilys.mviegas.speccdb.selenium;

import com.excilys.mviegas.speccdb.DatabaseUtils;
import com.excilys.mviegas.speccdb.persistence.IComputerDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans-front.xml"})
public class EditComputerTest extends BaseSeleniumTest {

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

		openAndWait();
		authentication("admin", "admin");

		DatabaseUtils.resetDatabase();
	}

	@Test
	public void edition1() throws Exception {
//		TODO a réadapter pour le changement de locale
		driver.findElement(By.linkText("4")).click();
		assertEquals("Macintosh Quadra", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[2]")).getText());
		assertEquals("1991-01-01", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[3]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[4]")).getText());
		assertEquals("Apple Inc.", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[5]")).getText());

		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,250)", "");
		driver.findElement(By.linkText("Macintosh Quadra")).click();

		driver.findElement(By.id("introducedDate")).click();
		driver.findElement(By.id("introducedDate")).clear();
		driver.findElement(By.id("introducedDate")).sendKeys("");
		driver.findElement(By.id("discontinuedDate")).click();
		driver.findElement(By.id("discontinuedDate")).sendKeys("08/04/2016");
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys("Plus de Matc");
		new Select(driver.findElement(By.id("idCompany"))).selectByVisibleText("Nokia");
		assertEquals("id: 36", driver.findElement(By.xpath("//section[@id='main']/div/div/div/div")).getText());
		assertEquals(getMessage("lbl.editComputer"), driver.findElement(By.cssSelector("h1")).getText());
		assertEquals(getMessage("lbl.edit"), driver.findElement(By.id("btnSubmit")).getText());
		driver.findElement(By.id("btnSubmit")).click();
		driver.findElement(By.linkText("4")).click();
		assertEquals("Plus de Matc", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[2]")).getText());
		assertEquals("", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[3]")).getText());
		assertEquals("2016-04-08", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[4]")).getText());
		assertEquals("Nokia", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[5]")).getText());

	}
}
