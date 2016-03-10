package com.excilys.mviegas.speccdb.ui.cligui;

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