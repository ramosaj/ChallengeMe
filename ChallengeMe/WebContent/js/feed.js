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
	//   <div class="col-sm-12 well">
	var colDiv = document.createElement("div");
	colDiv.classList.add("col-sm-12");
	colDiv.classList.add("well");
	
	// name
	var nameLink = document.createElement("a");
	nameLink.href = "#"; // TODO
	nameLink.onclick = function () {
		// TODO
	}
	nameLink.innerHTML = challenge.name;
	var nameHeaderLink = document.createElement("h2");
	nameHeaderLink.appendChild(nameLink);
	// username
	var usernameParagraph = document.createElement("p");
	usernameParagraph.innerHTML = challenge.owner.username;
	// date
	var dateParagraph = document.createElement("p");
	dateParagraph.innerHTML = "at " + challenge.createdAt;
	// description
	var descriptionParagraph = document.createElement("p");
	descriptionParagraph.innerHTML = challenge.description;
	// interested button
	var interestedButton = document.createElement("button");
	interestedButton.type = "button";
	interestedButton.classList.add("btn");
	interestedButton.classList.add("btn-primary");
	interestedButton.innerHTML = "Interested";
	
	// add all attributes to colDiv
	colDiv.appendChild(nameHeaderLink);
	colDiv.appendChild(usernameParagraph);
	colDiv.appendChild(dateParagraph);
	colDiv.appendChild(document.createElement("br"));
	colDiv.appendChild(descriptionParagraph);
	colDiv.appendChild(document.createElement("br"));
	colDiv.appendChild(interestedButton);
	
	// add colDiv to rowDiv
	rowDiv.appendChild(colDiv);
	return rowDiv;
}