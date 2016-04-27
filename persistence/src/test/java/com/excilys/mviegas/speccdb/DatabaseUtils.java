package com.excilys.mviegas.speccdb;

/**
 * Created by excilys on 22/04/16.
 */

import com.excilys.mviegas.speccdb.persistence.ICompanyDao;
import com.excilys.mviegas.speccdb.persistence.IComputerDao;
import com.excilys.mviegas.speccdb.persistence.jdbc.DatabaseManager;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * Created by excilys on 20/04/16.
 */
public class DatabaseUtils {
	public static final String DB_CREATE = "db/1-SCHEMA.sql";
	public static final String DB_INSERT = "db/3-ENTRIES.sql";

	public static void resetDatabase(Connection connection) throws Exception {
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

		try (Statement statement = connection.createStatement(); ResultSet resultSetComputer = statement.executeQuery("SELECT COUNT(*) FROM computer")) {
			resultSetComputer.next();
			assertEquals(574, resultSetComputer.getLong(1));

			try ( ResultSet resultSetCompany = statement.executeQuery("SELECT COUNT(*) FROM company")) {
				resultSetCompany.next();
				assertEquals(42, resultSetCompany.getLong(1));
			}
		}

		DatabaseManager.releaseConnection(connection);

	}

	public static void resetDatabase() throws Exception {
		resetDatabase(DatabaseManager.getConnection());
	}

	public static ICompanyDao sCompanyDao;
	public static IComputerDao sComputerDao;
}

