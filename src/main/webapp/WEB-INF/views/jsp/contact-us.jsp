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
        <title>Contact</title>
        <meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1">
        
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
                            <div id="googleMap" style="height:600px;"></div>
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
      var json = null; 
		function initialize() {
			map = new google.maps.Map(document.getElementById('googleMap'), {
				zoom : 14,
				center : new google.maps.LatLng(42.69652929836637,
						23.31474301147461),
				mapTypeId : 'roadmap'
			});
			
		 var json = (function () { 
	                $.ajax({ 
	                    'async': false, 
	                    'global': false, 
	                    'type': "POST",
	                    'url': "markers", 
	                    'dataType': "json", 
	                    'success': function (data) {
	                     json = data; } }); 
	                return json;})();
		 for (var i = 0, length = json.length; i < length; i++) {
			  var data = json[i]
			var newmarker = new google.maps.Marker({
				map : map,
				position : {
					lat : json[i].lat,
					lng : json[i].lng
				},
				icon: 'images/logo_icon.png',
				title : data.name
			});
			var infowindow = new google.maps.InfoWindow();
			bindInfoWindow(newmarker, map, infowindow, data.info);
			
		};

	}
	function bindInfoWindow(marker, map, infowindow, html) {
	    marker.addListener('click', function() {
	        infowindow.setContent(html);
	        infowindow.open(map, this);
	        $('#selectlocation').val(this.title).trigger('change');
	    });
	}
	</script>
	
	<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyByprgM_TZvFUC14rIRXrL00nxA1RQXWBw&callback=initialize"type="text/javascript"></script>            
    </body>
</html>