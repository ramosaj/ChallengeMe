var INTERESTED_BTN_ID = "interested-btn";
var INTERESTED_BTN_ICON_ID = "interested-btn-icon";

function getUserUrl (username)
{
	return "/ChallengeMe/users/" + username;
}

function getUser (username)
{
	var getUserRequest = new XMLHttpRequest();
	var url = getUserUrl(username, challengeId);
	getUserRequest.open("GET", url, false);
	getUserRequest.send();
	
	return JSON.parse(getUserRequest.responseText);
}

function getChallengeUrl (username, challengeId)
{
	return "/ChallengeMe/challenges/" + username + "/" + challengeId;
}

function loadChallenge (username, challengeId)
{
	var getChallengeRequest = new XMLHttpRequest();
	var url = getChallengeUrl(username, challengeId);
	getChallengeRequest.open("GET", url, true);
	
	getChallengeRequest.onload = function () {
		if (getChallengeRequest.readyState === 4) {
            if (getChallengeRequest.status === 200) {
            		var challenge = JSON.parse(getChallengeRequest.responseText);
        			
            		document.getElementById("title-value").innerHTML = challenge.name;
            		document.getElementById("username-value").innerHTML = challenge.owner.username;
            		document.getElementById("date-value").innerHTML = challenge.createdAt;
            		
            		// XXX: populate categories
            		challenge.categories = ["creative", "interesting"];
            		for (var category of challenge.categories) {
            			var categoryTag = document.createElement("span")
            			categoryTag.classList.add("challenge-tag");
            			categoryTag.classList.add("btn");
            			categoryTag.classList.add("btn-info");
            			categoryTag.classList.add("disabled");
            			categoryTag.innerHTML = "# " + category
            			// add a space between tags
            			var space = document.createElement("span");
            			space.innerHTML = "&nbsp;";
            			// render elements
            			document.getElementById("categories").appendChild(categoryTag);
            			document.getElementById("categories").appendChild(space);
            		}
            }
            else {
                console.error(getChallengeRequest.statusText);
            }
        }
	}
	
	getChallengeRequest.onerror = function (e) {
	  console.error(getChallengeRequest.statusText);
	};
	getChallengeRequest.send(null);
}

function checkInterestUrl (checker, username, challengeId)
{
	return "/ChallengeMe/users/" + checker + "/interested/" + username + "/" + challengeId;
}

function loadInterest (checker, username, challengeId)
{
	var checkInterestRequest = new XMLHttpRequest();
	var url = checkInterestUrl(checker, username, challengeId);
	checkInterestRequest.open("GET", url, true);
	
	// star or checkmark depending on whether the checker is already interested or not
	var interestedButtonIconEl = document.getElementById(INTERESTED_BTN_ICON_ID);
	
	checkInterestRequest.onload = function () {
		if (checkInterestRequest.readyState === 4) {
            if (checkInterestRequest.status === 204) {
            	// checker is already interested, display checkmark
            	markInterestedButton();
            }
            else if (checkInterestRequest.status == 404) {
            	// checker is not interested, display star
            	markUninterestedButton();
            }
            else {
                console.error(checkInterestRequest.statusText);
            }
        }
	}
	
	checkInterestRequest.onerror = function (e) {
	  console.error(checkInterestRequest.statusText);
	};
	checkInterestRequest.send(null);
}

function markInterestedButton ()
{
	var interestedButtonIconEl = document.getElementById(INTERESTED_BTN_ICON_ID);
	interestedButtonIconEl.classList.remove("fa-star");
	interestedButtonIconEl.classList.add("fa-check");
	
	var interestedButtonEl = document.getElementById(INTERESTED_BTN_ID);
	interestedButtonEl.classList.remove("btn-secondary");
	interestedButtonEl.classList.add("btn-primary");
	
	interestedButtonEl.onclick = function () {
		deleteInterest(currentUsername, username, challengeId);
	}
}

function markUninterestedButton ()
{
	var interestedButtonIconEl = document.getElementById(INTERESTED_BTN_ICON_ID);
	interestedButtonIconEl.classList.remove("fa-check");
	interestedButtonIconEl.classList.add("fa-star");
	
	var interestedButtonEl = document.getElementById(INTERESTED_BTN_ID);
	interestedButtonEl.classList.remove("btn-primary");
	interestedButtonEl.classList.add("btn-secondary");
		
	interestedButtonEl.onclick = function () {
		presentInterest(currentUsername, username, challengeId);
	}
}

