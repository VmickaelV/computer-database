package com.excilys.mviegas.speccdb.managers;

import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.persist.CrudService;
import com.excilys.mviegas.speccdb.persist.Paginator;
import com.excilys.mviegas.speccdb.persist.QueryParameter;
import com.excilys.mviegas.speccdb.persist.jdbc.ComputerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;

/**
 * Bean permettant de gérer une liste de Computeurs
 *
 * @author Mickael
 */
@ManagedBean
public class ListManagerBean {

	//===========================================================
	// Attributes static
	//===========================================================

	public static final Logger LOGGER = LoggerFactory.getLogger(ListManagerBean.class);
	
	public static final int DEFAULT_SIZE_PAGE = 10;

	//===========================================================
	// Attributes - privates
	//===========================================================
	
	private int mPage = 1;
	
	private int mSize = DEFAULT_SIZE_PAGE;
	
	private String mSearch;
	
	private Paginator<Computer> mPaginator;
	
	private CrudService<Computer> mComputerDao;
	
	//===========================================================
	// Constructeur
	//===========================================================

	public ListManagerBean() {
		init();
	}
	
	//===========================================================
	// Callback
	//===========================================================
	@PostConstruct
	public void init() {
		mPage = 1;
		mSize = DEFAULT_SIZE_PAGE;
		mComputerDao = ComputerDao.INSTANCE;
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

	//===========================================================
	// Méthodes - Object
	//===========================================================	

	@Override
	public String toString() {
		return "ListManagerBean{" +
				"mPage=" + mPage +
				", mSize=" + mSize +
				", mSearch='" + mSearch + '\'' +
				", mPaginator=" + mPaginator +
				", mComputerDao=" + mComputerDao +
				'}';
	}

	//===========================================================
	// Méthodes - Object
	//===========================================================
	public void update() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("ListManager#update");
			LOGGER.debug("");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(this.toString());
		}

		if (mPage == 0) {
			mPage = 1;
		}

		if (mSearch != null && !mSearch.isEmpty()) {
			QueryParameter parameter = QueryParameter.with(ComputerDao.Parameters.FILTER_NAME, mSearch);
			parameter.and(ComputerDao.Parameters.SIZE, mSize)
					.and(ComputerDao.Parameters.START, (mPage - 1)*mSize);
			try {
				mPaginator = mComputerDao.findWithNamedQueryWithPaginator(ComputerDao.NamedQueries.SEARCH, parameter.parameters());
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				// TODO à refaire
				throw new RuntimeException(pE);
			}
		} else {
			try {
				mPaginator = mComputerDao.findAllWithPaginator((mPage-1)*mSize, mSize);
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				// TODO à refaire
				throw new RuntimeException(pE);
			}
		}
	}

	public boolean delete(String pIntegers) {
		String[] indexes = pIntegers.split(",");

		// TODO à optimiser
		for (String index : indexes) {
			int i = Integer.parseInt(index);
			try {
				if (!mComputerDao.delete(i)) {
					return false;
				}
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				// TODO à refaire
				throw new RuntimeException(pE);
			}
		}
		return true;
	}
}