<%@ page import="java.util.Vector"%>
<%@ page import="db.User" %>
<%@ page import="db.Challenge" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.util.ArrayList"%>
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
		Type userType = new TypeToken<Vector<User>>(){}.getType();
		Type challengeType = new TypeToken<Vector<Challenge>>(){}.getType();
		Vector<User> myUser = (Vector<User>)gson.fromJson((String)session.getAttribute("userResult"), userType);
		Vector<Challenge> myChallenges = (Vector<Challenge>)gson.fromJson((String)session.getAttribute("challengeResult"), challengeType);
		%>
		
		<table cellpadding="10" width="800">
			<%	
			if (myUser != null)
			{
				for (int i = 0; i < myUser.size(); i ++)
				{
				%>
				<tr>
				
					<td class="firstcolumn" align="center" rowspan="1" colspan="1" name="<%=myUser.get(i).getUsername()%>"><%= myUser.get(i).getUsername() %></td>
				
				</tr>
				<%
				}
			}
			%>
			
			<%
			if (myChallenges != null)
			{
				for (int i = 0; i < myChallenges.size(); i ++)
				{
				%>
				<tr>
				
					<td class="firstcolumn" align="center" rowspan="1" colspan="1" name="<%=myChallenges.get(i).getTitle()%>"><%= myChallenges.get(i).getTitle() %></td>
					<td align = "center" rowspan = "1" colspan = "1" name = "<%=myChallenges.get(i).getDescription() %>"><%=myChallenges.get(i).getDescription() %></td>
				</tr>
				<%
				}
			}
			%>
		
		
		
		
		</table>
		
		
		
		
	</body>
</html>