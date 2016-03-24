package com.excilys.mviegas.speccdb.dto;

import com.excilys.mviegas.speccdb.data.Computer;

import java.util.Date;

/**
 * DTO d'un computer d'une formulaire
 */
public class ComputerDTO {

	//=============================================================
	// Attributs - private
	//=============================================================
	public long mId;
	public String mName;
	public Date mIntroducedDate;
	public Date mDiscontinuedDate;
	public long mIdCompany;

	//=============================================================
	// Constructeurs
	//=============================================================
	public ComputerDTO() {
	}

	public ComputerDTO(long pId, String pName, Date pIntroducedDate, Date pDiscontinuedDate, long pIdCompany) {
		mId = pId;
		mName = pName;
		mIntroducedDate = pIntroducedDate;
		mDiscontinuedDate = pDiscontinuedDate;
		mIdCompany = pIdCompany;
	}

	// TODO à implémenter plus tard
	public ComputerDTO(Computer pComputer) {
		throw new UnsupportedOperationException();
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

	public Date getIntroducedDate() {
		return mIntroducedDate;
	}

	public void setIntroducedDate(Date pIntroducedDate) {
		mIntroducedDate = pIntroducedDate;
	}

	public Date getDiscontinuedDate() {
		return mDiscontinuedDate;
	}

	public void setDiscontinuedDate(Date pDiscontinuedDate) {
		mDiscontinuedDate = pDiscontinuedDate;
	}

	public long getIdCompany() {
		return mIdCompany;
	}

	public void setIdCompany(long pIdCompany) {
		mIdCompany = pIdCompany;
	}


}
