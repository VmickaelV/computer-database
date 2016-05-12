package com.excilys.mviegas.speccdb;

import com.excilys.mviegas.speccdb.data.Computer;
import com.excilys.mviegas.speccdb.exceptions.DAOException;
import com.excilys.mviegas.speccdb.persistence.ICompanyDao;
import com.excilys.mviegas.speccdb.persistence.IComputerDao;
import com.excilys.mviegas.speccdb.persistence.Paginator;
import com.excilys.mviegas.speccdb.persistence.QueryParameter;
import com.excilys.mviegas.speccdb.persistence.jdbc.ComputerDao;
import com.excilys.mviegas.speccdb.utils.DatabaseUtils;
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
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:beans-back.xml"})
public class ComputerDAOTest {
	
	//===========================================================
	// Constantes
	//===========================================================
		
	public static final int SIZE_COMPUTER = 574;
	
	//===========================================================
	// Attribut s- private
	//===========================================================

	@Autowired
	private IComputerDao mComputerDao;

	@Autowired
	private ICompanyDao mCompanyDao;
	
	//===========================================================
	// Callbacks
	//===========================================================
	@Before
	public void before() throws Exception {
		DatabaseUtils.resetDatabase();
	}

	//===========================================================
	// Tests
	//===========================================================
			
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
		
