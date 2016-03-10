package com.excilys.mviegas.speccdb.controlers;

import com.excilys.mviegas.speccdb.data.Computer;

/**
 * Controleur pour gérer le détail d'un ordinteur
 * @author excilys
 *
 */
public interface IComputerDetailControler {

	/**
	 * demande d'effacement de l'ordinateur en cour
	 */
	void delete();

	/**
	 * demande de modification général de 
	 */
	void update();

	/**
	 * Quitte le détail de l'ordinateur
	 */
	void quit();

	/**
	 * Lance le détail de l'ordinateur
	 */
	void launch();
	
	/**
	 * @return Ordinateur que l'on voit en détail
	 */
	Computer getComputer();

}