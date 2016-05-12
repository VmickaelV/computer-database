package com.excilys.mviegas.speccdb.persistence;

import com.excilys.mviegas.speccdb.data.Group;
import com.excilys.mviegas.speccdb.data.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface d'une DAO de {@link Group}
 *
 * @author VIEGAS Mickael
 */
public interface IGroupDao extends Crudable<Group> {

	@Transactional
	public boolean addUser(Group pGroup, User pUser);
}
