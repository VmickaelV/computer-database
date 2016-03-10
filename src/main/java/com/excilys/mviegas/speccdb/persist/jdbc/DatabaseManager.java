package com.excilys.mviegas.speccdb.persist.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

import org.apache.log4j.Logger;

/**
 * Gestion de la connection à la base de données Created by Mickael on
 * 18/10/2014.
 */
public class DatabaseManager {

	// ===========================================================
	// Constantes
	// ===========================================================
	public static final String CONFIG_FILENAME = "config_bd.properties";

	public static final String KEY_URL = "url";
	public static final String KEY_USER = "user";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_SCHEMA = "schema";

	/**
	 * Queue des connections libres
	 */
	private static final Queue<Connection> freeConnections = new LinkedList<Connection>();

	/**
	 * The Constant numberOfInitialConnections.
	 */
	private static final int MIN_SIZE_QUEUE = 5;

	/** The Constant password. */
	private static final String PASSWORD;// System.getProperty("mviegas072.database.password");

	/** The Constant url. */
	private static final String URL;// =
									// "jdbc:mysql://localhost/Doodle";//System.getProperty("mviegas072.database.url");

	/** The Constant user. */
	private static final String USER;// =
									 // "test";//BDD.LOGIN;//System.getProperty("mviegas072.database.user");
	
	public static final Logger logger = Logger.getLogger(DatabaseManager.class);

	static {

		Properties prefs = new Properties();

		if (new File(CONFIG_FILENAME).exists()) {
			if (logger.isInfoEnabled()) {
				logger.info("Chargement config à partir du fichier "+CONFIG_FILENAME);
			}
			try (FileInputStream file = new FileInputStream(new File(CONFIG_FILENAME))) {
				prefs.load(file);
				file.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		} else {
			InputStream inputStream = DatabaseManager.class.getClassLoader().getResourceAsStream(CONFIG_FILENAME);
			if (inputStream != null) {
				try {
					prefs.load(inputStream);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			} else {
				prefs = System.getProperties();
			}
		}

		URL = prefs.getProperty("url", "");
		USER = prefs.getProperty("user", "");
		PASSWORD = prefs.getProperty("password", "");
//		String schema = prefs.getProperty("schema", "");

		for (int i = 0; i < MIN_SIZE_QUEUE; i++) {
			try {
				freeConnections.add(DriverManager.getConnection(URL, USER, PASSWORD));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 *
	 * @throws SQLException the SQL exception
	 */
	public static synchronized Connection getConnection() throws SQLException {
		Connection connection = null;
		if (freeConnections.isEmpty()) {
			try {
//				System.out.println("Création Connexion");
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				// connection.nativeSQL();

			} catch (SQLException e) {
				System.out.println(URL + ":" + USER + ":" + PASSWORD);
				throw e;
			}
		} else {
//			System.out.println("remove");
			connection = freeConnections.remove();
		}
		return connection;
	}

	/**
	 * Release connection.
	 *
	 * @param connection the connection
	 */
	public static synchronized void releaseConnection(Connection connection) {
		if (freeConnections.size() < MIN_SIZE_QUEUE) {
			freeConnections.add(connection);
		} else {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Connection connection = DatabaseManager.getConnection();

		Statement statement = connection.createStatement();

		statement.close();

		DatabaseManager.releaseConnection(connection);
	}

}
