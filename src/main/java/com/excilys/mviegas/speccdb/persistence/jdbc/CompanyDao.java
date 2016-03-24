package com.excilys.mviegas.speccdb.persistence.jdbc;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persistence.ICrudService;
import com.excilys.mviegas.speccdb.persistence.Paginator;
import com.excilys.mviegas.speccdb.wrappers.CompanyJdbcWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class CompanyDao implements ICrudService<Company> {

	public static final int MIN_SIZE_POOL = 5;
	public static final Queue<CompanyDao> sQUEUE = new ArrayBlockingQueue<>(MIN_SIZE_POOL);

	public static final CompanyDao INSTANCE;

	public static final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

	// ===========================================================
	// Attributres - private
	// ===========================================================
	private Connection mConnection;
	
//	private final PreparedStatement mCreateStatement;
//	private final PreparedStatement mUpdateStatement;
//	private final PreparedStatement mDeleteStatement;
//	private final PreparedStatement mDeleteCascadeStatement;
	private PreparedStatement mFindStatement;

	//===========================================================
	// Constructors
	//===========================================================
	
	private CompanyDao() {
//		try {
////			mConnection = DatabaseManager.getConnection();
//////			mCreateStatement = mConnection.prepareStatement("INSERT INTO `company` (name) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
//////			mUpdateStatement = mConnection.prepareStatement("UPDATE `company` SET name=? WHERE id = ?;");
////			mDeleteStatement = mConnection.prepareStatement("DELETE FROM `company` WHERE id = ?");
////			mDeleteCascadeStatement = mConnection.prepareStatement("DELETE FROM `computer` WHERE company_id = ?");
////
////			mFindStatement = mConnection.prepareStatement("SELECT * FROM `company` WHERE id = ?");
//		} catch (SQLException e) {
//			LOGGER.error(e.getMessage(), e);
//			throw new RuntimeException(e);
//		}
	}

	// ===========================================================
	// Getters & Setters
	// ===========================================================

	public Connection getConnection() {
		return mConnection;
	}

	public void setConnection(Connection pConnection) {
		mConnection = pConnection;
		try {
			mFindStatement = mConnection.prepareStatement("SELECT * FROM `company` WHERE id = ?");
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}

	// ===========================================================
	// Methods - ICrudService
	// ===========================================================
	@Override
	public Company create(Company pT) throws DAOException {
//		try {
////			mCreateStatement.clearParameters();
//			
//			mCreateStatement.setString(1, pT.getName());
//			
//			mCreateStatement.executeUpdate();
//			return pT;
//		} catch (SQLException e) {
//			throw new DAOException(e);
//		}
		
		throw new UnsupportedOperationException();
	}

	@Override
	public Company find(long pId) throws DAOException {
		try {
			mFindStatement.setLong(1, pId);
//			System.out.println(mFindStatement.toString());
			ResultSet resultSet = mFindStatement.executeQuery();
			
//			System.out.println(resultSet.isLast());
//			System.out.println(resultSet.isAfterLast());
//			
//			System.out.println(resultSet.isClosed());
//			System.out.println(resultSet.isFirst());
			
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
		try {
			if (mConnection.getAutoCommit()) {
				throw new IllegalStateException();
			}
		} catch (SQLException pE) {
			throw new DAOException(pE);
		}
		try (Statement statement = mConnection.createStatement()) {

			statement.executeUpdate("DELETE FROM COMPUTER WHERE company_id = "+pId);
			if (statement.executeUpdate("DELETE FROM COMPANY WHERE id = "+pId) != 1) {
				mConnection.rollback();
				throw new IllegalArgumentException("Not persisted company");
			}
			return true;
		} catch (SQLException pE) {
			try {
				mConnection.rollback();
			} catch (SQLException pE1) {
				LOGGER.error(pE1.getMessage(), pE1);
				throw new DAOException(pE1);
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
		Statement statement;
		Paginator<Company> paginator;
		try {
			statement = mConnection.createStatement();
			if (pSize > 0) {
				statement.setMaxRows(pSize);
			} else {
				statement.setMaxRows(0);
				pSize = 0;
			}


			ResultSet resultSet;
			if (pSize > 0) {
				resultSet = statement.executeQuery("SELECT * FROM company LIMIT " + pSize + " OFFSET "+pStart);
			} else {
				resultSet = statement.executeQuery("SELECT * FROM company");
			}

			List<Company> mCompanies = new ArrayList<>(resultSet.getFetchSize());

			while (resultSet.next()) {
				Company company = CompanyJdbcWrapper.fromJdbc(resultSet);
				mCompanies.add(company);
			}

			int nbCount;
			if (pSize > 0) {
				resultSet = statement.executeQuery("SELECT COUNT(*) FROM company");

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
	public List<Company> findWithNamedQuery(String pQueryName, int pResultLimit) throws DAOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ICrudService<Company>#findWithNamedQuery not implemented yet.");
	}

	@Override
	public Paginator<Company> findWithNamedQueryWithPaginator(String queryName, int resultLimit) throws DAOException {
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
	public List<Company> findWithNamedQuery(String pNamedQueryName, Map<String, Object> pParameters, int pResultLimit) throws DAOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ICrudService<Company>#findWithNamedQuery not implemented yet.");
	}

	@Override
	public Paginator<Company> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws DAOException {
		// ${todo} To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.persistence.jdbc.CompanyDao#findWithNamedQueryWithPaginator not implemented yet.");
	}


	@Override
	public int size() throws DAOException {
		Statement statement;
		try {
			statement = mConnection.createStatement();
			
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM company");
			
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
		if (sQUEUE.isEmpty()) {
			return new CompanyDao();
		} else {
			return sQUEUE.poll();
		}
	}

	public static void releaseInstance(CompanyDao pCompanyDao) {
		if (sQUEUE.size() < MIN_SIZE_POOL) {
			sQUEUE.add(pCompanyDao);
		}
	}

	static {
		INSTANCE = new CompanyDao();

		try {
			INSTANCE.setConnection(DatabaseManager.getConnection());
		} catch (SQLException pE) {
			LOGGER.error(pE.getMessage(), pE);
			throw new RuntimeException(pE);
		}
	}

}
