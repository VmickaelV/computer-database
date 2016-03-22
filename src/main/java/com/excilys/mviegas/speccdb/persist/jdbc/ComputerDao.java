package com.excilys.mviegas.speccdb.persist.jdbc;

import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persist.CrudService;
import com.excilys.mviegas.speccdb.persist.Paginator;
import com.excilys.mviegas.speccdb.wrappers.ComputerJdbcWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Dao d'un Ordinateur {@link Computer}
 *
 * @author VIEGAS Mickael
 */
public class ComputerDao implements CrudService<Computer> {

	// TODO a effacer
	public static final ComputerDao INSTANCE;

	/**
	 * Taille minimum du pool de computerDao
	 */
	public static final int MIN_SIZE_POOL = 5;

	/**
	 * Pool de computerDao
	 */
	public static final Queue<ComputerDao> sPOOL = new ArrayBlockingQueue<>(MIN_SIZE_POOL);

	/**
	 * Logger de la classe
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(ComputerDao.class);

	/**
	 * Taille par défaut d'une page
	 */
	public static final int BASE_SIZE_PAGE = 100;
	
	/**
	 * Liste des Queries nommées proposer pour le ComputerDao
	 * 
	 * @author VIEGAS Mickael
	 */
	public final class NamedQueries {
		/**
		 * Effectue une recherche par nom
		 * 	SIZE 	int (opt) : nombre d'éléments
		 *  START	int (opt) : décalage
		 *  FILTER_NAME String (opt) : filtre
		 *  ORDER 	String (opt) : rajoute
		 */
		public static final String SEARCH = "search";
	}

	/**
	 * Liste des noms de paramètres pour les NamedQuerys
	 */
	public final class Parameters {
		public static final String SIZE = "size";
		public static final String START = "start";
		public static final String FILTER_NAME = "filter_name";
		public static final String ORDER = "order";
		public static final String TYPE_ORDER = "type_order";
	}

	/**
	 * Type d'ordre possible pour un Tri
	 */
	public enum TypeOrder {
		ASC, DESC
	}

	/**
	 * Liste des Champs ordonnable
	 */
	public enum Order {
		NAME("name"), INTRODUCED_DATE("introduced"), DISCONTINUED_DATE("discontinued"), ID_COMPANY("company_id"), NAME_COMPANY("company.name");

		public final String queryName;

		Order(String pQueryName) {
			queryName = pQueryName;
		}
	}
	
	// ===========================================================
	// Attributres - private
	// ===========================================================
	private Connection mConnection;
	
	private final PreparedStatement mCreateStatement;
	private final PreparedStatement mUpdateStatement;
	private final PreparedStatement mDeleteStatement;
	private final PreparedStatement mFindStatement;

	// ===========================================================
	// Constructors
	// ===========================================================

	private ComputerDao() {
		LOGGER.info("Init of ComputerDao");
		try {
			mConnection = DatabaseManager.getConnection();
			mCreateStatement = mConnection.prepareStatement("INSERT INTO `computer` (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			mUpdateStatement = mConnection.prepareStatement("UPDATE `computer` SET name=?, introduced=?, discontinued=?, company_id=? WHERE id = ?;");
			mDeleteStatement = mConnection.prepareStatement("DELETE FROM `computer` WHERE id = ?");
			mFindStatement = mConnection.prepareStatement("SELECT * FROM `computer` WHERE id = ?");
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	//===========================================================
	// Getters & Setters
	//===========================================================

	public Connection getConnection() {
		return mConnection;
	}

	public void setConnection(Connection pConnection) {
		mConnection = pConnection;
	}

	//===========================================================
	// Methods - private
	//===========================================================

	/**
	 * Genere le prépareStatement d'un ordinateur
	 * @param pT Ordinateur à mapper
	 * @param pPreparedStatement PreparedStatement à mapper
	 * @throws SQLException Si une erreur SQL est levée
	 */
	private void prepareStatement(Computer pT, PreparedStatement pPreparedStatement) throws SQLException {
		
		if (pT == null) {
			throw new IllegalArgumentException("computer must not be null");
		}
		pPreparedStatement.setString(1, pT.getName());
		if (pT.getIntroducedDate() != null) {
			pPreparedStatement.setDate(2, new Date(pT.getIntroducedDate().toEpochDay()));
		} else {
			pPreparedStatement.setDate(2, null);
		}
		if (pT.getDiscontinuedDate() != null) {
			pPreparedStatement.setDate(3, new Date(pT.getDiscontinuedDate().toEpochDay()));
		} else {
			pPreparedStatement.setDate(3, null);
		}
		if (pT.getManufacturer() != null) {
			pPreparedStatement.setInt(4, pT.getManufacturer().getId());
		} else {
			pPreparedStatement.setNull(4, Types.BIGINT);
		}
	}

	// ===========================================================
	// Methods - CrudService
	// ===========================================================

	@Override
	public Computer create(Computer pT) throws DAOException {
		
		if (pT == null || pT.getId() > 0) {
			throw new IllegalArgumentException("null or already persisted object are illegales values");
		}

		try {
//			mCreateStatement.clearParameters();
			
			prepareStatement(pT, mCreateStatement);
			
			int nbResult = mCreateStatement.executeUpdate();
			if (nbResult == 1) {
				ResultSet a = mCreateStatement.getGeneratedKeys();
				a.next();
				pT.setId(a.getInt(1));
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Persist of "+pT);
				}
				
				return pT;
				
			}

			LOGGER.error("Erreur lors de la persistance d'un objet");
			throw new DAOException("Erreur lors de la persistance d'un objet");
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e);
		}
	}

	@Override
	public Computer find(long pId) throws DAOException {
		try {
			mFindStatement.setLong(1, pId);
			ResultSet resultSet = mFindStatement.executeQuery();
			
			if (!resultSet.isBeforeFirst()) {
				return null;
			}
			resultSet.next();
			return ComputerJdbcWrapper.fromJdbc(resultSet);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e);
		}
	}

