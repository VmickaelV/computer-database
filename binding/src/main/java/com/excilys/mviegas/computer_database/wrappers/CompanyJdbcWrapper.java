package com.excilys.mviegas.computer_database.wrappers;

import com.excilys.mviegas.computer_database.data.Company;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

@Deprecated
public class CompanyJdbcWrapper extends Company {

	public static final Logger LOGGER = LoggerFactory.getLogger(CompanyJdbcWrapper.class);

	public static Company fromJdbc(ResultSet pResultSet) {
		try {
			Company company = new Company();
			company.setId(pResultSet.getInt("id"));
			company.setName(pResultSet.getString("name"));
			return company;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);

			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	public static Company toJdbc(Company pCompany) {
		return null;
	}
}
