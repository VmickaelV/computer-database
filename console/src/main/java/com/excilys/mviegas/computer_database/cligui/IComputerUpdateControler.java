package com.excilys.mviegas.computer_database.cligui;

public interface IComputerUpdateControler {

	/**
	 * Lance l'édition
	 */
	void launch();
	
	/**
	 * Demande l'annulation
	 */
	void cancel();
	
	/**
	 * Valide les changements
	 */
	void valid();

}