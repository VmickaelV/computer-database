package com.excilys.mviegas.speccdb;

import com.excilys.mviegas.speccdb.persistence.jdbc.CompanyDao;
import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao;
import com.excilys.mviegas.speccdb.persistence.jdbc.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class DatabaseManagerTest {

	public static final String DB_CREATE = "db/1-SCHEMA.sql";
	public static final String DB_INSERT = "db/3-ENTRIES.sql";

	public static final Logger LOGGER = LoggerFactory.getLogger(DatabaseManagerTest.class);
	private Connection mConnection;

	@Before
	public void setUp() throws Exception {
		DataSource source = (DataSource) C.appContext.getBean("dataSource");
		mConnection = source.getConnection();
	}

	@Test
	public void connection() throws Exception {

		try (Statement statement = mConnection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECt * FROM computer")) {
			
		}

		DatabaseManager.releaseConnection(mConnection);
	}
	
	@Test
	public void testName() throws Exception {
		
	}
	
	@Test
	public void testResetDatabase() throws Exception {
		resetDatabase();
	}

	public static void resetDatabase() throws Exception {

		Connection connection = DatabaseManager.getConnection();

		connection.setAutoCommit(false);
		
		StringBuilder builder = new StringBuilder();
		
		for(File file : new File[] {
				new File(DatabaseManager.class.getClassLoader().getResource(DB_INSERT).getFile())
			}) {

    		try (Scanner scanner = new Scanner(file)) {
    			while (scanner.hasNextLine()) {
    				String line = scanner.nextLine();
    				if (!line.trim().isEmpty()) {
    					builder.append(line);
    					
    					if (line.trim().endsWith(";")) {
    						Statement statement = connection.createStatement();
    						statement.executeUpdate(builder.toString());
    						builder = new StringBuilder();
    					}
    				}
    			}

				if (!builder.toString().isEmpty()) {
					Statement statement = connection.createStatement();
					statement.executeUpdate(builder.toString());
				}
    		}
    		connection.commit();
		}
		
		connection.setAutoCommit(true);
		
		assertEquals(574, sComputerDao.size());
		assertEquals(42, sCompanyDao.size());

		DatabaseManager.releaseConnection(connection);
		
	}

	private static CompanyDao sCompanyDao = C.appContext.getBean(CompanyDao.class);
	private static ComputerDao sComputerDao = C.appContext.getBean(ComputerDao.class);

//	static {
//		ApplicationContext appContext = new ClassPathXmlApplicationContext("beans.xml");
//		sCompanyDao = appContext.getBean(CompanyDao.class);
//	}
}
