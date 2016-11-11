package com.excilys.mviegas.computer_database.data;

import com.excilys.mviegas.computer_database.interfaces.Identifiable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

/**
 * Group d'utilisateurs pour la gestion de droits d'accès.
 *
 * @author VIEGAS Mickael
 */
@Entity(name = "GroupOfUsers")
public class Group implements Identifiable {

	//=============================================================
	// Attributs - private
	//=============================================================
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
	@SequenceGenerator(name = "idGenerator", sequenceName = "idGenerator", initialValue = 600)
	/**
	 * ID du group.
	 */
	private long mId;

	/**
	 * Nom du groupe.
	 */
	private String mName;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "GROUP_AUTH",
			joinColumns = @JoinColumn(name = "GROUP_ID", referencedColumnName = "MID"),
			inverseJoinColumns = @JoinColumn(name = "AUTH_ID", referencedColumnName = "MID"))
	/**
	 * Liste d'autorisations associé aux groupes.
	 */
	private List<Authorization> mAuthorizationList;

	@ManyToMany
	@JoinTable(name = "GROUP_USER",
			joinColumns = @JoinColumn(name = "GROUP_ID", referencedColumnName = "MID"),
			inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "MID"))
	/**
	 * Utilisateurs présent dans le groupe.
	 */
	private List<User> mUserList;

	//=============================================================
	// Constructors
	//=============================================================
	public Group() {
	}

	public Group(String pName) {
		mName = pName;
		mAuthorizationList = new ArrayList<>();
		mUserList = new ArrayList<>();
	}

	//=============================================================
	// Getters & Setters
	//=============================================================
	public long getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	public List<Authorization> getAuthorizationList() {
		return mAuthorizationList;
	}

	public List<User> getUserList() {
		return mUserList;
	}

	public void setName(String pName) {
		mName = pName;
	}
}
