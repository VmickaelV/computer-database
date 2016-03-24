package com.excilys.mviegas.speccdb.concurrency;

import com.excilys.mviegas.speccdb.persistence.jdbc.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classes regroupant les Threads locaux
 * Created by Mickael on 24/03/16.
 * @author Mickael
 */
public final class ThreadLocals {
	public static final ThreadLocal<Connection> CONNECTIONS = new ThreadLocal<Connection>() {
		@Override
		protected Connection initialValue() {
			try {
				return DatabaseManager.getConnection();
			} catch (SQLException pE) {
				return null;
			}
		}
	};
}
