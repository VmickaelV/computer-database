package com.excilys.mviegas.speccdb.ui.webapp;

/**
 * Représente un message à afficher sur la page Web
 * 
 * @author Mickael
 */
public class Message {
	
	//===========================================================
	// Attributs - private
	//===========================================================
	
	public final String title;
	
	public final String description;
	
	public final Level level;

	//===========================================================
	// Constructors
	//===========================================================
	
	public Message(String pTitle, String pDescription, Level pLevel) {
		super();
		title = pTitle;
		description = pDescription;
		level = pLevel;
	}
	
	//===========================================================
	// Getters & Setters
	//===========================================================
	
	public String getDescription() {
		return description;
	}

	public String getTitle() {
		return title;
	}

	public Level getLevel() {
		return level;
	}
	
	//===========================================================
	// Inner Class
	//===========================================================
	@SuppressWarnings("unused")
	public enum Level {
		DEBUG, INFO, WARN, ERROR, SUCCESS
	}

}
