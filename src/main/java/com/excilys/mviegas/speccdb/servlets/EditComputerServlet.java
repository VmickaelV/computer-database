package com.excilys.mviegas.speccdb.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Mickael on 23/03/2016.
 */
public class EditComputerServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(req, resp);
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
