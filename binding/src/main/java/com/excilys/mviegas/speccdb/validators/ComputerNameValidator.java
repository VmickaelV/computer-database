package com.excilys.mviegas.speccdb.validators;

/**
 * Validateur de nom de {@link com.excilys.mviegas.speccdb.data.Computer}
 *
 * @author VIEGAS Mickael
 */
public final class ComputerNameValidator {
	public static boolean isValidName(String mName) {
		return (mName != null && !mName.isEmpty());
	}
}
