package com.excilys.mviegas.speccdb.taglib;

import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.excilys.mviegas.speccdb.managers.ComputerEditorPage.FORMATTER_EN;
import static com.excilys.mviegas.speccdb.managers.ComputerEditorPage.FORMATTER_FR;

/**
 * Classes de fonctions pour l'Expression Language.
 *
 * @author VIGAS Mickael
 *         Created by excilys on 22/03/16.
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
	 * Fonction générant un lien.
	 *
	 * @param pTarget        Page cible
	 * @param pQueries       Map de paramètres GET à ajouter dans le lien
	 * @param pQueryToEscape Un paramètre à ne pas inclure, ou avec une valeur à fixer à part
	 * @param value          Valeur à remplacer par le nom fournie dans le paramètre à ne pas inclure
	 * @return Lien texte généré
	 */
	public static String link(String pTarget, Map<String, String> pQueries, String pQueryToEscape, Object value) {
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
			return pTarget + ".html" + (stringBuilder.length() > 0 ? '?' + stringBuilder.toString() : "");
		} else {
			return pTarget + ".html";
		}
	}

	/**
	 * Fonction générant un lien.
	 *
	 * @param pTarget        Page cible
	 * @param pQueries       Map de paramètres GET à ajouter dans le lien
	 * @param pNewParameters Nouveaux paramètres à intégrer
	 * @return Lien texte généré
	 */
	public static String link(String pTarget, Map<String, String> pQueries, Map<String, String> pNewParameters) {

		if (pQueries != null) {
			StringBuilder stringBuilder = new StringBuilder(50);

			Iterator<Map.Entry<String, String>> iterator = pQueries.entrySet().iterator();
			Map.Entry<String, String> entry;
			while (iterator.hasNext()) {
				entry = iterator.next();

				if (pNewParameters == null || !pNewParameters.containsKey(entry.getKey())) {
					stringBuilder.append(entry.getKey()).append('=').append(entry.getValue());
					stringBuilder.append('&');
				}
			}

			if (pNewParameters != null) {
				iterator = pNewParameters.entrySet().iterator();
				while (iterator.hasNext()) {
					entry = iterator.next();
					if (entry.getValue() != null && !entry.getValue().isEmpty()) {
						stringBuilder.append(entry.getKey()).append('=').append(entry.getValue());
						stringBuilder.append('&');
					}
				}
			}

			if (stringBuilder.length() > 0) {
				stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			}

			return pTarget + ".html" + (stringBuilder.length() > 0 ? '?' + stringBuilder.toString() : "");
		} else {
			return pTarget + ".html";
		}
	}

	/**
	 * Génère un lien préformé pour un tri avec GET.
	 *
	 * @param pTarget    Page cible
	 * @param pQueries   Map de paramètres GET à ajouter dans le lien
	 * @param pOrder     Element selon lequel on doit trier
	 * @param pTypeOrder Type d'ordre (descendant ou ascendant)
	 * @return Lien généré
	 */
	public static String linkSort(String pTarget, Map<String, String> pQueries, String pOrder, String pTypeOrder) {
		Map<String, String> map = new HashMap<>(2);
		map.put("order", pOrder);
		map.put("typeOrder", pTypeOrder);

		return link(pTarget, pQueries, map);
	}

	/**
	 * Affiche une date qui est localisé, selon Spring.
	 *
	 * @param pLocalDate Date à formatter
	 * @return Date formatté
	 */
	public static String printLocalizedDate(LocalDate pLocalDate) {
		if (pLocalDate == null) {
			return "";
		}

		switch (LocaleContextHolder.getLocale().getLanguage()) {
			case "fr":
				return FORMATTER_FR.format(pLocalDate);
			case "en":
				return FORMATTER_EN.format(pLocalDate);

			default:
				return DateTimeFormatter.ISO_DATE.format(pLocalDate);
		}
	}

}
