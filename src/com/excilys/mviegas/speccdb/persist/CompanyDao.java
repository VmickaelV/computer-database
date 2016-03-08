package com.excilys.mviegas.speccdb.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persist.jdbc.DatabaseManager;

import wrappers.CompanyJdbcWrapper;

public enum CompanyDao implements CrudService<Company> {
	INSTANCE;

	// ===========================================================
	// Attributres - private
	// ===========================================================
	private final Connection mConnection;
	
	private final PreparedStatement mCreateStatement;
	private final PreparedStatement mUpdateStatement;
	private final PreparedStatement mDeleteStatement;
	private final PreparedStatement mFindStatement;
	
	//===========================================================
	// Constructors
	//===========================================================
	
	private CompanyDao() {
		try {
			mConnection = DatabaseManager.getConnection();
			mCreateStatement = mConnection.prepareStatement("INSERT INTO `company` (name) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
			mUpdateStatement = mConnection.prepareStatement("UPDATE `company` SET name=? WHERE id = ?;");
			mDeleteStatement = mConnection.prepareStatement("DELETE FROM `company` WHERE id = ?");
			mFindStatement = mConnection.prepareStatement("SELECT * FROM `company` WHERE id = ?");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	// ===========================================================
	// Methods - CrudService
	// ===========================================================
	@Override
	public Company create(Company pT) {
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
	public Company find(long pId) {
//		System.out.println("CompanyDao#find");
//		System.out.println(pId);
		
			
			
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
			throw new DAOException(e);
		}
	}
	

	@Override
	public Company update(Company pT) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("CrudService<Company>#update not implemented yet.");
	}

	@Override
	public boolean delete(long pId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("CrudService<Company>#delete not implemented yet.");
	}
	
	@Override
	public boolean delete(Company pT) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("CompanyDao#delete not implemented yet.");
	}
	
	@Override
	public boolean refresh(Company pT) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("CrudService<Company>#refresh not implemented yet.");
	}

	@Override
	public List<Company> findAll() {
		return findAll(0, 100);	
	}
	
	@Override
	public List<Company> findAll(int pStart, int pSize) {
		Statement statement;
		try {
			statement = mConnection.createStatement();
			if (pSize > 0) {
				statement.setMaxRows(pSize);
			} else {
				statement.setMaxRows(0);
				pSize = 0;
			}
			
			
			ResultSet resultSet = statement.executeQuery("SELECT * FROM company LIMIT " + pSize + " OFFSET "+pStart);
			List<Company> mCompanies = new ArrayList<>(resultSet.getFetchSize());
			
			while (resultSet.next()) {
				Company company = CompanyJdbcWrapper.fromJdbc(resultSet);
				mCompanies.add(company);
			}
			
			return mCompanies;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	@Override
	public List<Company> findWithNamedQuery(String pQueryName) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("CrudService<Company>#findWithNamedQuery not implemented yet.");
	}

	@Override
	public List<Company> findWithNamedQuery(String pQueryName, int pResultLimit) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("CrudService<Company>#findWithNamedQuery not implemented yet.");
	}

	@Override
	public List<Company> findWithNamedQuery(String pNamedQueryName, Map<String, Object> pParameters) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("CrudService<Company>#findWithNamedQuery not implemented yet.");
	}

	@Override
	public List<Company> findWithNamedQuery(String pNamedQueryName, Map<String, Object> pParameters, int pResultLimit) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("CrudService<Company>#findWithNamedQuery not implemented yet.");
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		INSTANCE.create(new Company("Ma Companie"));
		
		System.out.println(INSTANCE.findAll(6, 10));
		System.out.println(INSTANCE.findAll(6, 10).size());
		
		Company company = new Company("Ma Companie"); 
		Company companyAfter = INSTANCE.create(company);
		
		System.out.println(company);
		System.out.println(companyAfter);
		
		System.out.println(INSTANCE.find(2));
	}

}
