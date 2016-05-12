package com.excilys.mviegas.speccdb.persistence.jdbc;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persistence.ICompanyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;

/**
 * Dao d'une companie ({@link Company}).
 */
@Repository
public class CompanyDao extends AbstractGenericCrudServiceBean<Company> implements ICompanyDao {

	// ============================================================
	//	Constantes
	// ============================================================
	public static final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

	//=============================================================
	// Constructors
	//=============================================================
	public CompanyDao() {
	}

	// ===========================================================
	// Methods - Crudable
	// ===========================================================

	@Override
	public Company create(Company pCompany) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Company update(Company pCompany) {
		throw new UnsupportedOperationException();
	}

	@Override
	@Transactional(rollbackFor = {EntityNotFoundException.class})
	public boolean delete(long id) {
		CriteriaBuilder cb = mEntityManager.getCriteriaBuilder();
		CriteriaDelete<Computer> cd = cb.createCriteriaDelete(Computer.class);
		Root<Computer> rootComputer = cd.from(Computer.class);
		rootComputer.getModel().getAttributes().forEach(a -> {
			System.out.println("-------------------------------");
			System.out.println(a.getName());
			System.out.println(a.getJavaMember());
			System.out.println(a.getJavaType());
			System.out.println(a.getPersistentAttributeType());
		});
		cd.where(cb.equal(rootComputer.get("mManufacturer"), id));

		mEntityManager.createQuery(cd).executeUpdate();

		return super.delete(id);
	}

	@Override
	@Transactional
	public boolean delete(Company pCompany) throws DAOException {
		if (pCompany == null || pCompany.getId() <= 0) {
			throw new IllegalArgumentException();
		}
		return delete(pCompany.getId());
	}
}
