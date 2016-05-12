package com.excilys.mviegas.speccdb.spring.controllers;

import com.excilys.mviegas.speccdb.managers.ComputerEditorPage;
import com.excilys.mviegas.speccdb.services.CompanyService;
import com.excilys.mviegas.speccdb.services.ComputerService;
import com.excilys.mviegas.speccdb.spring.singletons.ListOfCompanies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Controleur pour l'édition de Computer
 * Created by excilys on 14/04/16.
 */
@Controller
public class EditionComputerController {

	public static final Logger LOGGER = LoggerFactory.getLogger(EditionComputerController.class);
	
	@Autowired
	private CompanyService mCompanyService;
	
	@Autowired
	private ComputerService mComputerService;

	@Autowired
	private ListOfCompanies mListOfCompanies;

	@RequestMapping(value = "/addComputer", method = {RequestMethod.GET})
	public String get(@Valid ComputerEditorPage pComputerEditorPage, ModelMap  pModelMap) {
		pComputerEditorPage.setCompanyService(mCompanyService);
		pComputerEditorPage.setComputerService(mComputerService);
		pComputerEditorPage.refresh();

		pModelMap.put("computerEditor", pComputerEditorPage);
		pModelMap.put("listOfCompanies", mListOfCompanies);
		return "addComputer";
	}

	@RequestMapping(value = "/addComputer", method = {RequestMethod.POST}, params = {"action=Add"})
	public String add(@Valid ComputerEditorPage pComputerEditorPage, ModelMap pModelMap) {
		pComputerEditorPage.setCompanyService(mCompanyService);
		pComputerEditorPage.setComputerService(mComputerService);
		pComputerEditorPage.refresh();

		if (pComputerEditorPage.addComputer()) {
			pModelMap.put("computerAdded", true);
			return "redirect:dashboard.html";
		} else {
			pModelMap.put("computerEditor", pComputerEditorPage);
			pModelMap.put("listOfCompanies", mListOfCompanies);
			return "addComputer";
		}
	}
	
	@RequestMapping(value = "/editComputer", method = {RequestMethod.GET})
	public String getComputer(@Valid ComputerEditorPage pComputerEditorPage, ModelMap pModelMap) {
		pComputerEditorPage.setCompanyService(mCompanyService);
		pComputerEditorPage.setComputerService(mComputerService);
		pComputerEditorPage.refresh();
		
		pModelMap.put("computerEditor", pComputerEditorPage);
		pModelMap.put("listOfCompanies", mListOfCompanies);
		return "addComputer";
	}

	@RequestMapping(value = "/editComputer", method = {RequestMethod.POST}, params = {"action=Edit"})
	public String edit(@Valid ComputerEditorPage pComputerEditorPage, ModelMap pModelMap) {
		pComputerEditorPage.setCompanyService(mCompanyService);
		pComputerEditorPage.setComputerService(mComputerService);
		pComputerEditorPage.refresh(true);
		
		if (pComputerEditorPage.editComputer()) {
			pModelMap.put("computerAdded", true);
			return "redirect:dashboard.html";
		} else {
			pModelMap.put("computerEditor", pComputerEditorPage);
			pModelMap.put("listOfCompanies", mListOfCompanies);
			return "addComputer";
		}
	}
}
