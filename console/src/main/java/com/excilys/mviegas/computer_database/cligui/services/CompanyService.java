package com.excilys.mviegas.computer_database.cligui.services;

import com.excilys.mviegas.computer_database.cligui.ClientRest;
import com.excilys.mviegas.computer_database.data.Company;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * @author VIEGAS Mickael
 */
public enum CompanyService {

	INSTANCE;


 	public Company find(long pId) {
		WebTarget target = mClient.target(ClientRest.BASE_URL).path("companies/"+pId);
		return target.request().get(Company.class);
	}

	private final Client mClient;

	CompanyService() {
		mClient = ClientBuilder.newClient();
	}
}
