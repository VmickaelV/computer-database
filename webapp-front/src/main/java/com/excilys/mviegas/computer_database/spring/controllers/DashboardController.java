package com.excilys.mviegas.computer_database.spring.controllers;

import com.excilys.mviegas.computer_database.managers.DashboardPage;
import com.excilys.mviegas.computer_database.services.ComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Controleur pour les pages du dashboard
 *
 * Created by excilys on 14/04/16.
 */
@Controller
public class DashboardController {

	@Autowired
	private ComputerService mComputerService;

	public static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	@RequestMapping(value = "/dashboard", method = {RequestMethod.GET})
	public String get(@Valid DashboardPage pDashboardPage, ModelMap pModelMap) {
		pDashboardPage.setComputerService(mComputerService);
		pDashboardPage.update();

		pModelMap.put("dashboardManager", pDashboardPage);
		return "dashboard";
	}
	
	@RequestMapping(value = "/dashboard", method = {RequestMethod.POST})
	public String delete(DashboardPage pDashboardPage, ModelMap pModelMap) {
		if (pDashboardPage.getSelection() == null  || pDashboardPage.getSelection().isEmpty()) {
			throw new RuntimeException();
		}

		pDashboardPage.setComputerService(mComputerService);

		boolean result = pDashboardPage.delete();
		
		pModelMap.put("dashboardManager", pDashboardPage);
		pModelMap.put("deleteSuccessful", result);
		pDashboardPage.update();
		
		return "dashboard";
	}
}
