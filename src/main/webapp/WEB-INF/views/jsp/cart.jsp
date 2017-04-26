<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/font-awesome.css">
<link rel="stylesheet" href="css/templatemo_style.css">
<link rel="stylesheet" href="css/templatemo_misc.css">
<link rel="stylesheet" href="css/flexslider.css">
<link rel="stylesheet" href="css/testimonails-slider.css">



<style type="text/css">
.prettyline {
	height: 5px;
	border-top: 0;
	background: #c4e17f;
	border-radius: 5px;
	background-image: -webkit-linear-gradient(left, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%,
		#f7fdca 25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%,
		#db9dbe 50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%,
		#669ae1 87.5%, #62c2e4 87.5%, #62c2e4);
	background-image: -moz-linear-gradient(left, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%,
		#f7fdca 25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%,
		#db9dbe 50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%,
		#669ae1 87.5%, #62c2e4 87.5%, #62c2e4);
	background-image: -o-linear-gradient(left, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%, #f7fdca
		25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%, #db9dbe
		50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%, #669ae1
		87.5%, #62c2e4 87.5%, #62c2e4);
	background-image: linear-gradient(to right, #c4e17f, #c4e17f 12.5%, #f7fdca 12.5%, #f7fdca
		25%, #fecf71 25%, #fecf71 37.5%, #f0776c 37.5%, #f0776c 50%, #db9dbe
		50%, #db9dbe 62.5%, #c49cde 62.5%, #c49cde 75%, #669ae1 75%, #669ae1
		87.5%, #62c2e4 87.5%, #62c2e4);
}

.modal-backdrop {
	/* bug fix - no overlay */
	display: none;
}

.modal-cart {
	border: 1px solid black;
	border-radius: 8px;
	background: #fff;
	padding: 15px;
	width: 600px;
	margin: 30px auto;
}

#map-canvas {
	height: 300px;
}
body{
    padding-bottom:200px;// You can adjust it 
}
.footer-main {
     background: #81ccfb;
     padding: 1em;
     position: absolute;
     left: 0;
     right: 0;
     bottom: 0;
}
#img { height: 100px; width: 100px; overflow: hidden; }

</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
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
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<table id="cart" class="table table-hover table-condensed">
			<thead>
				<tr>
					<th style="width: 70%">Product</th>
					<th style="width: 10%">Price</th>
					<th style="width: 8%">Quantity</th>
					<th style="width: 22%" class="text-center">Subtotal</th>
					<th style="width: 10%"></th>
				</tr>
			</thead>
			<tbody>
				<c:set var="total" value="${0}" />
				<c:forEach var="order" items="${sessionScope.products}">
					<c:set var="product" value="${order.product}" scope="page" />
					<c:set var="total" value="${total + product.price}" />
				</c:forEach>
				<c:forEach var="order" items="${sessionScope.products}">
					<c:set var="desc" value="${order.description}" scope="page" />
					<c:set var="product" value="${order.product}" scope="page" />
					<tr>
						<td data-th="Product">
							<div class="row">
								<div class="col-sm-2 hidden-xs">
									<img id="img" src="${product.img}" alt="..."
										class="img-responsive" />
								</div>
								<div class="col-sm-10">
									<h4 class="nomargin">${product.name}</h4>
									<p>${desc}</p>
								</div>
							</div>
						</td>
						<td data-th="Price">${order.price}</td>
						<td data-th="Quantity"><input data-id="${product.productId}"
							data-price="${order.price}" value="1"
							class="count form-control text-center" type="number" /></td>
						<td data-th="Subtotal" class="all text-center"
							id="total_price_${product.productId}">${order.price}</td>
						<td class="actions" data-th="">
							<input class="order" type="hidden" name="jsfForm:hiddenField"
							value="${order}" />
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
						<c:if test="${sessionScope.productsNumber ne 0}">
						<td><a href="index" class="btn btn-success btn-block"
							data-toggle="modal" data-target="#myModal" onclick="resize()">Checkout
								<i class="fa fa-angle-right"></i>
						</a></td>
						</c:if>
						<c:if test="${sessionScope.productsNumber eq 0}">
							<td><a href="#" class="btn btn-success btn-block"
								onClick="alert('You need to have products in your cart to make an order!')">Checkout <i
									class="fa fa-angle-right"></i>
							</a></td>
						</c:if>
					</c:if>

				</tr>
			</tfoot>
		</table>
	</div>

	<div class="fade modal bs-modal-sm" id="myModal" tabindex="-1"
		role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-cart">
				<br>
				<div class="bs-example bs-example-tabs">
					<ul id="myTab" class="nav nav-tabs">
						<li class="active"><a href="#delivery" data-toggle="tab">Deliver
								to me!</a></li>
						<li class="pickFromShop"><a href="#pickIt" data-toggle="tab"
							id="pickFromShop">Pick from shop!</a></li>
					</ul>
				</div>
				<div class="modal-body">
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade active in" id="delivery">

							<form action="order" method="post">
								<fieldset>
									<select class="selectpicker" id="address-select" name="address">
										<c:forEach var="address" items="${sessionScope.addresses}">										
											<option value="${address.name}">${address.name}</option>
										</c:forEach>
									</select>
									<c:forEach var="address" items="${sessionScope.addresses}">			
											<h3 id="${address.name}" style="display: none" class="addressinfo">${address.name}<br>
												<p></p>
												<p><strong>Street:</strong> ${address.street}</p>
												<p><strong>Address No:</strong> ${address.addressNumber}</p>
												<p><strong>Phone Number:</strong> ${address.phone}</p>
												<p><strong>Bell:</strong> ${address.bell}</p>
												<p><strong>Floor:</strong> ${address.floor}</p>
												<p><strong>Bulding No:</strong> ${address.buildingNumber}</p>
												<p><strong>Apartment No:</strong> ${address.apartmentNumber}</p>
												<p><strong>Entrace:</strong> ${address.entrance}</p>
												<p><strong>Postcode:</strong> ${address.postcode}</p>
											</h3>
									</c:forEach>
									<!-- Button -->
									<c:if test="${sessionScope.addresses eq null}">
										<div class="control-group">
											<label class="control-label"></label>
											<div class="controls">
												<button type="button" onclick="alert('Please add an address!')" class="btn btn-success">Order</button>
											</div>
										</div>
									</c:if>
									<c:if test="${sessionScope.addresses ne null}">
										<div class="control-group">
											<label class="control-label" for="order"></label>
											<div class="controls">
												<button id="order" type="submit" class="btn btn-success">Order</button>
											</div>
										</div>
									</c:if>
								</fieldset>
							</form>
						</div>
						<div class="tab-pane fade" id="pickIt">
							<form class="form-horizontal" action="order" method="post">
								<div class="col-md-12 col-sm-12">

									<select name="address" id="selectlocation">
									</select>

								</div>
								<div class="col-md-12 col-sm-12" id="map-canvas"></div>
								<!-- Button -->
								<div class="control-group">
									<label class="control-label" for="order"></label>
									<div class="controls">
										<button id="order" type="submit" class="btn btn-success">Order</button>
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
		$('#address-select').on('change click', function () {
		   var valueSelected = this.value;
		   $('.addressinfo').each(function(i, obj) {
			    if(this.id == valueSelected){
		    	 	$("#"+this.id).show();
			    }else{
			    	$("#"+this.id).hide();
			    }
			});
		 
		});
		
		$('.trash').on('click', function() {
			var obj = $(this).closest('tr').find('.order').val();
			$.post("deleteOrderObj", {
				name : obj
			});
			$(this).closest('tr').remove();
			var count = 0;
			var total = 0;
			$('.all').each(function() {
				total += Number($(this).text());
				count += 1;
			});
			$('.total_price_basket').text('TOTAL: ' + total.toFixed(2));
			$("#productCount").text(count + ' items')
			$("#totalPrices").text('$ ' + total.toFixed(2));
			return false;
		});

		$('.count').on('change keyup paste', function() {

			// Update individual price
			var price = $(this).data('price') * this.value;
			$('#total_price_' + $(this).data('id')).text(price.toFixed(2));

			// Update total
			var total = 0;
			$('.all').each(function() {
				total += Number($(this).text());
			});
			$('.total_price_basket').text('TOTAL: ' + total.toFixed(2));
		}).trigger('keyup');
		function resize() {
			setTimeout(resize, 50);
			$('#address-select').trigger('click');
			google.maps.event.trigger(map, 'resize');
		};
	</script>

	<script src="js/vendor/jquery-1.11.0.min.js"></script>
	<script src="js/vendor/jquery.gmap3.min.js"></script>
	<script src="js/plugins.js"></script>
	<script src="js/main.js"></script>
	<script>
		var map;
		var markerData = [ {
			lat : 42.6996424670517,
			lng : 23.31228017807007,
			zoom : 16,
			name : "Opulchenska",
			info : "<h4>Pizza</h4> bul. Todor Alexandrov </br>1303 </br>Sofia </br>Bulgaria"
		}, {
			lat : 42.692671891757854,
			lng : 23.310391902923584,
			zoom : 16,
			name : "Russian Monument",
			info : "<h4>Pizza</h4> Russian Monument </br>1606 </br>Sofia </br>Bulgaria"
		}, {
			lat : 42.70131402715675,
			lng : 23.322772979736328,
			zoom : 16,
			name : "Luvov most",
			info : "<h4>Pizza</h4> bul. Knyaginya Maria Luiza 45 </br>1202 </br>Sofia </br>Bulgaria"
		}, ];
		function initialize() {
			map = new google.maps.Map(document.getElementById('map-canvas'), {
				zoom : 14,
				center : new google.maps.LatLng(42.70652929836637,
						23.30174301147461),
				mapTypeId : 'roadmap'
			});
			markerData.forEach(function(data) {
				var newmarker = new google.maps.Marker({
					map : map,
					position : {
						lat : data.lat,
						lng : data.lng
					},
					icon: 'images/logo_icon.png',
					title : data.name
				});
				var infowindow = new google.maps.InfoWindow({
			          content: data.info
		        });
				newmarker.addListener('click', function() {
			          infowindow.open(map, this);
			        });
				jQuery("#selectlocation").append(
						'<option value="'
								+ [ data.name ].join('|')
								+ '" data-cord="'
								+ [ data.lat, data.lng, data.zoom ].join('|')
								+ '">' + data.name + '</option>');
			});

		}

		jQuery(document)
				.on(
						'change',
						'#selectlocation',
						function() {
							var latlngzoom = jQuery(this).getAttribute('data-cord').split('|');
							var newzoom = 1 * latlngzoom[2], newlat = 1 * latlngzoom[0], newlng = 1 * latlngzoom[1];
							map.setZoom(newzoom);
							map.setCenter({
								lat : newlat,
								lng : newlng
							});
						});
	</script>


	<script async defer
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyByprgM_TZvFUC14rIRXrL00nxA1RQXWBw&callback=initialize"
		type="text/javascript"></script>
	<c:import url="footer.jsp" />
</body>
</html>