package com.excilys.mviegas.computer_database.spring.rest;

import com.excilys.mviegas.computer_database.data.Company;
import com.excilys.mviegas.computer_database.data.Computer;
import com.excilys.mviegas.computer_database.dto.ComputerDto;
import com.excilys.mviegas.computer_database.persistence.Paginator;
import com.excilys.mviegas.computer_database.services.ComputerService;
import com.excilys.mviegas.computer_database.utils.DatabaseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.time.LocalDate;
import java.time.Month;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author VIEGAS Mickael
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:beans-rest.xml"})
@WebAppConfiguration
public class ComputerEndPointTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	private ObjectMapper mObjectMapper = new ObjectMapper();

	@Autowired
	private ComputerService mComputerService;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		DatabaseUtils.resetDatabase();
	}

	@Test
	public void testFind() throws Exception {
		String result = mockMvc.perform(get(URI.create("/computers/1")))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andReturn().getResponse().getContentAsString();

		assertEquals(mObjectMapper.writeValueAsString(new ComputerDto(mComputerService.find(1))), result);
	}

	@Test
	public void computerNotFound() throws Exception {
		mockMvc.perform(get(URI.create("/computers/64654")))
				.andExpect(status().isNotFound())
				.andExpect(content().string(""))
		;
	}

	@Test
	public void wrongTypeOfValueIdCompany() throws Exception {
		mockMvc.perform(get(URI.create("/computers/-1")))
				.andExpect(status().isNotFound())
				.andExpect(content().string(""))
		;
	}

	@Test
	public void findAll() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get(URI.create("/computers")))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn()
		;

		Paginator<Computer> computerPaginator = mComputerService.findAllWithPaginator(0, 100);
		Paginator<ComputerDto> dtoPaginator = new Paginator<>(computerPaginator);
		dtoPaginator.values = computerPaginator.values.stream().map(ComputerDto::new).collect(Collectors.toList());
		assertEquals(mvcResult.getResponse().getContentAsString(), mObjectMapper.writeValueAsString(dtoPaginator));
	}

	@Test
	public void findAll2() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get(URI.create("/computers?size=10")))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn()
				;

		Paginator<Computer> paginator = mComputerService.findAllWithPaginator(0, 10);
		Paginator<ComputerDto> dtoPaginator = new Paginator<>(paginator);
		dtoPaginator.values = paginator.values.stream().map(ComputerDto::new).collect(Collectors.toList());
		assertEquals(mvcResult.getResponse().getContentAsString(), mObjectMapper.writeValueAsString(dtoPaginator));
	}

	@Test
	public void findAll3() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get(URI.create("/computers?offset=6&size=15")))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn()
				;

		Paginator<Computer> paginator = mComputerService.findAllWithPaginator(6, 15);
		Paginator<ComputerDto> dtoPaginator = new Paginator<>(paginator);
		dtoPaginator.values = paginator.values.stream().map(ComputerDto::new).collect(Collectors.toList());
		assertEquals(mvcResult.getResponse().getContentAsString(), mObjectMapper.writeValueAsString(dtoPaginator));
	}

	@Test
	public void create() throws Exception {

		System.out.println(mObjectMapper.writeValueAsString(new ComputerDto(0, "nouveau", null, null, 0)));
		int n = mComputerService.size();
		MvcResult mvcResult =
				mockMvc
						.perform(post(URI.create("/computers"))
						.content(mObjectMapper.writeValueAsString(new ComputerDto(0, "nouveau", null, null, 0)))
						.contentType(MediaType.APPLICATION_JSON_UTF8))

				.andExpect(status().isCreated())
				.andExpect(content().string(""))
				.andExpect(MockMvcResultMatchers.header().string("Location", Matchers.startsWith("http://localhost")))
				.andReturn()
				;

		assertEquals(n+1, mComputerService.size());
	}

	@Test
	public void createFieldsMissing() throws Exception {

		// TODO retravailler les messages d'erreurs
		int n = mComputerService.size();
		MvcResult mvcResult =
				mockMvc
						.perform(post(URI.create("/computers"))
								.content("{\"indroducedDate\":\"1989-01-01\"}")
								.contentType(MediaType.APPLICATION_JSON_UTF8))

						.andExpect(status().isBadRequest())
						.andExpect(content().string("Not Valid Computer"))
						.andReturn()
				;

		assertEquals(n, mComputerService.size());
	}

	@Test
	public void createFieldsMissing2() throws Exception {

		// TODO retravailler les messages d'erreurs
		int n = mComputerService.size();
		MvcResult mvcResult =
				mockMvc
						.perform(post(URI.create("/computers"))
								.content("{\"id\":0,\"introducedDate\":null,\"discontinuedDate\":null,\"idCompany\":0}\n")
								.contentType(MediaType.APPLICATION_JSON_UTF8))

						.andExpect(status().isBadRequest())
						.andExpect(content().string("Not Valid Computer"))
						.andReturn()
				;

		assertEquals(n, mComputerService.size());
	}

	@Test
	public void createMinimalField() throws Exception {

		// TODO retravailler les messages d'erreurs
		int n = mComputerService.size();
		MvcResult mvcResult =
				mockMvc
						.perform(post(URI.create("/computers"))
								.content("{\"name\":\"name\"}")
								.contentType(MediaType.APPLICATION_JSON_UTF8))

						.andExpect(status().isCreated())
						.andExpect(content().string(""))
						.andReturn()
				;

		assertEquals(n+1, mComputerService.size());
	}

	@Test
	public void createMissingField() throws Exception {

		// TODO retravailler les messages d'erreurs
		int n = mComputerService.size();
		MvcResult mvcResult =
				mockMvc
						.perform(post(URI.create("/computers"))
								.content("{\"id\":321}")
								.contentType(MediaType.APPLICATION_JSON_UTF8))

						.andExpect(status().isBadRequest())
						.andExpect(content().string("Not Valid Computer"))
						.andReturn()
				;

		assertEquals(n, mComputerService.size());
	}

	@Test
	public void deleteNotFound1() throws Exception {

		// TODO retravailler les messages d'erreurs
		int n = mComputerService.size();
		MvcResult mvcResult =
				mockMvc
						.perform(delete(URI.create("/computers/-1"))
								.content("{\"id\":321}")
								.contentType(MediaType.APPLICATION_JSON_UTF8))

						.andExpect(status().isNotFound())
						.andExpect(content().string(""))
						.andReturn()
				;

		assertEquals(n, mComputerService.size());
	}

	@Test
	public void deleteNotFound2() throws Exception {

		// TODO retravailler les messages d'erreurs
		int n = mComputerService.size();
		MvcResult mvcResult =
				mockMvc
						.perform(delete(URI.create("/computers/64332")))
						.andExpect(status().isNotFound())
						.andExpect(content().string(""))
						.andReturn()
				;

		assertEquals(n, mComputerService.size());
	}

	@Test
	public void deleteResource() throws Exception {

		// TODO retravailler les messages d'erreurs
		int n = mComputerService.size();
		MvcResult mvcResult =
				mockMvc
						.perform(delete(URI.create("/computers/23")))
						.andExpect(status().isOk())
						.andExpect(content().string(""))
						.andReturn()
				;

		assertEquals(n-1, mComputerService.size());
	}

	@Test
	public void deleteResourcenotIndompedence() throws Exception {

		// TODO retravailler les messages d'erreurs
		int n = mComputerService.size();
		MvcResult mvcResult =
				mockMvc
						.perform(delete(URI.create("/computers/25")))
						.andExpect(status().isOk())
						.andExpect(content().string(""))
						.andReturn()
				;

		assertEquals(n-1, mComputerService.size());

		mvcResult =
				mockMvc
						.perform(delete(URI.create("/computers/25")))
						.andExpect(status().isNotFound())
						.andExpect(content().string(""))
						.andReturn()
				;

		assertEquals(n-1, mComputerService.size());
	}

	@Test
	public void deleteSuceptibleBig() throws Exception {

		// TODO retravailler les messages d'erreurs
		int n = mComputerService.size();
		MvcResult mvcResult =
				mockMvc
						.perform(delete(URI.create("/computers/25"))
								.contentType(MediaType.APPLICATION_JSON_UTF8)
								.content("[-1, 2, 3]"))
						.andExpect(status().isOk())
						.andExpect(content().string(""))
						.andReturn()
				;

		assertEquals(n-1, mComputerService.size());
	}

	@Test
	public void deleteListWrong() throws Exception {

		// TODO retravailler les messages d'erreurs
		int n = mComputerService.size();
		MvcResult mvcResult =
				mockMvc
						.perform(delete(URI.create("/computers"))
								.contentType(MediaType.APPLICATION_JSON_UTF8)
								.content("[-1, 2, 3]"))
						.andExpect(status().isNotFound())
						.andExpect(content().string(""))
						.andReturn()
				;

		assertEquals(n, mComputerService.size());
	}

	@Test
	public void deleteListWright() throws Exception {

		// TODO retravailler les messages d'erreurs
		int n = mComputerService.size();
		MvcResult mvcResult =
				mockMvc
						.perform(delete(URI.create("/computers"))
								.contentType(MediaType.APPLICATION_JSON_UTF8)
								.content("[1, 2, 3]"))
						.andExpect(status().isOk() 	)
						.andExpect(content().string(""))
						.andReturn()
				;

		assertEquals(n-3, mComputerService.size());
	}

	@Test
	public void updateWrongId() throws Exception {
		int n = mComputerService.size();

		Computer computer = mComputerService.find(26);
//		String previous
		MvcResult mvcResult =
				mockMvc
						.perform(put(URI.create("/computers/25"))
								.contentType(MediaType.APPLICATION_JSON_UTF8)
								.content(mObjectMapper.writeValueAsString(new ComputerDto(computer))))
						.andExpect(status().isBadRequest())
						.andExpect(content().string("Wrong argument id"))
						.andReturn()
				;

		assertEquals(n, mComputerService.size());
	}

	@Test
	public void update() throws Exception {
		int n = mComputerService.size();

		final int id = 25;
		Computer computer = mComputerService.find(id);
		final String previousName = computer.getName();
		final String newName = "NouveauNom";

		assertNotEquals(previousName, newName);
		computer.setName(newName);
		MvcResult mvcResult =
				mockMvc
						.perform(put(URI.create("/computers/"+id))
								.contentType(MediaType.APPLICATION_JSON_UTF8)
								.content(mObjectMapper.writeValueAsString(new ComputerDto(computer))))
						.andExpect(status().isNoContent())
						.andExpect(content().string(""))
						.andReturn()
				;

		computer = mComputerService.find(id);

		assertEquals(newName, computer.getName());

		assertEquals(n, mComputerService.size());
	}

	@Test
	public void updateWithDateAndCompany() throws Exception {
		int n = mComputerService.size();

		final int id = 25;
		Computer computer = mComputerService.find(id);
		final String previousName = computer.getName();
		final String newName = "NouveauNom";

		assertNotEquals(previousName, newName);
		computer.setName(newName);
		ComputerDto computerDto = new ComputerDto(computer);
		computerDto.setIntroducedDate("2015-12-01");
		MvcResult mvcResult =
				mockMvc
						.perform(put(URI.create("/computers/"+id))
								.contentType(MediaType.APPLICATION_JSON_UTF8)
								.content(mObjectMapper.writeValueAsString(computerDto)))
						.andExpect(status().isNoContent())
						.andExpect(content().string(""))
						.andReturn()
				;

		computer = mComputerService.find(id);

		assertEquals(newName, computer.getName());
		assertEquals(LocalDate.of(2015, Month.DECEMBER, 1), computer.getIntroducedDate());

		assertEquals(n, mComputerService.size());
	}

	@Test
	public void updateWrongFormatDate() throws Exception {
		int n = mComputerService.size();

		final int id = 25;
		Computer computer = mComputerService.find(id);
		final String previousName = computer.getName();
		final String newName = "NouveauNom";

		assertNotEquals(previousName, newName);
		computer.setName(newName);
		ComputerDto computerDto = new ComputerDto(computer);
		computerDto.setIntroducedDate("2015-13-01");
		MvcResult mvcResult =
				mockMvc
						.perform(put(URI.create("/computers/"+id))
								.contentType(MediaType.APPLICATION_JSON_UTF8)
								.content(mObjectMapper.writeValueAsString(computerDto)))
						.andExpect(status().isBadRequest())
						.andExpect(content().string("Not Valid Computer"))
						.andReturn()
				;

		computer = mComputerService.find(id);

		assertEquals(previousName, computer.getName());

		assertEquals(n, mComputerService.size());
	}

	public void assertCompany(List<Company> pListExcepted, List<Company> pListResult) {
		Iterator<Company> iteratorExcepted = pListExcepted.iterator();
		Iterator<Company> iteratorResult = pListResult.iterator();

		while (iteratorExcepted.hasNext()) {
			assertCompany(iteratorExcepted.next(), iteratorResult.next());
		}

		if (iteratorExcepted.hasNext() != iteratorResult.hasNext()) {
			fail();
		}
	}

	public void assertCompany(Company expected, Company value) {
		Assert.assertEquals(expected.getId(), value.getId());
		Assert.assertEquals(expected.getName(), value.getName());
	}
}
