package com.excilys.mviegas.computer_database.interfaces;

import java.io.Serializable;

/**
 * Interface permattant de rendre Identifiable un objet
 *
 * Created by Mickael on 21/03/2016.
 */
public interface Identifiable extends Serializable {

	/**
	 * @return Id de l'objet
	 */
	long getId();
}
