package com.excilys.mviegas.speccdb.spring.controllers;

import com.excilys.mviegas.speccdb.managers.DashboardManagerBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by excilys on 14/04/16.
 */
@Controller
public class DashboardController {

	@Autowired
	private DashboardManagerBean mDashboardManagerBean;

	public static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	@RequestMapping("/dashboard")
	public String get(@RequestParam Map<String,String> allRequestParams, ModelMap pModelMap) {

		mDashboardManagerBean.init();
		mDashboardManagerBean.map(allRequestParams);
		mDashboardManagerBean.update();
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("m = " + mDashboardManagerBean.getPaginator());
		}

		pModelMap.put("dashboardManager", mDashboardManagerBean);
		return "dashboard";
	}
}
