package com.excilys.mviegas.speccdb.persistence.jdbc;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persistence.Crudable;
import com.excilys.mviegas.speccdb.persistence.Paginator;
import com.excilys.mviegas.speccdb.wrappers.CompanyJdbcWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Dao d'une companie ({@link Company})
 */
@Repository
public class CompanyDao implements Crudable<Company> {

	//=============================================================
	// Attributs
	//=============================================================
	private JdbcTemplate mJdbcTemplate;

	//=============================================================
	// Getters & Setters
	//=============================================================

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.mJdbcTemplate = new JdbcTemplate(dataSource);
	}

	// ============================================================
	//	Constantes
	// ============================================================
	public static final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

	// ===========================================================
	// Methods - Crudable
	// ===========================================================
	@Override
	public Company find(long pId) throws DAOException {
		List<Company> companies = mJdbcTemplate.query("SELECT * FROM `company` WHERE id = " + pId, (rs, introw) -> {
			return CompanyJdbcWrapper.fromJdbc(rs);
		});
		if (companies.size() != 1) {
			return null;
		}
		return companies.get(0);
	}

	@Override
	public boolean delete(long pId) throws DAOException {
		try {
			mJdbcTemplate.update("DELETE FROM COMPUTER WHERE company_id = " + pId);
			if (mJdbcTemplate.update("DELETE FROM COMPANY WHERE id = "+pId) != 1) {
				throw new IllegalStateException("Not persisted company");
			}
			return true;
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e);
		}
	}
	
	@Override
	public boolean delete(Company pT) throws DAOException {
		if (pT == null || pT.getId() <= 0) {
			throw new IllegalArgumentException();
		}
		return delete(pT.getId());
	}

	@Override
	public List<Company> findAll() throws DAOException {
		return findAll(0, 0);
	}
	
	@Override
	public List<Company> findAll(int pStart, int pSize) throws DAOException {
		return findAllWithPaginator(pStart, pSize).getValues();
	}

	@Override
	public Paginator<Company> findAllWithPaginator(int pStart, int pSize) throws DAOException {
		Paginator<Company> paginator;

		try {
			String sqlQuery;

			if (pSize > 0) {
				sqlQuery = "SELECT * FROM company LIMIT " + pSize + " OFFSET " + pStart;
			} else {
				sqlQuery = "SELECT * FROM company";
			}


			List<Company> mCompanies = mJdbcTemplate.query(sqlQuery, (r, i) -> CompanyJdbcWrapper.fromJdbc(r));

			int nbCount;
			if (pSize > 0) {
				nbCount = mJdbcTemplate.queryForObject("SELECT COUNT(*) FROM company", Integer.class);
			} else {
				nbCount = mCompanies.size();
			}

			paginator = new Paginator<>(pStart, nbCount, pSize, mCompanies);

			return paginator;
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage(), e);
			throw new DAOException(e);
		}
	}

	@Override
	public int size() throws DAOException {
		return mJdbcTemplate.queryForObject("SELECT COUNT(*) FROM company", Integer.class);
	}

	// ===========================================================
	// Methods statics
	// ===========================================================
}
