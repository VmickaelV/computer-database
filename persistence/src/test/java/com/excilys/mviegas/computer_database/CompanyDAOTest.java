package com.excilys.mviegas.computer_database;

import com.excilys.mviegas.computer_database.concurrency.ThreadLocals;
import com.excilys.mviegas.computer_database.data.Company;
import com.excilys.mviegas.computer_database.exceptions.DAOException;
import com.excilys.mviegas.computer_database.persistence.ICompanyDao;
import com.excilys.mviegas.computer_database.persistence.IComputerDao;
import com.excilys.mviegas.computer_database.persistence.Paginator;
import com.excilys.mviegas.computer_database.utils.DatabaseUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Test de la DAO de Companie ({@link Company}
 * 
 * @author Mickael
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:beans-back.xml"})
public class CompanyDAOTest {
	
	//===========================================================
	// Attributs - Private
	//===========================================================
	@Autowired
	private ICompanyDao mCompanyDao;

	@Autowired
	private IComputerDao mComputerDao;
	
	//===========================================================
	// Callbacks
	//===========================================================
	@Before
	public void before() throws Exception {
		DatabaseUtils.resetDatabase();
	}

	@After
	public void tearDown() throws Exception {

	}
	
	//===========================================================
	// Tests
	//===========================================================

	@Test
	public void findAll1() throws Exception {
		assertEquals(42, mCompanyDao.findAll().size());
	}

	@Test
	public void findAllPagintator() throws Exception {
		Paginator<Company> paginator = mCompanyDao.findAllWithPaginator(0, 50);

		assertNotNull(paginator);

		assertEquals(1, paginator.nbPages);
		assertEquals(50, paginator.elementsByPage);
		assertEquals(42, paginator.elementsCount);
		assertEquals(1, paginator.currentPage);
		assertEquals(42, paginator.values.size());
	}

	@Test
	public void findAllPagintator2() throws Exception {
		Paginator<Company> paginator = mCompanyDao.findAllWithPaginator(20, 20);

		assertNotNull(paginator);

		assertEquals(3, paginator.nbPages);
		assertEquals(20, paginator.elementsByPage);
		assertEquals(42, paginator.elementsCount);
		assertEquals(2, paginator.currentPage);
		assertEquals(20, paginator.values.size());
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

		assertEquals(2, paginator.nbPages);
		assertEquals(40, paginator.elementsByPage);
		assertEquals(42, paginator.elementsCount);
		assertEquals(1, paginator.currentPage);
		assertEquals(40, paginator.values.size());
	}

	@Test
	public void findPaginationWithPaginator3() throws Exception {
		Paginator<Company> paginator = mCompanyDao.findAllWithPaginator(40, 10);

		assertEquals(5, paginator.nbPages);
		assertEquals(10, paginator.elementsByPage);
		assertEquals(42, paginator.elementsCount);
		assertEquals(5, paginator.currentPage);
		assertEquals(2, paginator.values.size());
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
	@Ignore("Voir comment gérer bien les transactions")
	public void delete2() throws Exception {
		try {
			mCompanyDao.delete(1);
			fail("delete seems to throw an exception when there are not transaction");
		} catch (IllegalStateException ignored) {

		}
	}

	@Test
	public void delete3() throws Exception {
		mCompanyDao.delete(1);

		assertEquals(41, mCompanyDao.size());
		assertEquals(574-39, mComputerDao.size());
	}

	@Test
	public void delete3_1() throws Exception {
		mCompanyDao.delete(mCompanyDao.find(1));

		assertEquals(41, mCompanyDao.size());
		assertEquals(574-39, mComputerDao.size());
	}

	@Test
	public void delete4() throws Exception {
		assertFalse(mCompanyDao.delete(413213));
		assertEquals(42, mCompanyDao.size());
		assertEquals(574, mComputerDao.size());
	}

//	Voiri si on peut créer un test pour vérifier qu'une transaction est bien présente
//	@Test
//	public void delete4() throws Exception {
//		try {
//			mCompanyDao.delete(413213);
//			fail();
//		} catch (IllegalStateException ignored) {
//
//		}
//		assertEquals(42, mCompanyDao.size());
//		assertEquals(574, mComputerDao.size());
//	}

	@Test
	@Ignore("Non disponible...")
	public void findSQLException() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Mockito.when(connection.createStatement()).thenThrow(new SQLException());

		ThreadLocals.CONNECTIONS.set(connection);

		try {
			find1();
			fail();
		} catch (DAOException ignored) {

		}
	}

	@Test
	@Ignore("Non disponible...")
	public void deleteSQLException() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Mockito.when(connection.createStatement()).thenThrow(new SQLException());

		ThreadLocals.CONNECTIONS.set(connection);

		try {
			delete3();
			fail();
		} catch (DAOException ignored) {

		}
	}
}
