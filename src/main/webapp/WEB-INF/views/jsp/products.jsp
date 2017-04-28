<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="msg" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<!-- 

Grill Template 

http://www.templatemo.com/free-website-templates/417-grill

-->
    <head>
        <meta charset="utf-8">
        <title>Products - Grill Template</title>
        <meta name="description" content="">
		<meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>

        <link rel="stylesheet" href="css/bootstrap.css">
        <link rel="stylesheet" href="css/font-awesome.css">
        <link rel="stylesheet" href="css/templatemo_style.css">
        <link rel="stylesheet" href="css/templatemo_misc.css">
        <link rel="stylesheet" href="css/flexslider.css">
        <link rel="stylesheet" href="css/testimonails-slider.css">

        <script src="js/vendor/modernizr-2.6.1-respond-1.1.0.min.js"></script>
     <style type="text/css">
     #img { height: 264px; width: 264px; overflow: hidden; }
     </style>
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
                                <h2>Our Products</h2>
                            </div>
                        </div>
                    </div>
                </div>
            </div>





            <div id="products-post">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <div id="product-heading">
                                <h2>Hungry ?</h2>
                                <img src="img/under-heading.png" alt="" >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="filters col-md-12 col-xs-12">
       						 <div id="somediv"></div>
                            <ul id="filters" class="clearfix">
                                <li><span class="filter" data-filter="all">All</span></li>
                        <c:set var="items" scope="session" value="${products}"/>
						<c:set var="totalCount" scope="session" value="${fn:length(products)}"/>
						<c:set var="perPage" scope="session"  value="${20}"/>
						<c:set var="pageStart" value="${param.start}"/>
						<c:if test="${empty pageStart or pageStart < 0}">
				      		<c:set var="pageStart" value="0"/>
						</c:if>
						<c:if test="${totalCount-1 < pageStart}">
						     <c:set var="pageStart" value="${pageStart - perPage}"/>
						</c:if>
                        <c:forEach var="category" items="${categories}"> 	
                                <li><span class="filter" data-filter=".${category}">${category}</span></li>
						</c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="row" id="Container">
                    <h1></h1>
                   <c:forEach var="product" items="${items}" begin="${pageStart}" end="${pageStart + perPage - 1}"> 	
                        <div class="col-md col-sm mix ${product.category}">       
                            <div class="portfolio-wrapper">                
                                <div class="portfolio-thumb">
                                    <img id="img" src="${product.img}" alt="" />
                                    <div class="hover">
                                        <div class="hover-iner">
                                            <a class="fancybox" href="products?product=${product.name}&category=${product.category}"><img src="img/open-icon.png" alt="" /></a>
                                            <span>${product.sub}</span>
                                        </div>
                                    </div>
                                </div>  
                                <div class="label-text">
                                    <h4><a href="products?product=${product.name}&category=${product.category}">${product.name}</a></h4>
                                </div>
                            </div>          
                        </div>
					</c:forEach>
                 
                    </div>
                    <div class="pagination">
                        <div class="row">   
                            <div class="col-md-12">
                                <ul>
                                	<li><a href="?start=${pageStart - perPage}">Previous</a></li>
                                    <li><a href="#">${pageStart + 1} - ${pageStart + perPage}</a></li>
                                    <li><a href="?start=${pageStart + perPage}">Next</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>     
                </div>
            </div>



			<c:import url="footer.jsp" />

    
        <script src="js/vendor/jquery-1.11.0.min.js"></script>
        <script src="js/vendor/jquery.gmap3.min.js"></script>
        <script src="js/plugins.js"></script>
        <script src="js/main.js"></script>

    </body>
</html>