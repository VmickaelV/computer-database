package com.excilys.mviegas.speccdb.selenium;

import org.apache.catalina.*;
import org.apache.catalina.startup.Tomcat;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
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
@SuppressWarnings("WeakerAccess")
public abstract class BaseSeleniumTest {

	public String getApplicationId() {
		return "speccdb";
	}

	//=============================================================
	// Constantes
	//=============================================================
	public static class Properties {
		public static final String TOMCAT_WORKING_DIR = "tomcat.workingdir";
		public static final String TOMCAT_CONTEXT = "tomcat.context";

		public static final String URL_REMOTE_WEBDRIVER = "webdriver.url";
		public static final String SERVER_IP = "server.ip";
		public static final String SERVER_PORT = "server.port";

		public static final String DONT_CLOSE = "test.dont_close";
	}

//	public static final String DEFAULT_URL_REMOTE_WEBDRIVER = "http://localhost:4444/wd/hub";
	public static final String DEFAULT_URL_REMOTE_WEBDRIVER = null;
//public static final String DEFAULT_SERVER_IP = "test_server";
	public static final String DEFAULT_SERVER_IP = "localhost";
	public static final String DEFAULT_SERVER_PORT = "8888";

	public static final boolean DEFAULT_DONT_CLOSE = false;

	public static final String TOMCAT_WORKING_DIR = System.getProperty(Properties.TOMCAT_WORKING_DIR, "target/tomcatembedded");
	public static final String TOMCAT_DEPLOY_DIR = TOMCAT_WORKING_DIR + "/webapps";
	public static final String SERVER_IP = System.getProperty(Properties.SERVER_IP, DEFAULT_SERVER_IP);
	public static final int SERVER_PORT = Integer.parseInt(System.getProperty(Properties.SERVER_PORT, DEFAULT_SERVER_PORT));
	public static final String TOMCAT_CONTEXT = System.getProperty(Properties.TOMCAT_CONTEXT, "/");

	public static final String URL_REMOTE_WEBDRIVER = System.getProperties().getProperty(Properties.URL_REMOTE_WEBDRIVER, DEFAULT_URL_REMOTE_WEBDRIVER);
	public static final boolean DONT_CLOSE = Boolean.parseBoolean(System.getProperty(Properties.DONT_CLOSE, String.valueOf(DEFAULT_DONT_CLOSE)));



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

//		initServer();
//		startServer();
//		deploy();

		if (URL_REMOTE_WEBDRIVER == null || URL_REMOTE_WEBDRIVER.isEmpty()) {
			if (mFirefoxProfile == null) {
				mFirefoxProfile = new FirefoxProfile();
			}
			System.out.println(SERVER_IP);
			mFirefoxProfile.setPreference("intl.accept_languages", "en");
			mWebDriver = new FirefoxDriver(mFirefoxProfile);

		} else {
			mWebDriver = new RemoteWebDriver(new URL(URL_REMOTE_WEBDRIVER), DesiredCapabilities.firefox());
		}
		mWebDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		mWebDriver.manage().timeouts().setScriptTimeout(3000, TimeUnit.MICROSECONDS);
		driver = mWebDriver;

		Cookie cookie = new Cookie.Builder("org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE", "en")
				.isSecure(false)
				.isHttpOnly(false)
				.path("/")
				.build();
		open();
		mWebDriver.manage().addCookie(cookie);
	}

	@After
	public void tearDown() throws Exception {
		if (mWebDriver != null && !DONT_CLOSE) {
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

	protected boolean isElementPresent(WebElement pWebElement, By by) {
		try {
			pWebElement.findElement(by);
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
		return String.format("http://%s:%d/%s", SERVER_IP,
				SERVER_PORT, appName);
	}

	public String getApplicationUrl() {
		return getApplicationUrl(getApplicationId());
	}

	protected String getTargetUrl(String pTarget) {
		return String.format("http://%s:%d/%s/views/%s.jsp", SERVER_IP,
				SERVER_PORT, getApplicationId(), pTarget);
	}

	//=============================================================
	// Méthodes - for init
	//=============================================================
	protected void initServer() throws IOException, LifecycleException, ServletException {
		if (mTomcat != null) {
			stopServer();
		}

		mTomcat = new Tomcat();
		mTomcat.setPort(SERVER_PORT);
		mTomcat.setBaseDir(TOMCAT_WORKING_DIR);
		mTomcat.getHost().setAppBase(TOMCAT_WORKING_DIR);
		mTomcat.getHost().setAutoDeploy(true);
		mTomcat.getHost().setDeployOnStartup(true);
	}

	protected void deploy() throws ServletException {
		String contextPath = "/" + getApplicationId();
//		String contextPath = "/";
		System.out.println("contextPath = " + contextPath);
//		File webApp = new File(TOMCAT_WORKING_DIR, getApplicationId());
//		File oldWebApp = new File(webApp.getAbsolutePath());
//		FileUtils.deleteDirectory(oldWebApp);
//		new ZipExporterImpl(createWebArchive()).exportTo(new File(TOMCAT_WORKING_DIR + "/" + getApplicationId() + ".war"),
//				true);
//		mTomcat.addWebapp(mTomcat.getHost(), contextPath, webApp.getAbsolutePath());
//		mTomcat.addContext(contextPath, "src/main/webapp");
		Context context = mTomcat.addWebapp(contextPath, new File("src/main/webapp").getAbsolutePath());
//		Context context = mTomcat.addContext(contextPath, new File("src/main/webapp").getAbsolutePath());
		System.out.println(Arrays.toString(context.findApplicationListeners()));
		System.out.println(Arrays.toString(context.findErrorPages()));
		System.out.println(Arrays.toString(context.findParameters()));
		System.out.println(Arrays.toString(context.findStatusPages()));
//		mTomcat.addContext(contextPath, new File("src/main/webapp").getAbsolutePath());


//		StandardContext ctx = (StandardContext) mTomcat.addWebapp("/", new File("src/main/webapp").getAbsolutePath());
//		System.out.println("configuring app with basedir: " + new File("./" + "src/main/webapp").getAbsolutePath());
//		File additionWebInfClasses = new File("target/classes");
//		WebResourceRoot resources = new StandardRoot(ctx);
//		resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
//				additionWebInfClasses.getAbsolutePath(), contextPath));
//		ctx.setResources(resources);

	}

	protected void startServer() throws LifecycleException {
		mTomcat.start();
		mTomcat.getServer().addLifecycleListener(new LifecycleListener() {
			@Override
			public void lifecycleEvent(final LifecycleEvent pLifecycleEvent) {
				System.out.println("BaseSeleniumTest.lifecycleEvent");
				System.out.println("pLifecycleEvent = [" + pLifecycleEvent + "]");
			}
		});

		System.out.println(Arrays.toString(mTomcat.getServer().findServices()));
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

		open(getTargetUrl(pTarget));
	}

	protected void open(String pURL) {
		mWebDriver.get(pURL);
	}

	protected void open() {
		open(getApplicationUrl());
	}

	protected void openAndWait() {
		open();
		mWebDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);

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

	//=============================================================
	// Main Method
	//=============================================================
	public static void main(String[] args) {
		BaseSeleniumTest baseSeleniumTest = new BaseSeleniumTest() {

		};

		try {
			baseSeleniumTest.initServer();

			baseSeleniumTest.deploy();
			baseSeleniumTest.startServer();

		} catch (LifecycleException | IOException | ServletException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("Waiting input for shuting down...");
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("END");
	}

}
