<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title></title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <style>
  .fa {
    font-size: 36px;
  }
  
  </style>
</head>
<body onload="">
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <ul class="navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="Feed.jsp">
          <i class="fa fa-home" aria-hidden="true"></i>
        </a>
      </li>
      <li class="nav-item profile">
        <a class="nav-link profile" href="Profile.jsp">
          <i class="fa fa-user profile" aria-hidden="true"></i>
        </a>
      </li>
      <!--
      <li class="nav-item">
        <a class="nav-link" href="Search.jsp">Challenge Search</a>
      </li>
      <li class="nav-item">
        No. viewing challenges:
        <div id="num-users">

        </div>
      </li>
      -->
    </ul>
    <form class="navbar-form" action="Results.jsp" method="POST" onsubmit ="return searchPressed();" role="search" >
      <div class="input-group">
        <input id="searchItem" type="text" class="form-control" placeholder="Search...">
        <span class="input-group-btn">
          <button type="submit" class="btn btn-default">
          	<i class="fa fa-search" aria-hidden="true" style="font-size: 20px;"></i>
          </button>
        </span>
      </div>
    </form>
  </nav>
</body>
<script type="text/javascript">
function searchPressed ()
{
	var resultsUrl = "SearchRedirect?q=" + encodeURIComponent(document.getElementById("searchItem").value);
	
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", resultsUrl, false);
	xhttp.send();
}
</script>

</html>
