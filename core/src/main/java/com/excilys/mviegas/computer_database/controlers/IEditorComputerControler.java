package com.excilys.mviegas.computer_database.controlers;

import com.excilys.mviegas.computer_database.data.Company;
import com.excilys.mviegas.computer_database.data.Computer;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Interface d'un controleur d'éditeur de {@link Computer} en édition/ajout
 *
 * Created by Mickael on 23/03/16.
 * @author Mickael
 */
public interface IEditorComputerControler {

	//===========================================================
	// Getters & Setters
	//===========================================================

	/**
	 * @return Nom écrit
	 */
	String getName();

	/**
	 * @return Liste des compagnies à choisir
	 */
	List<Company> getCompanies();

	/**
	 * @param pName Nom de l'ordinateur à fixer
	 */
	void setName(String pName);

	/**
	 * @return Date d'introduction saisie
	 */
	String getIntroducedDate();

	/**
	 * @param pIntroducedDate fixe la date d'introduction de l'ordinateur
	 */
	void setIntroducedDate(String pIntroducedDate);

	/**
	 * @return Date de fin de distribution de l'ordinateur
	 */
	String getDiscontinuedDate();

	/**
	 * @param pDiscontinuedDate fixe la date de fin de distribution de l'ordinateur
	 */
	void setDiscontinuedDate(String pDiscontinuedDate);

	/**
	 * @return Entreprise fabricant l'ordinateur
	 */
	long getIdCompany();

	/**
	 * @param pIdCompany Fixe l'entreprise fabricant l'ordinateur
	 */
	void setIdCompany(int pIdCompany);

	/**
	 * @return Id de l'ordinateur (si en mode édition)
	 */
	long getId();

	/**
	 * @param pId fixe l'Id de l'ordinateur à éditer
	 */
	void setId(long pId);

	/**
	 * @return ordinateur à Editer
	 */
	Computer getComputer();

	/**
	 * @return Action à faire choisi par l'utilisateur
	 */
	String getAction();

	/**
	 * @param pAction Fixe l'action faites par l'utilisateur
	 */
	void setAction(String pAction);

	//===========================================================
	// Functions
	//===========================================================

	/**
	 * @return Vrai si édite un ordinateur, faux si ajoute un ordinateur
	 */
	boolean isEditing();

	// ============================================================
	//	Méthodes - Callback
	// ============================================================
	@PostConstruct
	/**
	 * Initialise le controleur
	 */
	void init();

	//===========================================================
	// Méthodes Controleurs
	//===========================================================

	/**
	 * Demande d'ajout d'ordinateur
	 * @return Vrai si réussi
	 */
	boolean addComputer();

	/**
	 * Demande la sauvegarde effective des modifications faites
	 * @return Vrai si réussi
	 */
	boolean editComputer();
}
