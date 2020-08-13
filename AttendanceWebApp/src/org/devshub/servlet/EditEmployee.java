package org.devshub.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.devshub.bean.Employee;
import org.devshub.dbservice.EmployeeDbservice;

/**
 * Servlet implementation class EditEmployee
 * 
 * @author vishal
 */
@WebServlet("/EditEmployee")
public class EditEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditEmployee() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String empIdStr = request.getParameter("empId").toString().trim();
			if (empIdStr == null || empIdStr.isEmpty()) {
				errorPage(request, response, "Please Enter Valid Input!!");
			} else {
				int employeeId = Integer.parseInt(empIdStr);
				System.out.println(employeeId);
				Employee employee = EmployeeDbservice.getEmployeeDetails(employeeId);
				if (employee == null) {
					errorPage(request, response, "Employee Record Not Found");
				} else {
					request.setAttribute("employee", employee);
					request.getRequestDispatcher("updateEmployee.jsp").forward(request, response);
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			errorPage(request, response, "Unable To Fetch Record");
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
		request.setAttribute("link", "adminHome.jsp");
		request.getRequestDispatcher("errorPage.jsp").forward(request, response);
	}

}
