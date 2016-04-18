package com.excilys.mviegas.speccdb.spring.controllers;

import com.excilys.mviegas.speccdb.managers.ComputerEditorManagerBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by excilys on 14/04/16.
 */
@Controller
public class EditionComputerController {

	@Autowired
	private ComputerEditorManagerBean mComputerEditorManagerBean;

	public static final Logger LOGGER = LoggerFactory.getLogger(EditionComputerController.class);

	@RequestMapping(value = "/addComputer", method = {RequestMethod.GET})
	public String get(ModelMap pModelMap) {
		mComputerEditorManagerBean.reset();

		pModelMap.put("computerEditor", mComputerEditorManagerBean);
		return "addComputer";
	}

	@RequestMapping(value = "/addComputer", method = {RequestMethod.POST}, params = {"action=Add"})
	public String add(@RequestParam Map<String,String> allRequestParams, ModelMap pModelMap) {
		mComputerEditorManagerBean.reset();

		mComputerEditorManagerBean.map(allRequestParams);
		if (mComputerEditorManagerBean.addComputer()) {
			pModelMap.put("computerAdded", true);
			return "redirect:dashboard.html";
		} else {
			pModelMap.put("computerEditor", mComputerEditorManagerBean);
			return "addComputer";
		}
	}
	
	@RequestMapping(value = "/editComputer", method = {RequestMethod.GET})
	public String getComputer(@RequestParam Map<String,String> allRequestParams, ModelMap pModelMap) {
		mComputerEditorManagerBean.reset();

		mComputerEditorManagerBean.map(allRequestParams);
		
		pModelMap.put("computerEditor", mComputerEditorManagerBean);
		return "addComputer";
	}

	@RequestMapping(value = "/editComputer", method = {RequestMethod.POST}, params = {"action=Edit"})
	public String edit(@RequestParam Map<String,String> allRequestParams, ModelMap pModelMap) {
		mComputerEditorManagerBean.reset();

		mComputerEditorManagerBean.map(allRequestParams);
		if (mComputerEditorManagerBean.editComputer()) {
			pModelMap.put("computerAdded", true);
			return "redirect:dashboard.html";
		} else {
			pModelMap.put("computerEditor", mComputerEditorManagerBean);
			return "addComputer";
		}
	}

//	public String get(@RequestParam Map<String,String> allRequestParams, ModelMap pModelMap) {
//
//		mComputerEditorBean.init();
//		mComputerEditorBean.map(allRequestParams);
//		mComputerEditorBean.update();
//		if (LOGGER.isDebugEnabled()) {
//			LOGGER.debug("m = " + mComputerEditorBean.getPaginator());
//		}
//
//		pModelMap.put("dashboardManager", mComputerEditorBean);
//		return "dashboard";
//	}
//
//	@RequestMapping("/addComputer")
//	public String addComputer() {
//		return "addComputer";
//	}
}
