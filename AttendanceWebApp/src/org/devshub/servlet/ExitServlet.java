package org.devshub.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.devshub.dbservice.EmployeeDbservice;

@SuppressWarnings("serial")
public class ExitServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			HttpSession httpSession = request.getSession(true);
			int id = (int) httpSession.getAttribute("empId");
			EmployeeDbservice.doExit(id);
		} catch (ClassNotFoundException | SQLException e) {
			errorMessage(request, response);
		}
		request.getRequestDispatcher("userHome.jsp").forward(request, response);
	}

	public static void errorMessage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("message", "Sorry an Error Has Occured");
		request.setAttribute("link", "userHome.jsp");
		request.getRequestDispatcher("errorPage.jsp").forward(request, response);
	}
}
