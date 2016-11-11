package com.excilys.mviegas.computer_database.persistence.jdbc;

import com.excilys.mviegas.computer_database.data.User;
import com.excilys.mviegas.computer_database.exceptions.DAOException;
import com.excilys.mviegas.computer_database.persistence.IUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Dao d'un {@link User}.
 *
 * @author VIEGAS Mickael
 */
@Repository
public class UserDao extends AbstractGenericCrudServiceBean<User> implements IUserDao {
	//=============================================================
	// Constantes
	//=============================================================
	/**
	 * Logger de la classe.
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

	/**
	 * Taille par défaut d'une page.
	 */
	public static final int BASE_SIZE_PAGE = 100;

	//=============================================================
	// Constructors
	//=============================================================
	public UserDao() {
	}

	// ===========================================================
	// Methods - Crudable
	// ===========================================================
	@Override
	public User findByName(String pName) {
		User result = null;

		CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();

		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> computerRoot = cq.from(User.class);

		cq.select(computerRoot);

		if (pName != null && !pName.isEmpty()) {
			cq.where(cb.like(cb.lower(computerRoot.get("mUsername")), pName.toLowerCase()));
			cq.select(computerRoot);
			try {
				result = mEntityManager.createQuery(cq).getSingleResult();
			} catch (NonUniqueResultException | NoResultException ignored) {

			}
		}

		return result;
	}

	@Override
	public List<User> findAll() {
		try {
			return this.findAll(0, 0);
		} catch (DAOException e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}
}
