package com.excilys.mviegas.speccdb.validators;

import com.excilys.mviegas.speccdb.dto.ComputerDto;

/**
 * Validateur de {@link com.excilys.mviegas.speccdb.data.Computer}
 * @author VIEGAS Mickael
 */
public final class ComputerValidator {

	public static boolean isValidComputer(ComputerDto pComputerDto) {
		return pComputerDto != null &&
				ComputerNameValidator.isValidName(pComputerDto.getName()) &&
				ComputerDateValidator.isValidDate(pComputerDto.getIntroducedDate(), null) &&
				ComputerDateValidator.isValidDate(pComputerDto.getIntroducedDate(), null) &&
				CompanyIdValidator.isValidIdCompany(pComputerDto.getIdCompany())
		;
	}
}
