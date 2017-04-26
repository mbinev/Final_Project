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
			
			<div class="container">
			<br>
				<c:if test="${empty sessionScope.addresses || sessionScope.addresses == null}">
					<h4>You have no added addresses. Go to address section to add an address.</h4>
				</c:if>

				<c:if test="${sessionScope.addresses != null && not empty sessionScope.addresses}">
					<div id="myModal" class="modal hide fade" tabindex="-1"
						role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true"></button>
							<h3 id="myModalLabel">Delete</h3>
						</div>
						<div class="modal-body">
							<p></p>
						</div>
					</div>
					<div class="table-responsive">
						<table class="table table-striped table-hover table-users">
							<thead>
								<tr>
									<th>Name</th>
									<th>City</th>
									<th>Phone</th>
									<th>Street</th>
									<th>Address number</th>
									<th>Edit</th>
									<th>Delete</th>
								</tr>
							</thead>
							<tbody>
									<c:forEach items="${sessionScope.addresses}" var="address">
									<tr>
										<td>${address.name}</td>
										<td>${address.city}</td>
										<td>${address.phone}</td>
										<td>${address.street}</td>
										<td>${address.addressNumber}</td>
	
										<td>
											<a class="btn btn-info btn-sm refresh"
											onclick="updateAddress(this,'${address.addressId}');">
												<i class="fa fa-pencil-square-o"></i>
											</a>
										</td>
	
										<td>
											<button class="btn btn-danger btn-sm trash"
												onclick="deleteAddress(this,'${address.addressId}');">
												<i class="fa fa-trash-o"></i>
											</button>
										</td>
								     </tr>
									</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
			</div>
		</div>
	</div>


	<c:import url="footer.jsp" />

	<script src="js/vendor/jquery-1.11.0.min.js"></script>
	<script src="js/vendor/jquery.gmap3.min.js"></script>
	<script src="js/plugins.js"></script>
	<script src="js/main.js"></script>
	
	<script type="text/javascript">
	
	function deleteAddress(btn, id) {
		
		if (!confirm('Are you sure?')) {
			return;
		}
		
		$.post("deleteAddress", {
			id : id
		}).then(function() {
			//window.location.reload();
		});
		var $tr = $(btn).closest('tr');
		$tr.hide(300, function() {
			$tr.remove();
		});
	}
	
	function updateAddress(btn, id) {
		
		$.get("update", {
			id : id
		}).then(function() {
			window.location.assign("addresses")
			
		});
	}
	</script>

</body>
</html>