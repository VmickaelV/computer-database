package com.excilys.mviegas.speccdb.mappers;

import com.excilys.mviegas.speccdb.managers.DashboardManagerBean;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

import static com.excilys.mviegas.speccdb.managers.DashboardManagerBean.DEFAULT_SIZE_PAGE;

/**
 * Mapper de DashboardBean
 *
 * Created by Mickael on 12/04/16.
 */
public class DashboardBeanMapper {
	public static DashboardManagerBean build(HttpServletRequest pHttpServletRequest) {
		return mapToObject(pHttpServletRequest.getParameterMap().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, (e) -> e.getValue()[0])));
	}

	private static DashboardManagerBean mapToObject(Map<String, String> map) {
		int page = 1;
		int size = DEFAULT_SIZE_PAGE;
		String search = null;
		String order = null;
		String typeOrder = null;

		if (map.containsKey("page")) {
			page = Integer.parseInt(map.get("page"));
		}

		if (map.containsKey("size")) {
			size = Integer.parseInt(map.get("size"));
		}

		if (map.containsKey("search")) {
			search = map.get("search");
		}

		if (map.containsKey("order")) {
			order = map.get("order");
		}

		if (map.containsKey("typeOrder")) {
			typeOrder = map.get("typeOrder");
		}

		return new DashboardManagerBean(page, size, search, order, typeOrder);
	}
}
