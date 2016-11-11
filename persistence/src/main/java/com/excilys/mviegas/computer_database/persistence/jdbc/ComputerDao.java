package com.excilys.mviegas.computer_database.persistence.jdbc;

import com.excilys.mviegas.computer_database.concurrency.ThreadLocals;
import com.excilys.mviegas.computer_database.data.Computer;
import com.excilys.mviegas.computer_database.exceptions.DAOException;
import com.excilys.mviegas.computer_database.persistence.IComputerDao;
import com.excilys.mviegas.computer_database.persistence.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * Dao d'un Ordinateur {@link Computer}.
 * <p>
 * Chaque appel à une méthode doit avoir une variable Connection stocké dans un threadlocal {@link ThreadLocals#CONNECTIONS}
 *
 * @author VIEGAS Mickael
 *         <p>
 *         TODO voir si on raoute une vérif de présence de connexion dans ThreadLocal
 */
@Repository
public class ComputerDao extends AbstractGenericCrudServiceBean<Computer> implements IComputerDao {

	//=============================================================
	// Constantes
	//=============================================================
	/**
	 * Logger de la classe.
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(ComputerDao.class);

	/**
	 * Taille par défaut d'une page.
	 */
	public static final int BASE_SIZE_PAGE = 100;

	//=============================================================
	// Inner Classes
	//=============================================================

	/**
	 * Liste des Queries nommées proposer pour le ComputerDao.
	 *
	 * @author VIEGAS Mickael
	 */
	public final class NamedQueries {
		/**
		 * Effectue une recherche par nom.
		 * SIZE 	int (opt) : nombre d'éléments
		 * START	int (opt) : décalage
		 * FILTER_NAME String (opt) : filtre
		 * ORDER 	String (opt) : rajoute
		 */
		public static final String SEARCH = "search";
	}

	/**
	 * Liste des noms de paramètres pour les NamedQuerys.
	 */
	public final class Parameters {
		public static final String SIZE = "size";
		public static final String START = "start";
		public static final String FILTER_NAME = "filter_name";
		public static final String ORDER = "order";
		public static final String TYPE_ORDER = "type_order";
	}

	/**
	 * Type d'ordre possible pour un Tri.
	 */
	public enum TypeOrder {
		ASC, DESC;

		/**
		 * Parse un String.
		 *
		 * @param pTypeOrder Texte à Parser
		 * @return TypeOrder parsé ou null si pTypeOrder null ou vide
		 */
		public static TypeOrder from(String pTypeOrder) {
			if (pTypeOrder == null || pTypeOrder.isEmpty()) {
				return null;
			}
			pTypeOrder = pTypeOrder.toUpperCase();
			for (TypeOrder typeOrder : TypeOrder.values()) {
				if (typeOrder.toString().equals(pTypeOrder)) {
					return typeOrder;
				}
			}
			return null;
		}
	}

	/**
	 * Liste des Champs ordonnable.
	 */
	public enum Order {
		NAME("name", "mName"), INTRODUCED_DATE("introduced", "mIntroducedDate"), DISCONTINUED_DATE("discontinued", "mDiscontinuedDate"), ID_COMPANY("company_id", ""), NAME_COMPANY("company_name", "");

		public final String queryName;
		public final String attributeName;

		Order(final String pQueryName, final String pAttributeName) {
			attributeName = pAttributeName;
			queryName = pQueryName;
		}

		/**
		 * Parse d'un String en Order.
		 *
		 * @param pTexte Texte à parser
		 * @return Order ou null si pTextde est null ou vide
		 */
		public static Order from(String pTexte) {
			if (pTexte == null || pTexte.isEmpty()) {
				return null;
			}
			for (Order order : Order.values()) {
				if (order.queryName.equals(pTexte.toLowerCase())) {
					return order;
				}
			}
			return null;
		}
	}

	//=============================================================
	// Constructors
	//=============================================================
	public ComputerDao() {
	}

	// ===========================================================
	// Methods - Crudable
	// ===========================================================

	@Override
	public Paginator<Computer> findWithNamedQueryWithPaginator(String namedQueryName, Map<String, Object> parameters) throws DAOException {
		switch (namedQueryName) {
			case NamedQueries.SEARCH:
				int size = (int) parameters.getOrDefault(Parameters.SIZE, BASE_SIZE_PAGE);
				int start = (int) parameters.getOrDefault(Parameters.START, 0);
				String search = (String) parameters.get(Parameters.FILTER_NAME);
				Order order = (Order) parameters.get(Parameters.ORDER);
				TypeOrder typeOrder = (TypeOrder) parameters.getOrDefault(Parameters.TYPE_ORDER, TypeOrder.ASC);

				CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();

				CriteriaQuery<Long> cqCount = cb.createQuery(Long.class);
				CriteriaQuery<Computer> cq = cb.createQuery(Computer.class);
				Root<Computer> computerRoot = cq.from(Computer.class);
				Root<Computer> cqCountcomputerRoot = cqCount.from(Computer.class);

				cq.select(computerRoot);
				cqCount.select(cb.count(cqCountcomputerRoot));

				if (search != null && !search.isEmpty()) {
					cq.where(cb.like(cb.lower(computerRoot.get("mName")), "%" + search.toLowerCase() + "%"));
					cqCount.where(cb.like(cb.lower(cqCountcomputerRoot.get("mName")), "%" + search.toLowerCase() + "%"));
				}

				if (order != null) {
					if (typeOrder == TypeOrder.DESC) {
						cq.orderBy(cb.desc(computerRoot.get(order.attributeName)));
					} else {
						cq.orderBy(cb.asc(computerRoot.get(order.attributeName)));
					}
				}

				int nbCount = Math.toIntExact(mEntityManager.createQuery(cqCount).getSingleResult());

				cq.select(computerRoot);

				return new Paginator<>(start, nbCount, size, mEntityManager.createQuery(cq).setMaxResults(size).setFirstResult(start).getResultList());
			default:
				throw new UnsupportedOperationException("NamedQueries not supported : " + namedQueryName);
		}
	}

	@Override
	public List<Computer> findAll() {
		try {
			return this.findAll(0, BASE_SIZE_PAGE);
		} catch (DAOException e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
	}

	// ===========================================================
	// Methods statics
	// ===========================================================
	@Deprecated
	public static ComputerDao getInstance() {
		return null;
	}
}
