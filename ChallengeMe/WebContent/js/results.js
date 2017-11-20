function getResultsUrl (q)
{
	return "/ChallengeMe/search?q=" + q;
}

function loadResults (q)
{
	var getResultsRequest = new XMLHttpRequest();
	var url = getResultsUrl(q);
	getResultsRequest.open("GET", url, true);
	
	getResultsRequest.onload = function () {
		if (getResultsRequest.readyState === 4) {
            if (getResultsRequest.status === 200) {
        		var results = JSON.parse(getResultsRequest.responseText);
        		console.log(results);
    			
        		// set count
        		document.getElementById("searched-users-count").innerHTML = results.users.length;
        		document.getElementById("searched-users-count").dataset.value = results.users.length;        		
        		// load users
        		for (var user of results.users) {
        			var userDiv = createUserDiv(user, "searched-user");
        			document.getElementById("searched-users").appendChild(userDiv);
        		}
        		
        		// set count
        		document.getElementById("searched-challenges-count").innerHTML = results.challenges.length;
        		document.getElementById("searched-challenges-count").dataset.value = results.challenges.length; 
        		// load challenges
        		for (var challenge of results.challenges) {
        			var challengeLink = document.createElement("a");
        			challengeLink.href = getChallengePageUrl(challenge.owner.username, challenge.id);
        			challengeLink.innerHTML = challenge.name;
        			document.getElementById("searched-challenges").appendChild(challengeLink);
        			document.getElementById("searched-challenges").appendChild(document.createElement("br"));
        		}
            }
            else {
                console.error(getResultsRequest.statusText);
            }
        }
	}
	
	getResultsRequest.onerror = function (e) {
	  console.error(getResultsRequest.statusText);
	};
	getResultsRequest.send(null);	
}