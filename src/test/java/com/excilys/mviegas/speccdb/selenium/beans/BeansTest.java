package com.excilys.mviegas.speccdb.selenium.beans;

import com.excilys.mviegas.speccdb.managers.DashboardManagerBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Created by excilys on 15/04/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/beans.xml" })
public class BeansTest {

	@Autowired
	private DashboardManagerBean mDashboardManagerBean;

	@Test
	public void name() throws Exception {
		assertNotNull(mDashboardManagerBean);
		mDashboardManagerBean.update();
	}
}
