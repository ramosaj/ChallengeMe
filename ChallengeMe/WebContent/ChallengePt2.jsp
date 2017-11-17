<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@include file="Navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
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
  </style>
  
  <script>
  	
  	window.onload = function() {
  		getChallenges();
  		
  		
  	}
  	function getChallenges() {
  		var challenges = new XMLHttpRequest();
  		//var interested = new XMLHttpRequest();
  		//var completed = new XMLHttpRequest();
  		

  		challenges.open('GET','challenges',false);
  		challenges.send();
  		alert(challenges.responseText);
  		var response = JSON.parse(challenges.responseText);
  		
  		alert(response.length)
  		var html = "";
  		for(var i=0;i<response.length;i++){
  			html=html+"<div class=\"row\"> <div class=\"col-sm-12 well\">\n";
  			html = html+ "<a href="+ response[i].challengeLink + ">" + response[i].title + " </a>\n";
  			html = html + "<p> Created by: "+response[i].creator +" at "+ response[i].createdAt +"</p>\n";
  			html=html+"<p>"+response[i].description+"</p>\n";
  			html=html+"<div class=\"col-md-6\">";
  			html=html+"<a href=" + response[i].challengeLink+"> Interested Users </a>";
  			html=html+"</div>";
  			html=html+"<div class=\"col-md-6\">";
  			html=html+"<a href="+response[i].challengeLink+"> Users that completed the Challenge </a>";
  			html=html+"</div>";
  			html=html+"</div>";

  		}
  		html+="</div>";
  		document.getElementById("feed").innerHTML = html;
  		
  	}
  	
  	
  	
  	function addPost() {
  		var title = getElementById("title").value;
  		var description = getElementById("description").value;
  		var challenge = new XMLHttpRequest();
  		challenge.open('POST',);
  		
  		
  		
  		
  	}
  
  
  
  
  
  </script>
</head>

<body onload="getChallenges()">

<div class="container text-center">    
  <div class="row">
    <div class="col-sm-3">
    </div>
    <div class="col-sm-7">
    <div class="row">
    		<div class="col-sm-12 well">
    			Upload a challenge. 
    			<form method=POST onsubmit="return addPost();">
    				Title:<input type="text" class="form-control" id="title" placeholder="Enter Title">
    				Description: <textarea class="form-control" id="description" placeholder="Description">
    				
    				</textarea>
    				<br />
    				<input type="submit" class="form-control">
    				
    				
    			
    			</form>
    		
    		</div> 
    </div>
    <div id="feed">
      
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
     </div>
   
     
    </div>
  </div>
</div>

<footer class="container-fluid text-center">
  <p>Footer Text</p>
</footer>

</body>
</html>