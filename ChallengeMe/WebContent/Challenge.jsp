<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="Navbar.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Challenge Page</title>
<script src="./js/userInterest.js" type="text/javascript"></script>
<script src="./js/challenge.js" type="text/javascript"></script>
<style>
.challenge-tag {
  padding: 4px;
  font-size: 14px;
}

.fa-check, .fa-star, .fa-times {
  font-size: 20px;
}
</style>
<script>
	
	var currentUsername = "anthony";
	
	var url = new URL(window.location.href);
	var username = url.searchParams.get("username");
	var challengeId = url.searchParams.get("challengeId");
	var socket;
	window.onbeforeunload = function(e) {
			socket.close();
		};
	window.onload = function() {
		loadInterest(
			currentUsername, username, challengeId,
			document.getElementsByClassName(INTERESTED_BTN_CNAME)[0],
			function () {
				// Remove user from the interested users column
            	for (var div of document.getElementsByClassName("interested-user")) {
            		if (div.dataset.username == currentUsername) {
            			div.parentElement.removeChild(div);
            			break;
            		}
            	}
            	// Decrement count
        		var interestedUsersCountEl = document.getElementById("interested-users-count");
        		interestedUsersCountEl.dataset.value = parseInt(interestedUsersCountEl.dataset.value) - 1;
        		interestedUsersCountEl.innerHTML = interestedUsersCountEl.dataset.value;
			},
			function () {
	        	// Add user to the interested users column
	        	var user = getUser(currentUsername);
	        	var userDiv = createUserDiv(user, "interested-user");
				document.getElementById("interested-users").appendChild(userDiv);
	        	// Increment count
	    		var interestedUsersCountEl = document.getElementById("interested-users-count");
	    		interestedUsersCountEl.dataset.value = parseInt(interestedUsersCountEl.dataset.value) + 1;
	    		interestedUsersCountEl.innerHTML = interestedUsersCountEl.dataset.value;
			}
		);
		socket = new WebSocket("ws://localhost:8080/ChallengeMe/ws");
		socket.onopen = function(event) {
			socket.send(challengeId);
			//document.getElementById("mychat").innerHTML += "Connected!";
		}
		socket.onmessage = function(event) {
			document.getElementById("viewingNum").innerHTML += event.data;
		}
		socket.onclose = function(event) {
			var x="close"+challengeId;
			socket.send(x)
		//	document.getElementById("mychat").innerHTML += "Disconnected!";
		};

		loadChallenge(username, challengeId);
		loadInterestedUsers(username, challengeId);
		loadCompletedUsers(username, challengeId);
	}
</script>
</head>
<body>
  <div class="text-center">
    <span id="username">
      <h4><span id="username-value"></span></h4>
    </span>
    <span id="date">
      <span id="date-value"></span>
    </span>
    <span id="numInterested">
      <span>Num-Viewing: </span><span id="viewingNum"></span>
    </span>
    <span>
      <br />
      <button type="button" class="interested-btn btn btn-secondary">
        <i class="interested-btn-icon fa"></i> Interested
      </button>
    </span>
    <div id="title" class="text-center">
      <h1><span id="title-value"></span></h1>
	</div>
    <div id="categories" class="text-center"></div>	
  </div>
  <hr />
  <div class="container">
    <div class="row">
      <div class="col text-center" id="interested-users">
        <h4>Interested Users (<span id="interested-users-count"></span>)</h4>
      </div>
      <div class="col text-center" id="completed-users">
        <h4>Completed Users (<span id="completed-users-count"></span>)</h4>
      </div>
    </div>
  </div>
</body>
</html>