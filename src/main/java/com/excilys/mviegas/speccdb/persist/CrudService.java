package com.excilys.mviegas.speccdb.persist;

import com.excilys.mviegas.speccdb.exceptions.DAOException;

import java.util.List;
import java.util.Map;

/**
 * Interface global pour tout service de CRUD
 *
 * @param <T> Type que gèrera le prochain CrudService
 *
 * @author Mickael
 */
public interface CrudService<T> {
    public T create(T t) throws DAOException;

    public T find(long id) throws DAOException;

    public T update(T t) throws DAOException;
    
    public int size() throws DAOException;

    public boolean delete(long id) throws DAOException;
    
    public boolean delete(T t) throws DAOException;

    public boolean refresh(T t) throws DAOException;

    public List<T> findAll() throws DAOException;
    
    public List<T> findAll(int start, int size) throws DAOException;

    public Paginator<T> findAllWithPaginator(int start, int size) throws DAOException;

    public List<T> findWithNamedQuery(String queryName) throws DAOException;

    public Paginator<T> findWithNamedQueryWithPaginator(String queryName) throws DAOException;

    public List<T> findWithNamedQuery(String queryName, int resultLimit) throws DAOException;

    public Paginator<T> findWithNamedQueryWithPaginator(String queryName, int resultLimit) throws DAOException;

    public List<T> findWithNamedQuery(String namedQueryName, Map<String, Object> parameters) throws DAOException;

    public Paginator<T> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters) throws DAOException;

    public List<T> findWithNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws DAOException;

    public Paginator<T> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters, int resultLimit) throws DAOException;
}
