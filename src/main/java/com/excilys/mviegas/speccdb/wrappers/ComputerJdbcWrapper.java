package com.excilys.mviegas.speccdb.wrappers;

import com.excilys.mviegas.speccdb.IConverter;
import com.excilys.mviegas.speccdb.concurrency.ThreadLocals;
import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persistence.jdbc.CompanyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

@Deprecated
public enum ComputerJdbcWrapper implements IConverter<ResultSet, Computer> {
	INSTANCE;

	public static final Logger LOGGER = LoggerFactory.getLogger(ComputerJdbcWrapper.class);

	@Override
	public ResultSet getAsString(Computer pValue) {
		// TODO To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.wrappers.ComputerJdbcWrapper#getAsString not implemented yet.");
	}

	@Override
	public Computer getAsObject(ResultSet pResultSet) {
		Computer company = null;
		Computer.Builder companyBuilder = new Computer.Builder();

		CompanyDao companyDao = (CompanyDao) ThreadLocals.COMPUTER_DAOS.get();
		Object object;

		try {
			companyBuilder.setName(pResultSet.getString("name"));


			object = pResultSet.getDate("introduced");
			if (object != null) {
				companyBuilder.setIntroducedDate(pResultSet.getDate("introduced").toLocalDate());
			}

			if (pResultSet.getDate("discontinued") != null) {
				companyBuilder.setDiscontinuedDate(pResultSet.getDate("discontinued").toLocalDate());
			}

			if (pResultSet.getLong("company_id") != 0) {
				companyBuilder.setManufacturer(companyDao.find(pResultSet.getLong("company_id"))).build();
			}
			company = companyBuilder.build();
			company.setId(pResultSet.getInt("id"));

		} catch (SQLException | DAOException pE) {
			LOGGER.error(pE.getMessage(), pE);
		}

		return company;
	}

	public static Computer fromJdbc(ResultSet pResultSet) throws SQLException, DAOException {
		return INSTANCE.getAsObject(pResultSet);
	}

}
