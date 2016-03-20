package com.excilys.mviegas.speccdb;

import com.excilys.mviegas.speccdb.exceptions.BuilderException;

/**
 * Created by Mickael on 21/03/2016.
 */
public interface IBuilder<T extends IBuilder, Q> {
	public Q build() throws BuilderException;

	public T init() throws BuilderException;
}