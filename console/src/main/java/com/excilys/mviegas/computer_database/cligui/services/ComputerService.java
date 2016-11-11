package com.excilys.mviegas.computer_database.cligui.services;

import com.excilys.mviegas.computer_database.cligui.ClientRest;
import com.excilys.mviegas.computer_database.data.Computer;
import com.excilys.mviegas.computer_database.dto.ComputerDto;
import com.excilys.mviegas.computer_database.persistence.Paginator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * @author VIEGAS Mickael
 */
public enum ComputerService {

	INSTANCE;

	private Client mClient = ClientBuilder.newClient();

	public Computer find(long pId) {
		WebTarget target = mClient.target(ClientRest.BASE_URL).path("computers/"+pId);
		return target.request().get(Computer.class);
	}

	public void delete(Computer pComputer) {
		WebTarget target = mClient.target(ClientRest.BASE_URL).path("computers");
		target.request().delete();
	}

	public Paginator<Computer> findAllWithPaginator(int pStart, int pSize) {
		WebTarget target = mClient.target(ClientRest.BASE_URL).path("/computers").queryParam("start", pStart).queryParam("size", pSize);
		Paginator<ComputerDto> dtoPaginator;

		dtoPaginator = target.request(MediaType.APPLICATION_JSON_TYPE).get().readEntity(new GenericType<Paginator<ComputerDto>>() {});

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
		WebTarget target = mClient.target(ClientRest.BASE_URL).path("/computers");
		target.request().post(Entity.entity(new ComputerDto(pComputer), MediaType.APPLICATION_JSON));
	}
}
