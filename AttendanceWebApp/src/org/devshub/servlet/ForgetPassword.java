package org.devshub.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.devshub.dbservice.AdminDbservice;
import org.devshub.dbservice.EmployeeDbservice;
import org.devshub.util.Email;

/**
 * Servlet implementation class ForgetPassword
 * 
 * @author vishal
 */
@WebServlet("/ForgetPassword")
public class ForgetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ForgetPassword() {
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
		String from = "vishal.yadav.developer@gmail.com";
		String pass = "JdNR@21eoxPn";
		String subject = "Your Attendance Tracker Password";
		String to = request.getParameter("to").trim();
		String type = request.getParameter("type").trim();
		String message = "";
		if (to.equals("") || to == null) {
			errorPage(request, response, "Invalid Email!!");
			return;
		}
		try {
			if (type.equalsIgnoreCase("admin")) {
				message = "Your Account Password is : " + AdminDbservice.getAdminPassword(to);
			} else if (type.equalsIgnoreCase("employee")) {
				message = "Your Account Password is : " + EmployeeDbservice.getEmployeePassword(to);
			} else {
				errorPage(request, response, "Please Enter Valid Input!!");
				return;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			errorPage(request, response, "Your Account Not Found!!");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			errorPage(request, response, "Something Went Wrong");
		}

		try {
			if (Email.send(from, pass, to, subject, message)) {
				response.sendRedirect("login.jsp");
			} else {
				errorPage(request, response, "Please Enter Valid Input");
				return;
			}
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
			errorPage(request, response, "Mail Not Sent!!");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			errorPage(request, response, "Something Went Wrong");
		}
	}

	public static void errorPage(HttpServletRequest request, HttpServletResponse response, String message)
			throws ServletException, IOException {
		request.setAttribute("message", message);
		request.setAttribute("link", "login.jsp");
		request.getRequestDispatcher("errorPage.jsp").forward(request, response);
	}

}
