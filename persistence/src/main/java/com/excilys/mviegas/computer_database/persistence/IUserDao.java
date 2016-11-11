package com.excilys.mviegas.computer_database.persistence;

import com.excilys.mviegas.computer_database.data.User;

/**
 * Interface d'une DAO de {@link User}
 *
 * @author VIEGAS Mickael
 */
public interface IUserDao extends Crudable<User> {

	public User findByName(String pName);
}
