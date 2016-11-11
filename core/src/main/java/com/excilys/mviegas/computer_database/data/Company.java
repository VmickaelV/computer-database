package com.excilys.mviegas.computer_database.data;

import com.excilys.mviegas.computer_database.exceptions.BuilderException;
import com.excilys.mviegas.computer_database.interfaces.IBuilder;
import com.excilys.mviegas.computer_database.interfaces.Identifiable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Objet représentant une compagnie.
 *
 * @author Mickael
 */
@Entity
@Access(AccessType.PROPERTY)
public class Company implements Identifiable {

	// ===========================================================
	// Attributes - private
	// ===========================================================

	private long mId;

	private String mName;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Company() {
		super();
	}

	public Company(long pId, String pName) {
		mId = pId;
		mName = pName;
	}

	// ===========================================================
	// Getters & Setters
	// ===========================================================

	@Id
	@GeneratedValue
	public long getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

	// -----------------------------------------------------------

	public void setId(final long pId) {
		mId = pId;
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
		int result = (int) (mId ^ (mId >>> 32));
		result = 31 * result + (mName != null ? mName.hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Company other = (Company) obj;

		return mId == other.mId;
	}
	
	// ============================================================
	//	Inner Class
	// ============================================================

	/**
	 * Class Builder d'une compagnie.
	 */
	public static class Builder implements IBuilder<Builder, Company> {
		private int mId;
		private String mName;

		public Builder setName(final String pName) {
			mName = pName;
			return this;
		}

		@Override
		public Company build() {
			Company company = new Company();
			company.mName = mName;
			return company;
		}

		@Override
		public Builder init() throws BuilderException {
			mId = 0;
			mName = null;
			return this;
		}
	}
}
