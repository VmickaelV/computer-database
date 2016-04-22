package com.excilys.mviegas.speccdb.dto;

/**
 * DTO d'un computer d'une formulaire
 */
public class ComputerDTO {

	//=============================================================
	// Attributs - private
	//=============================================================
	public long mId;
	public String mName;
	public String mIntroducedDate;
	public String mDiscontinuedDate;
	public long mIdCompany;

	//=============================================================
	// Constructeurs
	//=============================================================
	public ComputerDTO() {
	}

	public ComputerDTO(long pId, String pName, String pIntroducedDate, String pDiscontinuedDate, long pIdCompany) {
		mId = pId;
		mName = pName;
		mIntroducedDate = pIntroducedDate;
		mDiscontinuedDate = pDiscontinuedDate;
		mIdCompany = pIdCompany;
	}

	//=============================================================
	// Getters & Setters
	//=============================================================

	public long getId() {
		return mId;
	}

	public void setId(long pId) {
		mId = pId;
	}

	public String getName() {
		return mName;
	}

	public void setName(String pName) {
		mName = pName;
	}

	public String getIntroducedDate() {
		return mIntroducedDate;
	}

	public void setIntroducedDate(String pIntroducedDate) {
		mIntroducedDate = pIntroducedDate;
	}

	public String getDiscontinuedDate() {
		return mDiscontinuedDate;
	}

	public void setDiscontinuedDate(String pDiscontinuedDate) {
		mDiscontinuedDate = pDiscontinuedDate;
	}

	public long getIdCompany() {
		return mIdCompany;
	}

	public void setIdCompany(long pIdCompany) {
		mIdCompany = pIdCompany;
	}
}
