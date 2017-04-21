<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
										<div class="logout">
											<form action="logout" method="post">
												<button type="submit" class="btn-link">Profile</button>
											</form>
										</div>
										<div class="logout">
											<form action="logout" method="post">
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
											action="login" method="post">
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
												<button id="btnRegister" class="btn btn-block" type="submit">Login</button>
											</div>
											<a href="#" title="Fast and free sign up!" id="btnNewUser"
												data-toggle="collapse" data-target="#formRegister"
												class="small">New User? Sign-up..</a>
										</form>
										<form id="formRegister" class="form collapse container-fluid"
											action="login" method="post">
											<br>
											<div class="form-group">
												<input class="form-control" name="email" id="inputEmail"
													type="email" placeholder="Email" required="">
											</div>
											<div class="form-group">
												<input class="form-control" name="first name"
													id="inputFistName" type="text" placeholder="First Name"
													pattern="^[a-z,A-Z,0-9,_]{6,15}$" data-valid-min="6"
													title="First name" required="">
											</div>
											<div class="form-group">
												<input class="form-control" name="last name"
													id="inputlastName" type="text" placeholder="Last Name"
													pattern="^[a-z,A-Z,0-9,_]{6,15}$" data-valid-min="6"
													title="Last name" required="">
											</div>
											<div class="form-group">
												<input class="form-control" name="password"
													id="inputpassword" type="password" placeholder="Password"
													required="">
											</div>
											<div class="form-group">
												<input class="form-control" name="confirm password"
													id="inputVerify" type="password"
													placeholder="Password (again)" required="">
											</div>
											<div class="form-group">
												<button id="btnRegister" class="btn btn-block" type="submit">Sign
													Up</button>
											</div>
										</form>
										<hr>
										<div class="container-fluid">
											<a class="small" data-toggle="modal" role="button"
												href="#forgotPasswordModal">Forgot username or password?</a>
										</div>
									</div></li>
							</ul>
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

</body>
</html>