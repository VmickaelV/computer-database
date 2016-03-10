package wrappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.mviegas.speccdb.data.Company;

public class CompanyJdbcWrapper extends Company {

	public static Company fromJdbc(ResultSet pResultSet) {
		try {
			Company company = new Company(pResultSet.getInt("id"), pResultSet.getString("name"));
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
