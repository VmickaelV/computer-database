package com.excilys.mviegas.speccdb.spring.validators;

import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by excilys on 18/04/16.
 */
public class OrderComputerValidator implements Validator {
	@Override
	public boolean supports(Class<?> pClass) {
		return String.class.equals(pClass);
	}

	@Override
	public void validate(Object pO, Errors pErrors) {
		if (pO != null) {
			String order = (String) pO;

			if (ComputerDao.Order.from(order) == null) {
				pErrors.reject("Not a order value valide");
			}
		}
		// TODO To Implement
		throw new UnsupportedOperationException("com.excilys.mviegas.speccdb.spring.validators.OrderComputerValidator#validate not implemented yet.");
	}
}
