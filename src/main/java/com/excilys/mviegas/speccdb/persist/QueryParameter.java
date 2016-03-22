package com.excilys.mviegas.speccdb.persist;

import java.util.HashMap;
import java.util.Map;

/**
 * Générateur de maps de paramètres
 *
 * @author Mickael
 */
public class QueryParameter {

    // ============================================================
    //	Attributes - private
    // ============================================================

	/**
     * Liste des paramètres sauvegardés
     */
    private Map<String, Object> parameters = null;

    // ============================================================
    //	Constructors
    // ============================================================

	/**
     * Constructeur avec un couple clé/valeur de base
     *
     * @param name Nom du paramètre
     * @param value Valeur associé au nom du paramètre
     */
    private QueryParameter(String name,Object value){
        this.parameters = new HashMap<>();
        this.parameters.put(name, value);
    }

    // ============================================================
    //	Méthodes
    // ============================================================

	/**
     * Rajoute un paramètre
     *
     * @param name Nom du paramètre
     * @param value Valeur associé au nom du paramètre
     * @return Renvoie l'objet courant pour chaîner
     */
    public QueryParameter and(String name,Object value){
        this.parameters.put(name, value);
        return this;
    }

	/**
	 * Génère une map représentant la liste des paramètres
     * @return Objet Map
     */
    public Map<String, Object> parameters(){
        return this.parameters;
    }

    // ============================================================
    //	Méthodes static
    // ============================================================

	/**
     * Méthode générateur d'objets QueryParameter
     *
     * @param name Nom du paramètre
     * @param value Valeur associé au nom du paramètre
     * @return Nouvel Objet QueryParameter
     */
    public static QueryParameter with(String name,Object value){
        return new QueryParameter(name, value);
    }
}