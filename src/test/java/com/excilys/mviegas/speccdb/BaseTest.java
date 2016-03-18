package com.excilys.mviegas.speccdb;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * Created by excilys on 17/03/16.
 */
public abstract class BaseTest {

	protected WebDriver mWebDriver;
	protected String mBaseUrl;
	protected boolean mAcceptNextAlert = true;
	protected StringBuffer mVerificationErrors = new StringBuffer();

	protected FirefoxProfile mFirefoxProfile;

	@Before
	public void setUp() throws Exception {
		mWebDriver = new FirefoxDriver(mFirefoxProfile);
		mBaseUrl = "http://localhost:8080/";
		mWebDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		mWebDriver.manage().timeouts().setScriptTimeout(3000, TimeUnit.MICROSECONDS);
	}

	@After
	public void tearDown() throws Exception {
		if (mWebDriver != null) {
			mWebDriver.quit();
		}
		String verificationErrorString = mVerificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			mWebDriver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			mWebDriver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = mWebDriver.switchTo().alert();
			String alertText = alert.getText();
			if (mAcceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			mAcceptNextAlert = true;
		}
	}
}
