<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html >
<html class="no-js">
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
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
					<form action="" method="post" class="send-message">
						<div class="row">
							<legend><span id="output" class="help-block"></span></legend>
							<div class="first name col-md-4">
								First name
								 <input type="text" name="name" id="name"
									min="4" max="20" placeholder="" required />
								<span id="nameError" class="help-block"></span>
								
							</div>
							<div class="last name col-md-4">
								Last name 
								<input type="text" name="familyName" id="familyName" 
								min="4" max="20" required />
								<span id="familyNameError" class="help-block"></span>
							</div>
							<div class="email col-md-4">
								Email
								<input type="email" name="email"
									id="email"  required />
								<span id="emailError" class="help-block"></span>
							</div>
							<div class="password col-md-4">
								Password(1Aa)
								<input type="password" name="passwordFirst" id="passwordFirst"
									required />
								<span id="passwordFirstError" class="help-block"></span>
							</div>
							<div class="confirm password col-md-4">
								Confirm password
								<input type="password" name="passwordSecond" id="passwordSecond"
									required />
								<span id="passwordSecondError" class="help-block"></span>
							</div>
						</div>
						<div class="send">
							<button id="register_btn" type="button">Register</button>
							
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>


	<c:import url="footer.jsp" />

	<script src="js/vendor/jquery-1.11.0.min.js"></script>
	<script src="js/vendor/jquery.gmap3.min.js"></script>
	<script src="js/plugins.js"></script>
	<script src="js/main.js"></script>
	<script>
		$('#register_btn').click(function() {
    		$.ajax({
    			  url: "register",
    			  type: "POST",
    			  contentType : 'application/json; charset=utf-8',
    			  dataType : 'json',
    			  data: JSON.stringify(
    					  	{
    							name: document.getElementById("name").value,	  		
    							familyName: document.getElementById("familyName").value,
    							email: document.getElementById("email").value,
    							passwordFirst: document.getElementById("passwordFirst").value,
    							passwordSecond: document.getElementById("passwordSecond").value,
    					  	}			  
    			  ),
    			  success: function(response) {
    				  	var responseData = response;
    				  	document.getElementById("nameError").innerHTML="";
    					document.getElementById("familyNameError").innerHTML="";
    					document.getElementById("emailError").innerHTML="";
    					document.getElementById("passwordFirstError").innerHTML="";
    					document.getElementById("passwordSecondError").innerHTML="";
    					document.getElementById("status").innerHTML="";
    				  	
    				  if(!responseData.error){
    					  	document.getElementById("output").innerHTML="Successful registration! Please check your email for confirmation.";
    					  	document.getElementById("name").value="";	  		
    						document.getElementById("familyName").value="";
    						document.getElementById("email").value="";
    						document.getElementById("passwordFirst").value="";
    						document.getElementById("passwordSecond").value="";
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