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
	
<style type="text/css">
.prettyline {
  height: 5px;
  border-top: 0;
  background: #c4e17f;
  border-radius: 5px;
  background-image: -webkit-linear-gradient(left, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%, #f7fdca 25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%, #db9dbe 50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%, #669ae1 87.5%, #62c2e4 87.5%, #62c2e4);
  background-image: -moz-linear-gradient(left, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%, #f7fdca 25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%, #db9dbe 50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%, #669ae1 87.5%, #62c2e4 87.5%, #62c2e4);
  background-image: -o-linear-gradient(left, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%, #f7fdca 25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%, #db9dbe 50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%, #669ae1 87.5%, #62c2e4 87.5%, #62c2e4);
  background-image: linear-gradient(to right, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%, #f7fdca 25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%, #db9dbe 50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%, #669ae1 87.5%, #62c2e4 87.5%, #62c2e4);
}
.modal-backdrop {
    /* bug fix - no overlay */    
    display: none;    
}
.modal-cart {
 border: 1px solid black;
 border-radius:8px;
  background:#fff;
  padding:20px;
}
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
							onClick="alert('Please login!')" >Checkout <i
								class="fa fa-angle-right"></i>
						</a></td>
					</c:if>

					<c:if test="${sessionScope.logged}">
						<td><a href="index" class="btn btn-success btn-block" data-toggle="modal" data-target=".bs-modal-sm">Checkout
								<i class="fa fa-angle-right"></i>
						</a></td>
					</c:if>
					
				</tr>
			</tfoot>
		</table>
	</div>
	<div class="modal fade bs-modal-sm" id="myModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-cart">
        <br>
        <div class="bs-example bs-example-tabs">
            <ul id="myTab" class="nav nav-tabs">
              <li class="active"><a href="#delivery" data-toggle="tab">Deliver to me!</a></li>
              <li class=""><a href="#pickIt" data-toggle="tab">Pick from shop!</a></li>
            </ul>
        </div>
      <div class="modal-body">
        <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade active in" id="delivery">
            <form class="form-horizontal">
            <fieldset>
            <select class="selectpicker">
			  <option>Home</option>
			  <option>Work</option>
			  <option>Batcave</option>
			</select>

            <!-- Button -->
            <div class="control-group">
              <label class="control-label" for="order"></label>
              <div class="controls">
                <button id="order" name="order" class="btn btn-success">Order</button>
              </div>
            </div>
            </fieldset>
            </form>
        </div>
        <div class="tab-pane fade" id="pickIt">
            <form class="form-horizontal">
            <select class="selectpicker">
			  <option>Serdica</option>
			  <option>Mladost 3</option>
			  <option>Borovo</option>
			</select>
            <!-- Button -->
            <div class="control-group">
              <label class="control-label" for="order"></label>
              <div class="controls">
                <button id="order" name="order" class="btn btn-success">Order</button>
              </div>
            </div>
            </form>
      </div>
    </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
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