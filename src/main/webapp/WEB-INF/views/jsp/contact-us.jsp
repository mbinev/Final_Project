<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<!-- 

Grill Template 

http://www.templatemo.com/free-website-templates/417-grill

-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <head>
        <meta charset="utf-8">
        <title>Contact - Grill Template</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">
        
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>

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

            
    		<c:import url="header.jsp"/>	


            <div id="heading">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="heading-content">
                                <h2>Contact Us</h2>
                                <span>Home / <a href="contact-us.html">Contact Us</a></span>
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
                                <h2>Find Us On Map</h2>
                                <img src="images/under-heading.png" alt="" >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div id="googleMap" style="height:340px;"></div>
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
			name : "Ruski pametnik",
			info : "<h4>Pizza</h4> Russian Monument </br>1606 </br>Sofia </br>Bulgaria"
		}, {
			lat : 42.70131402715675,
			lng : 23.322772979736328,
			zoom : 16,
			name : "Luvov Most",
			info : "<h4>Pizza</h4> bul. Knyaginya Maria Luiza 45 </br>1202 </br>Sofia </br>Bulgaria"
		}, ];
      function initialize() {
        map = new google.maps.Map(document.getElementById('googleMap'), {
          zoom: 15,
          center: new google.maps.LatLng(42.69752929836637, 23.32174301147461),
          mapTypeId: 'roadmap'
        });

        var iconBase = 'images/';
        var icons = {
          info: {
            icon: iconBase + 'logo_icon.png'
          }
        };
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
		});


		
      }
    </script>
        <script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyByprgM_TZvFUC14rIRXrL00nxA1RQXWBw&callback=initialize" type="text/javascript"></script>                
    </body>
</html>