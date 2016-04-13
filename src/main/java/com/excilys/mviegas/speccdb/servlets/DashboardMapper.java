package com.excilys.mviegas.speccdb.servlets;

import com.excilys.mviegas.speccdb.managers.DashboardManagerBean;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Mickael on 12/04/16.
 */
public class DashboardMapper {
	public static DashboardManagerBean build(HttpServletRequest pHttpServletRequest) {
		return new DashboardManagerBean(pHttpServletRequest);
	}
}
