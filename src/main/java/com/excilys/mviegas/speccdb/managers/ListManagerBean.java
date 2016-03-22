package com.excilys.mviegas.speccdb.managers;

import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persist.CrudService;
import com.excilys.mviegas.speccdb.persist.QueryParameter;
import com.excilys.mviegas.speccdb.persist.jdbc.ComputerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import java.util.List;

@ManagedBean
public class ListManagerBean {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ListManagerBean.class);
	
	public static final int DEFAULT_SIZE_PAGE = 10;

	//===========================================================
	// Attributes - privates
	//===========================================================
	
	private int mPage = 1;
	
	private int mSize = DEFAULT_SIZE_PAGE;
	
	private String mSearch;
	
	private List<Computer> mDisplayedComputers;
	
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
	
	public int getNbComputers() {
		try {
			return mComputerDao.size();
		} catch (DAOException pE) {
			// TODO a modifier
			throw new RuntimeException(pE);
		}
	}
	
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
				mDisplayedComputers = mComputerDao.findWithNamedQuery(ComputerDao.NamedQueries.SEARCH, parameter.parameters());
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				// TODO à refaire
				throw new RuntimeException(pE);
			}
		} else {
			try {
				mDisplayedComputers = mComputerDao.findAll((mPage-1)*mSize, mSize);
			} catch (com.excilys.mviegas.speccdb.exceptions.DAOException pE) {
				// TODO à refaire
				throw new RuntimeException(pE);
			}
		}
	}

	public List<Computer> getDisplayedComputers() {
		return mDisplayedComputers;
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
	//===========================================================
	// Méthodes - Object
	//===========================================================	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ListManager [mPage=");
		builder.append(mPage);
		builder.append(", mSize=");
		builder.append(mSize);
		builder.append(", mSearch=");
		builder.append(mSearch);
		builder.append(", mDisplayedComputers=");
		builder.append(mDisplayedComputers);
		builder.append(", mComputerDao=");
		builder.append(mComputerDao);
		builder.append("]");
		return builder.toString();
	}

	
	
	
	
	
}
