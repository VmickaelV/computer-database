package com.excilys.mviegas.computer_database.spring.controllers;

import com.excilys.mviegas.computer_database.managers.LoginPage;
import com.excilys.mviegas.computer_database.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Controleur d'Authentification
 *
 * Created by excilys on 14/04/16.
 */
@Controller
public class LoginController {

	public static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService mUserService;

	@RequestMapping(value = "/login", method = {RequestMethod.GET})
	public String get(@Valid LoginPage pLoginPage, ModelMap  pModelMap) {
		pLoginPage.setUserService(mUserService);

		pModelMap.put("loginPage", pLoginPage);
		return "login";
	}

	@RequestMapping(value = "/login", method = {RequestMethod.POST})
	public String add(@Valid LoginPage pLoginPage, ModelMap pModelMap) {
		pLoginPage.setUserService(mUserService);

		return "login";
	}
}
