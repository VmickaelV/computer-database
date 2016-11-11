package com.excilys.mviegas.computer_database.exceptions;

/**
 * Exception lancée lors d'un problème de connexion à la BDD.
 *
 * @author Mickael
 */
public class ConnectionException extends Exception {

	public ConnectionException() {

	}

	public ConnectionException(String pMessage, Throwable pCause, boolean pEnableSuppression, boolean pWritableStackTrace) {
		super(pMessage, pCause, pEnableSuppression, pWritableStackTrace);
	}

	public ConnectionException(String pMessage, Throwable pCause) {
		super(pMessage, pCause);
	}

	public ConnectionException(String pMessage) {
		super(pMessage);
	}

	public ConnectionException(Throwable pCause) {
		super(pCause);
	}



}
