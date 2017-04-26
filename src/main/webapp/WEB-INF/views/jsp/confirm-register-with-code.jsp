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
<title>Confirm your email</title>
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
						<h2>Confrim your email</h2>
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
							<c:out value="Confrim your email"></c:out>
						</h2>
						<img src="images/under-heading.png" alt="">
					</div>
				</div>
			</div>
			<div class="space50"></div>


			<div class="col-md-8">
				<div class="message-form">
					<form action="confirmRegisterWithCode" method="post" class="send-message">
						<div class="row">
							<div class="name col-md-4">
								Email <input type="email" name="email" id="email"
									required />
							</div>
							<div class="street col-md-4">
								Password <input type="password" name="password" id="street" required />
							</div>
							<div class="subject col-md-4">
								Registration code <input type="text" name="code"
									id="address number" required />
							</div>
						</div>
						<div class="send">
							<button type="submit">Confirm</button>
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