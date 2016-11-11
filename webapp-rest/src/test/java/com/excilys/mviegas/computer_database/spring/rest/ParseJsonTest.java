package com.excilys.mviegas.computer_database.spring.rest;

import com.excilys.mviegas.computer_database.data.Company;
import com.excilys.mviegas.computer_database.data.Computer;
import com.excilys.mviegas.computer_database.dto.ComputerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * @author VIEGAS Mickael
 */
public class ParseJsonTest {

	private ObjectMapper mObjectMapper = new ObjectMapper();
	@Test
	public void computerDto() throws Exception {
		assertEquals(mObjectMapper.readTree("{\"id\":0,\"introducedDate\":null,\"discontinuedDate\":null,\"idCompany\":0,\"name\":\"un nom\"}"),
				mObjectMapper.readTree(mObjectMapper.writeValueAsString(new ComputerDto(new Computer.Builder().setName("un nom").build()))));
	}

	@Test
	public void computerDto2() throws Exception {
		assertEquals(mObjectMapper.readTree("{\"introducedDate\":\"2016-05-12\",\"id\":0,\"discontinuedDate\":null,\"idCompany\":12,\"name\":\"un nom\"}"),
				mObjectMapper.readTree(mObjectMapper.writeValueAsString(new ComputerDto(new Computer.Builder().setName("un nom").setIntroducedDate(LocalDate.of(2016, 5, 12)).setManufacturer(new Company(12, "Apple")).build()))));
	}

	@Test
	public void company() throws Exception {
		assertEquals(mObjectMapper.readTree("{\"id\":12,\"name\":\"Apple\"}"),
				mObjectMapper.readTree(mObjectMapper.writeValueAsString(new Company(12, "Apple"))));
	}
}
