package com.excilys.mviegas.speccdb.validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author VIEGAS Mickael
 */
public final class ComputerDateValidator {
	public static boolean isValidDate(String pDate, DateTimeFormatter pDateTimeFormatter) {
		if (pDate == null || pDate.isEmpty()) {
			return true;
		} else {
			try {
				LocalDate.parse(pDate, pDateTimeFormatter);
				return true;
			} catch (DateTimeParseException e) {
				return false;
			}
		}
	}
}
