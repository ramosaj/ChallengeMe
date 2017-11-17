<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title></title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script>
    function getnumusers(){
        var ws = new WebSocket("ws://serverSocket blah blah");
        ws.onopen = function() {
            document.getElementById("num-users")




        }

        ws.onmessage = function() {



        }


        ws.onclose = function () {


        }



    }


  </script>
  <script src="./js/search.js" type="text/javascript"></script>
</script>
</head>
<body onload="">
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <ul class="navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="Profile.jsp">User Profile</a>
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
    <form class="navbar-form" action="search" role="search" >
      <div class="input-group">
        <input id="searchItem" type="text" class="form-control" placeholder="Search...">
        <span class="input-group-btn">
          <button type="submit" class="btn btn-default">Search
            <span class="glyphicon glyphicon-search">
              <span class="sr-only">Search...</span>
            </span>
          </button>
        </span>
      </div>
    </form>
  </nav>
</body>
</html>
