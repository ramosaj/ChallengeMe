var COMPLETED_BTN_CNAME = "completed-btn";
var COMPLETED_BTN_ICON_CNAME = "completed-btn-icon";

function checkCompletionUrl (username, ownername, challengeId)
{
	return "/ChallengeMe/users/" + username + "/completed/" + ownername + "/" + challengeId;
}

function loadCompletion (username, ownername, challengeId, completedButtonEl, markIncompletionCallback, markCompletionCallback)
{
	var checkCompletionRequest = new XMLHttpRequest();
	var url = checkCompletionUrl(username, ownername, challengeId);
	checkCompletionRequest.open("GET", url, true);
	
	// star or checkmark depending on whether the checker is already completed or not	
	checkCompletionRequest.onload = function () {
		if (checkCompletionRequest.readyState === 4) {
            if (checkCompletionRequest.status === 204) {
            	// checker is already completed, display checkmark
            	toggleCompletionedButton(username, ownername, challengeId, completedButtonEl, markIncompletionCallback, markCompletionCallback);
            }
            else if (checkCompletionRequest.status == 404) {
            	// checker is not completed, display star
            	toggleUncompletedButton(username, ownername, challengeId, completedButtonEl, markIncompletionCallback, markCompletionCallback);
            }
            else {
                console.error(checkCompletionRequest.statusText);
            }
        }
	}
	
	checkCompletionRequest.onerror = function (e) {
	  console.error(checkCompletionRequest.statusText);
	};
	checkCompletionRequest.send(null);
}

function toggleCompletionedButton (username, ownername, challengeId, completedButtonEl, markIncompletionCallback, markCompletionCallback)
{
//	var completedButtonIconEl = completedButtonEl.getElementsByClassName(COMPLETED_BTN_ICON_CNAME)[0];
//	completedButtonIconEl.classList.remove("fa-star");
//	completedButtonIconEl.classList.add("fa-check");
	
	completedButtonEl.classList.remove("btn-secondary");
	completedButtonEl.classList.add("btn-success");
	
	completedButtonEl.onclick = function () {
		markIncompletion(username, ownername, challengeId, completedButtonEl, markIncompletionCallback, markCompletionCallback);
	}
}

function toggleUncompletedButton (username, ownername, challengeId, completedButtonEl, markIncompletionCallback, markCompletionCallback)
{
//	var completedButtonIconEl = completedButtonEl.getElementsByClassName(COMPLETED_BTN_ICON_CNAME)[0];
//	completedButtonIconEl.classList.remove("fa-check");
//	completedButtonIconEl.classList.add("fa-star");
	
	completedButtonEl.classList.remove("btn-success");
	completedButtonEl.classList.add("btn-secondary");
		
	completedButtonEl.onclick = function () {
		markCompletion(username, ownername, challengeId, completedButtonEl, markIncompletionCallback, markCompletionCallback);
	}
}

function markCompletion (username, ownername, challengeId, completedButtonEl, markIncompletionCallback, markCompletionCallback)
{
	var markCompletionRequest = new XMLHttpRequest();
	var url = checkCompletionUrl(username, ownername, challengeId);
	markCompletionRequest.open("PUT", url, true);
	
	markCompletionRequest.onload = function () {
		if (markCompletionRequest.readyState === 4) {
            if (markCompletionRequest.status === 204) {
            	toggleCompletionedButton(username, ownername, challengeId, completedButtonEl, markIncompletionCallback, markCompletionCallback);
            	if (markCompletionCallback != null) {
            		markCompletionCallback();
            	}
            }
            else {
                console.error(markCompletionRequest.statusText);
            }
        }
	}
	
	markCompletionRequest.onerror = function (e) {
	  console.error(markCompletionRequest.statusText);
	};
	markCompletionRequest.send(null);
}

function markIncompletion (username, ownername, challengeId, completedButtonEl, markIncompletionCallback, markCompletionCallback)
{
	var markIncompletionRequest = new XMLHttpRequest();
	var url = checkCompletionUrl(username, ownername, challengeId);
	markIncompletionRequest.open("DELETE", url, true);
	
	markIncompletionRequest.onload = function () {
		if (markIncompletionRequest.readyState === 4) {
            if (markIncompletionRequest.status === 204) {
            	toggleUncompletedButton(username, ownername, challengeId, completedButtonEl, markIncompletionCallback, markCompletionCallback);
            	if (markIncompletionCallback != null) {
            		markIncompletionCallback();
            	}
            }
            else {
                console.error(markIncompletionRequest.statusText);
            }
        }
	}
	
	markIncompletionRequest.onerror = function (e) {
	  console.error(markIncompletionRequest.statusText);
	};
	markIncompletionRequest.send(null);
}