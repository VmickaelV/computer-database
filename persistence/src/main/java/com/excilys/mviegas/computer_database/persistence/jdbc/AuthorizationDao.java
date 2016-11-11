package com.excilys.mviegas.computer_database.persistence.jdbc;

import com.excilys.mviegas.computer_database.data.Authorization;
import com.excilys.mviegas.computer_database.persistence.IAuthorityDao;
import org.springframework.stereotype.Repository;

/**
 * Dao d'une {@link Authorization}.
 *
 * @author VIEGAS Mickael
 */
@Repository
public class AuthorizationDao extends AbstractGenericCrudServiceBean<Authorization> implements IAuthorityDao {
	//=============================================================
	// Constructors
	//=============================================================
	public AuthorizationDao() {
	}

}
