package com.excilys.mviegas.speccdb.persistence.jdbc;

import com.excilys.mviegas.speccdb.concurrency.ThreadLocals;
import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persistence.ICrudService;
import com.excilys.mviegas.speccdb.persistence.Paginator;
import com.excilys.mviegas.speccdb.wrappers.ComputerJdbcWrapper;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Dao d'un Ordinateur {@link Computer}
 *
 * Chaque appel à une méthode doit avoir une variable Connection stocké dans un threadlocal {@link ThreadLocals#CONNECTIONS}
 *
 * @author VIEGAS Mickael
 *
 * TODO voir si on raoute une vérif de présence de connexion dans ThreadLocal
 */
@Repository
public class ComputerDao implements ICrudService<Computer> {

	//=============================================================
	// Constantes
	//=============================================================
	/**
	 * Logger de la classe
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(ComputerDao.class);

	/**
	 * Taille par défaut d'une page
	 */
	public static final int BASE_SIZE_PAGE = 100;

	//=============================================================
	// Attributs
	//=============================================================
	private JdbcTemplate mJdbcTemplate;


	//=============================================================
	// Inner Classes
	//=============================================================

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
		ASC, DESC;

		public static TypeOrder from(String pTypeOrder) {
			if (pTypeOrder == null || pTypeOrder.isEmpty()) {
				return null;
			}
			pTypeOrder = pTypeOrder.toUpperCase();
			for (TypeOrder typeOrder : TypeOrder.values()) {
				if (typeOrder.toString().equals(pTypeOrder)) {
					return typeOrder;
				}
			}
			return null;
		}
	}

	/**
	 * Liste des Champs ordonnable
	 */
	public enum Order {
		NAME("name"), INTRODUCED_DATE("introduced"), DISCONTINUED_DATE("discontinued"), ID_COMPANY("company_id"), NAME_COMPANY("company_name");

		public final String queryName;

		Order(String pQueryName) {
			queryName = pQueryName;
		}

		public static Order from(String pTexte) {
			if (pTexte == null || pTexte.isEmpty()) {
				return null;
			}
			for (Order order : Order.values()) {
				if (order.queryName.equals(pTexte.toLowerCase())) {
					return order;
				}
			}
			return null;
		}
	}

	//=============================================================
	// Attributres - private
	//=============================================================
	private PreparedStatement mCreateStatement;
	private PreparedStatement mUpdateStatement;
	private PreparedStatement mDeleteStatement;
	private PreparedStatement mFindStatement;

	//=============================================================
	// Constructors
	//=============================================================

	ComputerDao() {
//			mCreateStatement = mConnection.prepareStatement("INSERT INTO `computer` (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
//			mUpdateStatement = mConnection.prepareStatement("UPDATE `computer` SET name=?, introduced=?, discontinued=?, company_id=? WHERE id = ?;");
//			mDeleteStatement = mConnection.prepareStatement("DELETE FROM `computer` WHERE id = ?");
//			mFindStatement = mConnection.prepareStatement("SELECT * FROM `computer` WHERE id = ?");
	}

	//===========================================================
	// Getters & Setters
	//===========================================================

//	public Connection getConnection() {
//		return mConnection;
//	}
//
//	public void setConnection(Connection pConnection) {
//		mConnection = pConnection;
//	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.mJdbcTemplate = new JdbcTemplate(dataSource);
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
			pPreparedStatement.setDate(2, Date.valueOf(pT.getIntroducedDate()));
		} else {
			pPreparedStatement.setDate(2, null);
		}
		if (pT.getDiscontinuedDate() != null) {
			pPreparedStatement.setDate(3, Date.valueOf(pT.getDiscontinuedDate()));
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
	// Methods - ICrudService
	// ===========================================================

	@Override
	public Computer create(Computer pT) throws DAOException {
		if (pT == null || pT.getId() > 0) {
			throw new IllegalArgumentException("null or already persisted object are illegales values");
		}

		final KeyHolder holder = new GeneratedKeyHolder();

		if (mJdbcTemplate.update(connection -> {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `computer` (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			prepareStatement(pT, preparedStatement);

			return preparedStatement;
		}, holder) == 1) {
			pT.setId(holder.getKey().intValue());
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Persist of "+pT);
			}

			return pT;
		}

		LOGGER.error("Erreur lors de la persistance d'un objet");
		throw new DAOException("Erreur lors de la persistance d'un objet");
	}

	@Override
	public Computer find(long pId) throws DAOException {
		return mJdbcTemplate.query("SELECT * FROM `computer` WHERE id = ?", new Object[] {pId}, (rs) -> {
			rs.next();
			try {
				return ComputerJdbcWrapper.fromJdbc(rs);
			} catch (DAOException pE) {
				throw ((SQLException) pE.getCause());
			}
		});
	}

	@Override
	public Computer update(Computer pT) throws DAOException {
		if (pT == null || pT.getId() <= 0) {
			throw new IllegalArgumentException("Null or Not Persisted Object");
		}
		try {
			if (mJdbcTemplate.update("UPDATE `computer` SET name=?, introduced=?, discontinued=?, company_id=? WHERE id = ?;", ps -> {
				prepareStatement(pT, ps);
				ps.setInt(5, pT.getId());
			}) == 1) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Update of "+pT.getId()+" successfull");
				}
				return pT;
			} else {
				LOGGER.error("Error on update");
				return null;
			}
		} catch (DataAccessException e) {
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
			return mJdbcTemplate.update("DELETE FROM `computer` WHERE id = ?", new Object[]{pId}) == 1;
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e);
		}
		
