package com.excilys.mviegas.computer_database.validators;

import com.excilys.mviegas.computer_database.dto.ComputerDto;

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
				CompanyIdValidator.isValidIdCompany(pComputerDto.getIdCompany());
	}
}
