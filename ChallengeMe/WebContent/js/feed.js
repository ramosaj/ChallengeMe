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
	var dateComponents = challenge.createdAt.split(" ");
	var month = dateComponents[1];
	var day = dateComponents[2];
	var time = dateComponents[3];
	var year = dateComponents[5];
	dateParagraph.innerHTML = "Created: " + month + " " + day + ", " + year + " at " + time;
	// description
	var descriptionParagraph = document.createElement("p");
	descriptionParagraph.innerHTML = challenge.description;
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
	
	//completed button
	var completed = document.createElement("button");
	completed.type = "button";
	completed.classList.add("completed-btn");
	completed.classList.add("btn");
	completed.classList.add("btn-secondary");
	var completedIcon = document.createElement("i");
	completedIcon.classList.add("completed-btn-icon");
	completedIcon.classList.add("fa");
	completed.appendChild(completedIcon);
	completed.innerHTML += " Completed";
	
	loadCompleted(username,challenge.owner.username,challenge.id,completed,null,null);
	
	
	
	// add all attributes to colDiv
	colDiv.appendChild(nameHeaderLink);
	colDiv.appendChild(usernameParagraphBolded);
	colDiv.appendChild(dateParagraph);
	colDiv.appendChild(document.createElement("br"));
	colDiv.appendChild(descriptionParagraph);
	colDiv.appendChild(document.createElement("br"));
	colDiv.appendChild(interestedButton);
	colDiv.appendChild(document.createTextNode(" "));
	colDiv.appendChild(completed);
	
	// add colDiv to rowDiv
	rowDiv.appendChild(colDiv);
	return rowDiv;
}