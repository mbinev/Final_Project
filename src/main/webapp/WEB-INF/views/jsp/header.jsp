<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="css/font-awesome.css">
<link rel="stylesheet" href="css/templatemo_style.css">
<link rel="stylesheet" href="css/templatemo_misc.css">
<link rel="stylesheet" href="css/flexslider.css">
<link rel="stylesheet" href="css/testimonails-slider.css">

<script src="js/vendor/modernizr-2.6.1-respond-1.1.0.min.js"></script>
</head>
<body>
	<header>
		<c:if test="${sessionScope.logged}">
			<div id="top-header">
				<div class="container">
					<div class="row">
						<div class="col-md-6">
							<ul class="nav navbar-nav">
								<li class="dropdown"><a href="" class="dropdown-toggle"
									data-toggle="dropdown" title="User dashboard"> My account
										&nbsp; <i class="fa fa-lg fa-user"></i>
								</a>
									<div class="dropdown-menu">
										<div class="profile">
											<form action="profile" method="get">
												<button type="submit" class="btn-link">Profile</button>
											</form>
										</div>
										<div class="addresses">
											<form action="addresses" method="get">
												<button type="submit" class="btn-link">Addresses</button>
											</form>
										</div>
										<div class="logout">
											<form action="logout" method="post">
												<button type="submit" class="btn-link">Logout</button>
											</form>
										</div>
									</div></li>
							</ul>
						</div>
						<div class="col-md-6">
							<div class="cart-info">
								<i class="fa fa-shopping-cart"></i>
								<fmt:formatNumber var="i" type="number" minFractionDigits="2"
									maxFractionDigits="2" value="${sessionScope.totalPrice}" />
								(<a href="cart" id="productCount">${sessionScope.productsNumber}
									items</a>) in your cart (<a href="cart" id=""> <c:out
										value="${i}" />
								</a>)
							</div>
						</div>
					</div>
				</div>
			</div>

		</c:if>
		<c:if test="${!sessionScope.logged}">
			<div id="top-header">
				<div class="container">
					<div class="row">
						<div class="col-md-6">
							<ul class="nav navbar-nav">
								<li class="dropdown"><a href="" class="dropdown-toggle"
									data-toggle="dropdown" title="User dashboard"> Login or
										Signup &nbsp; <i class="fa fa-lg fa-user"></i>
								</a>
									<div class="dropdown-menu">
										<form id="formLogin" class="form container-fluid"
											action="" method="post">
											<div class="form-group">
												<input class="form-control" name="email" id="inputEmail"
													type="Email" placeholder="Email" required="">
											</div>
											<div class="form-group">
												<input class="form-control" name="password"
													id="inputpassword" type="password" placeholder="Password"
													required="">
											</div>
											<div class="form-group">
												<button id="loginbtn" class="btn btn-block" type="button">Login</button>
											</div>
											<span id="status" class="help-block"></span>
											<a href="register" title="Fast and free sign up!"
												id="btnNewUser" data-toggle="collapse"
												data-target="#formRegister" class="small">New User?
												Sign-up..</a>
										</form>
										<hr>
									</div></li>
							</ul>
						</div>
						<div class="col-md-6">
							<div class="cart-info">
								<i class="fa fa-shopping-cart"></i> (<a href="cart"
									id="productCount">${sessionScope.productsNumber} items</a>) in
								your cart (<a href="cart" id="totalPrices">$
									${sessionScope.totalPrice}</a>)
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if>
		<div id="main-header">
			<div class="container">
				<div class="row">
					<div class="col-md-3">
						<div class="logo">
							<a href="index"><img src="images/logo.png"></a>
						</div>
					</div>
					<div class="col-md-6">
						<div class="main-menu">
							<ul>
								<li><a href="index">Home</a></li>
								<li><a href="about-us">About</a></li>
								<li><a href="catalog">Products</a></li>
								<li><a href="contact-us">Contact</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>
	<script>
		$('#loginbtn').click(function() {
    		$.ajax({
    			  url: "login",
    			  type: "POST",
    			  contentType : 'application/json; charset=utf-8',
    			  dataType : 'json',
    			  data: JSON.stringify(
    					  	{
    							email: document.getElementById("inputEmail").value,	  		
    							password: document.getElementById("inputpassword").value,
    					  	}			  
    			  ),
    			  success: function(response) {
  				  	var responseData = response;
  					document.getElementById("status").innerHTML="";
  				  	
  				  if(!responseData.error){
  						window.location.replace("index");
  				  }
  				  else{
  					  var errors = responseData.errors;
  					  
  					  for(var i = 0; i < errors.length; i++){
  						  document.getElementById(errors[i].errorPlace).innerHTML = errors[i].errorMessege;
  					  }
  				  }
  				  
  				  
  			  },
  			  error: function(xhr) {
  				  document.getElementById("status").innerHTML="Its our mistake please excuse us.";
  			  }
  			});     
    		
    		
    		
    	});
        </script>

</body>
</html>