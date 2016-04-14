package com.excilys.mviegas.speccdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Classe de constantes
 * 
 * @author Mickael
 */
public final class C {
	
	public static final class Loggers {
		public static final Logger RUNTIME = LoggerFactory.getLogger("Runtime");
	}

//	TODO Voir si toujours utile
	public static final ApplicationContext appContext = new ClassPathXmlApplicationContext("beans.xml");

}
