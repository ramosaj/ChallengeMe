var INTERESTED_BTN_CNAME = "interested-btn";
var INTERESTED_BTN_ICON_CNAME = "interested-btn-icon";

function checkInterestUrl (username, ownername, challengeId)
{
	return "/ChallengeMe/users/" + username + "/interested/" + ownername + "/" + challengeId;
}

function loadInterest (username, ownername, challengeId, interestedButtonEl, deleteInterestCallback, presentInterestCallback)
{
	var checkInterestRequest = new XMLHttpRequest();
	var url = checkInterestUrl(username, ownername, challengeId);
	checkInterestRequest.open("GET", url, true);
	
	// star or checkmark depending on whether the checker is already interested or not	
	checkInterestRequest.onload = function () {
		if (checkInterestRequest.readyState === 4) {
            if (checkInterestRequest.status === 204) {
            	// checker is already interested, display checkmark
            	toggleInterestedButton(username, ownername, challengeId, interestedButtonEl, deleteInterestCallback, presentInterestCallback);
            }
            else if (checkInterestRequest.status == 404) {
            	// checker is not interested, display star
            	toggleUninterestedButton(username, ownername, challengeId, interestedButtonEl, deleteInterestCallback, presentInterestCallback);
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

function toggleInterestedButton (username, ownername, challengeId, interestedButtonEl, deleteInterestCallback, presentInterestCallback)
{
	var interestedButtonIconEl = interestedButtonEl.getElementsByClassName(INTERESTED_BTN_ICON_CNAME)[0];
	interestedButtonIconEl.classList.remove("fa-star");
	interestedButtonIconEl.classList.add("fa-check");
	
	interestedButtonEl.classList.remove("btn-secondary");
	interestedButtonEl.classList.add("btn-primary");
	
	interestedButtonEl.onclick = function () {
		deleteInterest(username, ownername, challengeId, interestedButtonEl, deleteInterestCallback, presentInterestCallback);
	}
}

function toggleUninterestedButton (username, ownername, challengeId, interestedButtonEl, deleteInterestCallback, presentInterestCallback)
{
	var interestedButtonIconEl = interestedButtonEl.getElementsByClassName(INTERESTED_BTN_ICON_CNAME)[0];
	interestedButtonIconEl.classList.remove("fa-check");
	interestedButtonIconEl.classList.add("fa-star");
	
	interestedButtonEl.classList.remove("btn-primary");
	interestedButtonEl.classList.add("btn-secondary");
		
	interestedButtonEl.onclick = function () {
		presentInterest(username, ownername, challengeId, interestedButtonEl, deleteInterestCallback, presentInterestCallback);
	}
}

function presentInterest (username, ownername, challengeId, interestedButtonEl, deleteInterestCallback, presentInterestCallback)
{
	var presentInterestRequest = new XMLHttpRequest();
	var url = checkInterestUrl(username, ownername, challengeId);
	presentInterestRequest.open("PUT", url, true);
	
	presentInterestRequest.onload = function () {
		if (presentInterestRequest.readyState === 4) {
            if (presentInterestRequest.status === 204) {
            	toggleInterestedButton(username, ownername, challengeId, interestedButtonEl, deleteInterestCallback, presentInterestCallback);
            	if (presentInterestCallback != null) {
            		presentInterestCallback();
            	}
            }
            else {
                console.error(presentInterestRequest.statusText);
            }
        }
	}
	
	presentInterestRequest.onerror = function (e) {
	  console.error(presentInterestRequest.statusText);
	};
	presentInterestRequest.send(null);
}

function deleteInterest (username, ownername, challengeId, interestedButtonEl, deleteInterestCallback, presentInterestCallback)
{
	var deleteInterestRequest = new XMLHttpRequest();
	var url = checkInterestUrl(username, ownername, challengeId);
	deleteInterestRequest.open("DELETE", url, true);
	
	deleteInterestRequest.onload = function () {
		if (deleteInterestRequest.readyState === 4) {
            if (deleteInterestRequest.status === 204) {
            	toggleUninterestedButton(username, ownername, challengeId, interestedButtonEl, deleteInterestCallback, presentInterestCallback);
            	if (deleteInterestCallback != null) {
            		deleteInterestCallback();
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