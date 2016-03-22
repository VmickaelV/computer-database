package com.excilys.mviegas.speccdb.persist;

import java.util.List;

/**
 * Regroupement d'informations utile pour une pagination.
 *
 * L'indexation des pages commence à 1 (car principalement utilisé pour de l'IU)
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
	public Paginator(int pStartIndex, int pElementsCount, int pElementsByPage, List<T> pValues) {
		super();
		if (pElementsByPage == 0) {
			pElementsByPage = -1;
		}
		mCurrentPage = pStartIndex/pElementsByPage;
		mCurrentPage++;

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

	//===========================================================
	// Méthodes - Object
	//===========================================================


	@Override
	public String toString() {
		return "Paginator{" +
				"mCurrentPage=" + mCurrentPage +
				", mElementsCount=" + mElementsCount +
				", mElementsByPage=" + mElementsByPage +
				", mValues=" + mValues +
				", mNbPages=" + mNbPages +
				'}';
	}
}
