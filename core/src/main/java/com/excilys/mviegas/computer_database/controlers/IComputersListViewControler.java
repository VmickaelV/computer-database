package com.excilys.mviegas.computer_database.controlers;

/**
 * Interface de Contrleur pour une liste d'ordinateurs.
 * 
 * @author Mickael Viegas
 */
public interface IComputersListViewControler {

	/**
	 * Lance la page suivante.
	 */
	void nextPage();
	
	/**
	 * Lance la Page Précédente.
	 */
	void previousPage();
	
	/**
	 * Impose l'affichage d'une page particulière.
	 * @param pPage Numéro de la page
	 */
	void setPage(int pPage);

	/**
	 * Fixe le nombre d'éléments à afficher par page.
	 * @param pSize Taille à fixer
	 */
	void setSize(int pSize);

	/**
	 * Demande d'affichage de la liste.
	 */
	void launch();

	/**
	 * Demande d'ordinateur à effacer.
	 */
	void deleteComputer();

	/**
	 * Demande de création d'ordinateur.
	 */
	void addComputer();

	/**
	 * Lance un recherche par id.
	 */
	void searchById();

}