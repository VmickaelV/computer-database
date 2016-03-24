package com.excilys.mviegas.speccdb;

import com.excilys.mviegas.speccdb.persistence.jdbc.CompanyDao;
import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao;
import com.excilys.mviegas.speccdb.persistence.jdbc.DatabaseManager;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class DatabaseManagerTest {

	public static final String DB_CREATE = "db/1-SCHEMA.sql";
	public static final String DB_INSERT = "db/3-ENTRIES.sql";

	@Test
	public void connection() throws Exception {
		Connection connection = DatabaseManager.getConnection();

		Statement statement = connection.createStatement();

		// statement.

		statement.close();

		DatabaseManager.releaseConnection(connection);
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
    			
    			Statement statement = connection.createStatement();
    			statement.executeUpdate(builder.toString());
    		}
    		connection.commit();
		}
		
		connection.setAutoCommit(true);
		
		assertEquals(574, ComputerDao.INSTANCE.size());
		assertEquals(42, CompanyDao.INSTANCE.size());

		
	}
}
