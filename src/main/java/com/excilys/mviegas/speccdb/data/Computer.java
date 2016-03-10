package com.excilys.mviegas.speccdb.data;

import java.util.Date;

public class Computer {

	// ===========================================================
	// Attributs - private
	// ===========================================================

	private int mId;

	private String mName;

	private Date mIntroducedDate;

	private Date mDiscontinuedDate;

	private Company mManufacturer;

	// ===========================================================
	// Constructors
	// ===========================================================

	protected Computer() {
		super();
	}

	public Computer(String pName) {
		super();
		mName = pName;
	}
	
	
	
	

	public Computer(String pName, Date pIntroducedDate, Date pDiscontinuedDate, Company pManufacturer) {
		super();
		mName = pName;
		mIntroducedDate = pIntroducedDate;
		mDiscontinuedDate = pDiscontinuedDate;
		mManufacturer = pManufacturer;
	}

	public Computer(int pId, String pName, Date pIntroducedDate, Date pDiscontinuedDate, Company pManufacturer) {
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

	public Date getIntroducedDate() {
		return mIntroducedDate;
	}

	public Date getDiscontinuedDate() {
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

	public void setIntroducedDate(Date pIntroducedDate) {
		mIntroducedDate = pIntroducedDate;
	}

	public void setDiscontinuedDate(Date pDiscontinuedDate) {
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
	
}
