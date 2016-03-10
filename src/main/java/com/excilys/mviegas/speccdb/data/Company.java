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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (mId != other.mId)
			return false;
		return true;
	}
	
	

}
