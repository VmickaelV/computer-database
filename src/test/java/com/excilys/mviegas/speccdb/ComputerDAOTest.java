package com.excilys.mviegas.speccdb;

import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persist.Paginator;
import com.excilys.mviegas.speccdb.persist.QueryParameter;
import com.excilys.mviegas.speccdb.persist.jdbc.CompanyDao;
import com.excilys.mviegas.speccdb.persist.jdbc.ComputerDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class ComputerDAOTest {
	
	private ComputerDao mComputerDao = ComputerDao.INSTANCE;
	private CompanyDao mCompanyDao = CompanyDao.INSTANCE;
	
	public static final int SIZE_COMPUTER = 574;

	@Before
	public void before() throws Exception {
		DatabaseManagerTest.resetDatabase();
	}
	
	@Test
	public void findAll1() throws Exception {
		assertEquals(ComputerDao.BASE_SIZE_PAGE, mComputerDao.findAll().size());
	}
	
	@Test
	public void findPagination1() throws Exception {
		assertEquals(40, mComputerDao.findAll(0, 40).size());
		
		assertEquals(SIZE_COMPUTER, mComputerDao.size());
	}

	@Test
	public void findPagination2() throws Exception {
		assertEquals(ComputerDao.BASE_SIZE_PAGE, mComputerDao.findAll(100, ComputerDao.BASE_SIZE_PAGE).size());
	}
	
	@Test
	public void findPagination3() throws Exception {
		assertEquals(574, mComputerDao.findAll(0, 1000).size());
	}
	
	@Test
	public void findPagination4() throws Exception {
		assertEquals(74, mComputerDao.findAll(500, 100).size());
	}
	
	@Test
	public void testFindNull1() throws Exception {
		Computer computer = mComputerDao.find(0);
		
		assertNull(computer);
	}
	
	@Test
	public void testFindNull2() throws Exception {
		Computer computer = mComputerDao.find(-1);
		
		assertNull(computer);
	}
	
	@Test
	public void testFindNull3() throws Exception {
		Computer computer = mComputerDao.find(687321324);
		
		assertNull(computer);
	}

	@Test
	public void testFind1() throws Exception {
		Computer computer = mComputerDao.find(200);
		
		assertEquals(200, computer.getId());
		assertEquals("PowerBook 500 series", computer.getName());
		assertNull(computer.getDiscontinuedDate());
		assertNull(computer.getIntroducedDate());
		assertNull(computer.getManufacturer());
	}
	
	@Test
	public void testFind2() throws Exception {
		Computer computer = mComputerDao.find(5);
		
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		assertEquals(5, computer.getId());
		assertEquals("CM-5", computer.getName());
		assertNull(computer.getDiscontinuedDate());
		assertEquals(dateTimeFormatter.parse("1991/01/01"), computer.getIntroducedDate());
		assertEquals(computer.getManufacturer(), CompanyDao.INSTANCE.find(2));
	}

	@Test
	public void findAllPaginator1() throws Exception {
		Paginator<Computer> paginator = mComputerDao.findAllWithPaginator(0, 50);

		assertNotNull(paginator);

		assertEquals(12, paginator.getNbPages());
		assertEquals(50, paginator.getElementsByPage());
		assertEquals(SIZE_COMPUTER, paginator.getElementsCount());
		assertEquals(1, paginator.getCurrentPage());
		assertEquals(50, paginator.getValues().size());
	}

	@Test
	public void findAllPaginator2() throws Exception {
		Paginator<Computer> paginator = mComputerDao.findAllWithPaginator(100, ComputerDao.BASE_SIZE_PAGE);

		assertNotNull(paginator);

		assertEquals(SIZE_COMPUTER/ComputerDao.BASE_SIZE_PAGE+1, paginator.getNbPages());
		assertEquals(ComputerDao.BASE_SIZE_PAGE, paginator.getElementsByPage());
		assertEquals(SIZE_COMPUTER, paginator.getElementsCount());
		assertEquals(2, paginator.getCurrentPage());
		assertEquals(ComputerDao.BASE_SIZE_PAGE, paginator.getValues().size());
	}
	
	@Test
	public void findAllPaginator3() throws Exception {
		Paginator<Computer> paginator = mComputerDao.findAllWithPaginator(0, 1000);

		assertNotNull(paginator);

		assertEquals(1, paginator.getNbPages());
		assertEquals(1000, paginator.getElementsByPage());
		assertEquals(SIZE_COMPUTER, paginator.getElementsCount());
		assertEquals(1, paginator.getCurrentPage());
		assertEquals(SIZE_COMPUTER, paginator.getValues().size());
	}

	@Test
	public void findAllPaginator4() throws Exception {
		Paginator<Computer> paginator = mComputerDao.findAllWithPaginator(500, 100);

		assertNotNull(paginator);

		assertEquals(6, paginator.getNbPages());
		assertEquals(100, paginator.getElementsByPage());
		assertEquals(SIZE_COMPUTER, paginator.getElementsCount());
		assertEquals(6, paginator.getCurrentPage());
		assertEquals(SIZE_COMPUTER-500, paginator.getValues().size());
	}

	@Test
	public void create() throws Exception {
		try {
			assertNull(mComputerDao.create(null));
			fail();
		} catch (IllegalArgumentException ignored) {

		}
	}
	
	@Test
	public void create1() throws Exception {
		try {
			mComputerDao.create(mComputerDao.find(5));
			fail();
		} catch (IllegalArgumentException ignored) {

		}
	}
	
	@Test
	public void create2() throws Exception {
		assertNotNull(mComputerDao.create(new Computer.Builder().setName("Un autre nom").build()));
		assertEquals(SIZE_COMPUTER+1, mComputerDao.size());
	}



	@Test
	public void createerrorTimeStamp() throws Exception {
		try {
			mComputerDao.create(new Computer.Builder()
					.setName("un nom")
					.setIntroducedDate(LocalDate.of(1969, 1, 1))
					.build());
			fail();
		} catch (DAOException ignored) {

		}

	}
	
	@Test(expected = IllegalArgumentException.class)
	public void update() throws Exception {
		assertNull( mComputerDao.update(null));
	}
	
	@Test
	public void update1() throws Exception {
		try {
			mComputerDao.update(new Computer.Builder().setName("name").build());
			fail();
		} catch (IllegalArgumentException e) {
			if (!e.getMessage().equals("Null or Not Persisted Object")) {
				fail();
			}
		}
	}
	
	@Test
	public void update2() throws Exception {
		
		Computer computer = mComputerDao.find(4);
		
		assertEquals(mCompanyDao.find(2), computer.getManufacturer());
		
		assertNotNull(mComputerDao.update(computer));
	}
	
	@Test
	public void update3() throws Exception {
		
		Computer computer = mComputerDao.find(4);
		
		assertEquals(mCompanyDao.find(2), computer.getManufacturer());
		
		computer.setManufacturer(mCompanyDao.find(80));
		
		computer = mComputerDao.update(computer);
		
		assertNotNull(computer);
		
		assertEquals(mCompanyDao.find(80), computer.getManufacturer());
		
		computer = mComputerDao.find(4);
		
		assertEquals(mCompanyDao.find(80), computer.getManufacturer());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void delete1() throws Exception {
		mComputerDao.delete(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void delete2() throws Exception {
		mComputerDao.delete(0);
	}
	
	@Test
	public void delete3() throws Exception {
		assertFalse(mComputerDao.delete(644312312));
	}
	
	@Test
	public void delete4() throws Exception {
		assertNotNull(mComputerDao.find(2));
		assertTrue(mComputerDao.delete(2));
	}
	
	@Test
	public void delete5() throws Exception {
		assertNotNull(mComputerDao.find(2));
		assertTrue(mComputerDao.delete(2));
		assertFalse(mComputerDao.delete(2));
		assertNull(mComputerDao.find(2));
	}

	@Test
	public void searchByName1() throws Exception {
		Paginator<Computer> paginator = mComputerDao.findWithNamedQueryWithPaginator(ComputerDao.NamedQueries.SEARCH, QueryParameter.with(ComputerDao.Parameters.FILTER_NAME, "apple").and(ComputerDao.Parameters.SIZE, 20).parameters());

		assertNotNull(paginator);
		assertEquals("Error on "+paginator.toString(), 1, paginator.getNbPages());
		assertEquals(20, paginator.getElementsByPage());
		assertEquals(13, paginator.getElementsCount());
		assertEquals(1, paginator.getCurrentPage());
		assertEquals(13, paginator.getValues().size());
	}

	@Test
	public void searchByName2() throws Exception {
		Paginator<Computer> paginator = mComputerDao.findWithNamedQueryWithPaginator(ComputerDao.NamedQueries.SEARCH, QueryParameter.with(ComputerDao.Parameters.FILTER_NAME, "pow").and(ComputerDao.Parameters.SIZE, 20).parameters());

		assertNotNull(paginator);
		assertEquals("Error on "+paginator.toString(), 3, paginator.getNbPages());
		assertEquals(20, paginator.getElementsByPage());
		assertEquals(47, paginator.getElementsCount());
		assertEquals(1, paginator.getCurrentPage());
		assertEquals(20, paginator.getValues().size());
	}

	@Test
	public void searchByName3() throws Exception {
		Paginator<Computer> paginator = mComputerDao.findWithNamedQueryWithPaginator(ComputerDao.NamedQueries.SEARCH, QueryParameter.with(ComputerDao.Parameters.FILTER_NAME, "pow").and(ComputerDao.Parameters.START, 40).and(ComputerDao.Parameters.SIZE, 20).parameters());

		assertNotNull(paginator);
		assertEquals("Error on "+paginator.toString(), 3, paginator.getNbPages());
		assertEquals(20, paginator.getElementsByPage());
		assertEquals(47, paginator.getElementsCount());
		assertEquals(3, paginator.getCurrentPage());
		assertEquals(7, paginator.getValues().size());
	}

	@Test
	public void testName() throws Exception {
		Connection connection = Mockito.mock(Connection.class);
		Statement statement = Mockito.mock(Statement.class);
		
		assertFalse(statement.execute("SELECT * FROM company"));
		assertEquals(0, statement.executeUpdate("SELECT * FROM company"));
		
		Mockito.when(statement.executeQuery(Mockito.anyString())).thenThrow(SQLException.class);
		
		try {
			statement.executeQuery("SELECT * FROM company");
			fail();
		} catch (SQLException ignored) {
			
		}
	}
	
	
//	TODO a finir
	public static void assertCompanyEquals(Computer pComputer1, Computer pComputer2) {
		if (pComputer1 == pComputer2) {
			return;
		}
		
//		if ()
	}
	
	
	public void main() {
//		mComputerDao.create(new Company("Ma Companie"));
//		
//		System.out.println(mComputerDao.findAll(6, 10));
//		System.out.println(mComputerDao.findAll(6, 10).size());
//		
//		Company company = new Company("Ma Companie"); 
//		Company companyAfter = mComputerDao.create(company);
//		
//		System.out.println(company);
//		System.out.println(companyAfter);
//		
//		System.out.println(mComputerDao.find(2));
	}
}
