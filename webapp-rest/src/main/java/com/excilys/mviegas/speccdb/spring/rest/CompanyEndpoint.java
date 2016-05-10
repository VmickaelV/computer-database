package com.excilys.mviegas.speccdb.spring.rest;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.exceptions.ResourceNotFound;
import com.excilys.mviegas.speccdb.services.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author VIEGAS Mickael
 */
@RestController
@RequestMapping("/companies")
public class CompanyEndpoint {

	public static final Logger LOGGER = LoggerFactory.getLogger(CompanyEndpoint.class);

	private final CompanyService mCompanyService;

	@Autowired
	public CompanyEndpoint(CompanyService pCompanyService) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("CompanyEndpoint.CompanyEndpoint");
			LOGGER.debug("pCompanyService = [" + pCompanyService + "]");
		}

		mCompanyService = pCompanyService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Company> findAll(@RequestParam(value = "start", defaultValue = "0") int pStart, @RequestParam(value = "size", defaultValue = "20") int pSize) throws DAOException {

		if (pSize > 50) {
			throw new IllegalArgumentException();
		}
		// TODO voir si on met uen erreur 400 en cas de nom de paramètre non évaluable

		return mCompanyService.findAll(pStart, pSize);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Company find(@PathVariable("id") int pId) throws DAOException {
		System.out.println("CompanyEndpoint.find");
		System.out.println("pId = [" + pId + "]");
		Company company = mCompanyService.find(pId);
		System.out.println(company);
		if (company == null) {
			throw new ResourceNotFound();
		}
		return company;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<?> test() {
		return new ResponseEntity<Object>("salut", HttpStatus.OK);
	}
}
