package com.excilys.mviegas.speccdb.persistence.jdbc;

import com.excilys.mviegas.speccdb.data.Authorization;
import com.excilys.mviegas.speccdb.persistence.IAuthorityDao;
import org.springframework.stereotype.Repository;

/**
 * Dao d'une {@link Authorization}
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
