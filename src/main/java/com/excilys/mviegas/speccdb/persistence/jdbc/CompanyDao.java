package com.excilys.mviegas.speccdb.persistence.jdbc;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.persistence.ICompanyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Dao d'une companie ({@link Company})
 */
@Repository
public class CompanyDao extends AbstractGenericCrudServiceBean<Company> implements ICompanyDao {

	// ============================================================
	//	Constantes
	// ============================================================
	public static final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

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
}
