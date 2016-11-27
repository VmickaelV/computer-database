package com.excilys.mviegas.computer_database.spring.rest;

import com.excilys.mviegas.computer_database.data.Computer;
import com.excilys.mviegas.computer_database.dto.ComputerDto;
import com.excilys.mviegas.computer_database.exceptions.DAOException;
import com.excilys.mviegas.computer_database.exceptions.ResourceNotFound;
import com.excilys.mviegas.computer_database.persistence.ICompanyDao;
import com.excilys.mviegas.computer_database.persistence.Paginator;
import com.excilys.mviegas.computer_database.persistence.QueryParameter;
import com.excilys.mviegas.computer_database.persistence.jdbc.ComputerDao;
import com.excilys.mviegas.computer_database.services.ComputerService;
import com.excilys.mviegas.computer_database.validators.ComputerValidator;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

	/**
	 * Renvoie une liste des paramètres
	 * @return Retour
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Paginator<ComputerDto> findAll(@RequestParam(value = "size", defaultValue = "100", required = false) int
                                                      size,
										  @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
										  @RequestParam(value = "order", required = false) ComputerDao.Order order,
										  @RequestParam(value = "search", required = false) String filterName,
										  @RequestParam(value = "type_order", required = false) ComputerDao.TypeOrder typeOrder) throws DAOException {
        QueryParameter queryParameter = QueryParameter.with(ComputerDao.Parameters.SIZE, size)
                .and(ComputerDao.Parameters.START, offset);

        if (filterName != null && !filterName.isEmpty()) {
            queryParameter.put(ComputerDao.Parameters.FILTER_NAME, filterName);
        }

        if (order != null) {
            queryParameter.put(ComputerDao.Parameters.ORDER, filterName);
        }

        if (typeOrder != null)
            queryParameter.put(ComputerDao.Parameters.TYPE_ORDER, filterName);

		Paginator<Computer> paginator = mComputerService.findWithNamedQueryWithPaginator(ComputerDao.NamedQueries.SEARCH, queryParameter.parameters());

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
	@ResponseStatus(HttpStatus.CREATED)
	@ApiResponse(message = "Ordinateur crée", code = 204)
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
