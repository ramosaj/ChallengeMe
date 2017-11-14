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
  	function getChallenges() {
  		var challenges = new XMLHttpRequest();
  		var interested = new XMLHttpRequest();
  		var completed = new XMLHttpRequest();
  		
  		var userId = <%request.getSession().getAttribute("userId");%>
  		xhttp.open('GET','/challenges',false);
  		var response = JSON.parse(xhttp.responseText);
  	
  		var html = "<div class=\"row\"> <div class=\"col-sm-12 well\">\n"";
  		for(var i=0;i<response.length;i++){
  			html = html+ "<p> "+ response[i].title + " </p>\n";
  			html = html + "<p> Created by: "+response[i].creator +" at "+ response[i].createdAt +"</p>\n";
  			html=html+"<p>"+response[i].description+"</p>\n";
  			interested.open('GET',reponse[i].interestedUrl,false);
  			html=html+"<div class=\"col-md-6\">
  			
  			
  			
  			
  			
  		}
  		html+="</div></div>"
  		
  		
  		
  		
  		
  		
  		
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
    			<form method=POST action="addPost()" onsubmit="updatePost()">
    				Title:<input type="text" class="form-control" placeholder="Enter Title">
    				Description: <textarea class="form-control" placeholder="Description">
    				
    				</textarea>
    				<br />
    				<input type="submit" class="form-control">
    				
    				
    			
    			</form>
    		
    		</div> 
    </div>
    
      
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

<footer class="container-fluid text-center">
  <p>Footer Text</p>
</footer>

</body>
</html>