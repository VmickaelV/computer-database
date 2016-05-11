package com.excilys.mviegas.speccdb.persistence;

import com.excilys.mviegas.speccdb.exceptions.DAOException;

import java.util.List;
import java.util.Map;

/**
 * Interface global pour tout service de CRUD
 *
 * @param <T> Type que gèrera le prochain Crudable
 *
 * @author Mickael
 */
public interface Crudable<T> {
	default T create(T t) throws DAOException {
		throw new UnsupportedOperationException();
	}

	default T find(long id) throws DAOException {
		throw new UnsupportedOperationException();
	}

	default T update(T t) throws DAOException {
		throw new UnsupportedOperationException();
	}

	default int size() throws DAOException {
		throw new UnsupportedOperationException();
	}

	default boolean delete(long id) throws DAOException {
		throw new UnsupportedOperationException();
	}

	default boolean delete(T t) throws DAOException {
		throw new UnsupportedOperationException();
	}

	default boolean refresh(T t) throws DAOException {
		throw new UnsupportedOperationException();
	}

	default List<T> findAll() throws DAOException {
		throw new UnsupportedOperationException();
	}

	default List<T> findAll(int start, int size) throws DAOException {
		return findAllWithPaginator(start, size).values;
	}

	default Paginator<T> findAllWithPaginator(int start, int size) throws DAOException {
		throw new UnsupportedOperationException();
	}

	default List<T> findWithNamedQuery(String queryName) throws DAOException {
		return findWithNamedQueryWithPaginator(queryName).values;
	}

	default Paginator<T> findWithNamedQueryWithPaginator(String queryName) throws DAOException {
		throw new UnsupportedOperationException();
	}

	default List<T> findWithNamedQuery(String namedQueryName, Map<String, Object> parameters) throws DAOException {
		return findWithNamedQueryWithPaginator(namedQueryName, parameters).values;
	}

	default Paginator<T> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters) throws DAOException {
		throw new UnsupportedOperationException();
	}

	default List<T> findWithNamedQuery(String namedQueryName, Map<String, Object> parameters, int start, int size) throws DAOException {
		return findWithNamedQueryWithPaginator(namedQueryName, parameters, start, size).values;
	}

	default Paginator<T> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters, int start, int size) throws DAOException {
		throw new UnsupportedOperationException();
	}
}
