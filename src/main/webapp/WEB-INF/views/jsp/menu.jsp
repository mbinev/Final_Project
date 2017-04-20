<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="jquery-2.1.4.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
   <script type="text/javascript">
   var category;
   function choose(choice){
       category = choice;
   }
   function menu()
   {
	$.ajax({
	        url: 'menu',
	        type: 'GET',
	        data: {'category':category} ,
	        dataType: "text",
	        async:false,
	        success: function (response) {
	        	document.getElementById("result").innerHTML  =response;
	        },
	        error: function () {
	        	 alert(response.innerHTML);
	        }
	    }); 
   }
   </script>
<body>
		<c:if test="${sessionScope.logged}">
    		<c:import url="headerLogged.jsp"/>
		</c:if>
		<c:if test="${!sessionScope.logged}">
    		<c:import url="headerNotLogged.jsp"/>
		</c:if>
		<c:if test="${sessionScope.categories != null}">
		
		
			<c:forEach var="categories" items="${sessionScope.categories}"> 	
				<input type="button" onClick="choose('${categories}'); menu()" value= "${ categories}"/>
       			 <div id="somediv"></div>

			</c:forEach>
	</c:if>
<br>
<h3 id= "result"></h3>
</body>
</html>