<%@ page language="java" contentType="text/html; charset=UTF-8" errorPage="error.jsp" pageEncoding="UTF-8"%>
<%@ page import="org.devshub.dbservice.EmployeeDbservice" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Employee Home</title>
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
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		if(session.getAttribute("email") == null){
			response.sendRedirect("login.jsp");
		}
		String employeeName = session.getAttribute("name").toString();
		int id = (int)session.getAttribute("empId");
		
		if(employeeName == null || id <= 0){
			response.sendRedirect("errorPage.jsp");
		}
	%>
	<div class="limiter">
		<div class="container-login100" style="background-image: url('images/bg-01.jpg');">
			<div class="wrap-login100">
				<form class="login100-form validate-form">
					<span class="login100-form-logo">
						<i class="zmdi zmdi-account"></i>
					</span>

					<span class="login100-form-title p-b-34 p-t-27">
						<%=employeeName %>
					</span>
						
						<%
						  	String entry,exit;
							if(EmployeeDbservice.ifEntryExist(id)){ 
							    entry="disabled=\"disabled\"";exit="";
							}else{
								 entry="";exit="disabled=\"disabled\"";
							}
						%>
						
					<div class="container-login100-form-btn mt-3 mb-3">
						<a class="txt1" href="entryservlet">
							<button class="login100-form-btn" type="button" <%=entry %>>
								Entry
							</button>
						</a>
                    </div>
                    
                    <div class="container-login100-form-btn mt-3 mb-3">
						<a class="txt1" href="exitservlet">
							<button class="login100-form-btn" type="button" <%=exit %>>
								Exit
							</button> 
						</a>
					</div>

					<div class="container-login100-form-btn mt-3 mb-3">
						<a class="txt1" href="history">
							<button class="login100-form-btn" type="button">
								History
							</button>
						</a>
					</div>

					<div class="text-center p-t-40">
						<a class="txt1" href="viewProfile">
							My Profile
						</a>
					</div>

					<div class="text-center p-t-40">
						<a class="txt1" href="logout">
							Logout
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