package com.excilys.mviegas.speccdb.selenium;

import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.concurrent.TimeUnit;

public class Tests2 extends BaseSeleniumTest {

	@Override
	public void setUp() throws Exception {
		FirefoxProfile firefoxProfile = new FirefoxProfile();
//		firefoxProfile. addExtension();setPreference("javascript.enabled", false);
		super.setUp();

		mWebDriver.manage().timeouts().setScriptTimeout(3000, TimeUnit.MICROSECONDS);
	}

	@Test
	public void test11() throws Exception {
		mWebDriver.get(mBaseUrl + "/speccdb/views/addComputer.jsp");
	}


}
