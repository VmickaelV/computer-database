package com.excilys.mviegas.speccdb.spring.rest;

import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.dto.ComputerDto;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.exceptions.ResourceNotFound;
import com.excilys.mviegas.speccdb.persistence.ICompanyDao;
import com.excilys.mviegas.speccdb.persistence.Paginator;
import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao;
import com.excilys.mviegas.speccdb.services.ComputerService;
import com.excilys.mviegas.speccdb.validators.ComputerValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;

/**
 * @author VIEGAS Mickael
 */
@RestController
@RequestMapping("/computers")
public class ComputerEndpoint {

	public static final Logger LOGGER = LoggerFactory.getLogger(ComputerEndpoint.class);

	private final ComputerService mComputerService;

	@Autowired
	private ICompanyDao mCompanyDao;

	@Autowired
	public ComputerEndpoint(ComputerService pComputerService) {
		mComputerService = pComputerService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Paginator<Computer> findAll(@RequestParam Map<String, Object> pMap) throws DAOException {

		if (pMap.containsKey(ComputerDao.Parameters.SIZE)) {
			pMap.put(ComputerDao.Parameters.SIZE, Integer.valueOf((String) pMap.get(ComputerDao.Parameters.SIZE)));
		}

		if (pMap.containsKey(ComputerDao.Parameters.START)) {
			pMap.put(ComputerDao.Parameters.START, Integer.valueOf((String) pMap.get(ComputerDao.Parameters.START)));
		}

		Paginator<Computer> paginator = mComputerService.findWithNamedQueryWithPaginator(ComputerDao.NamedQueries.SEARCH, pMap);
		paginator.getValues().stream().map(ComputerDto::new);
		return paginator;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ComputerDto find(@PathVariable("id") int pId) throws DAOException {
		Computer computer = mComputerService.find(pId);
		if (computer == null) {
			throw new ResourceNotFound();
		}
		return new ComputerDto(computer);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody ComputerDto pComputerDto) throws DAOException {
		if (!ComputerValidator.isValidComputer(pComputerDto)) {
			return new ResponseEntity<Object>("Not Valid Computer", HttpStatus.BAD_REQUEST);
		}

		Computer computer = pComputerDto.toComputer(mCompanyDao);
		mComputerService.create(computer);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(
				ServletUriComponentsBuilder
						.fromCurrentRequest()
						.path("/companies/{id}")
						.buildAndExpand(computer.getId()).toUri()
		);
		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}
}
