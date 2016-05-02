package com.excilys.mviegas.speccdb.persistence;

import com.excilys.mviegas.speccdb.data.User;

/**
 * @author VIEGAS Mickael
 */
public interface IUserDao extends Crudable<User> {

	public User findByName(String pName);
}
