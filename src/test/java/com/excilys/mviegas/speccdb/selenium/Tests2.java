package com.excilys.mviegas.speccdb.selenium;

import com.excilys.mviegas.speccdb.BaseTest;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class Tests2 extends BaseTest {

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
