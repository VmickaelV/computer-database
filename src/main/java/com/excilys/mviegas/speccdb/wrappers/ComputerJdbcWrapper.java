package com.excilys.mviegas.speccdb.wrappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.persist.jdbc.CompanyDao;

public class ComputerJdbcWrapper extends Computer {
	public static Computer fromJdbc(ResultSet pResultSet) {
		try {
			Computer company = new Computer(pResultSet.getInt("id"), pResultSet.getString("name"), pResultSet.getDate("introduced"), pResultSet.getDate("discontinued"), CompanyDao.INSTANCE.find(pResultSet.getLong("company_id")));
			return company;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
