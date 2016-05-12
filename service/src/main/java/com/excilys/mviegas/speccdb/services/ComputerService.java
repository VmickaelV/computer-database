package com.excilys.mviegas.speccdb.services;

import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persistence.IComputerDao;
import com.excilys.mviegas.speccdb.persistence.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service d'accès à des {@link Computer}
 *
 * Created by excilys on 18/04/16.
 */
@Service
public class ComputerService implements IComputerService {

	@Autowired
	private IComputerDao mComputerDao;

	@Override
	public Paginator<Computer> findWithNamedQueryWithPaginator(String queryName) throws DAOException {
		return mComputerDao.findWithNamedQueryWithPaginator(queryName);
	}

	@Override
	public Paginator<Computer> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters) throws DAOException {
		return mComputerDao.findWithNamedQueryWithPaginator(namedQueryName, parameters);
	}

	@Override
	public int size() throws DAOException {
		return mComputerDao.size();
	}

	@Override
	public List<Computer> findWithNamedQuery(String pNamedQueryName, Map<String, Object> pParameters) throws DAOException {
		return mComputerDao.findWithNamedQuery(pNamedQueryName, pParameters);
	}

	@Override
	public List<Computer> findWithNamedQuery(String pQueryName) throws DAOException {
		return mComputerDao.findWithNamedQuery(pQueryName);
	}

	@Override
	public Paginator<Computer> findAllWithPaginator(int pStart, int pSize) throws DAOException {
		return mComputerDao.findAllWithPaginator(pStart, pSize);
	}

	@Override
	public List<Computer> findAll(int start, int size) throws DAOException {
		return mComputerDao.findAll(start, size);
	}

	@Override
	public List<Computer> findAll() throws DAOException {
		return mComputerDao.findAll();
	}

	@Override
	public boolean refresh(Computer pT) throws DAOException {
		return mComputerDao.refresh(pT);
	}

	@Override
	public boolean delete(Computer pT) throws DAOException {
		return mComputerDao.delete(pT);
	}

	@Override
	public boolean delete(long pId) throws DAOException {
		return mComputerDao.delete(pId);
	}

	@Override
	public Computer update(Computer pT) throws DAOException {
		return mComputerDao.update(pT);
	}

	@Override
	public Computer find(long pId) throws DAOException {
		return mComputerDao.find(pId);
	}

	@Override
	public Computer create(Computer pT) throws DAOException {
		return mComputerDao.create(pT);
	}
}
