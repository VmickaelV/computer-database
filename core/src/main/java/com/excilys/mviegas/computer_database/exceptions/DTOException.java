package com.excilys.mviegas.computer_database.exceptions;

/**
 * Exception lancé lors de DTO.
 *
 * @author Mickael Viegas
 * Created by excilys on 22/03/16.
 */
@SuppressWarnings("unused")
public class DTOException extends Exception {

	/**
	 * @see Exception#Exception()
	 */
	public DTOException() {
	}

	/**
	 * @see Exception#Exception(String)
	 */
	public DTOException(String message) {
		super(message);
	}

	/**
	 * @see Exception#Exception(String, Throwable)
	 */
	public DTOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @see Exception#Exception(String, Throwable, boolean, boolean)
	 */
	public DTOException(Throwable cause) {
		super(cause);
	}

	/**
	 * @see Exception#Exception(String, Throwable, boolean, boolean)
	 */
	public DTOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
