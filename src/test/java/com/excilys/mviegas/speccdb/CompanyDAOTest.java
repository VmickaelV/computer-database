package com.excilys.mviegas.speccdb;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.persist.jdbc.CompanyDao;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CompanyDAOTest {
	
	private CompanyDao mCompanyDao = CompanyDao.INSTANCE;
	
	@Before
	public void before() throws Exception {
		DatabaseManagerTest.resetDatabase();
	}
	
	@Test
	public void findAll1() throws Exception {
		assertEquals(20, mCompanyDao.findAll().size());
	}
	
	@Test
	public void findPagination1() throws Exception {
		assertEquals(40, mCompanyDao.findAll(0, 40).size());
	}
	
	@Test
	public void findPagination2() throws Exception {
		assertEquals(42, mCompanyDao.findAll(0, 100).size());
	}
	
	@Test
	public void findPagination3() throws Exception {
		assertEquals(2, mCompanyDao.findAll(40, 10).size());
	}
	
	@Test
	public void testFindNull1() throws Exception {
		Company company = mCompanyDao.find(0);
		
		assertNull(company);
	}
	
	@Test
	public void testFindNull2() throws Exception {
		Company company = mCompanyDao.find(-1);
		
		assertNull(company);
	}
	
	@Test
	public void testFindNull3() throws Exception {
		Company company = mCompanyDao.find(687321324);
		
		assertNull(company);
	}
	
	@Test
	public void testFind1() throws Exception {
		Company company = mCompanyDao.find(1);
		
		assertEquals(1, company.getId());
		assertEquals("Apple Inc.", company.getName());
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void create() throws Exception {
		mCompanyDao.create(null);
	}
	
	@Test
	public void create1() throws Exception {
		try {
			mCompanyDao.create(new Company.Builder().setName("name").build());
			fail();
		} catch (UnsupportedOperationException ignored) {
			
		}
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void update() throws Exception {
		mCompanyDao.update(null);
	}
	
	@Test
	public void update1() throws Exception {
		try {
			mCompanyDao.update(new Company.Builder().setName("name").build());
			fail();
		} catch (UnsupportedOperationException ignored) {
			
		}
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void delete1() throws Exception {
		mCompanyDao.delete(null);
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void delete2() throws Exception {
		mCompanyDao.delete(1);
	}
}
