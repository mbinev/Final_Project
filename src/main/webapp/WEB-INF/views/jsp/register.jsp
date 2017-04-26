<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<!-- 

Grill Template 

http://www.templatemo.com/free-website-templates/417-grill

-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<meta charset="utf-8">
<title>Register</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
	rel='stylesheet' type='text/css'>

<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/font-awesome.css">
<link rel="stylesheet" href="css/templatemo_style.css">
<link rel="stylesheet" href="css/templatemo_misc.css">
<link rel="stylesheet" href="css/flexslider.css">
<link rel="stylesheet" href="css/testimonails-slider.css">

<script src="js/vendor/modernizr-2.6.1-respond-1.1.0.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
</head>
<body>
	<!--[if lt IE 7]>
            <p class="chromeframe">You are using an outdated browser. <a href="http://browsehappy.com/">Upgrade your browser today</a> or <a href="http://www.google.com/chromeframe/?redirect=true">install Google Chrome Frame</a> to better experience this site.</p>
        <![endif]-->


	<c:import url="header.jsp" />


	<div id="heading">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="heading-content">
						<h2>Register</h2>
					</div>
				</div>
			</div>
		</div>
	</div>


	<div id="timeline-post">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="heading-section">
						<h2>
							<c:out value="Register"></c:out>
						</h2>
						<img src="images/under-heading.png" alt="">
					</div>
				</div>
			</div>
			<div class="space50"></div>


			<div class="col-md-8">
				<div class="message-form">
					<form action="register" method="post" class="send-message">
						<div class="row">
						<c:if test="${sessionScope.incorrectData != null}">
						<c:out value="${sessionScope.incorrectData}"></c:out>
						</c:if>
							<div class="first name col-md-4">
								First name
								<c:if test="${sessionScope.firstName != null }">
								<c:out value="${sessionScope.firstName}"></c:out>
								</c:if>
								 <input type="text" name="first name" id="first name"
									min="4" max="20" placeholder="" required />
							</div>
							<div class="last name col-md-4">
								Last name 
								<c:if test="${sessionScope.lastName != null }">
								<c:out value="${sessionScope.lastName}"></c:out>
								</c:if>
								<input type="text" name="last name" id="last name" 
								min="4" max="20" required />
							</div>
							<div class="email col-md-4">
								Email 
								<c:if test="${sessionScope.email != null }">
								<c:out value="${sessionScope.email}"></c:out>
								</c:if>
								<input type="email" name="email"
									id="email"  required />
							</div>
							<div class="password col-md-4">
								Password(1Aa) 
								<c:if test="${sessionScope.password != null }">
								<c:out value="${sessionScope.password}"></c:out>
								</c:if>
								<input type="password" name="password" id="password"
									required />
							</div>
							<div class="confirm password col-md-4">
								Confirm password 
								<c:if test="${sessionScope.confirmPassword != null }">
								<c:out value="${sessionScope.confirmPassword}"></c:out>
								</c:if>
								<input type="password" name="confirm password" id="confirm password"
									required />
							</div>
						</div>
						<div class="send">
							<button type="submit">Register</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>


	<footer>
		<div class="container">
			<div class="top-footer">
				<div class="row">
					<div class="col-md-9"></div>
				</div>
			</div>
			<div class="main-footer">
				<div class="row">
					<div class="col-md-3">
						<div class="more-info">
							<h4 class="footer-title">More info</h4>
							<li><i class="fa fa-phone"></i>010-020-0340</li>
							<li><i class="fa fa-globe"></i>Sofia, Bulgaia</li>
							<li><i class="fa fa-envelope"></i><a href="#">info@company.com</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="bottom-footer">
				<p>
					Copyright © 2084 <a href="#">ITT Pizza</a>
				</p>
			</div>

		</div>
	</footer>

	<script src="js/vendor/jquery-1.11.0.min.js"></script>
	<script src="js/vendor/jquery.gmap3.min.js"></script>
	<script src="js/plugins.js"></script>
	<script src="js/main.js"></script>

</body>
</html>