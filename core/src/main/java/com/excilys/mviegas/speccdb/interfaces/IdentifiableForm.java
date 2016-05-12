package com.excilys.mviegas.speccdb.interfaces;

import java.io.Serializable;

/**
 * (Ancienne) Interface permattant de rendre Identifiable un objet
 *
 * Created by Mickael on 21/03/2016.
 */
public interface IdentifiableForm<PK extends Serializable> extends Serializable {

	/**
	 * @return Id de l'objet
	 */
	PK getId();
}
