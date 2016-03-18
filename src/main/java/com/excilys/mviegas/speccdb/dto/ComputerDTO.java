package com.excilys.mviegas.speccdb.dto;

import java.util.Date;

public class ComputerDTO {
	public long mId;
	public String mName;
	public Date mIntroducedDate;
	public Date mDiscontinuedDate;
	public long mIdCompany;
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
