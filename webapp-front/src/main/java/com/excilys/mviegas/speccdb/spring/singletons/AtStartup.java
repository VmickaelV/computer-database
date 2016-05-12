package com.excilys.mviegas.speccdb.spring.singletons;

import com.excilys.mviegas.speccdb.data.Authorization;
import com.excilys.mviegas.speccdb.data.Group;
import com.excilys.mviegas.speccdb.data.User;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persistence.IAuthorityDao;
import com.excilys.mviegas.speccdb.persistence.IGroupDao;
import com.excilys.mviegas.speccdb.persistence.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Initialisation d'objets.
 *
 * @author VIEGAS Mickael
 */
@Component
public class AtStartup {

	@Autowired
	private IAuthorityDao mAuthorityDao;

	@Autowired
	private IUserDao mUserDao;

	@Autowired
	private IGroupDao mGroupDao;

	@Autowired
	private PasswordEncoder mPasswordEncoder;

	@PostConstruct
	public void init() {
		try {
			Authorization defaultRole = new Authorization("ROLE_DEFAULT");
			Authorization adminRole = new Authorization("ROLE_ADMIN");
			mAuthorityDao.create(defaultRole);
			mAuthorityDao.create(adminRole);

			User userAdmin = new User("admin", mPasswordEncoder.encode("admin"), true);
			User userUser = new User("martin", mPasswordEncoder.encode("martin"), true);


			Group groupAdmin = new Group("admin");
			Group groupUser = new Group("user");

			groupAdmin.getAuthorizationList().add(adminRole);
			groupAdmin.getAuthorizationList().add(defaultRole);
			groupUser.getAuthorizationList().add(defaultRole);


			groupAdmin = mGroupDao.create(groupAdmin);
			groupUser = mGroupDao.create(groupUser);

			mUserDao.create(userAdmin);
			mUserDao.create(userUser);

			mGroupDao.addUser(groupAdmin, userAdmin);
			mGroupDao.addUser(groupUser, userUser);
		} catch (DAOException pE) {
			throw new RuntimeException();
		}
	}
}
