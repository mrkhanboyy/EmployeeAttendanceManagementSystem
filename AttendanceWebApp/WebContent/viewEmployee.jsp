<%@page import="org.devshub.bean.Employee"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" errorPage="error.jsp"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<title>View Employee</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!--===============================================================================================-->
	<link rel="icon" type="image/png" href="images/icons/favicon.ico" />
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="fonts/iconic/css/material-design-iconic-font.min.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/animsition/css/animsition.min.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/daterangepicker/daterangepicker.css">
	<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="css/util.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
	<!--===============================================================================================-->
	<style type="text/css">
		.wrap-login100 {
			width: 95%;
		}
	</style>
</head>

<body>
	<% 
		if(session.getAttribute("email") == null){
			response.sendRedirect("login.jsp");
		}
	%>
		
		<div class="limiter">
		<div class="container-login100" style="background-image: url('images/bg-masthead.jpg');">
			<div class="wrap-login100">
				<%
					ArrayList<Employee> employees = (ArrayList<Employee>)session.getAttribute("employees");
					if(employees == null || employees.isEmpty()){
				%>
						<div class="message p-b-34 p-t-27"> 
							No Record Found
						</div>
				<%
					} else {
				%>
					<div class="container table-responsive py-5">
					
						<table class="table table-bordered table-hover">
							<thead class="thead-dark">
								<tr>
									<th scope="col">Sr. No</th>
									<th scope="col">Id</th>
									<th scope="col">Name</th>
									<th scope="col">Email</th>
									<th scope="col">Gender</th>
									<th scope="col">Age</th>
									<th scope="col">Address</th>
									<th scope="col" colspan="3"></th>
								</tr>
							</thead>
							<tbody>
								<%
									int srNo = 0;
									for(Employee employee : employees){
										srNo++;
								%>
									<tr>
									<th scope="row"><%=srNo %></th>
									<td><%=employee.getEmployeeId() %></td>
									<td><%=employee.getEmployeeName() %></td>
									<td><%=employee.getEmail() %></td>
									<td><%=employee.getGender() %></td>
									<td><%=employee.getAge() %></td>
									<td><%=employee.getAddress() %></td>
									<td>
										<div class="container-login100-form-btn mt-3 mb-3">
											<a class="txt1" href="viewHistory?empId=<%=employee.getEmployeeId()%>">
												<button class="login100-form-btn" type="button">View History</button>
											</a>
										</div>
									</td>
									<td>
										<div class="container-login100-form-btn mt-3 mb-3">
											<a class="txt1" href="editEmployee?empId=<%=employee.getEmployeeId()%>">
												<button class="login100-form-btn" type="button">Edit</button>
											</a>
										</div>
									</td>
									<td>
										<div class="container-login100-form-btn mt-3 mb-3">
											<a class="txt1" href="deleteEmployee?empId=<%=employee.getEmployeeId()%>">
												<button class="login100-form-btn" type="button">Delete</button>
											</a>
										</div>
									</td>
								</tr>
								<%
									}
								%>
							</tbody>
						</table>
					</div>
					
					<%
						}
					%>

				<div class="container-login100-form-btn mt-3 mb-3">
						<a class="txt1" href="adminHome.jsp">
							<button class="login100-form-btn" type="button">Back</button>
						</a>
				</div>
			</div>
			
		</div>
	</div>

	<div id="dropDownSelect1"></div>

	<!--===============================================================================================-->
	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
	<!--===============================================================================================-->
	<script src="vendor/animsition/js/animsition.min.js"></script>
	<!--===============================================================================================-->
	<script src="vendor/bootstrap/js/popper.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
	<!--===============================================================================================-->
	<script src="vendor/select2/select2.min.js"></script>
	<!--===============================================================================================-->
	<script src="vendor/daterangepicker/moment.min.js"></script>
	<script src="vendor/daterangepicker/daterangepicker.js"></script>
	<!--===============================================================================================-->
	<script src="vendor/countdowntime/countdowntime.js"></script>
	<!--===============================================================================================-->
	<script src="js/main.js"></script>

</body>

</html>