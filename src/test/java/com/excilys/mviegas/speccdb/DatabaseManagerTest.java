package com.excilys.mviegas.speccdb;

import com.excilys.mviegas.speccdb.persistence.jdbc.CompanyDao;
import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao;
import com.excilys.mviegas.speccdb.persistence.jdbc.DatabaseManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.excilys.mviegas.speccdb.DatabaseUtils.resetDatabase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/beans-front.xml"})
public class DatabaseManagerTest {

	public static final String DB_CREATE = "db/1-SCHEMA.sql";
	public static final String DB_INSERT = "db/3-ENTRIES.sql";

	public static final Logger LOGGER = LoggerFactory.getLogger(DatabaseManagerTest.class);
	private Connection mConnection;

	@Autowired
	private ComputerDao mComputerDao;

	@Autowired
	private CompanyDao mCompanyDao;

	@Autowired
	private void setDatasource(DataSource pDatasource) {
		try {
			mConnection = pDatasource.getConnection();
		} catch (SQLException ignored) {

		}
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
}
