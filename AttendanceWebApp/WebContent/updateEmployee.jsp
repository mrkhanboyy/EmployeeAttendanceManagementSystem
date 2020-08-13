<%@page import="org.devshub.bean.Employee"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" errorPage="error.jsp" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Update Employee</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
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
</head>
<body>
	<%
		if(session.getAttribute("email") == null){
			response.sendRedirect("login.jsp");
		}
		Employee employee = (Employee)request.getAttribute("employee");
	%>
	<div class="limiter">
		<div class="container-login100" style="background-image: url('images/bg-01.jpg');">
			<div class="wrap-login100">
				<form class="login100-form validate-form" action="updateEmployee?empId=<%=employee.getEmployeeId()%>" method="post">
					<span class="login100-form-logo">
						<i class="zmdi zmdi-account"></i>
					</span>
					
					<%
						if(employee == null){
					%>
							<div class="message p-b-34 p-t-27"> 
								No Record Found
							</div>
					<%
						} else {
					%>

							<span class="login100-form-title p-b-34 p-t-27">
								Update Employee
		                    </span>
		                    
		                    <div class="wrap-input100">
								<input class="input100" type="text" name="name" value="<%=employee.getEmployeeName() %>" placeholder="Name">
								<span class="focus-input100" data-placeholder="&#xf207;"></span>
							</div>
		
							<div class="wrap-input100">
								<input class="input100" min="1" type="number" name="age" value="<%=employee.getAge() %>" placeholder="Age">
								<span class="focus-input100" data-placeholder="&#xf207;"></span>
							</div>
		
							<div class="wrap-input100">
								<select class="select100" name="gender">
									<%
										if(employee.getGender().equalsIgnoreCase("male")){
									%>
										<option value="male" selected="selected">Male</option>
										<option value="female">Female</option>
									<%
										} else if(employee.getGender().equalsIgnoreCase("female")){
									%>
										<option value="female" selected="selected">Female</option>
										<option value="male">Male</option>
									<%
										}
									%>
								</select>
								<span class="focus-input100" data-placeholder="&#xf387;"></span>
							</div>
		
							<div class="wrap-input100 validate-input" data-validate = "Enter Email">
								<input class="input100" type="email" name="email" value="<%=employee.getEmail() %>" placeholder="Email">
								<span class="focus-input100" data-placeholder="&#xf207;"></span>
							</div>
		
		
							<div class="wrap-input100">
								<textarea class="textarea100" rows="3" name="address" placeholder="Address"><%=employee.getAddress() %></textarea>
							</div>
		
							<div class="container-login100-form-btn">
								<button class="login100-form-btn" type="submit">
									Update
								</button>
							</div>
					
					<%
						}
					%>
					<div class="text-center p-t-40">
						<a class="txt1" href="adminHome.jsp">
							Back
						</a>
					</div>
                </form>
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