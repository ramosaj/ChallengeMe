<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@include file="Navbar.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script>
	function getProfile() {
		user = new XMLHttpRequest();
		user.open("GET","")
		
		
		
		
	}
	
	
	
	






</script>
</head>
<body onload="getProfile()">
	<div class="row">
		<div class="col-md-12"> 
			<h1 id="fullName">Tommy Trojan</h1>
			<p id="username">@tj</p>
			<p id="joined"> Sometime idk fam</p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-3">
		<h1> BIO </h1>
		<div id="bio">  </div>
		
		
		
		</div>
		<div class="col-md-3">
		<h2>Posted Challenges </h2>
		<div id="posted"> </div>
		
		
		</div>
		<div class="col-md-3">
		<h2>Completed Challenges</h2>
		<div id="completed "></div>
		
		
		
		
		</div>
		<div class="col-md-3">
		<h2>Interested Challenges</h2>
		<div id="interested"></div>
		
		
		
		
		</div>
	
	
	</div>


</body>
</html>