//		if (LOGGER.isInfoEnabled()) {
//			LOGGER.info("Delete of "+pId+(nbLines == 1 ? " successfull" : " failed"));
//		}
	}
	
	

	@Override
	public boolean delete(Computer pT) throws DAOException {
		if (pT == null) {
			throw new IllegalArgumentException("Null or Not Persisted Object");
		}		
		return delete(pT.getId());
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
		Paginator<Computer> paginator;

//			if (pSize > 0) {
//				statement.setMaxRows(pSize);
//			} else {
//				statement.setMaxRows(0);
//				pSize = 0;
//			}

		try {
			List<Computer> mCompanies = mJdbcTemplate.query("SELECT * FROM computer LIMIT " + pSize + " OFFSET "+pStart, (rs, introw) -> {
				try {
					return ComputerJdbcWrapper.fromJdbc(rs);
				} catch (DAOException pE) {
					throw ((SQLException) pE.getCause());
				}
			});

			int nbCount;
			if (pSize > 0) {
				nbCount = mJdbcTemplate.queryForObject("SELECT COUNT(*) FROM computer", Integer.class);
			} else {
				nbCount = mCompanies.size();
			}

			paginator = new Paginator<>(pStart, nbCount, pSize, mCompanies);

			return paginator;
		} catch (DataAccessException e) {
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
	public List<Computer> findWithNamedQuery(String pNamedQueryName, Map<String, Object> pParameters) throws DAOException {
		return findWithNamedQueryWithPaginator(pNamedQueryName, pParameters).getValues();
	}

	@Override
	public int size() throws DAOException {
		return mJdbcTemplate.queryForObject("SELECT COUNT(*) FROM computer", Integer.TYPE);
	}

	@Override
	public Paginator<Computer> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters) throws DAOException {
		Paginator<Computer> paginator;

		switch (namedQueryName) {
			case NamedQueries.SEARCH:
				int size = (int) parameters.getOrDefault(Parameters.SIZE, BASE_SIZE_PAGE);
				int start = (int) parameters.getOrDefault(Parameters.START, 0);
				String search = (String) parameters.get(Parameters.FILTER_NAME);
				Order order = (Order) parameters.get(Parameters.ORDER);
				TypeOrder typeOrder = (TypeOrder) parameters.getOrDefault(Parameters.TYPE_ORDER, TypeOrder.ASC);


				try {
//					if (size > 0) {
//						statement.setMaxRows(size);
//					} else {
//						statement.setMaxRows(0);
//						size = 0;
//					}

					StringBuilder stringBuilder = new StringBuilder();

					stringBuilder.append("SELECT COUNT(*) FROM computer");

					if (search != null && !search.isEmpty()) {
						stringBuilder.append(" WHERE lcase(name) LIKE '%")
								// TODO echapper la string des caractères spéciales de SQL
								.append(search)
								.append("%'");
					}

					if (LOGGER.isInfoEnabled()) {
						LOGGER.info(stringBuilder.toString());
					}

					int nbCount = 0;
					if (size > 0) {
						nbCount = mJdbcTemplate.queryForObject(stringBuilder.toString(), Integer.TYPE);
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

					List<Computer> mComputers = mJdbcTemplate.query(stringBuilder.toString(), (rs, introw) -> {
						try {
							return ComputerJdbcWrapper.fromJdbc(rs);
						} catch (DAOException pE) {
							throw ((SQLException) pE.getCause());
						}
					});

					if (size == 0) {
						nbCount = mComputers.size();
					}


					paginator = new Paginator<>(start, nbCount, size, mComputers);

					return paginator;
				} catch (DataAccessException e) {
					LOGGER.error(e.getMessage(), e);
					throw new DAOException(e);
				}
			default:
				throw new UnsupportedOperationException("NamedQueries not supported : " + namedQueryName);
		}
	}

	@Override
	public Paginator<Computer> findWithNamedQueryWithPaginator(String queryName) throws DAOException {
		// TODO To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao#findWithNamedQueryWithPaginator not implemented yet.");
	}

	// ===========================================================
	// Methods statics
	// ===========================================================
	public static ComputerDao getInstance() {
		return null;
	}
}
