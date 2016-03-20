package com.excilys.mviegas.speccdb.exceptions;

/**
 * Exception lancé par un Builder quelconque
 *
 * Created by Mickael on 21/03/2016.
 */
@SuppressWarnings("unused")
public class BuilderException extends Exception {

	public BuilderException() {
	}

	public BuilderException(final Throwable cause) {
		super(cause);
	}

	public BuilderException(final String message) {
		super(message);
	}

	public BuilderException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public BuilderException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
