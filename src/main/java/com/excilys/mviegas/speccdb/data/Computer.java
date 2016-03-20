package com.excilys.mviegas.speccdb.data;

import java.time.LocalDate;

/**
 * Objet représentant un ordinateur
 *
 * @author Mickael
 */
public class Computer {

	// ===========================================================
	// Attributs - private
	// ===========================================================

	private int mId;

	private String mName;

	private LocalDate mIntroducedDate;

	private LocalDate mDiscontinuedDate;

	private Company mManufacturer;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Computer() {
		super();
	}

	public Computer(String pName) {
		super();
		mName = pName;
	}

	public Computer(String pName, LocalDate pIntroducedDate, LocalDate pDiscontinuedDate, Company pManufacturer) {
		super();
		mName = pName;
		mIntroducedDate = pIntroducedDate;
		mDiscontinuedDate = pDiscontinuedDate;
		mManufacturer = pManufacturer;
	}

	public Computer(int pId, String pName, LocalDate pIntroducedDate, LocalDate pDiscontinuedDate, Company pManufacturer) {
		super();
		mId = pId;
		mName = pName;
		mIntroducedDate = pIntroducedDate;
		mDiscontinuedDate = pDiscontinuedDate;
		mManufacturer = pManufacturer;
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

	public LocalDate getIntroducedDate() {
		return mIntroducedDate;
	}

	public LocalDate getDiscontinuedDate() {
		return mDiscontinuedDate;
	}

	public Company getManufacturer() {
		return mManufacturer;
	}

	// ------------------------------------------------------------

	// TODO a effacer
	public void setId(int pId) {
		if (mId == 0) {
			mId = pId;
		}
	}
	
	public void setName(String pName) {
		mName = pName;
	}

	public void setIntroducedDate(LocalDate pIntroducedDate) {
		mIntroducedDate = pIntroducedDate;
	}

	public void setDiscontinuedDate(LocalDate pDiscontinuedDate) {
		mDiscontinuedDate = pDiscontinuedDate;
	}

	public void setManufacturer(Company pManufacturer) {
		mManufacturer = pManufacturer;
	}

	//===========================================================
	// Methods - Objets
	//===========================================================
	@Override
	public String toString() {
		return "Computer [mId=" + mId + ", mName=" + mName + ", mIntroducedDate=" + mIntroducedDate
				+ ", mDiscontinuedDate=" + mDiscontinuedDate + ", mManufacturer=" + mManufacturer + "]";
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
		Computer other = (Computer) obj;
		//noinspection RedundantIfStatement
		if (mId != other.mId)
			return false;
		return true;
	}
	
	// ============================================================
	//	Inner Class
	// ============================================================

	/**
	 * Class Builder d'un ordinateur
	 */
	public static class Builder {

		private String mName;
		private LocalDate mIntroducedDate;
		private LocalDate mDiscontinuedDate;
		private Company mManufacturer;

		public Builder setName(final String pName) {
			mName = pName;
			return this;
		}

		public Builder setIntroducedDate(final LocalDate pIntroducedDate) {
			mIntroducedDate = pIntroducedDate;
			return this;
		}

		public Builder setDiscontinuedDate(final LocalDate pDiscontinuedDate) {
			mDiscontinuedDate = pDiscontinuedDate;
			return this;
		}

		public Builder setManufacturer(final Company pManufacturer) {
			mManufacturer = pManufacturer;
			return this;
		}

		public Computer build() {
			Computer computer = new Computer();
			computer.mName = mName;
			computer.mIntroducedDate = mIntroducedDate;
			computer.mDiscontinuedDate = mDiscontinuedDate;
			computer.mManufacturer = mManufacturer;

			return computer;
		}
	}
}