		assertEquals(5, computer.getId());
		assertEquals("CM-5", computer.getName());
		assertNull(computer.getDiscontinuedDate());
		assertEquals(LocalDate.of(1991, 1, 1), computer.getIntroducedDate());
		assertEquals(computer.getManufacturer(), mCompanyDao.find(2));
	}

	@Test
	public void findAllPaginator1() throws Exception {
		Paginator<Computer> paginator = mComputerDao.findAllWithPaginator(0, 50);

		assertNotNull(paginator);

		assertEquals(12, paginator.nbPages);
		assertEquals(50, paginator.elementsByPage);
		assertEquals(SIZE_COMPUTER, paginator.elementsCount);
		assertEquals(1, paginator.currentPage);
		assertEquals(50, paginator.values.size());
	}

	@Test
	public void findAllPaginator2() throws Exception {
		Paginator<Computer> paginator = mComputerDao.findAllWithPaginator(100, ComputerDao.BASE_SIZE_PAGE);

		assertNotNull(paginator);

		assertEquals(SIZE_COMPUTER/ComputerDao.BASE_SIZE_PAGE+1, paginator.nbPages);
		assertEquals(ComputerDao.BASE_SIZE_PAGE, paginator.elementsByPage);
		assertEquals(SIZE_COMPUTER, paginator.elementsCount);
		assertEquals(2, paginator.currentPage);
		assertEquals(ComputerDao.BASE_SIZE_PAGE, paginator.values.size());
	}
	
	@Test
	public void findAllPaginator3() throws Exception {
		Paginator<Computer> paginator = mComputerDao.findAllWithPaginator(0, 1000);

		assertNotNull(paginator);

		assertEquals(1, paginator.nbPages);
		assertEquals(1000, paginator.elementsByPage);
		assertEquals(SIZE_COMPUTER, paginator.elementsCount);
		assertEquals(1, paginator.currentPage);
		assertEquals(SIZE_COMPUTER, paginator.values.size());
	}

	@Test
	public void findAllPaginator4() throws Exception {
		Paginator<Computer> paginator = mComputerDao.findAllWithPaginator(500, 100);

		assertNotNull(paginator);

		assertEquals(6, paginator.nbPages);
		assertEquals(100, paginator.elementsByPage);
		assertEquals(SIZE_COMPUTER, paginator.elementsCount);
		assertEquals(6, paginator.currentPage);
		assertEquals(SIZE_COMPUTER-500, paginator.values.size());
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
	public void create3() throws Exception {
		int n = mComputerDao.size();
		String desiredName = "Un autre nom";
		LocalDate desiredDiscontinuedDate = LocalDate.of(2005, 6, 22);
		
		Computer computer;
		assertNotNull(computer = mComputerDao.create(
				new Computer.Builder()
				.setName(desiredName)
				.setDiscontinuedDate(desiredDiscontinuedDate)
				.build()));
		assertEquals(SIZE_COMPUTER+1, mComputerDao.size());
		
		assertThat(computer.getId(), org.hamcrest.Matchers.greaterThan(0L));
		
		assertEquals(desiredName, computer.getName());
		assertEquals(desiredDiscontinuedDate, computer.getDiscontinuedDate());
		assertEquals(n+1, mComputerDao.size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void update() throws Exception {
		assertNull( mComputerDao.update(null));
	}
	
	@Test
	@Ignore("Voir si on décide d'interge l'update sur un objet non persisté")
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
	@Test
	public void update31OnlyName() throws Exception {

		Computer computer = mComputerDao.find(4);
		assertEquals("CM-5e", computer.getName());

		computer.setName("Hasard");

		computer = mComputerDao.update(computer);

		assertNotNull(computer);

		assertEquals("Hasard", computer.getName());

		computer = mComputerDao.find(4);

		assertEquals("Hasard", computer.getName());
	}
	
	@Test
	public void update4() throws Exception {
		
		Computer computer = mComputerDao.find(5);
		
		LocalDate localDateIntroduced = computer.getIntroducedDate();
		LocalDate localDateDiscontinued = computer.getDiscontinuedDate();
		LocalDate localDateDesiredIntroduced = LocalDate.of(1926, 3, 30);
		LocalDate localDateDesiredDiscontinued = LocalDate.of(1989, 6, 3);
		
		assertEquals(LocalDate.of(1991, 1, 1), computer.getIntroducedDate());
		assertEquals(null, computer.getDiscontinuedDate());
		
		computer.setDiscontinuedDate(localDateDesiredDiscontinued);
		
		mComputerDao.update(computer);
		computer = mComputerDao.find(5);
		
		assertNotNull(computer);
		
		assertEquals(localDateDesiredDiscontinued, computer.getDiscontinuedDate());
		assertEquals(localDateDesiredDiscontinued, mComputerDao.find(5).getDiscontinuedDate());
		assertNotEquals(localDateDiscontinued, mComputerDao.find(5).getDiscontinuedDate());
		assertEquals(LocalDate.of(1991, 1, 1), mComputerDao.find(5).getIntroducedDate());
	}
	
	@Test
	public void update6() throws Exception {
		
		Computer computer = mComputerDao.find(5);

		String previousName = computer.getName();
		String desiredName = "Un nom désiré";
		LocalDate localDateIntroduced = computer.getIntroducedDate();
		LocalDate localDateDiscontinued = computer.getDiscontinuedDate();
		LocalDate localDateDesiredIntroduced = LocalDate.of(1926, 3, 30);
		LocalDate localDateDesiredDiscontinued = LocalDate.of(1950, 6, 3);

		assertEquals(previousName, computer.getName());
		assertEquals(LocalDate.of(1991, 1, 1), computer.getIntroducedDate());
		assertEquals(null, computer.getDiscontinuedDate());
		
		computer.setDiscontinuedDate(localDateDesiredDiscontinued);
		computer.setName(desiredName);
		
		mComputerDao.update(computer);
		computer = mComputerDao.find(5);
		
		assertNotNull(computer);

		assertEquals(localDateDesiredDiscontinued, computer.getDiscontinuedDate());
		assertEquals(localDateDesiredDiscontinued, mComputerDao.find(5).getDiscontinuedDate());
		assertNotEquals(localDateDiscontinued, mComputerDao.find(5).getDiscontinuedDate());
		assertEquals(LocalDate.of(1991, 1, 1), mComputerDao.find(5).getIntroducedDate());
		assertEquals(desiredName, computer.getName());
	}
	
	@Test
	public void update7() throws Exception {
		
		Computer computer = mComputerDao.find(5);
		
		LocalDate localDateIntroduced = computer.getIntroducedDate();
		LocalDate localDateDiscontinued = computer.getDiscontinuedDate();
		LocalDate localDateDesiredIntroduced = LocalDate.of(1926, 3, 30);
		LocalDate localDateDesiredDiscontinued = LocalDate.of(1750, 6, 3);
		
		assertEquals(LocalDate.of(1991, 1, 1), computer.getIntroducedDate());
		assertEquals(null, computer.getDiscontinuedDate());
		
		computer.setDiscontinuedDate(localDateDesiredDiscontinued);
		
		mComputerDao.update(computer);
		computer = mComputerDao.find(5);
		
		assertNotNull(computer);
		
		assertEquals(localDateDesiredDiscontinued, computer.getDiscontinuedDate());
		assertEquals(localDateDesiredDiscontinued, mComputerDao.find(5).getDiscontinuedDate());
		assertNotEquals(localDateDiscontinued, mComputerDao.find(5).getDiscontinuedDate());
		assertEquals(LocalDate.of(1991, 1, 1), mComputerDao.find(5).getIntroducedDate());
	}
	
	@Test
	public void update5() throws Exception {
		Computer computer = mComputerDao.find(5);
		
		LocalDate localDateIntroduced = computer.getIntroducedDate();
		LocalDate localDateDiscontinued = computer.getDiscontinuedDate();
		LocalDate localDateDesiredIntroduced = LocalDate.of(1926, 3, 30);
		LocalDate localDateDesiredDiscontinued = LocalDate.of(1989, 6, 3);
		
		assertEquals(LocalDate.of(1991, 1, 1), computer.getIntroducedDate());
		assertEquals(null, computer.getDiscontinuedDate());
		
		computer.setIntroducedDate(localDateDesiredIntroduced);
		
		mComputerDao.update(computer);
		computer = mComputerDao.find(5);
		
		assertNotNull(computer);
		
		assertEquals(localDateDesiredIntroduced, computer.getIntroducedDate());
		assertEquals(localDateDesiredIntroduced, mComputerDao.find(5).getIntroducedDate());
		assertNotEquals(localDateIntroduced, mComputerDao.find(5).getIntroducedDate());
		assertEquals(localDateDiscontinued, mComputerDao.find(5).getDiscontinuedDate());
		assertEquals(null, mComputerDao.find(5).getDiscontinuedDate());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void delete1() throws Exception {
		mComputerDao.delete(null);
	}
	
	// TODO voir si on continue
	public void delete2() throws Exception {
		assertFalse(mComputerDao.delete(0));
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
		assertEquals("Error on "+paginator.toString(), 1, paginator.nbPages);
		assertEquals(20, paginator.elementsByPage);
		assertEquals(13, paginator.elementsCount);
		assertEquals(1, paginator.currentPage);
		assertEquals(13, paginator.values.size());
	}

	@Test
	public void searchByName2() throws Exception {
		assertEquals(574, mComputerDao.size());
		Paginator<Computer> paginator = mComputerDao.findWithNamedQueryWithPaginator(ComputerDao.NamedQueries.SEARCH, QueryParameter.with(ComputerDao.Parameters.FILTER_NAME, "pow").and(ComputerDao.Parameters.SIZE, 20).parameters());

		assertNotNull(paginator);
		assertEquals("Error on "+paginator.toString(), 3, paginator.nbPages);
		assertEquals(20, paginator.elementsByPage);
		assertEquals(47, paginator.elementsCount);
		assertEquals(1, paginator.currentPage);
		assertEquals(20, paginator.values.size());
	}

	@Test
	public void searchByName3() throws Exception {
		Paginator<Computer> paginator = mComputerDao.findWithNamedQueryWithPaginator(ComputerDao.NamedQueries.SEARCH, QueryParameter.with(ComputerDao.Parameters.FILTER_NAME, "pow").and(ComputerDao.Parameters.START, 40).and(ComputerDao.Parameters.SIZE, 20).parameters());

		assertNotNull(paginator);
		assertEquals("Error on "+paginator.toString(), 3, paginator.nbPages);
		assertEquals(20, paginator.elementsByPage);
		assertEquals(47, paginator.elementsCount);
		assertEquals(3, paginator.currentPage);
		assertEquals(7, paginator.values.size());
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

	@Test
	public void sortByName1() throws Exception {
		Paginator<Computer> paginator = mComputerDao.findWithNamedQueryWithPaginator(ComputerDao.NamedQueries.SEARCH, QueryParameter.with(ComputerDao.Parameters.FILTER_NAME, "pow").and(ComputerDao.Parameters.SIZE, 20).and(ComputerDao.Parameters.ORDER, ComputerDao.Order.NAME).parameters());


		List<Computer> list = Arrays.asList("361", "204", "226", "218", "28", "213", "245", "246", "243", "220", "210", "238", "239", "205", "207", "208", "219", "217", "211", "221").stream().map(i -> {
			try {
				return mComputerDao.find(Long.parseLong(i));
			} catch (DAOException pE) {
				throw new RuntimeException();
			}
		}).collect(Collectors.toList());

		assertNotNull(paginator);
		assertEquals("Error on "+paginator.toString(), 3, paginator.nbPages);
		assertEquals(20, paginator.elementsByPage);
		assertEquals(47, paginator.elementsCount);
		assertEquals(1, paginator.currentPage);
		assertEquals(20, paginator.values.size());

		assertEquals(list, paginator.values);
	}

	@Test
	public void noSortByName2() throws Exception {
		Paginator<Computer> paginator = mComputerDao.findWithNamedQueryWithPaginator(ComputerDao.NamedQueries.SEARCH, QueryParameter.with(ComputerDao.Parameters.FILTER_NAME, "pow").and(ComputerDao.Parameters.SIZE, 20).parameters());


		List<Computer> list = Arrays.asList("361", "204", "226", "218", "28", "213", "245", "246", "243", "220", "210", "238", "239", "205", "207", "208", "219", "217", "211", "221").stream().map(i -> {
			try {
				return mComputerDao.find(Long.parseLong(i));
			} catch (DAOException pE) {
				throw new RuntimeException();
			}
		}).collect(Collectors.toList());

		assertNotNull(paginator);
		assertEquals("Error on "+paginator.toString(), 3, paginator.nbPages);
		assertEquals(20, paginator.elementsByPage);
		assertEquals(47, paginator.elementsCount);
		assertEquals(1, paginator.currentPage);
		assertEquals(20, paginator.values.size());

		assertNotEquals(list, paginator.values);
	}

}
