package com.excilys.mviegas.speccdb.concurrency;

import com.excilys.mviegas.speccdb.persistence.ICompanyDao;
import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao;

import java.sql.Connection;

/**
 * Classes regroupant les Threads locaux
 *
 * Created by Mickael on 24/03/16.
 * @author Mickael
 */
public final class ThreadLocals {
	public static final ThreadLocal<Connection> CONNECTIONS = new ThreadLocal<>();
	public static final ThreadLocal<ComputerDao> COMPUTER_DAOS = new ThreadLocal<>();
	public static final ThreadLocal<ICompanyDao> COMPANY_DAOS = new ThreadLocal<>();
}
