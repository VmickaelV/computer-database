package com.excilys.mviegas.speccdb.spring.rest;

import com.excilys.mviegas.speccdb.exceptions.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author VIEGAS Mickael
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

//	@ExceptionHandler()
//	public Response daoException() {
//		return Response.serverError().build();
//	}

	@ExceptionHandler(ResourceNotFound.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> resourceNotFound() {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> illegalArgument(Exception pE) {
		return new ResponseEntity<>(pE.toString(), HttpStatus.BAD_REQUEST);
	}

}
