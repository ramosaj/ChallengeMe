<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="Navbar.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
	<script src="./js/profile.js" type="text/javascript"></script>
	<style>
	.navbar {
		margin-bottom: 0;
		border-radius: 0%;
	}
	
	#user-info {
		padding-left: 40px;
	}
	</style>
	<script type="text/javascript">		
		var url = new URL(window.location.href);
		var username = url.searchParams.get("username");
		
		if (username == null) {
			// by default it is the own's user
			username = "<%= session.getAttribute("username") %>";
			username = "presIdent";
		}
		
		window.onload = function () {
			getProfile(username);
			getChallenges(username);
			getInterestedChallenges(username);
			getCompletedChallenges(username);
		}
	</script>
</head>
<body>
	<div class="row">
		<div id="user-info" class="col-md-12 well"> 
			<div id="full-name">
				<h1 id="full-name-value"></h1>
			</div>
			<div id="username">
				<b><span id="username-value"></span></b>
			</p>
			</div>
			<div id="joined">
				Joined <span id="joined-value"></span>
			</div>
			
			<br />
			<div id="bio">
				<h4>Bio</h4>
				<p id="bio-value"></p>
			</div>
		</div>
	</div>
	
	<div class="container">
		<div class="row">
			<div class="col" id="posted-challenges">
				<h4>Posted Challenges (<span id="posted-challenges-count"></span>)</h4>
			</div>
			<div class="col" id="completed-challenges">
				<h4>Completed Challenges (<span id="completed-challenges-count"></span>)</h4>
			</div>
			<div class="col" id="interested-challenges">
				<h4>Interested Challenges (<span id="interested-challenges-count"></span>)</h4>
			</div>
		</div>
	</div>
</body>
</html>