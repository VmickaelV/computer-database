package com.excilys.mviegas.speccdb.cligui.services;

import com.excilys.mviegas.speccdb.cligui.ClientRest;
import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.dto.ComputerDto;
import com.excilys.mviegas.speccdb.persistence.Paginator;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author VIEGAS Mickael
 */
public enum ComputerService {

	INSTANCE;

	private RestTemplate mRestTemplate = new RestTemplate();

	private Map<String, String> mMap = new HashMap<>();

	public Computer find(long pId) {
		return mRestTemplate.getForEntity(ClientRest.BASE_URL + "/computers/" + pId, Computer.class).getBody();
	}

	public void delete(Computer pComputer) {
		mRestTemplate.delete(ClientRest.BASE_URL + "/computers/" + pComputer.getId());
	}

	public Paginator<Computer> findAllWithPaginator(int pStart, int pSize) {
		Paginator<ComputerDto> dtoPaginator = mRestTemplate.exchange(ClientRest.BASE_URL + "/computers?start=" + pStart + "&size=" + pSize, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Paginator<ComputerDto>>() {
		}).getBody();

		Paginator<Computer> paginator = new Paginator<>(dtoPaginator);

		paginator.values = dtoPaginator.values.stream().map(pComputerDto -> {
			// TODO revoir ce convertisseur
			Computer computer = new Computer();
			computer.setId(pComputerDto.getId());
			computer.setName(pComputerDto.getName());
			if (pComputerDto.getIntroducedDate() != null) {
				computer.setIntroducedDate(LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(pComputerDto.getIntroducedDate())));
			}

			if (pComputerDto.getDiscontinuedDate() != null) {
				computer.setDiscontinuedDate(LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(pComputerDto.getDiscontinuedDate())));
			}

			return computer;
		}).collect(Collectors.toList());

		return paginator;
	}

	public void update(Computer pComputer) {
		throw new UnsupportedOperationException();
	}

	public void create(Computer pComputer) {
		mRestTemplate.exchange(ClientRest.BASE_URL + "/computers", HttpMethod.POST, new HttpEntity<>(new ComputerDto(pComputer)), Void.class);
	}
}
