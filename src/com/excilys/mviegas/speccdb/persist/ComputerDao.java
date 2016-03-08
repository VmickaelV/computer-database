package com.excilys.mviegas.speccdb.persist;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persist.jdbc.DatabaseManager;

import wrappers.CompanyJdbcWrapper;
import wrappers.ComputerJdbcWrapper;

public enum ComputerDao implements CrudService<Computer> {
	INSTANCE;
	
	// ===========================================================
	// Attributres - private
	// ===========================================================
	private final Connection mConnection;
	
	private final PreparedStatement mCreateStatement;
	private final PreparedStatement mUpdateStatement;
	private final PreparedStatement mDeleteStatement;
	private final PreparedStatement mFindStatement;

	// ===========================================================
	// Constructors
	// ===========================================================
	private ComputerDao() {
		try {
			mConnection = DatabaseManager.getConnection();
			mCreateStatement = mConnection.prepareStatement("INSERT INTO `computer` (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			mUpdateStatement = mConnection.prepareStatement("UPDATE `computer` SET name=?, introduced=?, discontinued=?, company_id=? WHERE id = ?;");
			mDeleteStatement = mConnection.prepareStatement("DELETE FROM `computer` WHERE id = ?");
			mFindStatement = mConnection.prepareStatement("SELECT * FROM `computer` WHERE id = ?");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//===========================================================
	// Methods - private
	//===========================================================
	private void prepareStatement(Computer pT, PreparedStatement pPreparedStatement) throws SQLException {
		pPreparedStatement.setString(1, pT.getName());
		if (pT.getIntroducedDate() != null) {
			pPreparedStatement.setDate(2, new Date(pT.getIntroducedDate().getTime()));
		} else {
			pPreparedStatement.setDate(2, null);
		}
		if (pT.getDiscontinuedDate() != null) {
			pPreparedStatement.setDate(3, new Date(pT.getDiscontinuedDate().getTime()));
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
	public Computer create(Computer pT) {
		try {
//			mCreateStatement.clearParameters();
			
			prepareStatement(pT, mCreateStatement);
			
			int nbResult = mCreateStatement.executeUpdate();
			if (nbResult == 1) {
				ResultSet a = mCreateStatement.getGeneratedKeys();
				a.next();
				pT.setId(a.getInt(1));
				return pT;
			}
			return null;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public Computer find(long pId) {
		try {
			mFindStatement.setLong(1, pId);
			ResultSet resultSet = mFindStatement.executeQuery();
			
			if (!resultSet.isBeforeFirst()) {
				return null;
			}
			resultSet.next();
			return ComputerJdbcWrapper.fromJdbc(resultSet);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public Computer update(Computer pT) {
		if (pT == null || pT.getId() <= 0) {
			throw new IllegalArgumentException("Null or Not Persisted Object");
		}
		
		try {
			prepareStatement(pT, mUpdateStatement);
			mUpdateStatement.setInt(5, pT.getId());
			int nbResult = mUpdateStatement.executeUpdate();
			if (nbResult == 1) {
				return pT;
			}
			return null;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public boolean delete(long pId) {
		if (pId <= 0) {
//			throw new IllegalArgumentException("Null or Not Persisted Object");
			return false;
		}
		
		try {
			mDeleteStatement.setLong(1, pId);
			int nbLines = mDeleteStatement.executeUpdate();
			
			return nbLines == 1;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	

	@Override
	public boolean delete(Computer pT) {
		if (pT == null || pT.getId() <= 0) {
			throw new IllegalArgumentException("Null or Not Persisted Object");
		}
		
		try {
			mDeleteStatement.setLong(1, pT.getId());
			int nbLines = mDeleteStatement.executeUpdate();
			
			return nbLines == 1;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public boolean refresh(Computer pT) {
		if (pT == null || pT.getId() <= 0) {
			throw new IllegalArgumentException("Null or Not Persisted Object");
		}
		
		Computer computer = INSTANCE.find(pT.getId());
		
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
	public List<Computer> findAll() {
		return findAll(0,  1000);
	}

	@Override
	public List<Computer> findAll(int pStart, int pSize) {
		Statement statement;
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
			return mCompanies;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public List<Computer> findWithNamedQuery(String pQueryName) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ComputerDao#findWithNamedQuery not implemented yet.");
	}

	@Override
	public List<Computer> findWithNamedQuery(String pQueryName, int pResultLimit) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ComputerDao#findWithNamedQuery not implemented yet.");
	}

	@Override
	public List<Computer> findWithNamedQuery(String pNamedQueryName, Map<String, Object> pParameters) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ComputerDao#findWithNamedQuery not implemented yet.");
	}

	@Override
	public List<Computer> findWithNamedQuery(String pNamedQueryName, Map<String, Object> pParameters,
			int pResultLimit) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("ComputerDao#findWithNamedQuery not implemented yet.");
	}
	
	public static void main(String[] args) {
//		
		
		System.out.println(INSTANCE.findAll(6, 10));
		System.out.println(INSTANCE.findAll(6, 10).size());
		
//		Company company = new Company("Ma Companie"); 
//		Company companyAfter = INSTANCE.create(company);
		
		
		System.out.println(INSTANCE.find(6));
		System.out.println(INSTANCE.find(-1));
		System.out.println(INSTANCE.find(5461321));
//		System.out.println(company);
//		System.out.println(companyAfter);
		
		System.out.println(INSTANCE.delete(-1));
		System.out.println(INSTANCE.delete(7894));
		System.out.println(INSTANCE.delete(7));
		System.out.println(INSTANCE.delete(7));
		
		System.out.println(INSTANCE.create(new Computer("un nom", null, null, null)));
		System.out.println(INSTANCE.create(new Computer("un autre nom", Date.from(Instant.parse("2011-01-21T00:00:00Z")), null, null)));
		System.out.println(INSTANCE.create(new Computer("un autre nom", null, new java.util.Date(89, 2, 13), null)));
		System.out.println(INSTANCE.create(new Computer("un autre nom", new java.util.Date(89, 2, 13), new java.util.Date(92, 5, 29), null)));
		
		System.out.println(INSTANCE.create(new Computer("avec un facturer", null, null, CompanyDao.INSTANCE.find(8))));
		System.out.println(INSTANCE.create(new Computer("avec un facturer", null, null, CompanyDao.INSTANCE.find(-1))));
		try {
			System.out.println(INSTANCE.create(new Computer("avec un facturer", null, null, new Company("blablabla"))));
			throw new RuntimeException();
		} catch (DAOException e) {
//			e.printStackTrace();
		}
		
		try {
			System.out.println(INSTANCE.create(new Computer("avec un facturer", null, null, new Company(798764, "blablabla"))));
			throw new RuntimeException();
		} catch (DAOException e) {
//			e.printStackTrace();
		}
		
		System.out.println(INSTANCE.create(new Computer("avec un facturer", new java.util.Date(89, 2, 13), null, CompanyDao.INSTANCE.find(8))));
		
		// TODO tester si on met une date de fin avant la date de début et inversement
		
		System.out.println(INSTANCE.findAll(573, 1000));
		
		
		Computer computer = ComputerDao.INSTANCE.find(575);
		Computer computerBeforeRefresh = ComputerDao.INSTANCE.find(575);
		
		computer.setName("Nouveau nom");
		
		ComputerDao.INSTANCE.update(computer);
		
		System.out.println(computer.getName());
		System.out.println(computerBeforeRefresh.getName());
		
		ComputerDao.INSTANCE.refresh(computerBeforeRefresh);
		
		System.out.println(computer.getName());
		System.out.println(computerBeforeRefresh.getName());
		
		computer = ComputerDao.INSTANCE.find(577);
		
		computer.setDiscontinuedDate(new Date(120, 6, 15));
		computer.setManufacturer(CompanyDao.INSTANCE.find(15));
		
		
		
		ComputerDao.INSTANCE.update(computer);
		
	}

}
