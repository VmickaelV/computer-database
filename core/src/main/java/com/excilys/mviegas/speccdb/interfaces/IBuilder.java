package com.excilys.mviegas.speccdb.interfaces;

import com.excilys.mviegas.speccdb.exceptions.BuilderException;

/**
 * Interface permattant d'implémenter les mathodes de bases d'un builder.
 *
 * @param <T> Future type Builder
 * @param <Q> Type d'objet que le builder génèrerea
 *
 * @author Mickael
 */
@SuppressWarnings("UnnecessaryInterfaceModifier")
public interface IBuilder<T extends IBuilder, Q> {

	/**
	 * Génère l'objet Final.
	 *
	 * @return objet généré
	 * @throws BuilderException Exception lors du build
	 */
	public Q build() throws BuilderException;

	/**
	 * (Ré)initialise le builder.
	 *
	 * @return objet courant
	 * @throws BuilderException Exception lors de l'initialisation
	 */
	public T init() throws BuilderException;
}