package org.devshub.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.devshub.bean.AttandenceHistory;
import org.devshub.dbservice.EmployeeDbservice;

@SuppressWarnings("serial")
public class AttandenceHistoryServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ArrayList<AttandenceHistory> historyList = null;
		try {
			HttpSession httpSession = request.getSession();
			int id = (int) httpSession.getAttribute("empId");
			historyList = EmployeeDbservice.getAttandenceHistory(id);
			request.setAttribute("historyList", historyList);
			request.getRequestDispatcher("viewHistory.jsp").forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			errorMessage(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			errorMessage(request, response);
		}

	}

	public static void errorMessage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("message", "Something Went Wrong");
		request.setAttribute("link", "userHome.jsp");
		request.getRequestDispatcher("errorPage.jsp").forward(request, response);
	}

}
