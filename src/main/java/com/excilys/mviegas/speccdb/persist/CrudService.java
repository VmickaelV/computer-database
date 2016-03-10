package com.excilys.mviegas.speccdb.persist;

import java.util.List;
import java.util.Map;

public interface CrudService<T> {
    public T create(T t);

    public T find(long id);

    public T update(T t);
    
    public int size();

    public boolean delete(long id);
    
    public boolean delete(T t);

    public boolean refresh(T t);

    List<T> findAll();
    
    List<T> findAll(int start, int size);

    public List<T> findWithNamedQuery(String queryName);

    public List<T> findWithNamedQuery(String queryName, int resultLimit);

    public List<T> findWithNamedQuery(String namedQueryName, Map<String, Object> parameters);

    public List<T> findWithNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);
}
