package com.excilys.mviegas.computer_database.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by excilys on 20/04/16.
 */
public class DatabaseUtils {
	public static final String DB_CREATE = "META-INF/db/1-SCHEMA.sql";
	public static final String DB_INSERT = "META-INF/db/3-ENTRIES.sql";

	public static void 	resetDatabase(Connection connection) throws Exception {
		connection.setAutoCommit(false);

		StringBuilder builder = new StringBuilder();

		for(InputStream inputStream : new InputStream[] {
				DatabaseUtils.class.getClassLoader().getResourceAsStream(DB_INSERT)
		}) {
			try (Scanner scanner = new Scanner(inputStream)) {
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
			if (574 != resultSetComputer.getLong(1)) {
				throw new RuntimeException();
			}

			try ( ResultSet resultSetCompany = statement.executeQuery("SELECT COUNT(*) FROM company")) {
				resultSetCompany.next();
				if (42 != resultSetCompany.getLong(1)) {
					throw new RuntimeException();
				}
			}
		}

//		DatabaseManager.releaseConnection(connection);
	}

//	public static void resetDatabase() throws Exception {
//		resetDatabase(DatabaseManager.getConnection());
//	}
}