function presentInterest (username, owner, challengeId)
{
	console.log(username, owner, challengeId);
	var presentInterestRequest = new XMLHttpRequest();
	var url = checkInterestUrl(username, owner, challengeId);
	presentInterestRequest.open("PUT", url, true);
	
	presentInterestRequest.onload = function () {
		if (presentInterestRequest.readyState === 4) {
            if (presentInterestRequest.status === 204) {
            	markInterestedButton();
            	// Add user to the interested users column
            	var user = getUser(username);
            	var userDiv = createUserDiv(user, "interested-user");
    			document.getElementById("interested-users").appendChild(userDiv);
            }
            else {
                console.error(checkInterestRequest.statusText);
            }
        }
	}
	
	presentInterestRequest.onerror = function (e) {
	  console.error(presentInterestRequest.statusText);
	};
	presentInterestRequest.send(null);
}

function deleteInterest (username, owner, challengeId)
{
	var deleteInterestRequest = new XMLHttpRequest();
	var url = checkInterestUrl(username, owner, challengeId);
	deleteInterestRequest.open("DELETE", url, true);
	
	deleteInterestRequest.onload = function () {
		if (deleteInterestRequest.readyState === 4) {
            if (deleteInterestRequest.status === 204) {
            	markUninterestedButton();
            	// Remove user from the interested users column
            	for (var div of document.getElementsByClassName("interested-user")) {
            		if (div.dataset.username == username) {
            			div.parentElement.removeChild(div);
            			break;
            		}
            	}
            }
            else {
                console.error(deleteInterestRequest.statusText);
            }
        }
	}
	
	deleteInterestRequest.onerror = function (e) {
	  console.error(deleteInterestRequest.statusText);
	};
	deleteInterestRequest.send(null);
}

function getInterestedUsersUrl (username, challengeId)
{
	return "/ChallengeMe/challenges/" + username + "/" + challengeId + '/interested';
}

function loadInterestedUsers (username, challengeId)
{
	var getInterestedUsersRequest = new XMLHttpRequest();
	var url = getInterestedUsersUrl(username, challengeId);
	getInterestedUsersRequest.open("GET", url, true);
	
	getInterestedUsersRequest.onload = function () {
		if (getInterestedUsersRequest.readyState === 4) {
            if (getInterestedUsersRequest.status === 200) {
        		var interestedUsers = JSON.parse(getInterestedUsersRequest.responseText);
    			
        		document.getElementById("interested-users-count").innerHTML = interestedUsers.length;
        		for (var user of interestedUsers) {
        			var userDiv = createUserDiv(user, "interested-user");
        			document.getElementById("interested-users").appendChild(userDiv);
        		}
            }
            else {
                console.error(getInterestedUsersRequest.statusText);
            }
        }
	}
	
	getInterestedUsersRequest.onerror = function (e) {
	  console.error(getInterestedUsersRequest.statusText);
	};
	getInterestedUsersRequest.send(null);
}

function createUserDiv (user, className)
{
	// div
	var userDiv = document.createElement("div");
	userDiv.dataset.userId = user.id;
	userDiv.dataset.username = user.username;
	userDiv.classList.add(className);
	
	// name
	var nameBolded = document.createElement("b");
	nameBolded.innerHTML = user.name;
	var nameHeader = document.createElement("h5");
	nameHeader.appendChild(nameBolded);
	nameHeader.style.marginBottom = "0px";
	nameHeader.style.marginTop = "20px";
	
	// username
	var usernameLink = document.createElement("a");
	usernameLink.href = "Profile.jsp?" + user.username;
	usernameLink.innerHTML = "@" + user.username;
	
	// add to div
	userDiv.appendChild(nameHeader);
	userDiv.appendChild(usernameLink);
	
	return userDiv;
}

function getCompletedUsersUrl (username, challengeId)
{
	return "/ChallengeMe/challenges/" + username + "/" + challengeId + '/completed';
}

function loadCompletedUsers (username, challengeId)
{
	var getCompletedUsersRequest = new XMLHttpRequest();
	var url = getCompletedUsersUrl(username, challengeId);
	getCompletedUsersRequest.open("GET", url, true);
	
	getCompletedUsersRequest.onload = function () {
		if (getCompletedUsersRequest.readyState === 4) {
            if (getCompletedUsersRequest.status === 200) {
        		var completedUsers = JSON.parse(getCompletedUsersRequest.responseText);
    			
        		document.getElementById("completed-users-count").innerHTML = completedUsers.length;
        		for (var user of completedUsers) {
        			var userDiv = createUserDiv(user, "completed-user");
        			document.getElementById("completed-users").appendChild(userDiv);
        		}
            }
            else {
                console.error(getCompletedUsersRequest.statusText);
            }
        }
	}
	
	getCompletedUsersRequest.onerror = function (e) {
	  console.error(getCompletedUsersRequest.statusText);
	};
	getCompletedUsersRequest.send(null);
}