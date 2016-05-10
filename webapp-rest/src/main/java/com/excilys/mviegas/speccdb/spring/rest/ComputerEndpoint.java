package com.excilys.mviegas.speccdb.spring.rest;

import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.exceptions.ResourceNotFound;
import com.excilys.mviegas.speccdb.services.ComputerService;
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
@RequestMapping("/computers")
public class ComputerEndpoint {

	public static final Logger LOGGER = LoggerFactory.getLogger(ComputerEndpoint.class);

	private final ComputerService mComputerService;

	@Autowired
	public ComputerEndpoint(ComputerService pComputerService) {
		mComputerService = pComputerService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Computer> findAll(@RequestParam(value = "start", defaultValue = "0") int pStart, @RequestParam(value = "size", defaultValue = "20") int pSize) throws DAOException {
		if (pSize > 50) {
			throw new IllegalArgumentException();
		}
		// TODO voir si on met uen erreur 400 en cas de nom de paramètre non évaluable

		return mComputerService.findAll(pStart, pSize);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Computer find(@PathVariable("id") int pId) throws DAOException {
		Computer computer = mComputerService.find(pId);
		System.out.println(computer);
		if (computer == null) {
			throw new ResourceNotFound();
		}
		return computer;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<?> test() {
		return new ResponseEntity<Object>("salut", HttpStatus.OK);
	}
}
