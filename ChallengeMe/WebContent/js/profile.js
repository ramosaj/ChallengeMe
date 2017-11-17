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
            		// XXX
            		var responseText = "{\r\n    \u201Cid\u201D: \u201C123456789\u201D,\r\n    \u201Cusername\u201D: \u201Ctommytrojan\u201D,\r\n    \u201Cname\u201D: \u201CTommy Trojan\u201D,\r\n    \u201Curl\u201D: \u201Chttps:\/\/api.dareme.com\/users\/tommytrojan\u201D,\r\n    \u201CavatarUrl\u201D: \u201Chttps:\/\/theleftbench.com\/wp-content\/uploads\/2017\/03\/USC.png\u201D,\r\n    \u201Cbio\u201D: \u201CFight On!\u201D,\r\n    \u201CchallengesCount\u201D: 1,\r\n    \u201CchallengesUrl\u201D: \u201Chttps:\/\/api.dareme.com\/users\/tommytrojan\/challenges\u201D,\r\n    \u201CinterestedUrl\u201D: \u201Chttps:\/\/api.dareme.com\/users\/tommytrojan\/interested\u201D,\r\n    \u201CcompletedCount\u201D: 0,\r\n    \u201CcompletedUrl\u201D: \u201Chttps:\/\/api.dareme.com\/users\/tommytrojan\/completed\u201D,\r\n    \u201CcreatedAt\u201D: 1507921778,\r\n    \u201ClastActive\u201D: 1507921778\r\n}";
            		responseText = responseText.replace(/\u201C/g, '"').replace(/\u201D/g, '"');
            		var user = JSON.parse(getUserRequest.responseText);
        			
                document.getElementById("full-name-value").innerHTML = user.name;
	        		document.getElementById("username-value").innerHTML = user.username;
	        		document.getElementById("joined-value").innerHTML = user.createdAt;
	        		document.getElementById("bio-value").innerHTML = user.bio;
	        		
	        		getChallengesByUrl(user.challengesUrl);
	        		getInterestedChallengesByUrl(user.interestedUrl);
	        		getCompletedChallengesByUrl(user.completedUrl);
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
	// XXX
	url = "http://localhost:8080/ChallengeMe/Profile.jsp";
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
            			challengeLink.href = challenge.url;
            			challengeLink.innerHTML = challenge.name;
            			document.getElementById("posted-challenges").appendChild(challengeLink);
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
            			challengeLink.href = challenge.url;
            			challengeLink.innerHTML = challenge.name;
            			document.getElementById("interested-challenges").appendChild(challengeLink);
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
            			challengeLink.href = challenge.url;
            			challengeLink.innerHTML = challenge.name;
            			document.getElementById("completed-challenges").appendChild(challengeLink);
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