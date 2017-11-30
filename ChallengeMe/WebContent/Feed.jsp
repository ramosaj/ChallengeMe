<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="Navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Challenges Feed</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <style>    
    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }
    
  
    
	.fa-check, .fa-star, .fa-eye {
	  font-size: 20px;
	}
	
	.btn {
		border-radius:0;
	
	}
	.well{
		border-radius:0;
		border: 2px solid black;
		background-color: white;
		color: black;
		font-family:"Courier New";
	}
	
	.name {
		background-color:black;
		color:white;
		
		
	}
	
  </style>
  <script src="./js/util.js" type="text/javascript"></script>
  <script src="./js/userInterest.js" type="text/javascript"></script>
  <script src="./js/userCompletion.js" type="text/javascript"></script>
  <script src="./js/feed.js" type="text/javascript"></script>
  <script type="text/javascript">
    // Current user
	var username = "<%= session.getAttribute("username") %>";
	console.log(username);
	
	// XXX - for testing purposes
	if (username == "null") {
		username = "guest";
	}

  	window.onload = function() {
  		getChallenges();
  		var username = "<%= request.getSession().getAttribute("username") %>"
		if(username == "guest"){
			var profile = document.getElementsByClassName("profile");
			for(var i=0;i<profile.length;i++){
				profile[i].style.display="none";
			}
			var sub = document.getElementById("submit").disabled=true;
			 
			
		}
  	}
  	
  	function addPost ()
  	{
  		var title = document.getElementById("title").value;
  		var description = document.getElementById("description").value;
  		var categories = document.getElementById("categories").value;
  		alert(categories);
  		var challenge = new XMLHttpRequest();
  		var url = "users/"+"<%=request.getSession().getAttribute("username")%>" + "/challenges";
  		challenge.open("POST", url, true);
  		challenge.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  		var descript = encodeURI(description);
  		var cat = encodeURI(categories);
  		alert(cat);
  	
  		challenge.onreadystatechange = function() {
  			if(this.readyState == 4 && this.status == 200){
  				getChallenges();
  			}
  		}
  		
  		challenge.send("title="+title+"&description="+descript+"&categories="+cat);
	}
  </script>
  <style>
  .form-control {
			width:50%;
			margin:auto;
			border-radius:0;
			border-bottom: 2px solid #000000;
			border-left: none;
			border-right: none;
			border-top:none;
			
		
		}
		
	#submit:active{
			background-color:black;
			color:white;
			
		}
		
		#submit{
			border: 2px solid #000000;
		
		}
		
		.form-control:focus{
			box-shadow: none;
			border-bottom: 2px solid red;
		}
  
  
  </style>
</head>

<body>
<div class="container text-center">    
  <div class="row">
    <div class="col-sm-3">
    </div>
    <div class="col-sm-7">
      <div class="row">
        <div class="col-sm-12 well">
          <h1>
          	<b>CHALLENGE THE WORLD</b>
          </h1> 
          <form method=POST onsubmit="return addPost();">
            Title: <input type="text" class="form-control" id="title" placeholder="Enter Title">
            Description:
            <textarea class="form-control" id="description" placeholder="Description"></textarea>
            <br />
            <textarea class="form-control" id="categories" placeholder="Enter categories"></textarea><br />
            <input type="submit" id="submit" class="form-control">
          </form>
        </div> 
      </div>
      <div id="feed">
      <!--
      <div class="row">
        <div class="col-sm-12 well">
          <div style="text-align: center">
           	<p style="margin:0px">Tommy Trojan </p>
           	<p style="font-size:10px"> created at </p>
           	<h3>Challenge Title</h3>
           	<p> Challenge Description</p>
           	<p> Created at </p>
           	<div class="col-sm-6">
           		Interested
           		<p> Joe blow</p>
           	
           	</div>
           	<div class="col-sm-6">viewing</div>
          </div>
        </div>
      </div>
      -->
      
        <h3>Loading...</h3>
      </div>
    </div>
  </div>
</div>


</body>
</html>