	@Override
	public Computer update(Computer pT) throws DAOException {
		if (pT == null || pT.getId() <= 0) {
			throw new IllegalArgumentException("Null or Not Persisted Object");
		}
		
		try {
			prepareStatement(pT, mUpdateStatement);
			mUpdateStatement.setInt(5, pT.getId());
			int nbResult = mUpdateStatement.executeUpdate();
			if (nbResult == 1) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Update of "+pT.getId()+" successfull");
				}
				
				return pT;
			}
			LOGGER.error("Error on update");
			return null;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e);
		}
	}

	@Override
	public boolean delete(long pId) throws DAOException {
		if (pId <= 0) {
			throw new IllegalArgumentException("Null or Not Persisted Object");
		}
		
		try {
			mDeleteStatement.setLong(1, pId);
			int nbLines = mDeleteStatement.executeUpdate();
			
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Delete of "+pId+(nbLines == 1 ? " successfull" : " failed"));
			}
			return nbLines == 1;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e);
		}
	}
	
	

	@Override
	public boolean delete(Computer pT) throws DAOException {
		if (pT == null || pT.getId() <= 0) {
			throw new IllegalArgumentException("Null or Not Persisted Object");
		}
		
		try {
			mDeleteStatement.setLong(1, pT.getId());
			int nbLines = mDeleteStatement.executeUpdate();
			
			LOGGER.info("Delete of "+pT.getId()+(nbLines == 1 ? " successfull" : " failed"));
			return nbLines == 1;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e);
		}
	}

	@Override
	public boolean refresh(Computer pT) throws DAOException {
		if (pT == null || pT.getId() <= 0) {
			throw new IllegalArgumentException("Null or Not Persisted Object");
		}

		Computer computer;
		try {
			computer = find(pT.getId());
		} catch (DAOException pE) {
			// TODO à refaire
			throw new RuntimeException(pE);
		}

		if (computer == null) {
			return false;
		}
		
		pT.setName(computer.getName());
		pT.setIntroducedDate(computer.getIntroducedDate());
		pT.setDiscontinuedDate(computer.getDiscontinuedDate());
		pT.setManufacturer(computer.getManufacturer());
		
		return true;
	}

	@Override
	public List<Computer> findAll() throws DAOException {
		return findAllWithPaginator(0, BASE_SIZE_PAGE).getValues();
	}

	@Override
	public List<Computer> findAll(int start, int size) throws DAOException {
		// TODO To Implement
		return findAllWithPaginator(start, size).getValues();
	}

	@Override
	public Paginator<Computer> findAllWithPaginator(int pStart, int pSize) throws DAOException {
		Statement statement;
		Paginator<Computer> paginator;

		try {
			statement = mConnection.createStatement();
			if (pSize > 0) {
				statement.setMaxRows(pSize);
			} else {
				statement.setMaxRows(0);
				pSize = 0;
			}
			
			
			ResultSet resultSet = statement.executeQuery("SELECT * FROM computer LIMIT " + pSize + " OFFSET "+pStart);
			List<Computer> mCompanies = new ArrayList<>(resultSet.getFetchSize());
			
			while (resultSet.next()) {
				Computer computer = ComputerJdbcWrapper.fromJdbc(resultSet);
				mCompanies.add(computer);
			}

			int nbCount;
			if (pSize > 0) {
				resultSet = statement.executeQuery("SELECT COUNT(*) FROM computer");

				resultSet.next();
				nbCount = resultSet.getInt(1);
			} else {
				nbCount = mCompanies.size();
			}

			paginator = new Paginator<>(pStart, nbCount, pSize, mCompanies);

			return paginator;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e);
		}
	}

	@Override
	public List<Computer> findWithNamedQuery(String pQueryName) throws DAOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ComputerDao#findWithNamedQuery not implemented yet.");
	}

	@Override
	public List<Computer> findWithNamedQuery(String pQueryName, int pResultLimit) throws DAOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ComputerDao#findWithNamedQuery not implemented yet.");
	}

	@Override
	public List<Computer> findWithNamedQuery(String pNamedQueryName, Map<String, Object> pParameters) throws DAOException {
		return findWithNamedQueryWithPaginator(pNamedQueryName, pParameters).getValues();
	}

	@Override
	public List<Computer> findWithNamedQuery(String pNamedQueryName, Map<String, Object> pParameters,
			int pResultLimit) throws DAOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ComputerDao#findWithNamedQuery not implemented yet.");
	}
	
	@Override
	public int size() throws DAOException {
		Statement statement;
		try {
			statement = mConnection.createStatement();
			
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM computer");
			
			if (!resultSet.isBeforeFirst()) {
				throw new DAOException();
			}
			
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e);
		}
	}

	@Override
	public Paginator<Computer> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws DAOException {
		// TODO To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.persist.jdbc.ComputerDao#findWithNamedQueryWithPaginator not implemented yet.");
	}

	@Override
	public Paginator<Computer> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters) throws DAOException {
		Paginator<Computer> paginator;
		ResultSet resultSet;

		switch (namedQueryName) {
			case NamedQueries.SEARCH:
				Statement statement;
				int size = (int) parameters.getOrDefault(Parameters.SIZE, BASE_SIZE_PAGE);
				int start = (int) parameters.getOrDefault(Parameters.START, 0);
				String search = (String) parameters.get(Parameters.FILTER_NAME);

				Order order = (Order) parameters.get(Parameters.ORDER);
				TypeOrder typeOrder = (TypeOrder) parameters.getOrDefault(Parameters.TYPE_ORDER, TypeOrder.ASC);


				try {
					statement = mConnection.createStatement();
					if (size > 0) {
						statement.setMaxRows(size);
					} else {
						statement.setMaxRows(0);
						size = 0;
					}

					StringBuilder stringBuilder = new StringBuilder();

					stringBuilder.append("SELECT COUNT(*) FROM computer");

					if (search != null && !search.isEmpty()) {
						stringBuilder.append(" WHERE lcase(name) LIKE '%")
								.append(search)
								.append("%'");
					}

					if (LOGGER.isInfoEnabled()) {
						LOGGER.info(stringBuilder.toString());
					}


					int nbCount = 0;
					if (size > 0) {
						resultSet = statement.executeQuery(stringBuilder.toString());
						resultSet.next();
						nbCount = resultSet.getInt(1);
					}

					stringBuilder.replace(0, "SELECT COUNT(*) FROM computer".length(), "SELECT * FROM computer");

					if (order != null) {
						stringBuilder
								.append(" ORDER BY ")
								.append(order.queryName);

						if (typeOrder == TypeOrder.DESC) {
							stringBuilder.append(" DESC");
						}
					}

					stringBuilder.append(" LIMIT ").append(size).append(" OFFSET ").append(start);

					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("Query : "+stringBuilder.toString());
					}


					resultSet = statement.executeQuery(stringBuilder.toString());

					List<Computer> mComputers = new ArrayList<>(resultSet.getFetchSize());

					while (resultSet.next()) {
						Computer computer = ComputerJdbcWrapper.fromJdbc(resultSet);
						mComputers.add(computer);
					}

					if (size == 0) {
						nbCount = mComputers.size();
					}


					paginator = new Paginator<>(start, nbCount, size, mComputers);

					return paginator;
				} catch (SQLException e) {
					LOGGER.error(e.getMessage(), e);
					throw new DAOException(e);
				}
			default:
				throw new UnsupportedOperationException("NamedQueries not supported : " + namedQueryName);
		}
	}

	@Override
	public Paginator<Computer> findWithNamedQueryWithPaginator(String queryName, int resultLimit) throws DAOException {
		// TODO To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.persist.jdbc.ComputerDao#findWithNamedQueryWithPaginator not implemented yet.");
	}

	@Override
	public Paginator<Computer> findWithNamedQueryWithPaginator(String queryName) throws DAOException {
		// TODO To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.persist.jdbc.ComputerDao#findWithNamedQueryWithPaginator not implemented yet.");
	}

	// ===========================================================
	// Methods statics
	// ===========================================================
	public static ComputerDao getInstance() {
		if (sPOOL.isEmpty()) {
			return new ComputerDao();
		} else {
			return sPOOL.poll();
		}
	}

	public static void releaseInstance(ComputerDao pComputerDao) {
		if (sPOOL.size() < MIN_SIZE_POOL) {
			sPOOL.add(pComputerDao);
		}
	}

	static {
		INSTANCE = new ComputerDao();

		try {
			INSTANCE.setConnection(DatabaseManager.getConnection());
		} catch (SQLException pE) {
			LOGGER.error(pE.getMessage(), pE);
			throw new RuntimeException(pE);
		}
	}
}
