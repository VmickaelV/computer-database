package com.excilys.mviegas.speccdb.services;

import com.excilys.mviegas.speccdb.data.User;
import com.excilys.mviegas.speccdb.persistence.Crudable;

/**
 * Interface d'utilssation d'un UserService
 *
 * @author VIEGAS Mickael
 */
public interface IUserService extends Crudable<User> {
	public User findByName(String pName);
}
