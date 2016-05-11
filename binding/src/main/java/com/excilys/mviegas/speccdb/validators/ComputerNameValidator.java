package com.excilys.mviegas.speccdb.validators;

/**
 * @author VIEGAS Mickael
 */
public final class ComputerNameValidator {
	public static boolean isValidName(String mName) {
		return (mName != null && !mName.isEmpty());
	}
}
