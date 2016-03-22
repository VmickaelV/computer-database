package com.excilys.mviegas.speccdb.persist.jdbc;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persist.CrudService;
import com.excilys.mviegas.speccdb.persist.Paginator;
import com.excilys.mviegas.speccdb.wrappers.CompanyJdbcWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum CompanyDao implements CrudService<Company> {
	INSTANCE;
	
	public final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

	// ===========================================================
	// Attributres - private
	// ===========================================================
	private final Connection mConnection;
	
//	private final PreparedStatement mCreateStatement;
//	private final PreparedStatement mUpdateStatement;
//	private final PreparedStatement mDeleteStatement;
	private final PreparedStatement mFindStatement;
	
	//===========================================================
	// Constructors
	//===========================================================
	
	private CompanyDao() {
		try {
			mConnection = DatabaseManager.getConnection();
//			mCreateStatement = mConnection.prepareStatement("INSERT INTO `company` (name) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
//			mUpdateStatement = mConnection.prepareStatement("UPDATE `company` SET name=? WHERE id = ?;");
//			mDeleteStatement = mConnection.prepareStatement("DELETE FROM `company` WHERE id = ?");
			mFindStatement = mConnection.prepareStatement("SELECT * FROM `company` WHERE id = ?");
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		
		
	}
	// ===========================================================
	// Methods - CrudService
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
		throw new UnsupportedOperationException("CrudService<Company>#update not implemented yet.");
	}

	@Override
	public boolean delete(long pId) throws DAOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("CrudService<Company>#delete not implemented yet.");
	}
	
	@Override
	public boolean delete(Company pT) throws DAOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("CompanyDao#delete not implemented yet.");
	}
	
	@Override
	public boolean refresh(Company pT) throws DAOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("CrudService<Company>#refresh not implemented yet.");
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
		throw new UnsupportedOperationException("CrudService<Company>#findWithNamedQuery not implemented yet.");
	}

	@Override
	public Paginator<Company> findWithNamedQueryWithPaginator(String queryName) throws DAOException {
		// ${todo} To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.persist.jdbc.CompanyDao#findWithNamedQueryWithPaginator not implemented yet.");
	}

	@Override
	public List<Company> findWithNamedQuery(String pQueryName, int pResultLimit) throws DAOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("CrudService<Company>#findWithNamedQuery not implemented yet.");
	}

	@Override
	public Paginator<Company> findWithNamedQueryWithPaginator(String queryName, int resultLimit) throws DAOException {
		// ${todo} To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.persist.jdbc.CompanyDao#findWithNamedQueryWithPaginator not implemented yet.");
	}

	@Override
	public List<Company> findWithNamedQuery(String pNamedQueryName, Map<String, Object> pParameters) throws DAOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("CrudService<Company>#findWithNamedQuery not implemented yet.");
	}

	@Override
	public Paginator<Company> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters) throws DAOException {
		// ${todo} To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.persist.jdbc.CompanyDao#findWithNamedQueryWithPaginator not implemented yet.");
	}

	@Override
	public List<Company> findWithNamedQuery(String pNamedQueryName, Map<String, Object> pParameters, int pResultLimit) throws DAOException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("CrudService<Company>#findWithNamedQuery not implemented yet.");
	}

	@Override
	public Paginator<Company> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws DAOException {
		// ${todo} To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.persist.jdbc.CompanyDao#findWithNamedQueryWithPaginator not implemented yet.");
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



}
