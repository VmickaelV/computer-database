package com.excilys.mviegas.computer_database.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO de filtres de recherches d'ordinateurs
 *
 * Created by excilys on 22/04/16.
 */
public class FilterComputersDTO {

	//=============================================================
	// Attributes - private
	//=============================================================

	@Min(1)
	private int mPage = 1;

	@Min(0)
	private int mSize = 0;

	@NotNull
	@Size(min = 3)
	private String mSearch;

	private String mOrder;

	private String mTypeOrder;

	//=============================================================
	// Getters & Setters
	//=============================================================


	public int getPage() {
		return mPage;
	}

	public void setPage(int pPage) {
		mPage = pPage;
	}

	public int getSize() {
		return mSize;
	}

	public void setSize(int pSize) {
		mSize = pSize;
	}

	public String getSearch() {
		return mSearch;
	}

	public void setSearch(String pSearch) {
		mSearch = pSearch;
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
}
