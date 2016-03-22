package com.excilys.mviegas.speccdb.taglib;

import com.excilys.mviegas.speccdb.C;

import java.util.Iterator;
import java.util.Map;

/**
 * Classes de fonctions pour l'Expression Language
 *
 * @author VIGAS Mickael
 * Created by excilys on 22/03/16.
 */
public class ELFunctions {

	/**
	 * @see #link(String, Map, String, Object)
	 */
	public static String link(String pTarget) {
		return link(pTarget, null, null, null);
	}

	/**
	 * @see #link(String, Map, String, Object)
	 */
	public static String link(String pTarget, Map<String, String> pQueries) {
		return link(pTarget, pQueries, null, null);
	}

	/**
	 * Fonction générant un lien
	 *
	 * @param pTarget Page cible
	 * @param pQueries Map de paramètres GET à ajouter dans le lien
	 * @param pQueryToEscape Un paramètre à ne pas inclure, ou avec une valeur à fixer à part
	 * @param value Valeur à remplacer par le nom fournie dans le paramètre à ne pas inclure
	 *
	 * @return Lien texte généré
	 */
	public static String link(String pTarget, Map<String, String> pQueries, String pQueryToEscape, Object value) {

		C.LOGGER.RUNTIME.debug("ELFunctions.link");
		C.LOGGER.RUNTIME.debug("pTarget = [" + pTarget + "], pQueries = [" + pQueries + "], pQueryToEscape = [" + pQueryToEscape + "], value = [" + value + "]");

		if (pQueries != null) {
			StringBuilder stringBuilder = new StringBuilder(50);

			Iterator<Map.Entry<String, String>> iterator = pQueries.entrySet().iterator();
			Map.Entry<String, String> entry;
			while (iterator.hasNext()) {
				entry = iterator.next();

				if (pQueryToEscape != null && !pQueryToEscape.equals(entry.getKey())) {
					stringBuilder.append(entry.getKey()).append('=').append(entry.getValue());
					stringBuilder.append('&');
				}
			}

			if (pQueryToEscape != null && !pQueryToEscape.isEmpty() && value != null) {
				stringBuilder.append(pQueryToEscape)
						.append('=')
						.append(value.toString())
						.append('&');
			}

			if (stringBuilder.length() > 0) {
				stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			}
			return pTarget+".jsp"+(stringBuilder.length() > 0 ? '?' + stringBuilder.toString() : "");
		} else {
			return pTarget+".jsp";
		}

	}
}
