
<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
	<style> 
		body{
			font-family:"Courier New";
		
		}
		.form-control {
			width:50%;
			margin:auto;
			border-radius:0;
			border-bottom: 2px solid #000000;
			border-left: none;
			border-right: none;
			border-top:none;
			
		
		}
		
		.form-control:focus{
			box-shadow: none;
			border-bottom: 2px solid red;
		}
		
		.submit:active{
			background-color:black;
			color:white;
			
		}
		
		.submit{
			border: 2px solid #000000;
		
		}
		
		.btn {
			border-radius:0;
			background-color:white;
			border: 2px solid #000000;
		}
		
		
		 
	</style>
    <meta charset="UTF-8">
    <title>Log In Page</title>
    <script>
        function load() {
            /* document.getElementById("loginform").style.display = "block"; */
            document.getElementById("signupform").style.display = "block";
			document.getElementById("signupform").style.display = "none";

        }
        function loginButtonPressed()
        
        {
            document.getElementById("loginform").style.display = "block";
            document.getElementById("signupform").style.display = "none";
        }
        function signupButtonPressed() {
            document.getElementById("signupform").style.display = "block";
            document.getElementById("loginform").style.display = "none";
        }
        
        function loginPressed() {
        	
        		var username = document.loginform.username.value;
        		var password = document.loginform.password.value;
        		
        		if(username.length == 0 && password.length==0){
        			username="Guest";
        			password="Guest"
        		}
        		
        		
        		/* window.alert(username + " " + password); */
        		if (username.length == 0 || password.length == 0)
        		{
        			return false;
        		}
        		var requeststr = "LoginServlet?";
        		requeststr += "username=" + username; 
        		requeststr += "&password=" + password; 
        		
        		
        		
        		var xhttp = new XMLHttpRequest();
        		xhttp.open("POST", requeststr, false);
        		xhttp.send();

        		if(xhttp.responseText.trim().length > 0) {
        			alert(xhttp.responseText);
        			document.getElementById("err_message").innerHTML = xhttp.responseText;
        			return false;
        		}
        		window.location.assign("Feed.jsp");
        		return true; 
        		/* return false; */
        }
        function signupPressed() {
        		var fname = document.signupform.fname.value;
        		var lname = document.signupform.lname.value;
        		var username = document.signupform.username.value;
        		var email = document.signupform.email.value;
        		var password = document.signupform.password.value;
        		var repassword = document.signupform.reenterpassword.value;
        		
        		if(fname.length == 0 || lname.length == 0 || username.length == 0
        				|| email.length == 0 || email.length == 0 || password.length ==0 || repassword == 0)
        		{
        			return false;
        		}
        		if (password != repassword)
        		{
        			return false;
        		}
        		
        		
        		
        		var requeststr = "SignupServlet?";
        		requeststr += "fname=" + fname;
        		requeststr += "&lname=" + lname;
        		requeststr += "&username=" + username;
        		requeststr += "&email=" + email;
        		requeststr += "&password=" + password;
        	
        		var xhttp = new XMLHttpRequest();
        		xhttp.open("POST", requeststr, false);
        		xhttp.send();
        		if(xhttp.responseText.trim().length > 0) {
        			document.getElementById("err_message").innerHTML = xhttp.responseText;
        			return false;
        		}

        		
        		
        		return true;
        	
        }





        
    </script>


</head>
    <body onload = "load()">
        <h1 align = "center"> Challenge Me </h1>
        <div id="buttons" align="center">
            <button onclick="loginButtonPressed()" class="submit btn" name="loginButton" value="LOG IN" id="loginButton">LOG IN</button>
            <button onclick="signupButtonPressed()" class="submit btn" name="loginButton" value="SIGN UP">SIGN UP</button>
            <br />
        </div>
		<br />
        <form id="loginform" name = "loginform" method="POST" align="center" action="Feed.jsp" onsubmit ="return loginPressed();">
            <!-- <h6>Login</h6> -->
            <input type="text" class="form-control" name="username" placeholder="USERNAME"/><br />
            <input type="password" class="form-control" name="password" placeholder="PASSWORD" /><br />
            <input type="submit" class="form-control submit" name="login" value="LOG IN">
        </form>
        <form id="signupform" name="signupform" method="POST" align="center" onsubmit="return signupPressed();" action="SignLog.jsp">
            <!-- <h6>Signup</h6> -->
            <input type="text" class="form-control" name="fname" placeholder="FIRST NAME"/><br />
            <input type="text" class="form-control" name="lname" placeholder="LAST NAME"/><br />
            <input type="text" class="form-control" name="username" placeholder="USERNAME"/><br />
            
            <input type="email" class="form-control" name="email" placeholder="EMAIL" /><br />
            
            <input type="text"class="form-control"  name="password" placeholder="ENTER PASSWORD" /><br />
            <input type="text" class="form-control" name="reenterpassword" placeholder="RE-ENTER PASSWORD" /><br />
            
            <input type="submit" class="form-control submit" name="signup" value="SIGN UP NOW">
        </form>
        <div id="err_message" align="center" style="color:#0000FF"></div>

    </body>
</html>