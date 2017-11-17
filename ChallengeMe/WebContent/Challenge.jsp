<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="Navbar.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Challenge Page</title>
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
	// FIXME: get from the current session
	var currentUsername = "anthony";
	
	var url = new URL(window.location.href);
	var username = url.searchParams.get("username");
	var challengeId = url.searchParams.get("challengeId");
	
	window.onload = function() {
		loadInterest(currentUsername, username, challengeId);
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
      <button type="button" id="interested-btn" class="btn btn-secondary">
        <i id="interested-btn-icon" class="fa"></i> Interested
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