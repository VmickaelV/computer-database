package com.excilys.mviegas.speccdb.managers;

import com.excilys.mviegas.speccdb.concurrency.ThreadLocals;
import com.excilys.mviegas.speccdb.controlers.IEditorComputerControler;
import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.ConnectionException;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persistence.ICrudService;
import com.excilys.mviegas.speccdb.persistence.jdbc.CompanyDao;
import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao;
import com.excilys.mviegas.speccdb.persistence.jdbc.DatabaseManager;
import com.excilys.mviegas.speccdb.ui.webapp.Message;
import com.excilys.mviegas.speccdb.ui.webapp.Message.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * Bean lié à la gestion d'un ordinateur
 *
 * @author Mickael
 */
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

	private List<Company> mCompanies;
	private ICrudService<Company> mCompanyCrudService;
	private ICrudService<Computer> mComputerCrudService;
	
	private List<Message> mMessages = new LinkedList<>();
	
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
				Connection connection = DatabaseManager.getConnection();
				ThreadLocals.CONNECTIONS.set(connection);
				
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
				
				ThreadLocals.CONNECTIONS.remove();
				DatabaseManager.releaseConnection(connection);
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException | ConnectionException pE) {
				mComputer = null;
				mMessages.add(new Message("Internal Error", "Interal Error", Level.ERROR));
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
		Connection connection = null;
		try {
			connection = DatabaseManager.getConnection();
			ThreadLocals.CONNECTIONS.set(connection);
			return mIdCompany == 0 || mCompanyCrudService.find(mIdCompany) != null;
		} catch (com.excilys.mviegas.speccdb.exceptions.DAOException | ConnectionException pE) {
			LOGGER.error(pE.getMessage(), pE);
			mMessages.add(new Message("Internal Error", "Interal Error", Level.ERROR));
			return false;
		} finally {
			if (connection !=  null) {
				ThreadLocals.CONNECTIONS.remove();
				try {
					DatabaseManager.releaseConnection(connection);
				} catch (ConnectionException e) {
					LOGGER.error(e.getMessage(), e);
					throw new RuntimeException(e);
				}
				
			}
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
		
		Connection connection = null;
		try {
			connection = DatabaseManager.getConnection();
			ThreadLocals.CONNECTIONS.set(connection);
			mCompanies = mCompanyCrudService.findAll(0, 0);
		} catch (com.excilys.mviegas.speccdb.exceptions.DAOException | ConnectionException pE) {
			LOGGER.error(pE.getMessage(), pE);
			throw new RuntimeException(pE);
		} finally {
			if (connection == null) {
				ThreadLocals.CONNECTIONS.remove();
				try {
					DatabaseManager.releaseConnection(connection);
				} catch (ConnectionException e) {
					LOGGER.error(e.getMessage(), e);
					throw new RuntimeException(e);
				}
			}
		}
	}

	//===========================================================
	// Méthodes Controleurs
	//===========================================================
	@Override
	public boolean addComputer() {
		if (isValidForm()) {
			try {
				Connection connection = DatabaseManager.getConnection();
				ThreadLocals.CONNECTIONS.set(connection);
				
				mComputerCrudService.create(makeComputer());
				
				ThreadLocals.CONNECTIONS.remove();
				DatabaseManager.releaseConnection(connection);
				
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException | ConnectionException pE) {
				mMessages.add(new Message("Internal Error", "Interal Error", Level.ERROR));
				return false;
			}
			mMessages.add(new Message("Computed Added", "Computer Added", Level.SUCCESS));
			return true;
		} else {
			mMessages.add(new Message("Invalid Formular", "You formular is Invalid.\nCheck the details", Level.ERROR));
			return false;
		}
	}

	@Override
	public boolean editComputer() {
		if (isValidForm()) {
			try {
				Connection connection = DatabaseManager.getConnection();
				ThreadLocals.CONNECTIONS.set(connection);
				
				mComputerCrudService.update(makeComputer());
				
				ThreadLocals.CONNECTIONS.remove();
				DatabaseManager.releaseConnection(connection);
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException | ConnectionException pE) {
				mMessages.add(new Message("Internal Error", "Interal Error", Level.ERROR));
				return false;
			}
			mMessages.add(new Message("Computed Edited", "Computer Edited", Level.SUCCESS));
			return true;
		} else {
			mMessages.add(new Message("Invalid Formular", "You formular is Invalid.\nCheck the details", Level.ERROR));
			return false;
		}
	}

}
