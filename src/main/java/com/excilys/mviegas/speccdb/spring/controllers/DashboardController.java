package com.excilys.mviegas.speccdb.spring.controllers;

import com.excilys.mviegas.speccdb.managers.DashboardManagerBean;
import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao;
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

//	@Autowired
//	private DashboardManagerBean mDashboardManagerBean;

	@Autowired
	private ComputerDao mComputerDao;

	public static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

	@RequestMapping(value = "/dashboard", method = {RequestMethod.GET})
	public String get(@Valid DashboardManagerBean pDashboardManagerBean, BindingResult bindingResult, ModelMap pModelMap) {

//		mDashboardManagerBean.init();
//		mDashboardManagerBean.map(allRequestParams);
//		mDashboardManagerBean.update();

		pDashboardManagerBean.setComputerDao(mComputerDao);
		pDashboardManagerBean.update();


		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("pDashboardManagerBean = " + pDashboardManagerBean);
			LOGGER.debug("bindingResult = " + bindingResult);

		}
//		mDashboardManagerBean = pDashboardManagerBean;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("m = " + pDashboardManagerBean.getPaginator());
		}

		pModelMap.put("dashboardManager", pDashboardManagerBean);
		return "dashboard";
	}
	
	@RequestMapping(value = "/dashboard", method = {RequestMethod.POST})
	public String delete(DashboardManagerBean pDashboardManagerBean, ModelMap pModelMap) {
//		mDashboardManagerBean.init();
//
//		mDashboardManagerBean.map(allRequestParams);
//
		if (pDashboardManagerBean.getSelection() == null  || pDashboardManagerBean.getSelection().isEmpty()) {
			throw new RuntimeException();
		}

		pDashboardManagerBean.setComputerDao(mComputerDao);

		boolean result = pDashboardManagerBean.delete();
		
		pModelMap.put("dashboardManager", pDashboardManagerBean);
		pModelMap.put("deleteSuccessful", result);
		pDashboardManagerBean.update();
		
		return "dashboard";
	}
}
