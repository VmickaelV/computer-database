package com.excilys.mviegas.speccdb.persistence;

import com.excilys.mviegas.speccdb.exceptions.DAOException;

import java.util.List;
import java.util.Map;

/**
 * Interface global pour tout service de CRUD
 *
 * @param <T> Type que gèrera le prochain ICrudService
 *
 * @author Mickael
 */
public interface ICrudService<T> {
    T create(T t) throws DAOException;

    T find(long id) throws DAOException;

    T update(T t) throws DAOException;
    
    int size() throws DAOException;

    boolean delete(long id) throws DAOException;
    
    boolean delete(T t) throws DAOException;

    boolean refresh(T t) throws DAOException;

    List<T> findAll() throws DAOException;
    
    List<T> findAll(int start, int size) throws DAOException;

    Paginator<T> findAllWithPaginator(int start, int size) throws DAOException;

    List<T> findWithNamedQuery(String queryName) throws DAOException;

    Paginator<T> findWithNamedQueryWithPaginator(String queryName) throws DAOException;

    List<T> findWithNamedQuery(String namedQueryName, Map<String, Object> parameters) throws DAOException;

    Paginator<T> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters) throws DAOException;
}
