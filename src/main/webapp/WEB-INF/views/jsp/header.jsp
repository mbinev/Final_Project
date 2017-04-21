<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
.dropbtn {
    background-color: #db9a04;
    color: white;
    padding: 12px;
    font-size: 16px;
    border: none;
    cursor: pointer;
}

.dropbtn:hover, .dropbtn:focus {
    background-color: #db9a04;
}

.dropdown {
    position: relative;
    display: inline-block;
}

.dropdown-content {
    display: none;
    position: absolute;
    background-color: #f9f9f9;
    min-width: 160px;
    overflow: auto;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 1;
}

.dropdown-content a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
}

.dropdown a:hover {background-color: #f1f1f1}

.show {display:block;}
</style>
</head>
<body>
	<header>	
			<c:if test="${sessionScope.logged}">
	                <div id="top-header">
	                    <div class="container">
	                        <div class="row">
	                            <div class="col-md-6">
	                                <div class="home-account">
	                                    <a href="#">My account</a>
	                                </div>
	                            </div>
	                            <div class="col-md-6">
	                                <div class="logout">
	                                <form action="logout" method="post">
									  <button type="submit" class="btn-link">Logout</button>
									</form>
	                                </div>
	                            </div>
	                            <div class="col-md-6">
	                                <div class="cart-info">
	                                    <i class="fa fa-shopping-cart"></i>
	                                    (<a href="#">5 items</a>) in your cart (<a href="#">$45.80</a>)
	                                </div>
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
                                <div class="home-account">
                                    <div class="dropdown">
									  <button onclick="myFunction()" class="dropbtn">Login</button>
									  <div id="myDropdown" class="dropdown-content">
									    <form action="login" method="post">
										<div><input name="email" type="text" required="required" placeholder="Email" /></div>
										<div><input type="password" name="password" required="required" placeholder="Password" /></div>
										<div class="input signup"><input type="submit" /></div>
									</form>
									  </div>
									</div>
                                </div>
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
      <script>
/* When the user clicks on the button, 
toggle between hiding and showing the dropdown content */
function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown if the user clicks outside of it
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {

    var dropdowns = document.getElementsByClassName("dropbtn");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}
</script>
</body>
</html>