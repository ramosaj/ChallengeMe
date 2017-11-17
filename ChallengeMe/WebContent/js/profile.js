var MONTHS = [
	"January",
	"February",
	"March",
	"April",
	"May",
	"June",
	"July",
	"August",
	"September",
	"October",
	"November",
	"December"
]

function getDateString (ds)
{
	var dateComponents = ds.split('-');
	var day = dateComponents[2];
	var month = MONTHS[parseInt(dateComponents[1])];
	var year = dateComponents[0];
	
	return month + " " + day + ", " + year;
}

function getChallengePageUrl (username, challengeId)
{
	return "/ChallengeMe/Challenge.jsp?username=" + username + "&challengeId=" + challengeId;
}

function getProfileUrl (username)
{
	return "/ChallengeMe/users/" + username;
}

function getChallengesUrl (username)
{
	return "/ChallengeMe/users/" + username + "/challenges";
}

function getInterestedChallengesUrl (username)
{
	return "/ChallengeMe/users/" + username + "/interested";
}

function getCompletedChallengesUrl (username)
{
	return "/ChallengeMe/users/" + username + "/completed";
}

function getProfile (username)
{
	url = getProfileUrl(username);
	getProfileByUrl(url);
}

function getProfileByUrl (url)
{
	var getUserRequest = new XMLHttpRequest();
	getUserRequest.open("GET", url, true);
	
	getUserRequest.onload = function () {
		if (getUserRequest.readyState === 4) {
            if (getUserRequest.status === 200) {
            		var user = JSON.parse(getUserRequest.responseText);
        			
            		document.getElementById("full-name-value").innerHTML = user.name;
	        		document.getElementById("username-value").innerHTML = "@" + user.username;
	        		document.getElementById("joined-value").innerHTML = getDateString(user.createdAt);
	        		document.getElementById("bio-value").innerHTML = user.bio;
            }
            else {
                console.error(getUserRequest.statusText);
            }
        }
	}
	
	getUserRequest.onerror = function (e) {
	  console.error(getUserRequest.statusText);
	};
	getUserRequest.send(null);
}

function getChallenges (username)
{
	var url = getChallengesUrl(username);
	getChallengesByUrl(url);
}

function getChallengesByUrl (url)
{
	var getChallengesRequest = new XMLHttpRequest();
	getChallengesRequest.open("GET", url, true);
	
	getChallengesRequest.onload = function () {
		if (getChallengesRequest.readyState === 4) {
            if (getChallengesRequest.status === 200) {
            		var challenges = JSON.parse(getChallengesRequest.responseText);
        			
            		document.getElementById("posted-challenges-count").innerHTML = challenges.length;
            		for (var challenge of challenges) {
            			var challengeLink = document.createElement("a");
            			challengeLink.href = getChallengePageUrl(username, challenge.id);
            			challengeLink.innerHTML = challenge.name;
            			document.getElementById("posted-challenges").appendChild(challengeLink);
            			document.getElementById("posted-challenges").appendChild(document.createElement("br"));
            		}
            } else {
                console.error(getChallengesRequest.statusText);
            }
        }
	}
	
	getChallengesRequest.onerror = function (e) {
	  console.error(getChallengesRequest.statusText);
	};
	getChallengesRequest.send(null);
}

function getInterestedChallenges (username)
{
	var url = getInterestedChallengesUrl(username);
	getInterestedChallengesByUrl(url);
}

function getInterestedChallengesByUrl (url)
{
	var getInterestedChallengesRequest = new XMLHttpRequest();
	getInterestedChallengesRequest.open("GET", url, true);
	
	getInterestedChallengesRequest.onload = function () {
		if (getInterestedChallengesRequest.readyState === 4) {
            if (getInterestedChallengesRequest.status === 200) {
            		var challenges = JSON.parse(getInterestedChallengesRequest.responseText);
        			
	        		document.getElementById("interested-challenges-count").innerHTML = challenges.length;
            		for (var challenge of challenges) {
            			var challengeLink = document.createElement("a");
            			challengeLink.href = getChallengePageUrl(challenge.owner.username, challenge.id);
            			challengeLink.innerHTML = challenge.name;
            			document.getElementById("interested-challenges").appendChild(challengeLink);
            			document.getElementById("interested-challenges").appendChild(document.createElement("br"));
            		}
            } else {
                console.error(getInterestedChallengesRequest.statusText);
            }
        }
	}
	
	getInterestedChallengesRequest.onerror = function (e) {
	  console.error(getInterestedChallengesRequest.statusText);
	};
	getInterestedChallengesRequest.send(null);
}

function getCompletedChallenges (username)
{
	var url = getCompletedChallengesUrl(username);
	getCompletedChallengesByUrl(url);
}

function getCompletedChallengesByUrl (url)
{
	var getCompletedChallengesRequest = new XMLHttpRequest();
	getCompletedChallengesRequest.open("GET", url, true);
	
	getCompletedChallengesRequest.onload = function () {
		if (getCompletedChallengesRequest.readyState === 4) {
            if (getCompletedChallengesRequest.status === 200) {
            		var challenges = JSON.parse(getCompletedChallengesRequest.responseText);
        			
	        		document.getElementById("completed-challenges-count").innerHTML = challenges.length;
            		for (var challenge of challenges) {
            			var challengeLink = document.createElement("a");
            			challengeLink.href = getChallengePageUrl(challenge.owner.username, challenge.id);
            			challengeLink.innerHTML = challenge.name;
            			document.getElementById("completed-challenges").appendChild(challengeLink);
            			document.getElementById("completed-challenges").appendChild(document.createElement("br"));
            		}
            } else {
                console.error(getCompletedChallengesRequest.statusText);
            }
        }
	}
	
	getCompletedChallengesRequest.onerror = function (e) {
	  console.error(getCompletedChallengesRequest.statusText);
	};
	getCompletedChallengesRequest.send(null);
}