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
 * Utilisateur sur le site.
 *
 * @author VIEGAS Mickael
 */
@Entity
public class User implements Identifiable {

	//=============================================================
	// Attributes - private
	//=============================================================

	/**
	 * ID de l'utilisateur.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
	@SequenceGenerator(name = "idGenerator", sequenceName = "idGenerator", initialValue = 600)
	private long mId;

	/**
	 * Nom d'utilisateur.
	 */
	private String mUsername;

	/**
	 * Mot de passe non en clair.
	 */
	private String mPassword;

	/**
	 * Compte autorisé ou non.
	 */
	private boolean mEnabled;

	/**
	 * Liste des autorisations acquises.
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_AUTH",
			joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "MID"),
			inverseJoinColumns = @JoinColumn(name = "AUTH_ID", referencedColumnName = "MID"))
	private List<Authorization> mAuthorizationList;

	/**
	 * Liste d'appartenance à des groupes.
	 */
	@ManyToMany(mappedBy = "mUserList", fetch = FetchType.EAGER)
	private List<Group> mGroupList;

	//=============================================================
	// Constructors
	//=============================================================
	public User() {
	}

	public User(String pUsername, String pPassword, boolean pEnabled) {
		mUsername = pUsername;
		mPassword = pPassword;
		mEnabled = pEnabled;
		mAuthorizationList = new ArrayList<>();
		mGroupList = new ArrayList<>();
	}

	//=============================================================
	// Getters & setters
	//=============================================================
	public String getUsername() {
		return mUsername;
	}

	@Override
	public long getId() {
		return mId;
	}

	public String getPassword() {
		return mPassword;
	}

	public boolean isEnabled() {
		return mEnabled;
	}

	public List<Authorization> getAuthorizationList() {
		return mAuthorizationList;
	}

	public List<Group> getGroupList() {
		return mGroupList;
	}

	public void setUsername(String pUsername) {
		mUsername = pUsername;
	}

	public void setPassword(String pPassword) {
		mPassword = pPassword;
	}

	public void setEnabled(boolean pEnabled) {
		mEnabled = pEnabled;
	}
}
