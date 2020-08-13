package org.devshub.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.devshub.bean.Employee;
import org.devshub.dbservice.EmployeeDbservice;

/**
 * Servlet implementation class UpdateEmployee
 * 
 * @author vishal
 */
@WebServlet("/UpdateEmployee")
public class UpdateEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateEmployee() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name, gender, email, address;
		int employeeId = 0, age = 0;
		try {
			name = request.getParameter("name").trim();
			if (!request.getParameter("empId").trim().equals("")) {
				employeeId = Integer.parseInt(request.getParameter("empId").trim());
			}
			if (!request.getParameter("age").trim().equals("")) {
				age = Integer.parseInt(request.getParameter("age").trim());
			}
			gender = request.getParameter("gender").trim();
			email = request.getParameter("email").trim();
			address = request.getParameter("address").trim();
		} catch (Exception e) {
			e.printStackTrace();
			errorPage(request, response, "Please Input All Values");
			return;
		}

		Pattern pattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
		Matcher matcher = pattern.matcher(email);
		if (name == null || gender == null || email == null || address == null) {
			errorPage(request, response, "Please Input All Values");
			return;
		} else if (name.isEmpty() || age < 1 || gender.isEmpty() || !matcher.matches() || address.isEmpty()) {
			errorPage(request, response, "Invalid Input");
			return;
		} else {
			Employee employee = new Employee();
			employee.setEmployeeId(employeeId);
			employee.setEmployeeName(name);
			employee.setAge(age);
			employee.setGender(gender);
			employee.setEmail(email);
			employee.setAddress(address);
			try {
				if (EmployeeDbservice.updateEmployee(employee)) {
					new ViewEmployees().doGet(request, response);
				} else {
					errorPage(request, response, "Unable To Update Employee");
					return;
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				errorPage(request, response, "Unable To Update Employee");
				return;
			} catch (Exception e) {
				e.printStackTrace();
				errorPage(request, response, "Something Went Wrong");
				return;
			}
		}
	}

	public static void errorPage(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		request.setAttribute("message", message);
		request.setAttribute("link", "addEmployee.jsp");
		request.getRequestDispatcher("errorPage.jsp").forward(request, response);
	}

}
