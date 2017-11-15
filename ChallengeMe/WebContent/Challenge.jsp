<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="Navbar.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Challenge Page</title>
<script>
	function loadUsers() {
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
		
		function showUsersInterested()
		 	{
		 		document.getElementById("usersInterested").hidden=false;
		 		document.getElementById("usersCompleted").hidden=true;
		 	}
		 	
		 	function showUsersCompleted()
		 	{
		 		document.getElementById("usersInterested").hidden=true;
		 		document.getElementById("usersCompleted").hidden=false;		
		 	}
}
</script>
</head>
<body onload="loadUsers()">
	<br>
	<div style="text-align: center;"><span><h4>TOMMY TROJAN</h4></span><span>5 MINS AGO</span><span>&#9734 INTERESTED</span></div>
	<div style="text-align: center;"><h1>REALLY CREATIVE CHALLENGE</h1></div>
	<div style="text-align: center;"> <span ="tag">CREATIVE</span><span="tag">INTERESTING</span></div>	
	<div style="text-align: center;"><span>	&#9733 99 interested &nbsp; &nbsp;</span><span>&#9863 7 viewing</span></div>
<hr>
	<div style="text-align:center;"><span align="center"><button onclick="showUsersInterested()" type="button">Users Interested</button></span><button onclick="showUsersCompleted()" type="button">Users Completed</button><span align="center"></span></div>
	
	<table align="center" id="usersInterested">
		<tr><th>Donald Trump -- 10/20/2017</th></tr>
		<tr><th>Sachet Vijay -- 10/13/2017</th></tr>
		<tr><th>Barack Obama -- 10/10/2017</th></tr>
	</table>
	<table hidden align="center" id="usersCompleted">
		<tr><th>Hillary Clinton -- 10/28/2017</th></tr>
		<tr><th>Eddo Hintoso -- 10/15/2017</th></tr>
		<tr><th>Owen Gong -- 10/13/2017</th></tr>
	</table>
	</body>
</html>