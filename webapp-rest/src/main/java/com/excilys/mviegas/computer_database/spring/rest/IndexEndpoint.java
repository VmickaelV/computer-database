package com.excilys.mviegas.computer_database.spring.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Permet d'ajouter une redirection à l'index de l'API Rest
 * Created by Mickael on 27/05/2016.
 */
@Controller
@ApiIgnore
public class IndexEndpoint {

	@RequestMapping("/")
	public String index() {
		return "redirect:swagger-ui.html";
	}
}
