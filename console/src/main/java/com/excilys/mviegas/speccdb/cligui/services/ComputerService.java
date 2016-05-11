package com.excilys.mviegas.speccdb.cligui.services;

import com.excilys.mviegas.speccdb.cligui.ClientRest;
import com.excilys.mviegas.speccdb.data.Computer;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author VIEGAS Mickael
 */
public enum ComputerService {

	INSTANCE;

	private RestTemplate mRestTemplate;

	private Map<String, String> mMap = new HashMap<>();



	public Computer find(long pId) {
		return mRestTemplate.getForEntity(ClientRest.BASE_URL+"/computers/"+pId, Computer.class).getBody();
	}

	public void delete(Computer pComputer) {
		mRestTemplate.delete(ClientRest.BASE_URL+"/computers/"+pComputer.getId());
	}

	public List<Computer> findAll(int pStart, int pSize) {
		throw new UnsupportedOperationException();
	}

	public void update(Computer pComputer) {
		throw new UnsupportedOperationException();
	}

	public void create(Computer pComputer) {
		throw new UnsupportedOperationException();
	}
}
