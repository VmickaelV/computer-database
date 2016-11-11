package com.excilys.mviegas.computer_database.validators;

/**
 * Validateur de nom de {@link com.excilys.mviegas.computer_database.data.Computer}.
 *
 * @author VIEGAS Mickael
 */
public final class ComputerNameValidator {
	public static boolean isValidName(String mName) {
		return (mName != null && !mName.isEmpty());
	}
}
