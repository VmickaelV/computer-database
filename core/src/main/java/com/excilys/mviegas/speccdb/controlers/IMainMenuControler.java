package com.excilys.mviegas.speccdb.controlers;

/**
 * Controleur gérant l'affichage au démarrage.
 * 
 * @author VIEGAS Mickael
 *
 */
public interface IMainMenuControler {
	
	/**
	 * Lancement de l'affichage principale.
	 */
	void launch();
	
	/**
	 * Affichage de la liste des ordinateurs.
	 */
	void seeListComputers();
	
	/**
	 * Demande d'ajout d'ordinateurs.
	 */
	void addComputer();

	/**
	 * Demande de fermeture du programme.
	 */
	void quit();

}
