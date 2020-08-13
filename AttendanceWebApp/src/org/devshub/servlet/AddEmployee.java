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
import org.devshub.util.Email;
import org.devshub.util.PasswordGenerator;

/**
 * Servlet implementation class AddEmployee
 * 
 * @author vishal
 */
@WebServlet("/AddEmployee")
public class AddEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddEmployee() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name, ageStr, gender, email, address;
		final String pass = PasswordGenerator.generateRandomPassword(8);
		final String from = "vishal.yadav.developer@gmail.com";
		final String mailerPassword = "JdNR@21eoxPn";
		final String subject = "Your Attendance Tracker Account Created";
		try {
			name = request.getParameter("name").trim();
			ageStr = request.getParameter("age").trim();
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

		int age = 0;
		if (ageStr.equals("") || ageStr.isEmpty() || ageStr == null) {
			age = 0;
		} else {
			age = Integer.parseInt(ageStr);
		}

		System.out.println(name + " " + age + " " + gender + " " + email + " " + matcher.matches() + " " + address);

		if (name == null || gender == null || email == null || address == null) {
			errorPage(request, response, "Please Input All Values");
			return;
		} else if (name.isEmpty() || age < 1 || gender.isEmpty() || !matcher.matches() || address.isEmpty()) {
			errorPage(request, response, "Invalid Input");
			return;
		} else {
			Employee employee = new Employee();
			employee.setEmployeeName(name);
			employee.setAge(age);
			employee.setGender(gender);
			employee.setEmail(email);
			employee.setAddress(address);
			employee.setPassword(pass);
			try {
				if (EmployeeDbservice.addEmployee(employee)) {
					if (!Email.send(from, mailerPassword, email, subject, "Your Attendance Tracker Account Password is " + pass)) {
						errorPage(request, response, "Unable To Send Mail");
						return;
					}
					new ViewEmployees().doPost(request, response);
				} else {
					errorPage(request, response, "Unable To Add Employee");
					return;
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				errorPage(request, response, "Unable To Add Employee");
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
