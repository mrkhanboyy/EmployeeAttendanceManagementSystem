package org.devshub.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.devshub.dbservice.EmployeeDbservice;

/**
 * Servlet implementation class DeleteEmployee
 * 
 * @author vishal
 */
@WebServlet("/DeleteEmployee")
public class DeleteEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteEmployee() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String empIdStr = request.getParameter("empId").trim();
			int employeeId = Integer.parseInt(empIdStr);
			if (EmployeeDbservice.deleteEmployee(employeeId)) {
				new ViewEmployees().doPost(request, response);
			} else {
				errorPage(request, response, "Unable To Delete Employee");
				return;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			errorPage(request, response, "Unable To Delete Employee");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			errorPage(request, response, "Something Went Wrong");
			return;
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

	public static void errorPage(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		request.setAttribute("message", message);
		request.setAttribute("link", "viewEmployee.jsp");
		request.getRequestDispatcher("errorPage.jsp").forward(request, response);
	}

}
