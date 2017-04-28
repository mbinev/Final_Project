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
<title>Addresses</title>
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
						<h2>Manage addresses</h2>
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
							<c:out value="Add a new address"></c:out>
						</h2>
						<img src="images/under-heading.png" alt="">
					</div>
				</div>
			</div>
			<div class="space50"></div>


			<div class="col-md-8">
				<div class="message-form">
				<c:if test="${sessionScope.address == null }">
					<form action="addAddress" method="post" class="send-message">
						<div class="row">
							<div class="name col-md-4">
								Name your address <input type="text" name="name" id="name"
									required />
							</div>
							<div class="street col-md-4">
								Street <input type="text" name="street" id="street" required />
							</div>
							<div class="subject col-md-4">
								Address number(eg. 2A) <input type="text" name="address number"
									id="address number" required />
							</div>
							<div class="postcode col-md-4">
								Postcode <input type="text" name="postcode" id="postcode"
									min="1010" required />
							</div>
							<div class="phone col-md-4">
								Phone(eg. 0878661551) <input type="text" name="phone" id="phone"
									max="10" required />
							</div>
							<div class="bell col-md-4">
								Bell(eg. Ivanovi) <input type="text" name="bell" id="bell" 
								min="1"/>
							</div>
							<div class="floor col-md-4">
								Floor <input type="number" name="floor" id="floor" min="1"
								required />
							</div>
							<div class="building number col-md-4">
								Building number <input type="number" name="building number"
									min="1" id="building number" />
							</div>
							<div class="apartament number col-md-4">
								Apartament number <input type="number" name="apartament number"
									id="apartament number" min="1" />
							</div>
							<div class="entrance col-md-4">
								Entrance <input type="text" name="entrance" id="entrance" min="1"/>
							</div>
						</div>

						<div class="send">
							<button type="submit">Add</button>
						</div>
					</form>
				</c:if>
				
				<c:if test="${sessionScope.address != null }">
					<form action="updateAddress" method="post" class="send-message">
						<div class="row">
							<div class="name col-md-4">
								Name your address <input type="text" name="name" id="name"
								min="4" max="20"
									value="${sessionScope.address.name }" required />
							</div>
							<div class="street col-md-4">
								Street <input type="text" name="street" id="street" 
								value="${sessionScope.address.street }" max="20" required />
							</div>
							<div class="subject col-md-4">
								Address number(eg. 2A) <input type="text" name="address number"
									id="address number" value="${sessionScope.address.addressNumber }" 
									max="10" required />
							</div>
							<div class="postcode col-md-4">
								Postcode <input type="text" name="postcode" id="postcode"
									value="${sessionScope.address.postcode }" min="4" max="10" required />
							</div>
							<div class="phone col-md-4">
								Phone(eg. 0878661551) <input type="text" name="phone" id="phone"
									max="10" value="${sessionScope.address.phone }" required />
							</div>
							<div class="bell col-md-4">
								Bell(eg. Ivanovi) <input type="text" name="bell" id="bell" 
								value="${sessionScope.address.bell }"/>
							</div>
							<div class="floor col-md-4">
								Floor <input type="number" name="floor" id="floor" required
								min="1" max="20" value="${sessionScope.address.floor }" />
							</div>
							<div class="building number col-md-4">
								Building number <input type="number" name="building number"
									min="1" id="building number" value="${sessionScope.address.buildingNumber }"/>
							</div>
							<div class="apartament number col-md-4">
								Apartament number <input type="number" name="apartament number"
									min="1" id="apartament number" value="${sessionScope.address.apartmentNumber }"/>
							</div>
							<div class="entrance col-md-4">
								Entrance <input type="text" name="entrance" id="entrance" 
								min="1" value="${sessionScope.address.entrance }"/>
							</div>
						</div>

						<div class="send">
							<button type="submit">Update</button>
						</div>
					</form>
				</c:if>
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