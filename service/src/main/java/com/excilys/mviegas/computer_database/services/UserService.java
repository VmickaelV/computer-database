package com.excilys.mviegas.computer_database.services;

import com.excilys.mviegas.computer_database.data.User;
import com.excilys.mviegas.computer_database.exceptions.DAOException;
import com.excilys.mviegas.computer_database.persistence.IUserDao;
import com.excilys.mviegas.computer_database.persistence.Paginator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service d'accès à des {@link User}
 *
 * Created by excilys on 18/04/16.
 */
@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDao mUserDao;

	@Override
	public Paginator<User> findWithNamedQueryWithPaginator(String queryName) throws DAOException {
		return mUserDao.findWithNamedQueryWithPaginator(queryName);
	}

	@Override
	public Paginator<User> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters) throws DAOException {
		return mUserDao.findWithNamedQueryWithPaginator(namedQueryName, parameters);
	}

	@Override
	public int size() throws DAOException {
		return mUserDao.size();
	}

	@Override
	public List<User> findWithNamedQuery(String pNamedQueryName, Map<String, Object> pParameters) throws DAOException {
		return mUserDao.findWithNamedQuery(pNamedQueryName, pParameters);
	}

	@Override
	public List<User> findWithNamedQuery(String pQueryName) throws DAOException {
		return mUserDao.findWithNamedQuery(pQueryName);
	}

	@Override
	public Paginator<User> findAllWithPaginator(int pStart, int pSize) throws DAOException {
		return mUserDao.findAllWithPaginator(pStart, pSize);
	}

	@Override
	public List<User> findAll(int start, int size) throws DAOException {
		return mUserDao.findAll(start, size);
	}

	@Override
	public List<User> findAll() throws DAOException {
		return mUserDao.findAll();
	}

	@Override
	public boolean refresh(User pT) throws DAOException {
		return mUserDao.refresh(pT);
	}

	@Override
	public boolean delete(User pT) throws DAOException {
		return mUserDao.delete(pT);
	}

	@Override
	public boolean delete(long pId) throws DAOException {
		return mUserDao.delete(pId);
	}

	@Override
	public User update(User pT) throws DAOException {
		return mUserDao.update(pT);
	}

	@Override
	public User find(long pId) throws DAOException {
		return mUserDao.find(pId);
	}

	@Override
	public User create(User pT) throws DAOException {
		return mUserDao.create(pT);
	}

	@Override
	public User findByName(String pName) {
		return mUserDao.findByName(pName);
	}
}
