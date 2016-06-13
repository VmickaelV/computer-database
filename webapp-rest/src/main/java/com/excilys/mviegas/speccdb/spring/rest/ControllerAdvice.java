package com.excilys.mviegas.speccdb.spring.rest;

import com.excilys.mviegas.speccdb.exceptions.ResourceNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Mapper de Exceptions à des réponses HTTP.
 *
 * @author VIEGAS Mickael
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(ResourceNotFound.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<?> resourceNotFound() {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> illegalArgument(Exception e) {
		return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
	}

}
