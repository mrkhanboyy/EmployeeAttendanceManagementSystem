package org.devshub.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.devshub.bean.AttandenceHistory;
import org.devshub.dbservice.EmployeeDbservice;

/**
 * Servlet implementation class ViewEmployeeHistory
 * 
 * @author vishal
 */
@WebServlet("/ViewEmployeeHistory")
public class ViewEmployeeHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewEmployeeHistory() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int employeeId = 0;
			if (!request.getParameter("empId").trim().equals("")) {
				employeeId = Integer.parseInt(request.getParameter("empId").trim());
			}
			ArrayList<AttandenceHistory> historyList = EmployeeDbservice.getAttandenceHistory(employeeId);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public static void errorMessage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("message", "Something Went Wrong");
		request.setAttribute("link", "userHome.jsp");
		request.getRequestDispatcher("errorPage.jsp").forward(request, response);
	}

}
