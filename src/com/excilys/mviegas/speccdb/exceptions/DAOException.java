package com.excilys.mviegas.speccdb.exceptions;

public class DAOException extends RuntimeException {

	public DAOException() {
		super();
	}

	public DAOException(String pMessage, Throwable pCause, boolean pEnableSuppression, boolean pWritableStackTrace) {
		super(pMessage, pCause, pEnableSuppression, pWritableStackTrace);
	}

	public DAOException(String pMessage, Throwable pCause) {
		super(pMessage, pCause);
	}

	public DAOException(String pMessage) {
		super(pMessage);
	}

	public DAOException(Throwable pCause) {
		super(pCause);
	}
	
}
