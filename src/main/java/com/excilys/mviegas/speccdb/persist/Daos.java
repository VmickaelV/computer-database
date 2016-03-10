package com.excilys.mviegas.speccdb.persist;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.persist.jdbc.CompanyDao;
import com.excilys.mviegas.speccdb.persist.jdbc.ComputerDao;

public final class Daos {
	
	public static final CrudService<Company> COMPANY_DAO = CompanyDao.INSTANCE;
	public static final CrudService<Computer> COMPUTER_DAO = ComputerDao.INSTANCE;

}
