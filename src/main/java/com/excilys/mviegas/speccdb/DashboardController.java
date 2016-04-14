package com.excilys.mviegas.speccdb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by excilys on 14/04/16.
 */
@Controller
@RequestMapping("/views")
public class DashboardController {

	@RequestMapping(value = "/dashboard.html", method = {RequestMethod.GET})
	public String get() {
		return "Hello Word";
	}
}
