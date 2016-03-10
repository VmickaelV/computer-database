package com.excilys.mviegas.speccdb;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.Statement;

import org.junit.Test;

import com.excilys.mviegas.speccdb.persist.jdbc.DatabaseManager;

public class DatabaseManagerTest {
	
	public static final String DB_CREATE = "db/1-SCHEMA.sql";
	public static final String DB_INSERT = "db/3-ENTRIES.sql";
	
	@Test
	public void connection() throws Exception {
		Connection connection = DatabaseManager.getConnection();
		
		Statement statement = connection.createStatement();
		
//		statement.
		
		statement.close();

		DatabaseManager.releaseConnection(connection);
	}
	
	public void resetDatabase() throws Exception {
		ObjectInputStream br = new ObjectInputStream(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(DB_CREATE)));
		try {
		    String line;
		    while ((line = br.readLine()) != null) {
		       // process the line.
		    }
		} finally {
			
		}
	}
}
