package com.excilys.mviegas.speccdb.managers;

import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.persist.CrudService;
import com.excilys.mviegas.speccdb.persist.jdbc.ComputerDao;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

import java.util.List;

@ManagedBean
public class ListManager {
	
	public static final Logger LOGGER = Logger.getLogger(ListManager.class);
	
	public static final int DEFAULT_SIZE_PAGE = 10;

	//===========================================================
	// Attributes - privates
	//===========================================================
	
	private String mFilter;
	
	private int mPage = 1;
	
	private int mSize = DEFAULT_SIZE_PAGE;
	
	private List<Computer> mDisplayedComputers;
	
	private CrudService<Computer> mComputerDao;
	
	//===========================================================
	// Constructeur
	//===========================================================
	public ListManager() {
		init();
	}
	
	//===========================================================
	// Callback
	//===========================================================
	@PostConstruct
	public void init() {
		mFilter = "";
		mPage = 1;
		mSize = DEFAULT_SIZE_PAGE;
		mComputerDao = ComputerDao.INSTANCE;
	}
	
	//===========================================================
	// Méthodes controleurs
	//===========================================================
	
	public int getNbComputers() {
		return mComputerDao.size();
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
	
	public void update() {
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(this);
		}
		
		if (mPage == 0) {
			mPage = 1;
		}
		
		
		mDisplayedComputers = mComputerDao.findAll((mPage-1)*mSize, mSize);
	}

	public String getFilter() {
		return mFilter;
	}

	public void setFilter(String pFilter) {
		mFilter = pFilter;
//		mDisplayedComputers = mComputerDao.findAll(mPage*mSize, mSize);
	}

	public List<Computer> getDisplayedComputers() {
		return mDisplayedComputers;
	}

	//===========================================================
	// Méthodes - Object
	//===========================================================	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ListManager [mFilter=");
		builder.append(mFilter);
		builder.append(", mPage=");
		builder.append(mPage);
		builder.append(", mSize=");
		builder.append(mSize);
		builder.append(", mDisplayedComputers=");
		builder.append(mDisplayedComputers);
		builder.append(", mComputerDao=");
		builder.append(mComputerDao);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
}
