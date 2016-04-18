package com.excilys.mviegas.speccdb.managers;

import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.persistence.Paginator;
import com.excilys.mviegas.speccdb.persistence.QueryParameter;
import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao;
import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao.Order;
import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao.TypeOrder;
import com.excilys.mviegas.speccdb.ui.webapp.Message;
import com.excilys.mviegas.speccdb.ui.webapp.Message.Level;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Bean permettant de gérer une liste de Computeurs
 *
 * @author Mickael
 */
@Component
@org.springframework.context.annotation.Scope("prototype")
public class DashboardManagerBean {

	//===========================================================
	// Attributes static
	//===========================================================

	public static final Logger LOGGER = LoggerFactory.getLogger(DashboardManagerBean.class);
	
	public static final int DEFAULT_SIZE_PAGE = 10;

	//===========================================================
	// Attributes - privates
	//===========================================================


	@Min(1)
	private int mPage = 1;

	@Min(0)
	private int mSize = DEFAULT_SIZE_PAGE;

	@NotNull
	@NotEmpty
	@Size(min = 3)
	private String mSearch;
	
	private Paginator<Computer> mPaginator;

	private String mOrder;

	private String mTypeOrder;
	
	private List<Message> mMessages = new LinkedList<>();

//	private Connection mConnection;

	private String mSelection;

	@Autowired
	private ComputerDao mComputerDao;

	//===========================================================
	// Constructeur
	//===========================================================

	public DashboardManagerBean() {
		init();
	}

//	public DashboardManagerBean(HttpServletRequest pHttpServletRequest) {
//		this();
//
//		Map<String, String[]> map = pHttpServletRequest.getParameterMap();
//
//		map(map);
//	}

	//===========================================================
	// Callback
	//===========================================================
	@PostConstruct
	public void init() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("initialisation");
			LOGGER.debug("this = " + this);
		}

		reset();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("this.hashCode() = " + this.hashCode());
		}

	}

	public void reset() {
		mPage = 1;
		mSize = DEFAULT_SIZE_PAGE;
		mSearch = null;
		mOrder = null;
		mTypeOrder = null;
		mPaginator = null;
		mMessages.clear();
	}
	//===========================================================
	// Méthodes controleurs
	//===========================================================

	public int getPage() {
		return mPage;
	}

	public void setPage(int pPage) {
		mPage = pPage;
//		mDisplayedComputers = mComputerDao.findAll(mPage*mSize, mSize);
	}

	public int getSize() {
		return mSize;
	}

	public void setSize(int pSize) {
		mSize = pSize;
//		mDisplayedComputers = mComputerDao.findAll(mPage*mSize, mSize);
	}
	
	public String getSearch() {
		return mSearch;
	}

	public void setSearch(String pSearch) {
		mSearch = pSearch;
	}

	public Paginator<Computer> getPaginator() {
		return mPaginator;
	}

	public String getOrder() {
		return mOrder;
	}

	public void setOrder(String pOrder) {
		mOrder = pOrder;
	}

	public String getTypeOrder() {
		return mTypeOrder;
	}

	public void setTypeOrder(String pTypeOrder) {
		mTypeOrder = pTypeOrder;
	}

	public String getSelection() {
		return mSelection;
	}

	public void setSelection(String pSelection) {
		mSelection = pSelection;
	}

	public List<Message> getMessages() {
		return mMessages;
	}

	public void setComputerDao(ComputerDao pComputerDao) {
		mComputerDao = pComputerDao;
	}

	//===========================================================
	// Méthodes - Object
	//===========================================================	

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("DashboardManagerBean{");
		sb.append("mPage=").append(mPage);
		sb.append(", mSize=").append(mSize);
		sb.append(", mSearch='").append(mSearch).append('\'');
		sb.append(", mPaginator=").append(mPaginator);
		sb.append(", mComputerDao=").append(mComputerDao);
		sb.append(", mOrder='").append(mOrder).append('\'');
		sb.append(", mTypeOrder='").append(mTypeOrder).append('\'');
		sb.append(", mMessages=").append(mMessages);
		sb.append('}');
		return sb.toString();
	}

	//===========================================================
	// Méthodes - Object
	//===========================================================
	public void update() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("DashboardManagerBean.update");
			LOGGER.debug("");
			LOGGER.debug(String.valueOf(this.hashCode()));
			LOGGER.debug(String.valueOf(this));
		}
		
		if (mPage == 0) {
			mPage = 1;
		}
		
