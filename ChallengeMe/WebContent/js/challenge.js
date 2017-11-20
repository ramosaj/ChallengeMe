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
        		document.getElementById("date-value").innerHTML = new Date(challenge.createdAt);
        		
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
    			
        		var interestedUsersCountEl = document.getElementById("interested-users-count");
        		interestedUsersCountEl.innerHTML = interestedUsers.length;
        		interestedUsersCountEl.dataset.value = parseInt(interestedUsers.length);

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
    			
        		var completedUsersCountEl = document.getElementById("completed-users-count");
        		completedUsersCountEl.innerHTML = completedUsers.length;
        		completedUsersCountEl.dataset.value = parseInt(completedUsers.length);
        		
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