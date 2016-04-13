package com.excilys.mviegas.speccdb.servlets;

import com.excilys.mviegas.speccdb.managers.DashboardManagerBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mickael on 23/03/2016.
 */
public class DashboardServlet extends HttpServlet {

	public static final Logger LOGGER = LoggerFactory.getLogger(DashboardServlet.class);

	private DashboardManagerBean mDashboardManagerBean;

	//=============================================================
	// Constructeurs
	//=============================================================
	public DashboardServlet() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("beans.xml");
		DashboardManagerBean bean = appContext.getBean(DashboardManagerBean.class);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Datasource");
			LOGGER.info(String.valueOf(bean));
			LOGGER.info(String.valueOf(bean.hashCode()));
			LOGGER.info(String.valueOf(bean.getConnection()));
		}
		mDashboardManagerBean = bean;
	}

	//=============================================================
	// Getters & Setters
	//=============================================================
	public DashboardManagerBean getDashboardManagerBean() {
		return mDashboardManagerBean;
	}

	public void setDashboardManagerBean(DashboardManagerBean pDashboardManagerBean) {
		mDashboardManagerBean = pDashboardManagerBean;
	}

	//=============================================================
	// Override - HttpServlet
	//=============================================================
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.valueOf(mDashboardManagerBean));
		}

//		DashboardManagerBean dashboardManagerBean = new DashboardManagerBean(req);
		mDashboardManagerBean.getOrder();
		req.setAttribute("dashboardManager", mDashboardManagerBean);
		getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
//		List<String> errors = FormChecker.validate("/views/addComputer.jsp");
//		if (errors.isEmpty()) {
//			ComputerService.getComputerService().addComputer(RequestMapper.toComputer(req));
//			resp.sendRedirect(req.getContextPath() + "/dashboard");
//		} else {
//			req.setAttribute("companies", CompanyService.getCompanyList());
//			req.setAttribute("errors", errors);
//			req.getRequestDispatcher("/views/addComputer.jsp").forward(req, resp);
//		}
	}
}
