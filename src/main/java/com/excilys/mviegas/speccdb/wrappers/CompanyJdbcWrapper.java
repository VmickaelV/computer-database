package com.excilys.mviegas.speccdb.wrappers;

import com.excilys.mviegas.speccdb.data.Company;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyJdbcWrapper extends Company {

	public static Company fromJdbc(ResultSet pResultSet) {
		try {
			Company company = new Company();
			company.setId(pResultSet.getInt("id"));
			company.setName(pResultSet.getString("name"));
			return company;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	public static Company toJdbc(Company pCompany) {
		return null;
	}
}
