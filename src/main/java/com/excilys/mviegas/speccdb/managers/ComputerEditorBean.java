package com.excilys.mviegas.speccdb.managers;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.persist.CrudService;
import com.excilys.mviegas.speccdb.persist.jdbc.CompanyDao;
import com.excilys.mviegas.speccdb.persist.jdbc.ComputerDao;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Bean lié à la gestion d'un ordinateur
 *
 * @author Mickael
 */
@ManagedBean
public class ComputerEditorBean {

	public static final Logger LOGGER = LoggerFactory.getLogger(ComputerEditorBean.class);
	public static final String PATTERN_DATE = "dd/MM/yyyy";
	public final static DateTimeFormatter sDateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_DATE);
	
	//===========================================================
	// Attribute - private
	//===========================================================
	private String mName;
	private String mIntroducedDate;
	private String mDiscontinuedDate;
	private int mIdCompany;
	private long mId;
	private Computer mComputer;
	private String mAction;
	
	public List<Company> mCompanies;

	public CrudService<Company> mCompanyCrudService;
	public CrudService<Computer> mComputerCrudService;
	
	//===========================================================
	// Constructeurs
	//===========================================================
	public ComputerEditorBean() {
		init();
	}

	//===========================================================
	// Getters & Setters
	//===========================================================
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

	public long getId() {
		return mId;
	}

	public void setId(long pId) {
		mId = pId;
		if (pId > 0) {
			try {
				mComputer = mComputerCrudService.find(pId);
				
				mName = mComputer.getName();
				if (mComputer.getIntroducedDate() != null) {
					mIntroducedDate = mComputer.getIntroducedDate().format(sDateTimeFormatter);
				}
				
				if (mComputer.getDiscontinuedDate() != null) {
					mDiscontinuedDate = mComputer.getDiscontinuedDate().format(sDateTimeFormatter);
				}
				
				if (mComputer.getManufacturer() != null) {
					mIdCompany = mComputer.getManufacturer().getId();
				}
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				// TODO à refaire
				throw new RuntimeException(pE);
			}
		} else {
			mComputer = null;
		}
	}

	public Computer getComputer() {
		return mComputer;
	}

	public void setComputer(Computer pComputer) {
		mComputer = pComputer;
	}
	
	public String getAction() {
		return mAction;
	}

	public void setAction(String pAction) {
		mAction = pAction;
	}

	//===========================================================
	// Functions
	//===========================================================

	public boolean isEditing() {
		return mId > 0;
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
		try {
			return mIdCompany == 0 || mCompanyCrudService.find(mIdCompany) != null;
		} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
			// TODO à implenter
			throw new RuntimeException(pE);
		}
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
			if (mComputer == null) {
				return new Computer.Builder()
						.setName(mName)
						.setIntroducedDate(mIntroducedDate == null ? null : LocalDate.parse(mIntroducedDate, sDateTimeFormatter))
						.setDiscontinuedDate(mDiscontinuedDate == null ? null : LocalDate.parse(mDiscontinuedDate, sDateTimeFormatter))
						.setManufacturer(mCompanyCrudService.find(mIdCompany)).build();
			} else {				
				mComputer.setName(mName);
				mComputer.setIntroducedDate(mIntroducedDate == null ? null : LocalDate.parse(mIntroducedDate, sDateTimeFormatter));
				mComputer.setDiscontinuedDate(mDiscontinuedDate == null ? null : LocalDate.parse(mDiscontinuedDate, sDateTimeFormatter));
				mComputer.setManufacturer(mCompanyCrudService.find(mIdCompany));
				
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(mComputer.toString());
				}
				
				return mComputer;
			}
		} catch (DateTimeParseException pE) {
			// TODO a logger
			throw new RuntimeException("Erreur non attendu", pE);
		} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
			// TODO à refaire
			throw new RuntimeException(pE);
		}
	}

	// ============================================================
	//	Méthodes - Callback
	// ============================================================
	@PostConstruct
	public void init() {
		mCompanyCrudService = CompanyDao.INSTANCE;
		mComputerCrudService = ComputerDao.INSTANCE;
		try {
			mCompanies = mCompanyCrudService.findAll(0, 0);
		} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
			// TODO à refaire
			throw new RuntimeException(pE);
		}
	}
	
	public void request() {
		
	}

	//===========================================================
	// Méthodes Controleurs
	//===========================================================
	public boolean addComputer() {
		if (hasValidName() && hasValidIntroducedDate() && hasValidDiscontinuedDate() && hasValidIdCompany()) {
			try {
				mComputerCrudService.create(makeComputer());
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				// TODO à refaire
				throw new RuntimeException(pE);
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean editComputer() {		
		if (hasValidName() && hasValidIntroducedDate() && hasValidDiscontinuedDate() && hasValidIdCompany()) {
			try {
				mComputerCrudService.update(makeComputer());
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				// TODO à refaire
				throw new RuntimeException(pE);
			}
			return true;
		} else {
			return false;
		}
	}

}
