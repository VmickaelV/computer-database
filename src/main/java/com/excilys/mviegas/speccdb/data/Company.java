package com.excilys.mviegas.speccdb.data;

/**
 * Objet représentant une compagnie
 *
 * @author Mickael
 */
public class Company {

	// ===========================================================
	// Attributes - private
	// ===========================================================

	private int mId;

	private String mName;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Company() {
		super();
	}
	
	private Company(int pId, String pName) {
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

	// -----------------------------------------------------------

	public void setId(final int pId) {
		if (mId == 0 && pId > 0) {
			mId = pId;
		}
	}

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
		//noinspection RedundantIfStatement
		if (mId != other.mId)
			return false;
		return true;
	}
	
	// ============================================================
	//	Inner Class
	// ============================================================

	/**
	 * Class Builder d'une compagnie
	 */
	public static class Builder {
		private int mId;
		private String mName;

		public Builder setName(final String pName) {
			mName = pName;
			return this;
		}

		public Company build() {
			Company company = new Company();
			company.mName = mName;
			return company;
		}
	}
}
