function getChallenges ()
{
	var getChallengesRequest = new XMLHttpRequest();
	getChallengesRequest.open("GET", "/ChallengeMe/challenges", true);

	getChallengesRequest.onload = function () {
		if (getChallengesRequest.readyState === 4) {
            if (getChallengesRequest.status === 200) {
            		var challenges = JSON.parse(getChallengesRequest.responseText);
            		// get rid of the 'Loading...' text
            		document.getElementById("feed").innerHTML = null;
            		// render every challenge element
            		for (var challenge of challenges) {
            			var challengeDiv = createChallengeDiv(challenge);
            			document.getElementById("feed").appendChild(challengeDiv);
            		}
            }
            else {
                console.error(getChallengesRequest.statusText);
            }
        }
	}
	
	getChallengesRequest.onerror = function (e) {
	  console.error(getChallengesRequest.statusText);
	};
	getChallengesRequest.send(null);
}

function createChallengeDiv (challenge)
{
	// <div class="row">
	var rowDiv = document.createElement("div");
	rowDiv.classList.add("row");
	rowDiv.dataset.challengeId = challenge.id;
	//   <div class="col-sm-12 well">
	var colDiv = document.createElement("div");
	colDiv.classList.add("col-sm-12");
	colDiv.classList.add("well");
	
	// name
	var nameLink = document.createElement("a");
	nameLink.href = getChallengePageUrl(challenge.owner.username, challenge.id);
	nameLink.innerHTML = challenge.name;
	var nameHeaderLink = document.createElement("h2");
	nameHeaderLink.appendChild(nameLink);
	// username
	var usernameParagraph = document.createElement("p");
	usernameParagraph.innerHTML = "@" + challenge.owner.username;
	var usernameParagraphBolded = document.createElement("b");
	usernameParagraphBolded.appendChild(usernameParagraph);
	usernameParagraphBolded.onclick = function () {
		window.location.href = "Profile.jsp?username=" + challenge.owner.username;
	}
	// date
	var dateParagraph = document.createElement("p");
	var dateComponents = challenge.createdAt;	
	dateParagraph.innerHTML = new Date(new Number(challenge.createdAt));
	// description
	var descriptionParagraph = document.createElement("p");
	descriptionParagraph.innerHTML = challenge.description;
	
	// views button
	var viewsButton = document.createElement("button");
	viewsButton.type = "button";
	viewsButton.classList.add("views-btn");
	viewsButton.classList.add("btn");
	viewsButton.classList.add("btn-warning");
	viewsButton.classList.add("disabled");
	var viewsButtonIcon = document.createElement("i");
	viewsButtonIcon.classList.add("views-btn-icon");
	viewsButtonIcon.classList.add("fa");
	viewsButtonIcon.classList.add("fa-eye");
	var viewsButtonValueSpan = document.createElement("span");
	viewsButtonValueSpan.classList.add("views-count");
	viewsButton.appendChild(viewsButtonIcon);
	viewsButton.appendChild(document.createTextNode(" "));
	viewsButton.appendChild(viewsButtonValueSpan);
	
	// display views
	var viewsSocket;
	viewsSocket = new WebSocket("ws://localhost:8080/ChallengeMe/views");
	viewsSocket.onopen = function(event) {
		viewsSocket.send("QUERY " + challenge.id);
	}
	viewsSocket.onmessage = function (event) {
		viewsButtonValueSpan.dataset.viewsCount = event.data;
		viewsButtonValueSpan.innerHTML = event.data;
	}
	viewsSocket.onclose = function(event) {
		var x= "CLOSE " + challengeId;
		viewsSocket.send(x)
	};
	
	// interested button
	var interestedButton = document.createElement("button");
	interestedButton.type = "button";
	interestedButton.classList.add("interested-btn");
	interestedButton.classList.add("btn");
	interestedButton.classList.add("btn-secondary");
	var interestedButtonIcon = document.createElement("i");
	interestedButtonIcon.classList.add("interested-btn-icon");
	interestedButtonIcon.classList.add("fa");
	interestedButton.appendChild(interestedButtonIcon);
	interestedButton.innerHTML += " Interested";
	loadInterest(username, challenge.owner.username, challenge.id, interestedButton, null, null);

	// completed button
	var completedButton = document.createElement("button");
	completedButton.type = "button";
	completedButton.classList.add("completed-btn");
	completedButton.classList.add("btn");
	completedButton.classList.add("btn-secondary");
	var completedButtonIcon = document.createElement("i");
	completedButtonIcon.classList.add("completed-btn-icon");
	completedButtonIcon.classList.add("fa");
	completedButtonIcon.classList.add("fa-check");	
	completedButton.appendChild(completedButtonIcon);
	completedButton.innerHTML += " Completed";
	loadCompletion(username, challenge.owner.username, challenge.id, completedButton, null, null);
	
	// add all attributes to colDiv
	colDiv.appendChild(nameHeaderLink);
	colDiv.appendChild(usernameParagraphBolded);
	colDiv.appendChild(dateParagraph);
	colDiv.appendChild(document.createElement("br"));
	colDiv.appendChild(descriptionParagraph);
	colDiv.appendChild(document.createElement("br"));
	colDiv.appendChild(viewsButton);
	colDiv.appendChild(document.createTextNode(" "));
	colDiv.appendChild(interestedButton);
	colDiv.appendChild(document.createTextNode(" "));
	colDiv.appendChild(completedButton);
	
	// add colDiv to rowDiv
	rowDiv.appendChild(colDiv);
	return rowDiv;
}