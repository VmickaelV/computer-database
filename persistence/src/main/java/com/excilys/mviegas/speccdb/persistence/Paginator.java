package com.excilys.mviegas.speccdb.persistence;

import java.util.List;

/**
 * Regroupement d'informations utile pour une pagination.
 *
 * L'indexation des pages commence à 1 (car principalement utilisé pour de l'IU)
 *
 * Created by excilys on 15/03/16.
 * @author Mickael
 */
public class Paginator<T> {

	//===========================================================
	// Attributes - private
	//===========================================================
	/**
	 * Numéro de la page courante
	 */
	private int mCurrentPage;

	/**
	 * Nombre d'éléments totales
	 */
	private int mElementsCount;

	/**
	 * Nom d'éléments par pages
	 */
	private int mElementsByPage;

	/**
	 * Valeurs de la pages
	 */
	private List<T> mValues;

	/**
	 * Nombre de pages
	 */
	private int mNbPages;

	//===========================================================
	// Constructors
	//===========================================================
	/**
	 *
	 * @param pStartIndex Début de l'index de début
	 * @param pElementsCount Nombre d'éléments totales
	 * @param pElementsByPage Nombre d'éléments par page
	 * @param pValues Valeurs de la page actuelle
	 */
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
		//noinspection StringBufferReplaceableByString
		final StringBuilder sb = new StringBuilder("Paginator{");
		sb.append("mCurrentPage=").append(mCurrentPage);
		sb.append(", mElementsCount=").append(mElementsCount);
		sb.append(", mElementsByPage=").append(mElementsByPage);
		sb.append(", mValues=").append(mValues);
		sb.append(", mNbPages=").append(mNbPages);
		sb.append('}');
		return sb.toString();
	}
}
