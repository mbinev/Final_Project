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
<title>Profile</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">

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
						<h2>Your profile</h2>
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
							<c:out value="${sessionScope.user.firstName }"></c:out>
						</h2>
						<img src="images/under-heading.png" alt="">
					</div>
				</div>
			</div>
			<div class="space50"></div>

			<div class="row">
				<div class="col-md-2 col-sm-4">
					<div class="timeline-thumb">
						<div class="thumb">
						    <img src="image/${filename}">
						</div>
						<div class="overlay">
							<div class="timeline-caption">
								<h4>Nice pic</h4>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<form method="post" enctype="multipart/form-data">
						<input type="file" id="file" name="failche" accept="image/*">
						<input type="submit" value="Upload now">
					</form>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<h3>Our Project</h3>
					<p>
						Hope you enjoied enjoyed our final project for <a
							href="http://ittalents.bg/home"> IT Tallents </a>
					</p>
				</div>
			</div>
		</div>
	</div>



	<footer>
		<div class="container">
			<div class="top-footer">
				<div class="row">
					<div class="col-md-9">
						<div class="subscribe-form">
							<span>Get in touch with us</span>
							<form method="get" class="subscribeForm">
								<input id="subscribe" type="text" /> <input type="submit"
									id="submitButton" />
							</form>
						</div>
					</div>
					<div class="col-md-3">
						<div class="social-bottom">
							<span>Follow us:</span>
							<ul>
								<li><a href="#" class="fa fa-facebook"></a></li>
								<li><a href="#" class="fa fa-twitter"></a></li>
								<li><a href="#" class="fa fa-rss"></a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="main-footer">
				<div class="row">
					<div class="col-md-3">
						<div class="more-info">
							<h4 class="footer-title">More info</h4>
							<p>Sed dignissim, diam id molestie faucibus, purus nisl
								pretium quam, in pulvinar velit massa id elit.</p>
							<ul>
								<li><i class="fa fa-phone"></i>010-020-0340</li>
								<li><i class="fa fa-globe"></i>123 Dagon Studio, Yakin
									Street, Digital Estate</li>
								<li><i class="fa fa-envelope"></i><a href="#">info@company.com</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="bottom-footer">
				<p>
					Copyright Â© 2084 <a href="#">Your Company Name</a>
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