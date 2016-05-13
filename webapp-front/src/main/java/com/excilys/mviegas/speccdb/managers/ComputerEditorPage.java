package com.excilys.mviegas.speccdb.managers;

import com.excilys.mviegas.speccdb.controlers.IEditorComputerControler;
import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.services.CompanyService;
import com.excilys.mviegas.speccdb.services.ComputerService;
import com.excilys.mviegas.speccdb.spring.Message;
import com.excilys.mviegas.speccdb.spring.Message.Level;
import com.excilys.mviegas.speccdb.spring.singletons.ListOfCompanies;
import com.excilys.mviegas.speccdb.validators.CompanyIdValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.excilys.mviegas.speccdb.validators.ComputerDateValidator.isValidDate;
import static com.excilys.mviegas.speccdb.validators.ComputerNameValidator.isValidName;

/**
 * Bean lié à la page de gestion d'un ordinateur.
 *
 * @author Mickael
 */
@SuppressWarnings("WeakerAccess")
@Component
public class ComputerEditorPage implements IEditorComputerControler {

	//=============================================================
	// Constantes
	//=============================================================
	public static final Logger LOGGER = LoggerFactory.getLogger(ComputerEditorPage.class);
	public static final DateTimeFormatter FORMATTER_EN = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	public static final DateTimeFormatter FORMATTER_FR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	//===========================================================
	// Attribute - private
	//===========================================================
	private String mName;
	private String mIntroducedDate;
	private String mDiscontinuedDate;
	private long mIdCompany;

	@Min(0)
	private long mId;

	private String mAction;

	private Computer mComputer;

	@Autowired
	private ListOfCompanies mListOfCompanies;

	@Autowired
	private CompanyService mCompanyService;

	@Autowired
	private ComputerService mComputerService;

	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private List<Message> mMessages = new LinkedList<>();

	private Locale mLocale = Locale.ENGLISH;

	//===========================================================
	// Constructeurs
	//===========================================================
	public ComputerEditorPage() {
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
		return mListOfCompanies;
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
	public long getIdCompany() {
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

	public CompanyService getCompanyService() {
		return mCompanyService;
	}

	public void setCompanyService(CompanyService pCompanyService) {
		mCompanyService = pCompanyService;
	}

	public ComputerService getComputerService() {
		return mComputerService;
	}

	public void setComputerService(ComputerService pComputerService) {
		mComputerService = pComputerService;
	}

	public List<Message> getMessages() {
		return mMessages;
	}

	public void setMessages(List<Message> pMessages) {
		mMessages = pMessages;
	}

	public Locale getLocale() {
		return mLocale;
	}

	public void setLocale(Locale pLocale) {
		mLocale = pLocale;
	}

	//===========================================================
	// Functions
	//===========================================================
	@Override
	public boolean isEditing() {
		return mId > 0;
	}

	public boolean hasValidName() {
		return isValidName(mName);
	}

	public boolean hasValidIntroducedDate() {
		return isValidDate(mIntroducedDate, getFormatter());
	}

	public boolean hasValidDiscontinuedDate() {
		return isValidDate(mDiscontinuedDate, getFormatter());
	}

	public boolean hasValidIdCompany() {
		return CompanyIdValidator.isValidIdCompany(mIdCompany);
	}

	public boolean isValidForm() {
//		TODO revoir la condition de la variable mAction
		return (mAction != null) && isValidName(mName) && hasValidIntroducedDate() && hasValidDiscontinuedDate() && CompanyIdValidator.isValidIdCompany(mIdCompany);
	}

	private Computer makeComputer() {
		try {
			if (mComputer == null) {
				return new Computer.Builder()
						.setName(mName)
						.setIntroducedDate(mIntroducedDate == null || mIntroducedDate.isEmpty() ? null : LocalDate.parse(mIntroducedDate, getFormatter()))
						.setDiscontinuedDate(mDiscontinuedDate == null || mDiscontinuedDate.isEmpty() ? null : LocalDate.parse(mDiscontinuedDate, getFormatter()))
						.setManufacturer(mCompanyService.find(mIdCompany)).build();
			} else {
				mComputer.setName(mName);
				mComputer.setIntroducedDate(mIntroducedDate == null || mIntroducedDate.isEmpty() ? null : LocalDate.parse(mIntroducedDate, getFormatter()));
				mComputer.setDiscontinuedDate(mDiscontinuedDate == null || mDiscontinuedDate.isEmpty() ? null : LocalDate.parse(mDiscontinuedDate, getFormatter()));
				mComputer.setManufacturer(mCompanyService.find(mIdCompany));
				
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
	public void init() {

	}

	public void reset() {
		mAction = null;
		mComputer = null;
		mDiscontinuedDate = null;
		mId = 0;
		mIdCompany = 0;
		mIntroducedDate = null;
		mMessages.clear();
		mName = null;
	}

	/**
	 * Action spécialie pour télécharger des données après l'assignation des différents services.
	 */
	public void refresh(boolean isEditing) {
		if (mId > 0) {
			try {
				mComputer = mComputerService.find(mId);

				if (!isEditing) {
					mName = mComputer.getName();
					if (mComputer.getIntroducedDate() != null) {
						mIntroducedDate = mComputer.getIntroducedDate().format(getFormatter());
					}
					if (mComputer.getDiscontinuedDate() != null) {
						mDiscontinuedDate = mComputer.getDiscontinuedDate().format(getFormatter());
					}
					if (mComputer.getManufacturer() != null) {
						mIdCompany = mComputer.getManufacturer().getId();
					}
				}
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				mComputer = null;
				mMessages.add(new Message("Internal Error", "Interal Error", Level.ERROR));
			}
		} else {
			mComputer = null;
		}
	}

	public void refresh() {
		refresh(false);
	}

	//===========================================================
	// Méthodes Controleurs
	//===========================================================
	@Override
	public boolean addComputer() {
		if (isValidForm()) {
			try {
				mComputerService.create(makeComputer());
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
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
				mComputerService.update(makeComputer());
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
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

	/**
	 * Map une map à la liste des attributs du bean.
	 *
	 * @param map M
	 */
	public void map(Map<String, String> map) {
		if (map.containsKey("id")) {
			setId(Integer.parseInt(map.get("id")));
		}

		if (map.containsKey("name")) {
			mName = map.get("name");
		}

		if (map.containsKey("companyId")) {
			mIdCompany = Integer.parseInt(map.get("companyId"));
		}

		if (map.containsKey("introducedDate")) {
			mIntroducedDate = map.get("introducedDate");
		}

		if (map.containsKey("discontinuedDate")) {
			mDiscontinuedDate = map.get("discontinuedDate");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("this = " + this);
		}
	}

	public DateTimeFormatter getFormatter() {
		System.out.println("ComputerEditorPage.getFormatter");
		System.out.println("mLocale = " + mLocale.getLanguage());
		System.out.println("mLocale = " + mLocale.getCountry());
		// TODO trouver une autre façon
		switch (mLocale.getLanguage()) {
			case "fr":
				return FORMATTER_FR;
			case "en":
				return FORMATTER_EN;

			default:
				return DateTimeFormatter.ISO_DATE;
		}
	}
}
