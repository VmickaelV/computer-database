package com.excilys.mviegas.computer_database.validators;

import com.excilys.mviegas.computer_database.dto.ComputerDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Validateur de {@link com.excilys.mviegas.computer_database.data.Computer}.
 *
 * @author VIEGAS Mickael
 */
public final class ComputerValidator {

	public static boolean isValidComputer(ComputerDto pComputerDto) {
		return isValidComputer(pComputerDto, Locale.ROOT);
	}

	public static boolean isValidComputer(ComputerDto pComputerDto, Locale pLocale) {
		return pComputerDto != null &&
				ComputerNameValidator.isValidName(pComputerDto.getName()) &&
				ComputerDateValidator.isValidDate(pComputerDto.getIntroducedDate(), DateTimeFormatter.ISO_DATE.withLocale(pLocale)) &&
				ComputerDateValidator.isValidDate(pComputerDto.getIntroducedDate(), DateTimeFormatter.ISO_DATE.withLocale(pLocale)) &&
                isCorrectDates(pComputerDto.getIntroducedDate(), pComputerDto.getDiscontinuedDate(), pLocale) &&
				CompanyIdValidator.isValidIdCompany(pComputerDto.getIdCompany());
	}

	private static boolean isCorrectDates(String from, String to, Locale locale) {
        if (from == null || from.isEmpty() || to == null || to.isEmpty()) {
            return true;
        }
        LocalDate fromDate = LocalDate.parse(from, DateTimeFormatter.ISO_DATE.withLocale(locale));
        LocalDate toDate = LocalDate.parse(to, DateTimeFormatter.ISO_DATE.withLocale(locale));
        return fromDate.isBefore(toDate);
	}
}
