<%@ page import="java.util.Vector"%>
<%@ page import="db.User" %>
<%@ page import="db.Challenge" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<%@ page import="java.lang.reflect.Type" %>
    
<%@include file="Navbar.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Results Page</title>
	</head>
	<body>
		<h1>Results</h1> <br />
		<%
			Gson gson = new Gson();
		
		// read the List of Users and List of Challenges from the session
		Type userType = new TypeToken<ArrayList<User>>(){}.getType();
		Type challengeType = new TypeToken<ArrayList<Challenge>>(){}.getType();
		String userString = (String)session.getAttribute("userResult");
		System.out.println("user string is: "+ userString);
		ArrayList<User> myUser=(ArrayList<User>)gson.fromJson(userString,userType);
		//Vector<User> myUser = (Vector<User>)gson.fromJson((String)session.getAttribute("userResult"), userType);
				ArrayList<Challenge> myChallenges=(ArrayList<Challenge>)gson.fromJson((String)session.getAttribute("challengeResult"),challengeType);
		System.err.println("size of myUser: "+ myUser.size());

		//Vector<Challenge> myChallenges = (Vector<Challenge>)gson.fromJson((String)session.getAttribute("challengeResult"), challengeType);
		%>
		<h4>User Results</h4>
		<table cellpadding="10" width="800">
		
			<%	
			if (myUser != null)
			{
				for (int i = 0; i < myUser.size(); i ++)
				{
					String name=""; String username="";
					name = myUser.get(i).getName();
					username=myUser.get(i).getUsername();
				%>
				<tr>
				
					<td class="firstcolumn" align="center" rowspan="1" colspan="1"><%=name%> <%=username%></td>
				
				</tr>
				<%
				}
			}
			
			%>
			</table>
				<h4>Challenge Results</h4>
			<table>
		
			<%
			System.out.println("heyo");
			if (myChallenges != null)
			{
				System.out.println("wow: "+myChallenges.size());
				for (int i = 0; i < myChallenges.size(); i ++)
				{
				%>
				<tr><%
				System.out.println("in here");
					String title="";
					String description ="";
					title = myChallenges.get(i).getTitle();
					description = myChallenges.get(i).getDescription();
					System.out.println("CHALLENGE");
					System.out.println(description);
					System.out.println(title);
					%>
					<td class="firstcolumn" align="center" rowspan="1" colspan="1"><%=title%></td>
					<td align = "center" rowspan = "1" colspan = "1" ><%=description%></td>
				</tr>
				<%
				}
			}
			%>
		
		
		
		
		</table>
		
		
		
		
	</body>
</html>