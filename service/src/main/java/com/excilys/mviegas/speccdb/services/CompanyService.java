package com.excilys.mviegas.speccdb.services;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persistence.ICompanyDao;
import com.excilys.mviegas.speccdb.persistence.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service d'accès à des {@link Company}
 *
 * Created by excilys on 18/04/16.
 */
@Service
public class CompanyService implements ICompanyService {

	@Autowired
	private ICompanyDao mCompanyDao;

	@Override
	public Company create(Company pT) throws DAOException {
		return mCompanyDao.create(pT);
	}

	@Override
	public Company find(long pId) throws DAOException {
		return mCompanyDao.find(pId);
	}

	@Override
	public Company update(Company pT) throws DAOException {
		return mCompanyDao.update(pT);
	}

	@Override
	public boolean delete(long pId) throws DAOException {
		return mCompanyDao.delete(pId);
	}

	@Override
	public boolean delete(Company pT) throws DAOException {
		return mCompanyDao.delete(pT);
	}

	@Override
	public boolean refresh(Company pT) throws DAOException {
		return mCompanyDao.refresh(pT);
	}

	@Override
	public List<Company> findAll() throws DAOException {
		return mCompanyDao.findAll();
	}

	@Override
	public List<Company> findAll(int pStart, int pSize) throws DAOException {
		return mCompanyDao.findAll(pStart, pSize);
	}

	@Override
	public Paginator<Company> findAllWithPaginator(int pStart, int pSize) throws DAOException {
		return mCompanyDao.findAllWithPaginator(pStart, pSize);
	}

	@Override
	public List<Company> findWithNamedQuery(String pQueryName) throws DAOException {
		return mCompanyDao.findWithNamedQuery(pQueryName);
	}

	@Override
	public Paginator<Company> findWithNamedQueryWithPaginator(String queryName) throws DAOException {
		return mCompanyDao.findWithNamedQueryWithPaginator(queryName);
	}

	@Override
	public List<Company> findWithNamedQuery(String pNamedQueryName, Map<String, Object> pParameters) throws DAOException {
		return mCompanyDao.findWithNamedQuery(pNamedQueryName, pParameters);
	}

	@Override
	public Paginator<Company> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters) throws DAOException {
		return mCompanyDao.findWithNamedQueryWithPaginator(namedQueryName, parameters);
	}

	@Override
	public int size() throws DAOException {
		return mCompanyDao.size();
	}
}
