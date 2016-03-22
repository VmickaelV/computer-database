package com.excilys.mviegas.speccdb.wrappers;

import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persist.jdbc.CompanyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComputerJdbcWrapper extends Computer {

	public static final Logger LOGGER = LoggerFactory.getLogger(ComputerJdbcWrapper.class);

	public static Computer fromJdbc(ResultSet pResultSet) throws SQLException, DAOException {
		
		Computer company;
		Builder companyBuilder = new Builder();

		Object object;

		companyBuilder.setName(pResultSet.getString("name"));

		object = pResultSet.getDate("introduced");
		if (object != null) {

			// TODO a revoir
//			companyBuilder.setIntroducedDate(LocalDate.from(Instant.ofEpochMilli(pResultSet.getDate("introduced").getTime())));
//			System.out.println(LocalDateTime.from(Instant.ofEpochMilli(pResultSet.getDate("introduced").getTime())));
		}

		if (pResultSet.getDate("discontinued") != null) {
//			companyBuilder.setDiscontinuedDate(LocalDate.ofEpochDay(pResultSet.getDate("discontinued").getTime()/1000));
		}

		if (pResultSet.getLong("company_id") != 0) {
			companyBuilder.setManufacturer(CompanyDao.INSTANCE.find(pResultSet.getLong("company_id")))
					.build();
		}
		company = companyBuilder.build();
		company.setId(pResultSet.getInt("id"));
		return company;
	}

}
