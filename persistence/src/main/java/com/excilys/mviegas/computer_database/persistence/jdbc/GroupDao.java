package com.excilys.mviegas.computer_database.persistence.jdbc;

import com.excilys.mviegas.computer_database.data.Group;
import com.excilys.mviegas.computer_database.data.User;
import com.excilys.mviegas.computer_database.persistence.IGroupDao;
import com.excilys.mviegas.computer_database.persistence.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Dao d'un {@link Group}
 *
 * @author VIEGAS Mickael
 */
@Repository
public class GroupDao extends AbstractGenericCrudServiceBean<Group> implements IGroupDao {
	//=============================================================
	// Attributs
	//=============================================================
	 @Autowired
	 private IUserDao mUserDao;

	//=============================================================
	// Constructors
	//=============================================================
	public GroupDao() {
	}


	// ===========================================================
	// Methods - Crudable
	// ===========================================================
	@Override
	public boolean addUser(Group pGroup, User pUser) {
		pGroup = find(pGroup.getId());
		pUser = mEntityManager.find(User.class, pUser.getId());
		mEntityManager.refresh(pUser);
		pGroup.getUserList().add(pUser);
		pUser.getGroupList().add(pGroup);
		return update(pGroup) != null;
	}

}
