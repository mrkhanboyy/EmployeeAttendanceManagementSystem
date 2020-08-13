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
 * Servlet implementation class SearchEmployee
 * 
 * @author vishal
 */
@WebServlet("/SearchEmployee")
public class SearchEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchEmployee() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name, gender, email;
		int age = 0;
		try {
			name = request.getParameter("name").trim();
			if (!request.getParameter("age").trim().equals("")) {
				age = Integer.parseInt(request.getParameter("age").trim());
			}
			gender = request.getParameter("gender").trim();
			email = request.getParameter("email").trim();
			
		} catch (Exception e) {
			e.printStackTrace();
			errorPage(request, response);
			return;
		}
		
		Employee employee = new Employee();
		employee.setEmployeeName(name);
		employee.setAge(age);
		employee.setGender(gender);
		employee.setEmail(email);
		try {
			ArrayList<Employee> employees = EmployeeDbservice.searchEmployee(employee);
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("employees", employees);
			response.sendRedirect("viewEmployee.jsp");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			errorPage(request, response);
		}catch (Exception e) {
			e.printStackTrace();
			errorPage(request, response);
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

	public static void errorPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("message", "Unable To Search Employees");
		request.setAttribute("link", "searchEmployee.jsp");
		request.getRequestDispatcher("errorPage.jsp").forward(request, response);
	}

}
