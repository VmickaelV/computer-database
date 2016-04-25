package com.excilys.mviegas.speccdb.spring.controllers;

import com.excilys.mviegas.speccdb.managers.DashboardPage;
import com.excilys.mviegas.speccdb.services.ComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by excilys on 14/04/16.
 */
@Controller
public class DashboardController {
	@Autowired
	private ComputerService mComputerService;

	public static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	@RequestMapping(value = "/dashboard", method = {RequestMethod.GET})
	public String get(@Valid DashboardPage pDashboardManagerBean, BindingResult bindingResult, ModelMap pModelMap) {
		pDashboardManagerBean.setComputerService(mComputerService);
		pDashboardManagerBean.update();

		pModelMap.put("dashboardManager", pDashboardManagerBean);
		return "dashboard";
	}
	
	@RequestMapping(value = "/dashboard", method = {RequestMethod.POST})
	public String delete(DashboardPage pDashboardManagerBean, ModelMap pModelMap) {
		if (pDashboardManagerBean.getSelection() == null  || pDashboardManagerBean.getSelection().isEmpty()) {
			throw new RuntimeException();
		}

		pDashboardManagerBean.setComputerService(mComputerService);

		boolean result = pDashboardManagerBean.delete();
		
		pModelMap.put("dashboardManager", pDashboardManagerBean);
		pModelMap.put("deleteSuccessful", result);
		pDashboardManagerBean.update();
		
		return "dashboard";
	}
}
