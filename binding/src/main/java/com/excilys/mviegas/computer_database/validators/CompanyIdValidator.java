package com.excilys.mviegas.computer_database.validators;

import com.excilys.mviegas.computer_database.exceptions.DAOException;
import com.excilys.mviegas.computer_database.persistence.ICompanyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Validateur d'un ID de {@link com.excilys.mviegas.computer_database.data.Company}.
 *
 * @author VIEGAS Mickael
 */
@Component
public final class CompanyIdValidator {

	public static final Logger LOGGER = LoggerFactory.getLogger(CompanyIdValidator.class);

	private static CompanyIdValidator sInstance;

	@Autowired
	private ICompanyDao mCompanyDao;

	public CompanyIdValidator() {
		sInstance = this;
	}

	public static boolean isValidIdCompany(long pId) {
		if (pId == 0) {
			return true;
		}
		try {
			return sInstance.mCompanyDao.find(pId) != null;
		} catch (DAOException pE) {
			LOGGER.error(pE.getMessage(), pE);
			return false;
		}
	}
}
