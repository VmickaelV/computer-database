package com.excilys.mviegas.speccdb.persistence.jdbc;

import com.excilys.mviegas.speccdb.data.Group;
import com.excilys.mviegas.speccdb.data.User;
import com.excilys.mviegas.speccdb.persistence.IGroupDao;
import com.excilys.mviegas.speccdb.persistence.IUserDao;
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
	// Constantes
	//=============================================================

	//=============================================================
	// Attributs
	//=============================================================
	 @Autowired
	 private IUserDao mUserDao;

	//=============================================================
	// Inner Classes
	//=============================================================

	//=============================================================
	// Attributres - private
	//=============================================================

	//=============================================================
	// Constructors
	//=============================================================
	public GroupDao() {
	}


	//===========================================================
	// Getters & Setters
	//===========================================================

	//===========================================================
	// Methods - private
	//===========================================================

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
