package com.excilys.mviegas.speccdb.data;

import com.excilys.mviegas.speccdb.interfaces.Identifiable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
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
	private long mId;

	private String mName;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="GROUP_AUTH",
			joinColumns=@JoinColumn(name="GROUP_ID", referencedColumnName="MID"),
			inverseJoinColumns=@JoinColumn(name="AUTH_ID", referencedColumnName="MID"))
	private List<Authorization> mAuthorizationList;

	@ManyToMany
	@JoinTable(name="GROUP_USER",
			joinColumns=@JoinColumn(name="GROUP_ID", referencedColumnName="MID"),
			inverseJoinColumns=@JoinColumn(name="USER_ID", referencedColumnName="MID"))
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