//		try {
//			connection = DatabaseManager.getConnection();
//
//		} catch (ConnectionException e) {
//			mMessages.add(new Message("Internal Error", "We got an internal Error .\nPlease, retry later.", Level.ERROR));
//			return;
//		}

		if ((mSearch != null && !mSearch.isEmpty()) || (mOrder != null && !mOrder.isEmpty())) {
			QueryParameter parameter = QueryParameter.with(ComputerDao.Parameters.FILTER_NAME, mSearch);
			parameter
					.and(ComputerDao.Parameters.SIZE, mSize)
					.and(ComputerDao.Parameters.START, (mPage - 1)*mSize)
					.and(ComputerDao.Parameters.ORDER, Order.from(mOrder))
					.and(ComputerDao.Parameters.TYPE_ORDER, TypeOrder.from(mTypeOrder))
			;

			try {
				mPaginator = mComputerDao.findWithNamedQueryWithPaginator(ComputerDao.NamedQueries.SEARCH, parameter.parameters());
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				LOGGER.error(pE.getMessage(), pE);
				mMessages.add(new Message("Internal Error", "We have an Eror with the Database.\nRetrieve later", Level.ERROR));
			}
		} else {
			try {
				mPaginator = mComputerDao.findAllWithPaginator((mPage-1)*mSize, mSize);
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				LOGGER.error(pE.getMessage(), pE);
				mMessages.add(new Message("Internal Error", "We have an Eror with the Database.\nRetrieve later", Level.ERROR));
			}
		}
		
//		mMessages.add(new Message("Successful Update", "Successful Update", Level.SUCCESS));

//		try {
//			DatabaseManager.releaseConnection(connection);
//		} catch (ConnectionException e) {
//			LOGGER.error(e.getMessage(), e);
//			mMessages.add(new Message("Internal Error", "We have an Eror with the Database.\nRetrieve later", Level.ERROR));
//		}
	}

	public boolean delete() {
		return delete(mSelection);
	}

	public boolean delete(String pIntegers) {
		String[] indexes = pIntegers.split(",");
		
		boolean result = true;
		
		// TODO à optimiser
		for (String index : indexes) {
			int i = Integer.parseInt(index);
			try {
				if (!mComputerDao.delete(i)) {
					mMessages.add(new Message("Internal Error", "We have an Eror with the Database when we tried to delete the computer n°"+index+".\nWould you retry later.", Level.ERROR));
					result = false;
				}
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				LOGGER.error(pE.getMessage(), pE);
				mMessages.add(new Message("Internal Error", "We have an Eror with the Database when we tried to delete the computer n°"+index+".\nWould you retry later.", Level.ERROR));
				result = false;
			}
		}
		
		mMessages.add(new Message("Successful Deletion", "All selectede computers are deleted from the Database.", Level.SUCCESS));
		return result;
	}

	public void map(Map<String, String> map) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("map = " + map);
			LOGGER.debug("this = " + this);

		}

		if (map.containsKey("page")) {
			mPage = Integer.parseInt(map.get("page"));
		}

		if (map.containsKey("size")) {
			mSize = Integer.parseInt(map.get("size"));
		}

		if (map.containsKey("search")) {
			mSearch = map.get("search");
		}

		if (map.containsKey("order")) {
			mOrder = map.get("order");
		}

		if (map.containsKey("typeOrder")) {
			mTypeOrder = map.get("typeOrder");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("this = " + this);
		}
	}
}
