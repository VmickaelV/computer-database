package com.excilys.mviegas.speccdb.mappers;

import com.excilys.mviegas.speccdb.managers.DashboardPage;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

import static com.excilys.mviegas.speccdb.managers.DashboardPage.DEFAULT_SIZE_PAGE;

/**
 * Mapper de DashboardBean
 *
 * Created by Mickael on 12/04/16.
 */
public class DashboardPageMapper {

	public static class Keys {
		public static final String PAGE = "page";
		public static final String SIZE = "size";
		public static final String SEARCH = "search";
		public static final String ORDER = "order";
		public static final String TYPE_ORDER = "typeOrder";
	}
	public static DashboardPage build(HttpServletRequest pHttpServletRequest) {
		return mapToObject(pHttpServletRequest.getParameterMap().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, (e) -> e.getValue()[0])));
	}

	private static DashboardPage mapToObject(Map<String, String> map) {
		int page = 1;
		int size = DEFAULT_SIZE_PAGE;
		String search = null;
		String order = null;
		String typeOrder = null;

		if (map.containsKey(Keys.PAGE)) {
			page = Integer.parseInt(map.get(Keys.PAGE));
		}

		if (map.containsKey(Keys.SIZE)) {
			size = Integer.parseInt(map.get(Keys.SIZE));
		}

		if (map.containsKey(Keys.SEARCH)) {
			search = map.get(Keys.SEARCH);
		}

		if (map.containsKey(Keys.ORDER)) {
			order = map.get(Keys.ORDER);
		}

		if (map.containsKey(Keys.TYPE_ORDER)) {
			typeOrder = map.get(Keys.TYPE_ORDER);
		}

		return new DashboardPage(page, size, search, order, typeOrder);
	}
}
