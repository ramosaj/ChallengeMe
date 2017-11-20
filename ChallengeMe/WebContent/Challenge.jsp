<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="Navbar.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Challenge Page</title>
<script src="./js/userInterest.js" type="text/javascript"></script>
<script src="./js/userCompletion.js" type="text/javascript"></script>
<script src="./js/challenge.js" type="text/javascript"></script>
<script src="./js/util.js" type="text/javascript"></script>
<style>
.challenge-tag {
  padding: 4px;
  font-size: 14px;
}

.fa-check, .fa-star, .fa-eye {
  font-size: 20px;
}
</style>
<script>
	var currentUsername = <%= request.getSession().getAttribute("username") %>;
	var url = new URL(window.location.href);
	var username = url.searchParams.get("username");
	var challengeId = url.searchParams.get("challengeId");
	
	var viewsSocket;
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
		
		loadCompletion(
			currentUsername, username, challengeId,
			document.getElementsByClassName(COMPLETED_BTN_CNAME)[0],
			function () {
				// Remove user from the interested users column
            	for (var div of document.getElementsByClassName("completed-user")) {
            		if (div.dataset.username == currentUsername) {
            			div.parentElement.removeChild(div);
            			break;
            		}
            	}
            	// Decrement count
        		var completedUsersCountEl = document.getElementById("completed-users-count");
        		completedUsersCountEl.dataset.value = parseInt(completedUsersCountEl.dataset.value) - 1;
        		completedUsersCountEl.innerHTML = completedUsersCountEl.dataset.value;
			},
			function () {
	        	// Add user to the completed users column
	        	var user = getUser(currentUsername);
	        	var userDiv = createUserDiv(user, "completed-user");
				document.getElementById("completed-users").appendChild(userDiv);
	        	// Increment count
	    		var completedUsersCountEl = document.getElementById("completed-users-count");
	    		completedUsersCountEl.dataset.value = parseInt(completedUsersCountEl.dataset.value) + 1;
	    		completedUsersCountEl.innerHTML = completedUsersCountEl.dataset.value;
			}
		);
		
		viewsSocket = new WebSocket("ws://localhost:8080/ChallengeMe/views");
		viewsSocket.onopen = function(event) {
			viewsSocket.send("VIEW " + challengeId);
		}
		viewsSocket.onmessage = function (event) {
			var viewsButtonValueSpan = document.getElementsByClassName("views-count")[0];
			viewsButtonValueSpan.innerHTML = event.data;
			viewsButtonValueSpan.dataset.viewsCount = event.data;
		}
		viewsSocket.onclose = function(event) {
			var x= "CLOSE " + challengeId;
			viewsSocket.send(x)
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
    <span>
      <br />
      <button type="button" class="views-btn btn btn-warning disabled">
        <i class="interested-btn-icon fa fa-eye"></i> 
        <span class="views-count"></span>
      </button>
      <button type="button" class="interested-btn btn btn-secondary">
        <i class="interested-btn-icon fa"></i> Interested
      </button>
      <button type="button" class="completed-btn btn btn-secondary">
        <i class="completed-btn-icon fa fa-check"></i> Completed
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