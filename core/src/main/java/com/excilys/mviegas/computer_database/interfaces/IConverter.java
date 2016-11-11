package com.excilys.mviegas.computer_database.interfaces;

/**
 * Interface de création de convertisseur
 *
 * Created by excilys on 13/04/16.
 */
@SuppressWarnings({"UnnecessaryInterfaceModifier", "unused"})
public interface IConverter<STRING, OBJECT> {
	public OBJECT getAsObject(STRING pValue);

	public STRING getAsString(OBJECT pValue);
}
