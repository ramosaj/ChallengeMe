<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="Navbar.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Challenge Page</title>
<script>

	window.onload = function() {
		loadUsers();
		getChallenge();
		
	}
	function loadUsers() {
		var user = new XMLHttpRequest();
		var username = <%=request.getParameter("username") %>;	
		var challengeId = <%=request.getParameter("challengeId") %>;
		user.open("GET",'ChallengeMe/challenges/'+username+'/'+challengeId+'/interested',false);
		user.send();
		alert(user.responseText)
		var userResponse = JSON.parse(user.responseText);
		var html ="";
		for(var i =0; i< userResponse.size();i++)
			{
				var name = userResponse.get(i).name;
				html+="<tr><th>" + name + "</th></tr>";
			}
		document.getElementById("usersInterested").innerHTML = html;
		
		user = new XMLHttpRequest();
		user.open("GET",'ChallengeMe/challenges/'+username+'/'+challengeId+'/completed',false);
		user.send();
		var resp = JSON.parse(user.responseText);
		html="";
		for(var i=0;i<resp.length;i++){
			var name = resp[i].name;
			html+="<tr><th><a onclick='redirect("+resp[i].url+")'>" + name + "</a></th></tr>";
		}
		document.getElementById("usersCompleted").innerHTML = html;
		
		
		
	}
		function showUsersInterested() {
		 		document.getElementById("usersInterested").hidden=false;
		 		document.getElementById("usersCompleted").hidden=true;
		 }
		 	
		 function showUsersCompleted() {
		 		document.getElementById("usersInterested").hidden=true;
		 		document.getElementById("usersCompleted").hidden=false;		
		 }
	
		 
	function getChallenge() {
		var username = <%=request.getParameter("username") %>;
		var challengeId = <%= request.getParameter("challengeId") %>;
		var challenge = new XMLHttpRequest();
		challenge.open("GET","ChallengeMe/challenges/"+username+"/"+challengeId,false);
		challenge.send();
		var resp = JSON.parse(challenge.responseText);
		document.getElementById("title").innerHTML = "<h4>" +  resp.name + "</h4>";
		document.getElementById("name").innerHTML = resp.owner.username;
		document.getElemenyById("date").innerHTML = resp.createdAt;
		
	}
	
	function redirect() {
		var urlpattern = url.split("/");
  		var username = urlpattern[1];
  		window.location.href="Profile.jsp?username="+username;	
  		
		
		
		
		
	}

</script>
</head>
<body onload="loadUsers()">
	<br>
	<div style="text-align: center;">
		<span id="name">
			<h4>TOMMY TROJAN</h4>
		</span>
		<span id="date">
			5 MINS AGO
		</span>
		<span>
			<br />
			<button type="button" class="btn btn-secondary">Interested</button>
		</span>
	</div>
	<div id="title" style="text-align: center;"><h1>REALLY CREATIVE CHALLENGE</h1></div>
	<div id="categories" style="text-align: center;"> <span ="tag"><a class="btn btn-primary">CREATIVE</a>&nbsp;</span><span="tag"><a class="btn btn-primary">INTERESTING</a></span></div>	
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