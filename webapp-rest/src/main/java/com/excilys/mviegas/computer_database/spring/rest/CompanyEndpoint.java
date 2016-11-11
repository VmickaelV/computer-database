package com.excilys.mviegas.computer_database.spring.rest;

import com.excilys.mviegas.computer_database.data.Company;
import com.excilys.mviegas.computer_database.exceptions.DAOException;
import com.excilys.mviegas.computer_database.exceptions.ResourceNotFound;
import com.excilys.mviegas.computer_database.services.CompanyService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoint de resources de {@link Company}.
 *
 * @author VIEGAS Mickael
 */
@RestController
@RequestMapping("/companies")
public class CompanyEndpoint {

	@SuppressWarnings("WeakerAccess")
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
	@ApiOperation(value = "Récupère une liste d'entreprises - findall")
	public List<Company> findAll(@RequestParam(value = "start", defaultValue = "0") int start,
								 @RequestParam(value = "size", defaultValue = "0") int size) throws DAOException {

		if (size > 50) {
			throw new IllegalArgumentException();
		}
		// TODO voir si on met uen erreur 400 en cas de nom de paramètre non évaluable

		return mCompanyService.findAll(start, size);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Récupère un ordinateur particulier - find")
	public ResponseEntity<Company> find(@PathVariable("id") int pId) throws DAOException {
		System.out.println("CompanyEndpoint.find");
		System.out.println("pId = [" + pId + "]");
		Company company = mCompanyService.find(pId);
		System.out.println(company);
		if (company == null) {
			throw new ResourceNotFound();
		}
		return new ResponseEntity<>(company, HttpStatus.OK);
	}
}
