package com.excilys.mviegas.speccdb.managers;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.persist.CrudService;
import com.excilys.mviegas.speccdb.persist.jdbc.CompanyDao;
import com.excilys.mviegas.speccdb.persist.jdbc.ComputerDao;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@ManagedBean
public class ComputerEditor {

	public static final String PATTERN_DATE = "dd/MM/yyyy";
	
	//===========================================================
	// Attribute - private
	//===========================================================
	public String mName;
	public String mIntroducedDate;
	public String mDiscontinuedDate;
	public int mIdCompany;
	
	public List<Company> mCompanies;

	public static DateTimeFormatter sDateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_DATE);

	public CrudService<Company> mCompanyCrudService;
	public CrudService<Computer> mComputerCrudService;

	
	//===========================================================
	// Constructeurs
	//===========================================================
	public ComputerEditor() {
		init();
	}
	//===========================================================
	// Méthodes
	//===========================================================
	
	@PostConstruct
	public void init() {
		mCompanyCrudService = CompanyDao.INSTANCE;
		mComputerCrudService = ComputerDao.INSTANCE;
		mCompanies = mCompanyCrudService.findAll();
	}
	
	public String getName() {
		return mName;
	}

	public List<Company> getCompanies() {
		return mCompanies;
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
	public int getIdCompany() {
		return mIdCompany;
	}
	public void setIdCompany(int pIdCompany) {
		mIdCompany = pIdCompany;
	}

	public boolean hasValidName() {
		return mName != null && !mName.isEmpty();
	}

	public boolean hasValidIntroducedDate() {
		return isValidOptionnalDate(mIntroducedDate);
	}

	public boolean hasValidDiscontinuedDate() {
		return isValidOptionnalDate(mDiscontinuedDate);
	}

	public boolean hasValidIdCompany() {
		return mIdCompany == 0 || mCompanyCrudService.find(mIdCompany) != null;
	}

	private static boolean isValidOptionnalDate(String pDate) {
		if (pDate == null) {
			return true;
		} else {
			try {
				LocalDate.parse(pDate, sDateTimeFormatter);
				return true;
			} catch (DateTimeParseException e) {
				return false;
			}
		}
	}

	private Computer makeComputer() {
		try {
			return new Computer.Builder()
					.setName(mName)
					.setIntroducedDate(mIntroducedDate == null ? null : LocalDate.parse(mIntroducedDate, sDateTimeFormatter))
					.setDiscontinuedDate(mDiscontinuedDate == null ? null : LocalDate.parse(mDiscontinuedDate, sDateTimeFormatter))
					.setManufacturer(mCompanyCrudService.find(mIdCompany)).build();
		} catch (DateTimeParseException pE) {
			// TODO a logger
			throw new RuntimeException("Erreur non attendu", pE);
		}
	}

	//===========================================================
	// Méthodes Controleurs
	//===========================================================
	public boolean addComputer() {
		if (hasValidName() && hasValidIntroducedDate() && hasValidDiscontinuedDate() && hasValidIdCompany()) {
			mComputerCrudService.create(makeComputer());

			return true;
		} else {
			return false;
		}
	}

}
