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
import javax.servlet.http.HttpSession;

import org.devshub.bean.Admin;
import org.devshub.bean.Employee;
import org.devshub.dbservice.AdminDbservice;
import org.devshub.dbservice.EmployeeDbservice;

/**
 * Servlet implementation class Login
 * 
 * @author vishal
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
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
		String email = request.getParameter("email").trim();
		String password = request.getParameter("pass").trim();
		String type = request.getParameter("type").trim();
		Pattern pattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
		Matcher matcher = pattern.matcher(email);
		if (!email.isEmpty() && matcher.matches() && !password.isEmpty() && !type.isEmpty()) {
			if (type.equalsIgnoreCase("employee")) {
				Employee employee = new Employee();
				employee.setEmail(email);
				employee.setPassword(password);
				try {
					if (EmployeeDbservice.validateLogin(employee)) {
						int employeeId = EmployeeDbservice.getEmployeeId(email);
						String employeeName = EmployeeDbservice.getEmployeeName(employeeId);
						if (employeeId > 0 && employeeName != null && !employeeName.isEmpty()) {
							HttpSession httpSession = request.getSession(true);
							httpSession.setAttribute("email", email);
							httpSession.setAttribute("name", employeeName);
							httpSession.setAttribute("empId", employeeId);
							httpSession.setMaxInactiveInterval(60);
							response.sendRedirect("userHome.jsp");
						}else {
							throw new Exception("Something Went Wrong");
						}
					} else {
						errorPage(request, response);
						return;
					}
				} catch (ClassNotFoundException | SQLException | IOException e) {
					e.printStackTrace();
					errorPage(request, response);
					return;
				} catch (Exception e) {
					e.printStackTrace();
					errorPage(request, response);
					return;
				}
			} else if (type.equalsIgnoreCase("admin")) {
				Admin admin = new Admin();
				admin.setEmail(email);
				admin.setPassword(password);
				try {
					if (AdminDbservice.validateLogin(admin)) {
						HttpSession httpSession = request.getSession(true);
						httpSession.setAttribute("email", email);
						httpSession.setMaxInactiveInterval(60);
						response.sendRedirect("adminHome.jsp");
					} else {
						errorPage(request, response);
						return;
					}
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
					errorPage(request, response);
					return;
				} catch (Exception e) {
					e.printStackTrace();
					errorPage(request, response);
					return;
				}
			} else if (type.equalsIgnoreCase("default")) {
				errorPage(request, response);
				return;
			} else {
				errorPage(request, response);
				return;
			}
		} else {
			errorPage(request, response);
			return;
		}
	}

	public static void errorPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("message", "Please Enter Valid Input!!");
		request.setAttribute("link", "login.jsp");
		request.getRequestDispatcher("errorPage.jsp").forward(request, response);
	}

}
