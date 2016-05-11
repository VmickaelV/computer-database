package com.excilys.mviegas.speccdb.cligui;

import org.springframework.web.client.RestTemplate;

/**
 * @author VIEGAS Mickael
 */
public enum ClientRest {
	INSTANCE;

	private RestTemplate mRestTemplate;
	public static final String BASE_URL = "http://localhost:8888/rest/api/";

	ClientRest() {
//		Service.create()
		mRestTemplate = new RestTemplate();
	}

	public RestTemplate getRestTemplate() {
		return mRestTemplate;
	}
}
