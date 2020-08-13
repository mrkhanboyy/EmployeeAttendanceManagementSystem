package org.devshub.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.devshub.bean.Employee;
import org.devshub.dbservice.EmployeeDbservice;

/**
 * Servlet implementation class ViewEmployees
 * 
 * @author vishal
 */
@WebServlet("/ViewEmployees")
public class ViewEmployees extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewEmployees() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ArrayList<Employee> employees = EmployeeDbservice.getAllEmployees();
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("employees", employees);
			response.sendRedirect("viewEmployee.jsp");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			errorPage(request, response, "Unable To Fetch Records");
		} catch (Exception e) {
			e.printStackTrace();
			errorPage(request, response, "Something Went Wrong");
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
		request.setAttribute("link", "login.jsp");
		request.getRequestDispatcher("errorPage.jsp").forward(request, response);
	}

}
