package com.excilys.mviegas.speccdb.data;

import com.excilys.mviegas.speccdb.interfaces.Identifiable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author VIEGAS Mickael
 */
@Entity
public class User implements Identifiable {

	//=============================================================
	// Attributes - private
	//=============================================================
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
	@SequenceGenerator(name = "idGenerator", sequenceName = "idGenerator", initialValue = 600)
	private long mId;

	private String mUsername;

	private String mPassword;

	private boolean mEnabled;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="USER_AUTH",
			joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="MID"),
			inverseJoinColumns=@JoinColumn(name="AUTH_ID", referencedColumnName="MID"))
	private List<Authorization> mAuthorizationList;

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
