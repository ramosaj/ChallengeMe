<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@include file="Navbar.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	div.well {
		border-bottom:2px solid black;
		margin: 10px 10px 10px 10px;
		
	}
	#fullName, #username, #joined{
		padding: 0px 0px 0px 0px;
		margin: 0px 0px 0px 0px;
	}
	#joined {
		margin-bottom: 20px;
	
	}
	
</style>
<script>
	function getProfile() {
		var user = new XMLHttpRequest();
		user.open("GET",'/user/'+<%=request.getSession().getAttribute("userId")%>,false);
		var userRespone = JSON.parse(user.responseText);
		document.getElementById("fullName").innerHTML = userResponse.name;
		document.getElementById("username").innerHTML = userResponse.username;
		document.getElementById("joined").innerHTML = userResponse.createdAt;
		document.getElementById("bio").innerHTML = userResponse.bio;
		
		var userChallenges = new XHMLHttpRequest();
		userChallenges.open("GET",userResponse.challengesUrl,false);
		var challengeList = JSON.parse(userChallenges.responseText);
		var challengeHTML = ""
		for(var element in challengeList){
			challengeHTML=challengeHTML +  "<a href="+element.url+">"+element.name+"</a><br />";
			
		}
		document.getElementById("posted").innerHTML = challengeHTML;
		
		
		
		
		
}
</script>
</head>
<body onload="getProfile()">
	<div class="row">
		<div class="col-md-12 well"> 
			<h1 id="fullName">Trojan</h1>
			<p id="username">@tj</p>
			<p id="joined"> Sometime idk fam</p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-8 well">
		<h4> BIO </h4><hr />
		<div id="bio">Hi my name is Tommy <br />
		and my favorite past time is FIGHTING ON!
		 </div>
		
		
		
		</div>
		<div class="col-md-2 bridge"></div>
	</div>
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8 well">
		<h4>Posted Challenges </h4>
		<div id="posted">
			Challenge 8
			Challenge 9
		
		 </div>
	
		
		
		</div>
		<div class="col-md-2"></div>
	</div>
	<div class="row">
		
		<div class="col-md-2"></div>
		<div class="col-md-8 well" >
		<h4>Completed Challenges</h4>
		<div id="completed ">
			Challenge 1
			challenge 2
			challenge 3
			challenge 4
			challenge 5
		</div>
		
		
		
		
		</div>
		<div class="col-md-2"></div>
	</div>
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8 well">
		<h4>Interested Challenges</h4>
		<div id="interested">
			Challenge 1
			challenge 2
			challenge 3
			challenge 4
			challenge 5
		
		
		</div>
		
		
		
		
		</div>
		<div class="col-md-2"></div>
	</div>


</body>
</html>