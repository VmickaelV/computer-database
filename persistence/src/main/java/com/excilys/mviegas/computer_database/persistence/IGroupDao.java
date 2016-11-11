package com.excilys.mviegas.computer_database.persistence;

import com.excilys.mviegas.computer_database.data.Group;
import com.excilys.mviegas.computer_database.data.User;
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
