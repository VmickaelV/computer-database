package com.excilys.mviegas.speccdb.persistence;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.persistence.jdbc.CompanyDao;
import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao;

/**
 * Classes regroupant les Singletons des différents DAO.
 */
public final class Daos {
	
	public static final ICrudService<Company> COMPANY_DAO = CompanyDao.INSTANCE;
	public static final ICrudService<Computer> COMPUTER_DAO = ComputerDao.INSTANCE;

}
