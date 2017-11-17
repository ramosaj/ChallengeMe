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
    
	.fa-check, .fa-star, .fa-times {
	  font-size: 20px;
	}
  </style>
  <script src="./js/util.js" type="text/javascript"></script>
  <script src="./js/userInterest.js" type="text/javascript"></script>
  <script src="./js/feed.js" type="text/javascript"></script>
  <script type="text/javascript">
    // Current user
	var username = "anthony"; // XXX
  	window.onload = getChallenges;
  	
  	function addPost() {
  		var title = getElementById("title").value;
  		var description = getElementById("description").value;
  		var challenge = new XMLHttpRequest();
  		var url = "user/" +<%=request.getSession().getAttribute("username")%>+"/challenges";
  		xhttp.open("POST", url, true);
  		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  		var descript = encodeURI(description);
  		challenge.open('POST',"title="+title+"&description=descript",true);
  		challenge.onreadystatechange = function() {
  			if(this.readyState == 4 && this.status == 200){
  				getChallenges();
  				
  			}
  		}
  		challenge.send();
  		
  		
  		
  		
  		
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
          <h1>
          	<b>CHALLENGE THE WORLD</b>
          </h1> 
          <form method=POST onsubmit="return addPost();">
            Title: <input type="text" class="form-control" id="title" placeholder="Enter Title">
            Description:
            <textarea class="form-control" id="description" placeholder="Description"></textarea>
            <br />
            <input type="submit" class="form-control">
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

<footer class="container-fluid text-center">
  <p>Footer Text</p>
</footer>

</body>
</html>