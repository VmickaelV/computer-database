package com.excilys.mviegas.speccdb.wrappers;

import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.persist.jdbc.CompanyDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ComputerJdbcWrapper extends Computer {
	public static Computer fromJdbc(ResultSet pResultSet) {
		try {
			Computer company = new Computer.Builder()
					.setName(pResultSet.getString("name"))
					.setIntroducedDate(LocalDate.ofEpochDay(pResultSet.getDate("introduced").getTime()))
					.setDiscontinuedDate(LocalDate.ofEpochDay(pResultSet.getDate("discontinued").getTime()))
					.setManufacturer(CompanyDao.INSTANCE.find(pResultSet.getLong("company_id")))
					.build();
			company.setId(pResultSet.getInt("id"));
			return company;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
