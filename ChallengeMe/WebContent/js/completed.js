var COMPLETED_BTN_CNAME = "completed-btn";
var COMPLETED_BTN_ICON_CNAME = "completed-btn-icon";

function checkCompletedUrl (username, ownername, challengeId)
{
	return "/ChallengeMe/users/" + username + "/completed/" + ownername + "/" + challengeId;
}

function loadCompleted (username, ownername, challengeId, CompletedButtonEl, deleteCompletedCallback, presentCompletedCallback)
{
	var checkCompletedRequest = new XMLHttpRequest();
	var url = checkCompletedUrl(username, ownername, challengeId);
	checkCompletedRequest.open("GET", url, true);
	
	// star or checkmark depending on whether the checker is already completed or not	
	checkCompletedRequest.onload = function () {
		if (checkCompletedRequest.readyState === 4) {
            if (checkCompletedRequest.status === 204) {
            	// checker is already completed, display checkmark
            	toggleCompletedButton(username, ownername, challengeId, CompletedButtonEl, deleteCompletedCallback, presentCompletedCallback);
            }
            else if (checkCompletedRequest.status == 404) {
            	// checker is not completed, display star
            	toggleInCompletedButton(username, ownername, challengeId, CompletedButtonEl, deleteCompletedCallback, presentCompletedCallback);
            }
            else {
                console.error(checkCompletedRequest.statusText);
            }
        }
	}
	
	checkCompletedRequest.onerror = function (e) {
	  console.error(checkCompletedRequest.statusText);
	};
	checkCompletedRequest.send(null);
}

function toggleCompletedButton (username, ownername, challengeId, completedButtonEl, deleteCompleteCallback, presentCompleteCallback)
{
	var completedButtonIconEl = completedButtonEl.getElementsByClassName(COMPLETED_BTN_ICON_CNAME)[0];
	completedButtonIconEl.classList.remove("fa-star");
	completedButtonIconEl.classList.add("fa-check");
	
	completedButtonEl.classList.remove("btn-secondary");
	completedButtonEl.classList.add("btn-primary");
	
	completedButtonEl.onclick = function () {
		deleteCompleted(username, ownername, challengeId, completedButtonEl, deleteCompleteCallback, presentCompleteCallback);
	}
}


function toggleInCompletedButton (username, ownername, challengeId, completedButtonEl, deleteCompletCallback, presentCompletCallback)
{
	var completedButtonIconEl = completedButtonEl.getElementsByClassName(COMPLETED_BTN_ICON_CNAME)[0];
	completedButtonIconEl.classList.remove("fa-check");
	completedButtonIconEl.classList.add("fa-star");
	
	completedButtonEl.classList.remove("btn-primary");
	completedButtonEl.classList.add("btn-secondary");
		
	completedButtonEl.onclick = function () {
		presentCompleted(username, ownername, challengeId, completedButtonEl, deleteCompletCallback, presentCompletCallback);
	}
}
function presentCompleted (username, ownername, challengeId, completedButtonEl, deleteCompletCallback, presentCompletCallback)
{
	var presentCompletRequest = new XMLHttpRequest();
	var url = checkCompletedUrl(username, ownername, challengeId);
	presentCompletRequest.open("PUT", url, true);
	
	presentCompletRequest.onload = function () {
		if (presentCompletRequest.readyState === 4) {
            if (presentCompletRequest.status === 204) {
            	toggleCompletedButton(username, ownername, challengeId, completedButtonEl, deleteCompletCallback, presentCompletCallback);
            	if (presentCompletCallback != null) {
            		presentCompletCallback();
            	}
            }
            else {
                console.error(presentCompletRequest.statusText);
            }
        }
	}
	
	presentCompletRequest.onerror = function (e) {
	  console.error(presentCompletRequest.statusText);
	};
	presentCompletRequest.send(null);
}

function deleteCompleted (username, ownername, challengeId, interestedButtonEl, deleteCompletedCallback, presentCompletedCallback)
{
	var deleteCompletedRequest = new XMLHttpRequest();
	var url = checkCompletedUrl(username, ownername, challengeId);
	deleteCompletedRequest.open("DELETE", url, true);
	
	deleteCompletedRequest.onload = function () {
		if (deleteCompletedRequest.readyState === 4) {
            if (deleteCompletedRequest.status === 204) {
            	toggleInCompletedButton(username, ownername, challengeId, completedButtonEl, deleteCompletedCallback, presentCompletedCallback);
            	if (deleteCompletedCallback != null) {
            		deleteCompletedCallback();
            	}
            }
            else {
                console.error(deleteCompletedRequest.statusText);
            }
        }
	}
	
	deleteCompletedRequest.onerror = function (e) {
	  console.error(deleteCompletedRequest.statusText);
	};
	deleteCompletedRequest.send(null);
}

