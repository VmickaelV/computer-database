package com.excilys.mviegas.speccdb.managers;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;

import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.persist.CrudService;
import com.excilys.mviegas.speccdb.persist.jdbc.ComputerDao;

@ManagedBean
public class ListManager {
	
	public static final int DEFAULT_SIZE_PAGE = 10;

	//===========================================================
	// Attributes - privates
	//===========================================================
	
	private String mFilter;
	
	private int mPage;
	private int mSize;
	
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
		mPage = 0;
		mSize = DEFAULT_SIZE_PAGE;
		mComputerDao = ComputerDao.INSTANCE;
		setPage(mPage);
	}
	
	//===========================================================
	// Méthodes controleurs
	//===========================================================
	
	public int nbComputers() {
		return mComputerDao.size();
	}
	
	public int getPage() {
		return mPage;
	}

	public void setPage(int pPage) {
		mPage = pPage;
		mDisplayedComputers = mComputerDao.findAll(mPage*mSize, mSize);
	}

	public int getSize() {
		return mSize;
	}

	public void setSize(int pSize) {
		C.LOGGER.RUNTIME.info("setSize");
		mSize = pSize - 1;
		mDisplayedComputers = mComputerDao.findAll(mPage*mSize, mSize);
	}

	public String getFilter() {
		return mFilter;
	}

	public void setFilter(String pFilter) {
		mFilter = pFilter;
		mDisplayedComputers = mComputerDao.findAll(mPage*mSize, mSize);
	}

	public List<Computer> getDisplayedComputers() {
		return mDisplayedComputers;
	}
	
	
	
	
}
