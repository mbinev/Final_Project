<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/font-awesome.css">
<link rel="stylesheet" href="css/templatemo_style.css">
<link rel="stylesheet" href="css/templatemo_misc.css">
<link rel="stylesheet" href="css/flexslider.css">
<link rel="stylesheet" href="css/testimonails-slider.css">


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
						<h2>Cart</h2>
						<span>Home / <a href="cart.html">Cart</a></span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<table id="cart" class="table table-hover table-condensed">
			<thead>
				<tr>
					<th style="width: 50%">Product</th>
					<th style="width: 10%">Price</th>
					<th style="width: 8%">Quantity</th>
					<th style="width: 22%" class="text-center">Subtotal</th>
					<th style="width: 10%"></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="total" value="${0}" />
				<c:forEach var="order" items="${sessionScope.products}">
					<c:set var="product" value="${order.product}" scope="page"/>
					<c:set var="total" value="${total + product.price}" />
				</c:forEach>
				<c:forEach var="order" items="${sessionScope.products}">
					<c:set var="desc" value="${order.description}" scope="page"/>
					<c:set var="product" value="${order.product}" scope="page"/>
					<tr>
						<td data-th="Product">
							<div class="row">
								<div class="col-sm-2 hidden-xs">
									<img src="http://placehold.it/100x100" alt="..."
										class="img-responsive" />
								</div>
								<div class="col-sm-10">
									<h4 class="nomargin">${product.name}</h4>
									<p>${desc}</p>
								</div>
							</div>
						</td>
						<td data-th="Price">${product.price}</td>
						<td data-th="Quantity"><input data-id="${product.productId}"
							data-price="${product.price}" value="1"
							class="count form-control text-center" type="number" /></td>
						<td data-th="Subtotal" class="all text-center"
							id="total_price_${product.productId}">${product.price}</td>
						<td class="actions" data-th="">
							<button class="btn btn-info btn-sm refresh">
								<i class="fa fa-refresh"></i>
							</button>
						<input class="order" type = "hidden" name = "jsfForm:hiddenField"  
   value = "${order}" />
							<button class="btn btn-danger btn-sm trash" onclick=>
								<i class="fa fa-trash-o"></i>
							</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr class="visible-xs">
					<td><div class="total_price_basket">
							<strong>TOTAL: 0</strong>
						</div></td>
				</tr>
				<tr>
					<td><a href="catalog" class="btn btn-warning"><i
							class="fa fa-angle-left"></i> Continue Shopping</a></td>
					<td colspan="2" class="hidden-xs"></td>
					<td class="hidden-xs text-center total_price_basket"><strong>TOTAL:
							0</strong></td>

					<c:if test="${!sessionScope.logged}">
						<td><a href="#" class="btn btn-success btn-block"
							onClick="alert('Please login!')">Checkout <i
								class="fa fa-angle-right"></i>
						</a></td>
					</c:if>

					<c:if test="${sessionScope.logged}">
						<td><a href="index" class="btn btn-success btn-block">Checkout
								<i class="fa fa-angle-right"></i>
						</a></td>
					</c:if>
				</tr>
			</tfoot>
		</table>
	</div>
	<script>
		$('.trash').on('click', function() {
			var obj=$(this).closest('tr').find('.order').val();
			$.post("deleteOrderObj",
				    {
				        name: obj
				    });
			$(this).closest('tr').remove();
			var count = 0;
			var total = 0;
			$('.all').each(function() {
				total += Number($(this).text());
				count += 1;
			});
			$('.total_price_basket').text('TOTAL: ' + total);
			$("#productCount").text(count + ' items')
			$("#totalPrices").text('$ ' + total);
			return false;
		});

		$('.count').on('change keyup paste', function() {

			// Update individual price
			var price = $(this).data('price') * this.value;
			$('#total_price_' + $(this).data('id')).text(price);

			// Update total
			var total = 0;
			$('.all').each(function() {
				total += Number($(this).text());
			});
			$('.total_price_basket').text('TOTAL: ' + total);
		}).trigger('keyup');
	</script>
</body>
</html>