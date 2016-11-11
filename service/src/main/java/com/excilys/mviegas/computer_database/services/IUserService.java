package com.excilys.mviegas.computer_database.services;

import com.excilys.mviegas.computer_database.data.User;
import com.excilys.mviegas.computer_database.persistence.Crudable;

/**
 * Interface d'utilssation d'un UserService
 *
 * @author VIEGAS Mickael
 */
public interface IUserService extends Crudable<User> {
	public User findByName(String pName);
}
