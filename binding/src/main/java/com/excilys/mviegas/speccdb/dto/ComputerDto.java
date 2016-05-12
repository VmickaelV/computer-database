package com.excilys.mviegas.speccdb.dto;

import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persistence.ICompanyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * DTO d'un computer d'une formulaire
 */
public class ComputerDto {
	public static final Logger LOGGER = LoggerFactory.getLogger(ComputerDto.class);

	//=============================================================
	// Attributs - private
	//=============================================================
	private long mId;
	private String mName;
	private String mIntroducedDate;
	private String mDiscontinuedDate;
	private long mIdCompany;

	//=============================================================
	// Constructeurs
	//=============================================================
	public ComputerDto() {
	}

	public ComputerDto(long pId, String pName, String pIntroducedDate, String pDiscontinuedDate, long pIdCompany) {
		mId = pId;
		mName = pName;
		mIntroducedDate = pIntroducedDate;
		mDiscontinuedDate = pDiscontinuedDate;
		mIdCompany = pIdCompany;
	}

	public ComputerDto(Computer pComputer) {
		mId = pComputer.getId();
		mName = pComputer.getName();

		if (pComputer.getIntroducedDate() != null) {
			mIntroducedDate = pComputer.getIntroducedDate().toString();
		}

		if (pComputer.getDiscontinuedDate() != null) {
			mDiscontinuedDate = pComputer.getDiscontinuedDate().toString();
		}

		if (pComputer.getManufacturer() != null) {
			mIdCompany = pComputer.getManufacturer().getId();
		}
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

	//=============================================================
	// toString
	//=============================================================

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ComputerDto{");
		sb.append("mId=").append(mId);
		sb.append(", mName='").append(mName).append('\'');
		sb.append(", mIntroducedDate='").append(mIntroducedDate).append('\'');
		sb.append(", mDiscontinuedDate='").append(mDiscontinuedDate).append('\'');
		sb.append(", mIdCompany=").append(mIdCompany);
		sb.append('}');
		return sb.toString();
	}

	//=============================================================
	// Methods
	//=============================================================
	public Computer toComputer(ICompanyDao pCompanyDao) {
		Computer computer = new Computer();

		DateTimeFormatter sDateTimeFormatter = DateTimeFormatter.ISO_DATE;
		if (mId > 0) {
			computer.setId(mId);
		}
		computer.setName(mName);
		computer.setIntroducedDate(mIntroducedDate == null || mIntroducedDate.isEmpty() ? null : LocalDate.parse(mIntroducedDate, sDateTimeFormatter));
		computer.setDiscontinuedDate(mDiscontinuedDate == null || mDiscontinuedDate.isEmpty() ? null : LocalDate.parse(mDiscontinuedDate, sDateTimeFormatter));
		try {
			computer.setManufacturer(pCompanyDao.find(mIdCompany));
		} catch (DAOException pE) {
			LOGGER.error(pE.getMessage(), pE);
			return null;
		}
		return computer;
	}
}
