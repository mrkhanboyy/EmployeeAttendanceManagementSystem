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
 * Servlet implementation class ChangePassword
 * 
 * @author vishal
 */
@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePassword() {
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
		String oldPassword = request.getParameter("old").trim();
		String newPassword = request.getParameter("new").trim();
		String confirmPassword = request.getParameter("confirm").trim();

		if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
			errorPage(request, response, "Please Enter Valid Input", "login.jsp");
			return;
		} else {
			try {
				String email = request.getSession(false).getAttribute("email").toString().trim();
				if (email.isEmpty() || email == null) {
					errorPage(request, response, "Something Went Wrong", "login.jsp");
					return;
				}
				if (!oldPassword.equals(EmployeeDbservice.getEmployeePassword(email))) {
					errorPage(request, response, "Enter Correct Old Password", "changePassword.jsp");
					return;
				}
				if (!newPassword.equals(confirmPassword)) {
					errorPage(request, response, "Confirm Password Doesn't Match New Password!!", "changePassword.jsp");
					return;
				}
				if (EmployeeDbservice.setNewPassword(email, newPassword)) {
					new Logout().doPost(request, response);
				} else {
					errorPage(request, response, "Something Went Wrong", "login.jsp");
					return;
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				errorPage(request, response, "Something Went Wrong", "login.jsp");
				return;
			} catch (Exception e) {
				e.printStackTrace();
				errorPage(request, response, "Something Went Wrong", "login.jsp");
				return;
			}
		}

	}

	public static void errorPage(HttpServletRequest request, HttpServletResponse response, String message, String link)
			throws ServletException, IOException {
		request.setAttribute("message", message);
		request.setAttribute("link", link);
		request.getRequestDispatcher("errorPage.jsp").forward(request, response);
	}

}
