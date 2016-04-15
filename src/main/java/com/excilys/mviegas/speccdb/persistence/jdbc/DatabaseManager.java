package com.excilys.mviegas.speccdb.persistence.jdbc;

import com.excilys.mviegas.speccdb.exceptions.ConnectionException;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Gestion de la connection à la base de données
 *
 * @author Mickael
 * Created by Mickael on 18/10/2014.
 */
public class DatabaseManager {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DatabaseManager.class);

	// ===========================================================
	// Constantes
	// ===========================================================
	/**
	 * Nom du fichier de configuration de DatabaseManager
	 */
	public static final String CONFIG_FILENAME = "config_bd.properties";

	// Liste de constantes de clés propriété systèmes
	public static final String KEY_URL = "db.url";
	public static final String KEY_USER = "db.username";
	public static final String KEY_PASSWORD = "db.password";
	public static final String KEY_SCHEMA = "db.schema";
	public static final String KEY_CREATE_SCRIPTS = "db.createScripts";
	public static final String KEY_INSERT_SCRIPTS = "db.insertScripts";
	public static final String KEY_MIN_SIZE = "db.min_size";
	public static final String KEY_MAX_SIZE = "db.max_size";
	public static final String KEY_CLASSNAME = "db.className";

	public static final int DEFAULT_MIN_SIZE_POOL = 25;
	public static final int DEFAULT_MAX_SIZE_POOL = 200;
	public static final String SQL_STATEMENT_SEPARATOR = ";";

	/**
	 * Pool de Connexion
	 */
	private static final BoneCP sConnectionPool;

	/**
	 * The Constant password.
	 */
	private static final String PASSWORD;

	/**
	 * The Constant url.
	 */
	private static final String URL;

	/** 
	 * The Constant user. 
	 */
	private static final String USER;
	
	/**
	 * Liste de fichiers de scripts à lancer pour mettre en place la BDD
	 */
	private static final String CREATE_SCRIPTS; 
	
	/**
	 * Liste de fichiers de scripts à lancer à la première connexion pour remplir la BDD
	 */
	private static final String INSERT_SCRIPTS;
	
	/**
	 * Taille minimale du pool de connexion
	 */
	public static final int MIN_SIZE_POOL;
	
	/**
	 * Taille maximale qu'aura le pool de connexion
	 */
	public static final int MAX_SIZE_POOL;

	/**
	 * Nom de la classe du driver à charger
	 */
	public static final String CLASS_NAME_JDBC_DRIVER;

	// ============================================================
	//	Bloc static
	// ============================================================
	static {
		
		// Chargement des préférences de connexions
		Properties prefs = new Properties();

		// Chargement de l'objet Preferences
		if (new File(CONFIG_FILENAME).exists()) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Chargement config à partir du fichier "+CONFIG_FILENAME);
			}
			try (FileInputStream file = new FileInputStream(new File(CONFIG_FILENAME))) {
				prefs.load(file);
				file.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}

		} else {
			InputStream inputStream = DatabaseManager.class.getClassLoader().getResourceAsStream(CONFIG_FILENAME);
			if (inputStream != null) {
				try {
					prefs.load(inputStream);
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
					throw new RuntimeException(e);
				}
			}
		}

		prefs.putAll(System.getProperties());

		// Assignation des préférences
		URL = prefs.getProperty(KEY_URL, "");
		USER = prefs.getProperty(KEY_USER, "");
		PASSWORD = prefs.getProperty(KEY_PASSWORD, "");
		CREATE_SCRIPTS = prefs.getProperty(KEY_CREATE_SCRIPTS, "");
		INSERT_SCRIPTS = prefs.getProperty(KEY_INSERT_SCRIPTS, "");
		MIN_SIZE_POOL = Integer.parseInt(prefs.getProperty(KEY_MIN_SIZE, String.valueOf(DEFAULT_MIN_SIZE_POOL)));
		MAX_SIZE_POOL = Integer.parseInt(prefs.getProperty(KEY_MAX_SIZE, String.valueOf(DEFAULT_MAX_SIZE_POOL)));
		CLASS_NAME_JDBC_DRIVER = prefs.getProperty(KEY_CLASSNAME, "");


		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Connecting to "+USER+"@"+URL+"...");
			LOGGER.info("with these scripts : "+CREATE_SCRIPTS+", "+INSERT_SCRIPTS);
		}

		// Chargement des pilotes 
//		try {
//            Class.forName(CLASS_NAME_JDBC_DRIVER).newInstance();
//        } catch (Exception ex) {
//            LOGGER.error(ex.getMessage(), ex);
//			throw new RuntimeException(ex);
//        }
		
		// Scripts SQL à lancer au démarrage
		if (!CREATE_SCRIPTS.isEmpty() || INSERT_SCRIPTS.isEmpty()) {
			try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
				
				connection.setAutoCommit(false);
				
				List<String> list = new ArrayList<>();
				if (!CREATE_SCRIPTS.isEmpty()) {
					list.addAll(Arrays.asList(CREATE_SCRIPTS.split(SQL_STATEMENT_SEPARATOR)));
				}
				if (!INSERT_SCRIPTS.isEmpty()) {
					list.addAll(Arrays.asList(INSERT_SCRIPTS.split(SQL_STATEMENT_SEPARATOR)));
				}
				
				System.out.println(list);
				
				for (String fileLink : list) {
					StringBuilder builder = new StringBuilder();
					//noinspection ConstantConditions
					File file = new File(DatabaseManager.class.getClassLoader().getResource(fileLink).getFile());
					
					try (Scanner scanner = new Scanner(file)) {
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							if (!line.trim().isEmpty()) {
								builder.append(line);
							
								if (line.trim().endsWith(SQL_STATEMENT_SEPARATOR)) {
									Statement statement = connection.createStatement();
									statement.executeUpdate(builder.toString());
									builder = new StringBuilder();
								}
							}
						}

						if (!builder.toString().trim().isEmpty()) {
							try (Statement statement = connection.createStatement()) {
								statement.executeUpdate(builder.toString());
							}
						}

					} catch (IOException e) {
						LOGGER.error(e.getMessage(), e);
						throw new RuntimeException("Error with the file "+fileLink, e);
					}
				}
				
				connection.setAutoCommit(true);
				
			} catch (SQLException e1) {
				LOGGER.error(e1.getMessage(), e1);
				throw new RuntimeException(e1);
			}
		}

		try {
			// Mise en plase du pool de connexion
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(URL);
			config.setUsername(USER); 
			config.setPassword(PASSWORD);
			config.setMinConnectionsPerPartition(MIN_SIZE_POOL);
			config.setMaxConnectionsPerPartition(MAX_SIZE_POOL);
			config.setPartitionCount(1); // TODO a revoir
			sConnectionPool = new BoneCP(config);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	// ============================================================
	//	Méthodes static
	// ============================================================
	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws ConnectionException 
	 */
	public static synchronized Connection getConnection() throws ConnectionException {
		try {
			return sConnectionPool.getConnection();
		} catch (SQLException e) {
			throw new ConnectionException(e);
		}
	}

	/**
	 * Release connection.
	 *
	 * @param connection the connection
	 * @throws ConnectionException 
	 */
	public static synchronized void releaseConnection(Connection connection) throws ConnectionException {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new ConnectionException(e);
		}
	}

}
