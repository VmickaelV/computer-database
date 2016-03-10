package com.excilys.mviegas.speccdb.data;

public class Company {

	// ===========================================================
	// Attributes - private
	// ===========================================================

	private int mId;

	private String mName;

	// ===========================================================
	// Constructors
	// ===========================================================

	protected Company() {
		super();
	}

	public Company(String pName) {
		super();
		mName = pName;
	}
	
	public Company(int pId, String pName) {
		super();
		mId = pId;
		mName = pName;
	}

	// ===========================================================
	// Getters & Setters
	// ===========================================================

	public int getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	// ----------------------------------------------------------

	public void setName(String pName) {
		mName = pName;
	}

	// ===========================================================
	// Methods - Object
	// ===========================================================

	@Override
	public String toString() {
		return "Company [mId=" + mId + ", mName=" + mName + "]";
	}

}
