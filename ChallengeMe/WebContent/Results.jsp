<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@include file="Navbar.jsp"%>
<%
	String query = (String) session.getAttribute("query");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Results Page</title>
  <script src="./js/results.js" type="text/javascript"></script>
  <script src="./js/util.js" type="text/javascript"></script>

  <script>
  var url = new URL(window.location.href);
  var query = url.searchParams.get("q");
  
  if (query == null) {
	  query = "<%= query %>";
  }
  
  window.onload = function ()
  {
	console.log("Searching '" + query + "' ...")
    loadResults(query);  
  }
  </script>
  <style>
  #search-results {
  	margin-top: 30px;
  }
  </style>
</head>
<body>
<div class="container" id="search-results">
  <div class="row">
  	<div class="col" id="searched-users">
  	  <h2>Users (<span id="searched-users-count"></span>)</h2>
  	</div>
  	<div class="col" id="searched-challenges">
  	  <h2>Challenges (<span id="searched-challenges-count"></span>)</h2>
  	</div>
  </div>
</div>
</body>
</html>