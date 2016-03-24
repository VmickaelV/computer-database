package com.excilys.mviegas.speccdb.managers;

import com.excilys.mviegas.speccdb.controlers.IEditorComputerControler;
import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persist.CrudService;
import com.excilys.mviegas.speccdb.persist.jdbc.CompanyDao;
import com.excilys.mviegas.speccdb.persist.jdbc.ComputerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
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
public class ComputerEditorBean implements IEditorComputerControler {

	//=============================================================
	// Constantes
	//=============================================================
	public static final Logger LOGGER = LoggerFactory.getLogger(ComputerEditorBean.class);
	public static final String PATTERN_DATE = "dd/MM/yyyy";
	public static final DateTimeFormatter sDateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_DATE);
	
	//===========================================================
	// Attribute - private
	//===========================================================
	private String mName;
	private String mIntroducedDate;
	private String mDiscontinuedDate;
	private int mIdCompany;
	private long mId;

	private String mAction;

	private Computer mComputer;

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
	@Override
	public String getName() {
		return mName;
	}

	@Override
	public List<Company> getCompanies() {
		return mCompanies;
	}

	@Override
	public void setName(String pName) {
		mName = pName;
	}

	@Override
	public String getIntroducedDate() {
		return mIntroducedDate;
	}

	@Override
	public void setIntroducedDate(String pIntroducedDate) {
		mIntroducedDate = pIntroducedDate;
	}

	@Override
	public String getDiscontinuedDate() {
		return mDiscontinuedDate;
	}

	@Override
	public void setDiscontinuedDate(String pDiscontinuedDate) {
		mDiscontinuedDate = pDiscontinuedDate;
	}

	@Override
	public int getIdCompany() {
		return mIdCompany;
	}

	@Override
	public void setIdCompany(int pIdCompany) {
		mIdCompany = pIdCompany;
	}

	@Override
	public long getId() {
		return mId;
	}

	@Override
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

	@Override
	public Computer getComputer() {
		return mComputer;
	}

	public void setComputer(Computer pComputer) {
		mComputer = pComputer;
	}
	
	@Override
	public String getAction() {
		return mAction;
	}

	@Override
	public void setAction(String pAction) {
		mAction = pAction;
	}

	//===========================================================
	// Functions
	//===========================================================
	@Override
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

	public boolean isValidForm() {
		return hasValidName() && hasValidIntroducedDate() && hasValidDiscontinuedDate() && hasValidIdCompany();
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
			LOGGER.error(pE.getMessage(), pE);
			throw new IllegalStateException("Erreur non attendu", pE);
		} catch (DAOException pE) {
			throw new RuntimeException(pE);
		}
	}

	// ============================================================
	//	Méthodes - Callback
	// ============================================================
	@Override
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

	//===========================================================
	// Méthodes Controleurs
	//===========================================================
	@Override
	public boolean addComputer() {
		if (isValidForm()) {
			try {
				mComputerCrudService.create(makeComputer());
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean editComputer() {
		if (isValidForm()) {
			try {
				mComputerCrudService.update(makeComputer());
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

}
