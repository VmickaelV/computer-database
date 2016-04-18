package com.excilys.mviegas.speccdb.spring.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.mviegas.speccdb.managers.DashboardManagerBean;

/**
 * Created by excilys on 14/04/16.
 */
@Controller
public class DeletionComputerController {

	@Autowired
	private DashboardManagerBean mDashboardManagerBean;

	public static final Logger LOGGER = LoggerFactory.getLogger(DeletionComputerController.class);

	
}
