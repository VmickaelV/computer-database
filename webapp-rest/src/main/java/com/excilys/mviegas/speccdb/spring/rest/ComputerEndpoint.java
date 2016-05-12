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
import java.util.stream.Collectors;

/**
 * Endpoint de {@link Computer}.
 *
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
	public Paginator<ComputerDto> findAll(@RequestParam Map<String, Object> pMap) throws DAOException {

		if (pMap.containsKey(ComputerDao.Parameters.SIZE)) {
			pMap.put(ComputerDao.Parameters.SIZE, Integer.valueOf((String) pMap.get(ComputerDao.Parameters.SIZE)));
		}

		if (pMap.containsKey(ComputerDao.Parameters.START)) {
			pMap.put(ComputerDao.Parameters.START, Integer.valueOf((String) pMap.get(ComputerDao.Parameters.START)));
		}

		Paginator<Computer> paginator = mComputerService.findWithNamedQueryWithPaginator(ComputerDao.NamedQueries.SEARCH, pMap);

		Paginator<ComputerDto> paginatorDto = new Paginator<>(paginator);
		paginatorDto.values = paginator.values.stream().map(ComputerDto::new).collect(Collectors.toList());
		return paginatorDto;
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

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> edit(@PathVariable("id") long id, @RequestBody ComputerDto pComputerDto) throws DAOException {
		if (id != pComputerDto.getId()) {
			// TODO remplacer par une exception
			return new ResponseEntity<Object>("Wrong argument id", HttpStatus.BAD_REQUEST);
		}

		if (!ComputerValidator.isValidComputer(pComputerDto)) {
			return new ResponseEntity<Object>("Not Valid Computer", HttpStatus.BAD_REQUEST);
		}

		Computer computer = pComputerDto.toComputer(mCompanyDao);
		mComputerService.update(computer);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") long id) throws DAOException {
		Computer computer = mComputerService.find(id);
		if (computer == null) {
			throw new ResourceNotFound();
		}
		mComputerService.delete(id);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@RequestBody long[] ids) throws DAOException {
		for (long id : ids) {
			Computer computer = mComputerService.find(id);
			if (computer == null) {
				throw new ResourceNotFound();
			}
		}

		for (long id : ids) {
			mComputerService.delete(id);
		}

		return new ResponseEntity<>(null, HttpStatus.OK);
	}


}
