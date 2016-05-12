package com.excilys.mviegas.speccdb.data;

import com.excilys.mviegas.speccdb.interfaces.Identifiable;

import javax.persistence.*;

/**
 * Représentation du'n autorisation.
 *
 * @author VIEGAS Mickael
 */
@Entity
public class Authorization implements Identifiable {

	//=============================================================
	// Attribute - private
	//=============================================================
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idGenerator")
	@SequenceGenerator(name = "idGenerator", sequenceName = "idGenerator", initialValue = 600)
	private long mId;

	private String mName;

	//=============================================================
	// Constructors
	//=============================================================
	public Authorization() {
	}

	public Authorization(String pName) {
		mName = pName;
	}

	//=============================================================
	// Getters & Setters
	//=============================================================
	@Override
	public long getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}
}
