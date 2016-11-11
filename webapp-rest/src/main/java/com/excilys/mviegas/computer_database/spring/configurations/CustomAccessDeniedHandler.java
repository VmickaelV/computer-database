package com.excilys.mviegas.computer_database.spring.configurations;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author VIEGAS Mickael
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException,
            ServletException {

//        response.sendRedirect(accessDeniedUrl);
        response.getWriter().print("Access denied : " + accessDeniedException.getMessage());
//        request.getSession().setAttribute("message",
//                " Sorry user_dineshonjava You don't have privileges to view this page!!!");

    }
}
