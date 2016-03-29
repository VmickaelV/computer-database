package com.excilys.mviegas.speccdb.persistence.jdbc;

import com.excilys.mviegas.speccdb.concurrency.ThreadLocals;
import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persistence.ICrudService;
import com.excilys.mviegas.speccdb.persistence.Paginator;
import com.excilys.mviegas.speccdb.wrappers.CompanyJdbcWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Dao d'une companie ({@link Company})
 */
public enum CompanyDao implements ICrudService<Company> {

	INSTANCE;

	// ============================================================
	//	Constantes
	// ============================================================
	public static final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

	// ===========================================================
	// Methods - ICrudService
	// ===========================================================
	@Override
	public Company create(Company pT) throws DAOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Company find(long pId) throws DAOException {
		Connection connection = ThreadLocals.CONNECTIONS.get();
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM `company` WHERE id = "+pId);
			
			if (!resultSet.isBeforeFirst()) {
				return null;
			}
			resultSet.next();
			return CompanyJdbcWrapper.fromJdbc(resultSet);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e);
		}
	}
	

	@Override
	public Company update(Company pT) throws DAOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ICrudService<Company>#update not implemented yet.");
	}

	@Override
	public boolean delete(long pId) throws DAOException {
		Connection connection = ThreadLocals.CONNECTIONS.get();
		try {
			if (connection.getAutoCommit()) {
				throw new IllegalStateException();
			}
		} catch (SQLException pE) {
			// TODO a logger
			throw new DAOException(pE);
		}
		try (Statement statement = connection.createStatement()) {

			statement.executeUpdate("DELETE FROM COMPUTER WHERE company_id = "+pId);
			if (statement.executeUpdate("DELETE FROM COMPANY WHERE id = "+pId) != 1) {
				connection.rollback();
				throw new IllegalArgumentException("Not persisted company");
			}
			return true;
		} catch (SQLException pE) {
			try {
				connection.rollback();
			} catch (SQLException pE1) {
				LOGGER.error(pE1.getMessage(), pE1);
			}

			LOGGER.error(pE.getMessage(), pE);
			throw new DAOException(pE);
		}
	}
	
	@Override
	public boolean delete(Company pT) throws DAOException {
		if (pT == null || pT.getId() <= 0) {
			throw new IllegalArgumentException();
		}
		return delete(pT.getId());
	}
	
	@Override
	public boolean refresh(Company pT) throws DAOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ICrudService<Company>#refresh not implemented yet.");
	}

	@Override
	public List<Company> findAll() throws DAOException {
		return findAll(0, 0);
	}
	
	@Override
	public List<Company> findAll(int pStart, int pSize) throws DAOException {
		return findAllWithPaginator(pStart, pSize).getValues();
	}

	@Override
	public Paginator<Company> findAllWithPaginator(int pStart, int pSize) throws DAOException {
		Paginator<Company> paginator;
		Connection connection = ThreadLocals.CONNECTIONS.get();
		ResultSet resultSet = null;

		// TODO: 25/03/2016 effacer ²²
		System.out.println(Thread.activeCount());
		System.out.println(Thread.currentThread().getId());

		try (Statement statement = connection.createStatement()){

			if (pSize > 0) {
				statement.setMaxRows(pSize);
			} else {
				statement.setMaxRows(0);
				pSize = 0;
			}

			String sqlQuery;

			if (pSize > 0) {
				sqlQuery = "SELECT * FROM company LIMIT " + pSize + " OFFSET "+pStart;
			} else {
				sqlQuery = "SELECT * FROM company";
			}

			resultSet = statement.executeQuery(sqlQuery);

			List<Company> mCompanies = new ArrayList<>(resultSet.getFetchSize());

			while (resultSet.next()) {
				Company company = CompanyJdbcWrapper.fromJdbc(resultSet);
				mCompanies.add(company);
			}

			resultSet.close();
			resultSet = null;

			int nbCount;
			if (pSize > 0) {
				resultSet = statement.executeQuery("SELECT COUNT(*) FROM company");

				resultSet.next();
				nbCount = resultSet.getInt(1);
				resultSet.close();
				resultSet = null;
			} else {
				nbCount = mCompanies.size();
			}

			paginator = new Paginator<>(pStart, nbCount, pSize, mCompanies);

			return paginator;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException pE) {
					// TODO
					throw new DAOException(pE);
				}
			}
		}
	}

	@Override
	public List<Company> findWithNamedQuery(String pQueryName) throws DAOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ICrudService<Company>#findWithNamedQuery not implemented yet.");
	}

	@Override
	public Paginator<Company> findWithNamedQueryWithPaginator(String queryName) throws DAOException {
		// ${todo} To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.persistence.jdbc.CompanyDao#findWithNamedQueryWithPaginator not implemented yet.");
	}

	@Override
	public List<Company> findWithNamedQuery(String pNamedQueryName, Map<String, Object> pParameters) throws DAOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ICrudService<Company>#findWithNamedQuery not implemented yet.");
	}

	@Override
	public Paginator<Company> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters) throws DAOException {
		// ${todo} To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.persistence.jdbc.CompanyDao#findWithNamedQueryWithPaginator not implemented yet.");
	}

	@Override
	public int size() throws DAOException {
		Connection connection = ThreadLocals.CONNECTIONS.get();

		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM company")) {

			
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

	// ===========================================================
	// Methods statics
	// ===========================================================
	public static CompanyDao getInstance() {
		return INSTANCE;
	}
}
