package com.excilys.mviegas.speccdb.persistence.jdbc;

import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.interfaces.Identifiable;
import com.excilys.mviegas.speccdb.persistence.Crudable;
import com.excilys.mviegas.speccdb.persistence.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * Classe générique de Bean de DAO
 *
 * Created by excilys on 14/04/16.
 */
public abstract class AbstractGenericCrudServiceBean<T extends Identifiable> implements Crudable<T> {

	public static final Logger LOGGER = LoggerFactory.getLogger(AbstractGenericCrudServiceBean.class);

	//=============================================================
	// Attributs
	//=============================================================

	@SuppressWarnings("WeakerAccess")
	@PersistenceContext
	protected EntityManager mEntityManager;

	@SuppressWarnings("WeakerAccess")
	protected final Class<T> entityBeanType;

	//=============================================================
	// Constructeurs
	//=============================================================
	public AbstractGenericCrudServiceBean() {
		Class c = getClass();

		while (!(c.getGenericSuperclass() instanceof ParameterizedType)) {
			System.out.println(c);
			c = c.getSuperclass();
		}
		//noinspection unchecked
		entityBeanType = ((Class) ((ParameterizedType) c.getGenericSuperclass()).getActualTypeArguments()[0]);
	}

	//=============================================================
	// Override
	//=============================================================
	@Override
	@Transactional
	public T create(T t) {
		if (t == null || t.getId() > 0) {
			throw new IllegalArgumentException();
		}
		mEntityManager.persist(t);
		mEntityManager.flush();
		mEntityManager.refresh(t);
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public  T find(long id) {
		return mEntityManager.find(entityBeanType, id);
	}

	@Override
	@Transactional
	public boolean delete(long id) {
		// TODO revoir cette méthode
		Object ref;
		ref = mEntityManager.find(entityBeanType, id);
		if (ref == null) {
			return false;
		}
		try {

			mEntityManager.remove(ref);
		} catch (EntityNotFoundException pE) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean delete(T pT) throws DAOException {
		if (pT == null) {
			throw new IllegalArgumentException("null value with Dao#delete");
		}
		if (mEntityManager.contains(pT)) {
			mEntityManager.remove(pT);
			return true;
		} else {
			return delete(pT.getId());
		}

	}

	@Override
	public List<T> findAll() {
		try {
			return findAll(0, 0);
		} catch (DAOException pE) {
			LOGGER.error(pE.getMessage(), pE);
			return null;
		}
	}

	@Override
	@Transactional
	public  T update(T t) {
		if (t == null || mEntityManager.find(entityBeanType, t.getId()) == null) {
			throw new IllegalArgumentException();
		}
		return mEntityManager.merge(t);
	}

	@Override
	public boolean refresh(final T t) {
		mEntityManager.refresh(t);
		return true;
	}

	@Override
	public Paginator<T> findWithNamedQueryWithPaginator(String queryName) throws DAOException {
		return findWithNamedQueryWithPaginator(queryName, null, 0, 0);
	}

	@Override
	public Paginator<T> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters) throws DAOException {
		return this.findWithNamedQueryWithPaginator(namedQueryName, parameters, 0, 0);
	}

	@Override
	public Paginator<T> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters, int start, int size) throws DAOException {
		Query query;

		int nbCount = 0;

		if (size > 0) {
			try {
				query = mEntityManager.createNamedQuery(namedQueryName + ".size");
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("withPaginator needs a namedQuery for the size suffied by \".size\"", e);
			}

			parameters.forEach(query::setParameter);

			nbCount = (int) query.getSingleResult();
		}

		query = mEntityManager.createNativeQuery(namedQueryName);
		parameters.forEach(query::setParameter);
		if (start > 0) {
			query.setFirstResult(start);
		}
		if (size > 0) {
			query.setMaxResults(size);
		}
		//noinspection unchecked
		List<T> list = query.getResultList();

		if (size == 0) {
			nbCount = list.size();
		}

		return new Paginator<>(start, nbCount, size, list);
	}

	@Override
	public Paginator<T> findAllWithPaginator(int pStart, int pSize) {
		CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
		int count = Math.toIntExact(mEntityManager.createQuery(cqCount.select(cb.count(cqCount.from(entityBeanType)))).getSingleResult());

		CriteriaQuery<T> cq = cb.createQuery(entityBeanType);
		Root<T> t = cq.from(entityBeanType);
		cq.select(t);

		cq.where();
		TypedQuery<T> q = mEntityManager.createQuery(cq);
		q.setFirstResult(pStart);
		q.setMaxResults(pSize);
		return new Paginator<>(pStart, count, pSize, q.getResultList());
	}

	@Override
	public int size() throws DAOException {
		CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
		return Math.toIntExact(mEntityManager.createQuery(cqCount.select(cb.count(cqCount.from(entityBeanType)))).getSingleResult());
	}


}
