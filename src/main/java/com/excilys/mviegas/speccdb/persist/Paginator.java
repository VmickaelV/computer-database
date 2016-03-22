package com.excilys.mviegas.speccdb.persist;

import java.util.List;

/**
 * Regroupement d'informations utile pour une pagination.
 *
 * Created by excilys on 15/03/16.
 */
public class Paginator<T> {

	//===========================================================
	// Attributes - private
	//===========================================================
	private int mCurrentPage;

	private int mElementsCount;

	private int mElementsByPage;

	private List<T> mValues;

	private int mNbPages;

	//===========================================================
	// Constructors
	//===========================================================
	public Paginator(int pCurrentPage, int pElementsCount, int pElementsByPage, List<T> pValues) {
		super();
		mCurrentPage = pCurrentPage;
		mElementsCount = pElementsCount;
		mElementsByPage = pElementsByPage;
		mValues = pValues;
		mNbPages = mElementsCount/mElementsByPage;
		if (mElementsCount % mElementsByPage != 0) {
			mNbPages++;
		}
	}

	//===========================================================
	// Getters
	//===========================================================
	public int getCurrentPage() {
		return mCurrentPage;
	}

	public int getElementsCount() {
		return mElementsCount;
	}

	public int getElementsByPage() {
		return mElementsByPage;
	}

	public List<T> getValues() {
		return mValues;
	}

	public int getNbPages() {
		return mNbPages;
	}

}
