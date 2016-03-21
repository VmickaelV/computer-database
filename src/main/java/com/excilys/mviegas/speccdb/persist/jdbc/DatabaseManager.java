package com.excilys.mviegas.speccdb.persist.jdbc;

import com.excilys.mviegas.speccdb.exceptions.ConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Gestion de la connection à la base de données Created by Mickael on
 * 18/10/2014.
 */
public class DatabaseManager {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DatabaseManager.class);

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
	private static final String PASSWORD;

	/** The Constant url. */
	private static final String URL;

	/** The Constant user. */
	private static final String USER;
	
	private static final String CREATE_SCRIPTS; 
	private static final String INSERT_SCRIPTS;

	static {

		Properties prefs = new Properties();

		if (new File(CONFIG_FILENAME).exists()) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Chargement config à partir du fichier "+CONFIG_FILENAME);
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
		CREATE_SCRIPTS = prefs.getProperty("createScripts", "");
		INSERT_SCRIPTS = prefs.getProperty("insertScripts", "");
//		String schema = prefs.getProperty("schema", "");
		
		try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }
		
		if (!CREATE_SCRIPTS.isEmpty() || INSERT_SCRIPTS.isEmpty()) {
			try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
				
				connection.setAutoCommit(false);
				
				List<String> list = new ArrayList<>();
				if (!CREATE_SCRIPTS.isEmpty()) {
					list.addAll(Arrays.asList(CREATE_SCRIPTS.split(";")));
				}
				if (!INSERT_SCRIPTS.isEmpty()) {
					list.addAll(Arrays.asList(INSERT_SCRIPTS.split(";")));
				}
				
				System.out.println(list);
				
				for (String fileLink : list) {
					
					StringBuilder builder = new StringBuilder();
					
					File file = new File(DatabaseManager.class.getClassLoader().getResource(fileLink).getFile());
					
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

					} catch (IOException e) {
						throw new ConnectionException("Error with the file "+fileLink, e);
					}
				}
				
				connection.setAutoCommit(true);
				
			} catch (SQLException e1) {
				LOGGER.error(e1.getMessage(), e1);
				throw new ConnectionException(e1);
			}
		}

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

}
