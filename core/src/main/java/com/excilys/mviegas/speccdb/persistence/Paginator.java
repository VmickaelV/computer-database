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
	public int currentPage;

	/**
	 * Nombre d'éléments totales
	 */
	public int elementsCount;

	/**
	 * Nom d'éléments par pages
	 */
	public int elementsByPage;

	/**
	 * Valeurs de la pages
	 */
	public List<T> values;

	/**
	 * Nombre de pages
	 */
	public int nbPages;

	//===========================================================
	// Constructors
	//===========================================================

	/**
	 * (For JsonMapper)
	 */
	public Paginator() {
	}

	/**
	 * Pseudo constructeur par copie (ne copie pas les valeurs du tableaux)
	 *
	 * @param pPaginator Paginator à copier
	 */
	public Paginator(Paginator<?> pPaginator) {
		currentPage = pPaginator.currentPage;
		elementsCount = pPaginator.elementsCount;
		elementsByPage = pPaginator.elementsByPage;
		nbPages = pPaginator.nbPages;
	}

	/**
	 * Constructeur avec paramètres
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
		currentPage = pStartIndex/pElementsByPage;
		currentPage++;

		elementsCount = pElementsCount;
		elementsByPage = pElementsByPage;
		values = pValues;
		nbPages = elementsCount / elementsByPage;
		if (elementsCount % elementsByPage != 0) {
			nbPages++;
		}
	}

	//=============================================================
	// Getters & Setters
	//=============================================================
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int pCurrentPage) {
		currentPage = pCurrentPage;
	}

	public int getElementsCount() {
		return elementsCount;
	}

	public void setElementsCount(int pElementsCount) {
		elementsCount = pElementsCount;
	}

	public int getElementsByPage() {
		return elementsByPage;
	}

	public void setElementsByPage(int pElementsByPage) {
		elementsByPage = pElementsByPage;
	}

	public List<T> getValues() {
		return values;
	}

	public void setValues(List<T> pValues) {
		values = pValues;
	}

	public int getNbPages() {
		return nbPages;
	}

	public void setNbPages(int pNbPages) {
		nbPages = pNbPages;
	}

	//===========================================================
	// Méthodes - Object
	//===========================================================
	@Override
	public String toString() {
		//noinspection StringBufferReplaceableByString
		final StringBuilder sb = new StringBuilder("Paginator{");
		sb.append("currentPage=").append(currentPage);
		sb.append(", elementsCount=").append(elementsCount);
		sb.append(", elementsByPage=").append(elementsByPage);
		sb.append(", values=").append(values);
		sb.append(", nbPages=").append(nbPages);
		sb.append('}');
		return sb.toString();
	}
}
