<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<head>
<meta charset="utf-8">
<title>Single product</title>
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
<link rel="stylesheet" href="css/style.css">
<script src="js/vendor/modernizr-2.6.1-respond-1.1.0.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<style type="text/css">
.checkbox {
  padding-left: 20px; }
  .checkbox label {
    display: inline-block;
    position: relative;
    padding-left: 5px; }
    .checkbox label::before {
      content: "";
      display: inline-block;
      position: absolute;
      width: 17px;
      height: 17px;
      left: 0;
      margin-left: -20px;
      border: 1px solid #cccccc;
      border-radius: 3px;
      background-color: #fff;
      -webkit-transition: border 0.15s ease-in-out, color 0.15s ease-in-out;
      -o-transition: border 0.15s ease-in-out, color 0.15s ease-in-out;
      transition: border 0.15s ease-in-out, color 0.15s ease-in-out; }
    .checkbox label::after {
      display: inline-block;
      position: absolute;
      width: 16px;
      height: 16px;
      left: 0;
      top: 0;
      margin-left: -20px;
      padding-left: 3px;
      padding-top: 1px;
      font-size: 11px;
      color: #555555; }
  .checkbox input[type="checkbox"] {
    opacity: 0; }
    .checkbox input[type="checkbox"]:focus + label::before {
      outline: thin dotted;
      outline: 5px auto -webkit-focus-ring-color;
      outline-offset: -2px; }
    .checkbox input[type="checkbox"]:checked + label::after {
      font-family: 'FontAwesome';
      content: "\f00c"; }
    .checkbox input[type="checkbox"]:disabled + label {
      opacity: 0.65; }
      .checkbox input[type="checkbox"]:disabled + label::before {
        background-color: #eeeeee;
        cursor: not-allowed; }
  .checkbox.checkbox-circle label::before {
    border-radius: 50%; }
  .checkbox.checkbox-inline {
    margin-top: 0; }
.checkbox-warning input[type="checkbox"]:checked + label::before {
  background-color: #f0ad4e;
  border-color: #f0ad4e; }
.checkbox-warning input[type="checkbox"]:checked + label::after {
  color: #fff; }
 
#img { height: 400px; width: 400px; overflow: hidden; }
</style>
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
						<h2>Single Product</h2>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="product-post">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="heading-section">
						<h2>Single Product</h2>
						<img src="images/under-heading.png" alt="" />
					</div>
				</div>
			</div>
			<div id="single-blog" class="page-section first-section">
				<div class="container">
					<div class="row">
						<div class="product-item col-md-12">
							<div class="row">
								<div class="col-md-8">
									<div class="image">
										<div class="image-post">
											<img id="img" src="${product.img}" alt="">
										</div>
									</div>
									<div class="product-content">
										<div class="product-title">
											<h3>
												<c:out value="${product.name}" />
											</h3>
											<h3 class="price" data-base-price="${product.price}">
												Price: <span>${product.price}</span>
											</h3>
										</div>
										<c:if test="${product.category eq 'Pizzaz'}">
											<form action="products" method="post">
												<table>
													<tr>
														<th><div class="form-group">
														 <label class="col-md-2 control-label" for="crust"> </label>
														 <div class="col-md-12">
														    <select id="crustSelect" name="crust" class="form-control price-option">
														      <c:forEach var="crust" items="${crusts}">
																	<option value="${crust}" data-price="${crust.price}">${crust}</option>
																</c:forEach>
														    </select>
														  </div>
														</div></th>
														<th><div class="form-group">
														 <label class="col-md-2 control-label" for="size"> </label>
														 <div class="col-md-12">
														    <select id="sizeSelect" name="size" class="form-control price-option">
														      <c:forEach var="size" items="${sizes}">
																	<option value="${size}" data-price="${size.price}">${size}</option>
																</c:forEach>
														    </select>
														  </div>
														</div></th>
													</tr>
												</table>
												<br>
												<c:forEach var="topping" items="${toppings}">
													<c:set var="contains" value="false" />
													<c:forEach var="sub" items="${product.subproducts}">
														<c:if test="${sub eq topping.name }">
															<c:set var="contains" value="true" />
														</c:if>
													</c:forEach>
													<c:if test="${contains eq true }">
														<div class="checkbox checkbox-warning ">
									                        <input id="${topping.name}" type="checkbox" name="subproduct" data-price="${topping.price}" value="${topping.name}" checked>
									                        <label for="${topping.name}">
									                            ${topping.name}
									                        </label>
									                    </div>
													</c:if>
													<c:if test="${contains eq false }">
														<div class="checkbox checkbox-warning ">
									                        <input id="${topping.name}" type="checkbox" name="subproduct" data-price="${topping.price}" value="${topping.name}">
									                        <label for="${topping.name}">
									                            ${topping.name}
									                        </label>
									                    </div>
													</c:if>
												</c:forEach>
												<input id="productPrice" type="hidden" name="productPrice" value="${product.price}">
												<c:set var="product" value="${product}" scope="session" />
												  <label class="col-md-12 control-label" for="buttonSelect"></label>
												  <div class="col-md-8">
												    <button type="submit" id="buttonCreateTour" name="buttonCreateTour" class="btn btn-warning">Submit</button>
												  </div>
											</form>
										</c:if>

										<c:if test="${product.category ne 'Pizzaz'}">
											<form action="products" method="post">
												<input id="productPrice" type="hidden" name="productPrice" value="${product.price}">
												<label class="col-md-12 control-label" for="buttonSelect"></label>
												  <div class="col-md-8">
												    <button type="submit" id="buttonCreateTour" name="buttonCreateTour" class="btn btn-warning">Submit</button>
												  </div>
											</form>

										</c:if>
									</div>
									<div class="divide-line">
										<img src="images/div-line.png" alt="" />
									</div>
									</div></div></div></div></div></div></div></div>
									<script>
										function changePrice() {
											var price = parseFloat($('.price')
													.data('base-price'));
											$('.price-option').each(function(i, el) {
												price += parseFloat($('option:selected',el).data('price'));
											});
											$('.product-content input:checkbox:checked').each(function(){
										        price += parseFloat($(this).data('price'));
										    });
											$('.price span').text(
													price.toFixed(2));
											$("#productPrice").val(price.toFixed(2));
										};
										$('.price-option').on("change", changePrice);
										$('.checkbox').on("change", changePrice);
										$(document).ready( changePrice());
										
									</script>


									<c:import url="footer.jsp" />


									<script src="js/vendor/jquery-1.11.0.min.js"></script>
									<script src="js/vendor/jquery.gmap3.min.js"></script>
									<script src="js/plugins.js"></script>
									<script src="js/main.js"></script>
</body>
</html>