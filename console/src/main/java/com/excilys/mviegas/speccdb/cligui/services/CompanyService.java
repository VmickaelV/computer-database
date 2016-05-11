package com.excilys.mviegas.speccdb.cligui.services;

import com.excilys.mviegas.speccdb.cligui.ClientRest;
import com.excilys.mviegas.speccdb.data.Company;
import org.springframework.web.client.RestTemplate;

/**
 * @author VIEGAS Mickael
 */
public enum CompanyService {

	INSTANCE;


	private RestTemplate mRestTemplate = new RestTemplate();

	public Company find(long pId) {
		return mRestTemplate.getForEntity(ClientRest.BASE_URL+"/companies/"+pId, Company.class).getBody();
	}
}
