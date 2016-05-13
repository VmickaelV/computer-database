package com.excilys.mviegas.speccdb.selenium;

import com.excilys.mviegas.speccdb.utils.DatabaseUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

/**
 * @author VIEGAS Mickael
 */
public class LocalizationTest extends BaseSeleniumTest {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		DatabaseUtils.resetDatabase();

		openAndWait();
	}

	@Test
	public void loginEn() throws Exception {
		assertEquals("Application - Computer Database", driver.findElement(By.linkText("Application - Computer Database")).getText());
		assertEquals("Computer Database", driver.getTitle());
		assertEquals("Authentication", driver.findElement(By.id("homeTitle")).getText());
		assertEquals("Username", driver.findElement(By.cssSelector("label.control-label.col-sm-2")).getText());
		assertEquals("Password", driver.findElement(By.xpath("//form[@id='loginForm']/fieldset/div[2]/label")).getText());
		assertEquals("Login", driver.findElement(By.id("btnSubmit")).getText());
	}

	@Test
	public void loginFr() throws Exception {
		driver.findElement(By.cssSelector("img")).click();
		assertEquals("Base de données d'ordinateurs", driver.getTitle());
		assertEquals("Application - Base de données d'ordinateurs", driver.findElement(By.linkText("Application - Base de données d'ordinateurs")).getText());
		assertEquals("Authentification", driver.findElement(By.id("homeTitle")).getText());
		assertEquals("Nom d'utilisateur", driver.findElement(By.cssSelector("label.control-label.col-sm-2")).getText());
		assertEquals("Mot de Passe", driver.findElement(By.xpath("//form[@id='loginForm']/fieldset/div[2]/label")).getText());
		assertEquals("S'authentifier", driver.findElement(By.id("btnSubmit")).getText());
	}

	@Test
	public void dashboardFr() throws Exception {
		authentication("admin", "admin");
		driver.findElement(By.cssSelector("img")).click();
		assertEquals("574 ordinateurs trouvées", driver.findElement(By.id("homeTitle")).getText());
		assertEquals("", driver.findElement(By.id("searchsubmit")).getText());
		assertEquals("Nom de l'ordinateur", driver.findElement(By.linkText("Nom de l'ordinateur")).getText());
		assertEquals("Date de mise en marché", driver.findElement(By.linkText("Date de mise en marché")).getText());
		assertEquals("Date de retrait", driver.findElement(By.linkText("Date de retrait")).getText());
		assertEquals("Société", driver.findElement(By.linkText("Société")).getText());
		assertEquals("10/01/2006", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[3]")).getText());
		assertEquals("+ Ordinateur", driver.findElement(By.id("addComputer")).getText());
		assertEquals("Modifier", driver.findElement(By.id("editComputer")).getText());
	}

	@Test
	public void dashboardEn() throws Exception {
		authentication("admin", "admin");
		assertEquals("Application - Computer Database", driver.findElement(By.linkText("Application - Computer Database")).getText());
		assertEquals("574 Computers found", driver.findElement(By.id("homeTitle")).getText());
		assertEquals("", driver.findElement(By.id("searchsubmit")).getText());
		assertEquals("Computer name", driver.findElement(By.linkText("Computer name")).getText());
		assertEquals("Introduced date", driver.findElement(By.linkText("Introduced date")).getText());
		assertEquals("Discontinued date", driver.findElement(By.linkText("Discontinued date")).getText());
		assertEquals("Company", driver.findElement(By.linkText("Company")).getText());
		assertEquals("Add Computer", driver.findElement(By.id("addComputer")).getText());
		assertEquals("Edit", driver.findElement(By.id("editComputer")).getText());
		assertEquals("01/10/2006", driver.findElement(By.xpath("//tbody[@id='results']/tr[6]/td[3]")).getText());
	}

	@Test
	public void editEn() throws Exception {
		authentication("admin", "admin");

		driver.findElement(By.id("addComputer")).click();
		driver.findElement(By.id("introducedDate")).click();
		WebElement webElement = driver.findElement(By.cssSelector("select.ui-datepicker-year"));
		webElement.click();
		for (WebElement option : webElement.findElements(By.tagName("option"))) {
			if (option.getText().equals("2011")) {
				option.click();
				break;
			}
		}
		driver.findElement(By.linkText("12")).click();
		assertEquals("", driver.findElement(By.id("introducedDate")).getText());

		assertEquals("Application - Computer Database", driver.findElement(By.linkText("Application - Computer Database")).getText());
		assertEquals("Add Computer", driver.findElement(By.cssSelector("h1")).getText());
		assertEquals("Computer name", driver.findElement(By.cssSelector("label.control-label")).getText());
		assertEquals("Introduced date", driver.findElement(By.xpath("//section[@id='main']/div/div/div/form/fieldset/div[2]/label")).getText());
		assertEquals("Discontinued date", driver.findElement(By.xpath("//section[@id='main']/div/div/div/form/fieldset/div[3]/label")).getText());
		assertEquals("Company", driver.findElement(By.xpath("//section[@id='main']/div/div/div/form/fieldset/div[4]/label")).getText());
		assertEquals("Add or Cancel", driver.findElement(By.cssSelector("div.actions.pull-right")).getText());
		assertEquals("05/12/2011", driver.findElement(By.id("introducedDate")).getAttribute("value"));
	}

	@Test
	public void editFr() throws Exception {
		authentication("admin", "admin");
		driver.findElement(By.cssSelector("img")).click();

		driver.findElement(By.id("addComputer")).click();
		driver.findElement(By.id("introducedDate")).click();

		WebElement webElement = driver.findElement(By.cssSelector("select.ui-datepicker-year"));
		webElement.click();
		for (WebElement option : webElement.findElements(By.tagName("option"))) {
			if (option.getText().equals("2011")) {
				option.click();
				break;
			}
		}
		driver.findElement(By.linkText("12")).click();
		assertEquals("", driver.findElement(By.id("introducedDate")).getText());

		assertEquals("Application - Base de données d'ordinateurs", driver.findElement(By.linkText("Application - Base de données d'ordinateurs")).getText());
		assertEquals("Ajouter un Ordinateur", driver.findElement(By.cssSelector("h1")).getText());
		assertEquals("Nom de l'ordinateur", driver.findElement(By.cssSelector("label.control-label")).getText());
		assertEquals("Date de mise en marché", driver.findElement(By.xpath("//section[@id='main']/div/div/div/form/fieldset/div[2]/label")).getText());
		assertEquals("Date de retrait", driver.findElement(By.xpath("//section[@id='main']/div/div/div/form/fieldset/div[3]/label")).getText());
		assertEquals("Société", driver.findElement(By.xpath("//section[@id='main']/div/div/div/form/fieldset/div[4]/label")).getText());
		assertEquals("Ajouter ou Annuler", driver.findElement(By.cssSelector("div.actions.pull-right")).getText());
		assertEquals("12/05/2011", driver.findElement(By.id("introducedDate")).getAttribute("value"));
	}
}
