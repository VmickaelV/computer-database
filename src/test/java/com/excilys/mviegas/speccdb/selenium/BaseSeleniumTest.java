package com.excilys.mviegas.speccdb.selenium;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import javax.servlet.ServletException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Classe de base pour des tests avec Selenium avec
 * des méthodes de bases pour les tests
 *
 * Created by excilys on 17/03/16.
 * @author Mickael
 */
public abstract class BaseSeleniumTest {

	public String getApplicationId() {
		return "speccdb";
	}

	//=============================================================
	// Constantes
	//=============================================================
	public static class Properties {
		public static final String TMP_WORKING_DIR = "tomcat.tmpworkingdir";
		public static final String TMP_PORT = "tomcat.tmpport";
		public static final String TMP_URL = "tomcat.tmpurl";
		public static final String TMP_CONTEXT = "tomcat.tmpcontext";
	}

	public static final String TMP_WORKING_DIR = System.getProperty(Properties.TMP_WORKING_DIR, "target/tomcatembedded");
	public static final String TMP_DEPLOY_DIR = TMP_WORKING_DIR + "/webapps";
	public static final String TMP_URL = System.getProperty(Properties.TMP_URL, "localhost");
	public static final int TMP_PORT = Integer.parseInt(System.getProperty(Properties.TMP_PORT, String.valueOf(8080)));
	public static final String TMP_CONTEXT = System.getProperty(Properties.TMP_CONTEXT, "/");

	//=============================================================
	// Attributs
	//=============================================================
	
	protected WebDriver mWebDriver;
	protected WebDriver driver;
	protected String mBaseUrl;
	protected boolean mAcceptNextAlert = true;
	protected StringBuffer mVerificationErrors = new StringBuffer();

	protected FirefoxProfile mFirefoxProfile;

	protected Tomcat mTomcat;

	//=============================================================
	// Callbacks
	//=============================================================
	@Before
	public void setUp() throws Exception {

		initServer();
		startServer();
		deploy();

		mWebDriver = new FirefoxDriver(mFirefoxProfile);
		mWebDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		mWebDriver.manage().timeouts().setScriptTimeout(3000, TimeUnit.MICROSECONDS);
		driver = mWebDriver;
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

		stopServer();
	}

	//=============================================================
	// Functions
	//=============================================================
	protected boolean isElementPresent(By by) {
		try {
			mWebDriver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	protected boolean isAlertPresent() {
		try {
			mWebDriver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	protected String closeAlertAndGetItsText() {
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

	public String getApplicationUrl(String appName) {
//		return String.format("http://%s:%d/%s", mTomcat.getHost().getName(),
//				mTomcat.getConnector().getLocalPort(), appName);
		return String.format("http://%s:%d/%s", TMP_URL,
				TMP_PORT, appName);
	}

	public String getApplicationUrl() {
		return getApplicationUrl(getApplicationId());
	}

	protected String getTargetUrl(String pTarget) {
		return String.format("http://%s:%d/%s/views/%s.jsp", TMP_URL,
				TMP_PORT, getApplicationId(), pTarget);
	}

	//=============================================================
	// Méthodes - for init
	//=============================================================
	protected void initServer() throws IOException, LifecycleException, ServletException {
		if (mTomcat != null) {
			stopServer();
		}

		mTomcat = new Tomcat();
		mTomcat.setPort(TMP_PORT);
		mTomcat.setBaseDir(TMP_WORKING_DIR);
		mTomcat.getHost().setAppBase(TMP_WORKING_DIR);
		mTomcat.getHost().setAutoDeploy(true);
		mTomcat.getHost().setDeployOnStartup(true);
	}
;
	protected void deploy() throws ServletException {
		String contextPath = "/" + getApplicationId();
//		File webApp = new File(TMP_WORKING_DIR, getApplicationId());
//		File oldWebApp = new File(webApp.getAbsolutePath());
//		FileUtils.deleteDirectory(oldWebApp);
//		new ZipExporterImpl(createWebArchive()).exportTo(new File(TMP_WORKING_DIR + "/" + getApplicationId() + ".war"),
//				true);
//		mTomcat.addWebapp(mTomcat.getHost(), contextPath, webApp.getAbsolutePath());
		mTomcat.addWebapp("/"+getApplicationId(), new File("target/speccdb.war").getAbsolutePath());
	}

	protected void startServer() throws LifecycleException {
		mTomcat.start();
	}

	protected void stopServer() throws LifecycleException {
		if (mTomcat != null && mTomcat.getServer() != null
				&& mTomcat.getServer().getState() != LifecycleState.DESTROYED) {
			if (mTomcat.getServer().getState() != LifecycleState.STOPPED) {
				mTomcat.stop();
			}
			mTomcat.destroy();
		}

		mTomcat = null;

	}

	//=============================================================
	// Méthodes Actions
	//=============================================================
	protected void openTarget(String pTarget) {
		assertThat(pTarget, not(CoreMatchers.containsString(".")));
	}

	protected void open(URL pURL) {
		mWebDriver.get(pURL.toString());
	}


	//=============================================================
	// Méthodes Asserts
	//=============================================================

	protected void assert404() {
		mWebDriver.findElement(By.id("error_404"));
	}

	protected void assert403() {
		mWebDriver.findElement(By.id("error_403"));
	}

	protected void assert500() {
		mWebDriver.findElement(By.id("error_500"));
	}
	
	public static void main(String[] args) {
		BaseSeleniumTest baseSeleniumTest = new BaseSeleniumTest() {
			
		};
		
		try {
			baseSeleniumTest.initServer();
			baseSeleniumTest.startServer();
			baseSeleniumTest.deploy();
		} catch (LifecycleException | IOException | ServletException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("END");
	}

}
