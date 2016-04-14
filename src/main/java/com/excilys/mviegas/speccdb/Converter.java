package com.excilys.mviegas.speccdb;

/**
 * Interface de création de convertisseur
 *
 * Created by excilys on 13/04/16.
 */
@SuppressWarnings({"UnnecessaryInterfaceModifier", "unused"})
public interface Converter<STRING, OBJECT> {
	public OBJECT getAsObject(STRING pValue);

	public STRING getAsString(OBJECT pValue);
}
