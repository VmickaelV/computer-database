package com.excilys.mviegas.speccdb.spring.rest;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.services.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author VIEGAS Mickael
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:beans-rest.xml"})
@WebAppConfiguration
public class CompanyEndPointTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	private ObjectMapper mObjectMapper = new ObjectMapper();

	@Autowired
	private CompanyService mCompanyService;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

//		this.bookmarkRepository.deleteAllInBatch();
//		this.accountRepository.deleteAllInBatch();
//
//		this.account = accountRepository.save(new Account(userName, "password"));
//		this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + userName, "A description")));
//		this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + userName, "A description")));

	}

	@Test
	public void name() throws Exception {
		mockMvc.perform(get(URI.create("/companies/1")))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}

	@Test
	public void companyNotFound() throws Exception {
		mockMvc.perform(get(URI.create("/companies/50")))
				.andExpect(status().isNotFound())
				.andExpect(content().string(""))
		;
	}

	@Test
	public void wrongTypeOfValueIdCompany() throws Exception {
		mockMvc.perform(get(URI.create("/companies/-1")))
				.andExpect(status().isNotFound())
				.andExpect(content().string(""))
		;
	}

	@Test
	public void findAll() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get(URI.create("/companies")))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn()
		;

		assertEquals(mvcResult.getResponse().getContentAsString(), mObjectMapper.writeValueAsString(mCompanyService.findAll()));
	}

	@Test
	public void findAll2() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get(URI.create("/companies?size=10")))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn()
				;

		assertEquals(mvcResult.getResponse().getContentAsString(), mObjectMapper.writeValueAsString(mCompanyService.findAll(0, 10)));
	}

	@Test
	public void findAll3() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		MvcResult mvcResult = mockMvc.perform(get(URI.create("/companies?start=6&size=15")))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andReturn()
				;

		assertEquals(mvcResult.getResponse().getContentAsString(), mObjectMapper.writeValueAsString(mCompanyService.findAll(6, 15)));
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
