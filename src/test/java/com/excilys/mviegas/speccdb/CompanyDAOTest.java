package com.excilys.mviegas.speccdb;

import com.excilys.mviegas.speccdb.data.Company;
import com.excilys.mviegas.speccdb.persistence.Paginator;
import com.excilys.mviegas.speccdb.persistence.jdbc.CompanyDao;
import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao;
import com.excilys.mviegas.speccdb.persistence.jdbc.DatabaseManager;
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
		assertEquals(42, mCompanyDao.findAll().size());
	}

	@Test
	public void findAllPagintator() throws Exception {
		Paginator<Company> paginator = mCompanyDao.findAllWithPaginator(0, 50);

		assertNotNull(paginator);

		assertEquals(1, paginator.getNbPages());
		assertEquals(50, paginator.getElementsByPage());
		assertEquals(42, paginator.getElementsCount());
		assertEquals(1, paginator.getCurrentPage());
		assertEquals(42, paginator.getValues().size());
	}

	@Test
	public void findAllPagintator2() throws Exception {
		Paginator<Company> paginator = mCompanyDao.findAllWithPaginator(20, 20);

		assertNotNull(paginator);

		assertEquals(3, paginator.getNbPages());
		assertEquals(20, paginator.getElementsByPage());
		assertEquals(42, paginator.getElementsCount());
		assertEquals(2, paginator.getCurrentPage());
		assertEquals(20, paginator.getValues().size());
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
	public void findPaginationWithPaginator1() throws Exception {
		Paginator<Company> paginator = mCompanyDao.findAllWithPaginator(0, 40);

		assertEquals(2, paginator.getNbPages());
		assertEquals(40, paginator.getElementsByPage());
		assertEquals(42, paginator.getElementsCount());
		assertEquals(1, paginator.getCurrentPage());
		assertEquals(40, paginator.getValues().size());
	}

	@Test
	public void findPaginationWithPaginator3() throws Exception {
		Paginator<Company> paginator = mCompanyDao.findAllWithPaginator(40, 10);

		assertEquals(5, paginator.getNbPages());
		assertEquals(10, paginator.getElementsByPage());
		assertEquals(42, paginator.getElementsCount());
		assertEquals(5, paginator.getCurrentPage());
		assertEquals(2, paginator.getValues().size());
	}
	
	@Test
	public void findNull1() throws Exception {
		Company company = mCompanyDao.find(0);
		
		assertNull(company);
	}
	
	@Test
	public void findNull2() throws Exception {
		Company company = mCompanyDao.find(-1);
		
		assertNull(company);
	}
	
	@Test
	public void findNull3() throws Exception {
		Company company = mCompanyDao.find(687321324);
		
		assertNull(company);
	}
	
	@Test
	public void find1() throws Exception {
		Company company = mCompanyDao.find(1);

		assertNotNull(company);
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
	
	@Test(expected = IllegalArgumentException.class)
	public void delete1() throws Exception {
		mCompanyDao.delete(null);
	}
	
	@Test
	public void delete2() throws Exception {
		try {
			mCompanyDao.delete(1);
			fail("delete seems to throw an exception when there are not transaction");
		} catch (IllegalStateException ignored) {

		}
	}

	@Test
	public void delete3() throws Exception {

//		int nCompanies = mCompanyDao.size();

		ComputerDao computerDao = ComputerDao.getInstance();
		computerDao.setConnection(DatabaseManager.getConnection());

//		int nComputers = computerDao.size();

		mCompanyDao.getConnection().setAutoCommit(false);
		mCompanyDao.delete(1);
		mCompanyDao.getConnection().commit();

		assertEquals(41, mCompanyDao.size());
		assertEquals(574-39, computerDao.size());


		DatabaseManager.releaseConnection(computerDao.getConnection());
		ComputerDao.releaseInstance(computerDao);


	}

	@Test
	public void delete4() throws Exception {
		try {
			mCompanyDao.getConnection().setAutoCommit(false);
			mCompanyDao.delete(413213);
			mCompanyDao.getConnection().commit();
			fail();
		} catch (IllegalArgumentException ignored) {

		}
	}


}
