function getChallengePageUrl (username, challengeId)
{
	return "/ChallengeMe/Challenge.jsp?username=" + username + "&challengeId=" + challengeId;
}

function getProfilePageUrl (username)
{
	return "/ChallengeMe/Profile.jsp?username=" + username;
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