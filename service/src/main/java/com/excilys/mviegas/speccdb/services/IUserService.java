package com.excilys.mviegas.speccdb.services;

import com.excilys.mviegas.speccdb.data.User;
import com.excilys.mviegas.speccdb.persistence.Crudable;

/**
 * Created by excilys on 22/04/16.
 */
public interface IUserService extends Crudable<User> {
	public User findByName(String pName);
}
