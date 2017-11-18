package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.User;
import db.User.UserNotFoundException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Why are we using the GET protocol for logging in...
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("logging in " + username);
		
		Boolean userExists = null;
		try {
			userExists = User.validation(username, password);
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			userExists = false;
		}
		
		if (!userExists) {
			request.getSession().setAttribute("Authenticated", "NO");
//			request.setAttribute("errmsg", "Invalid username and password.");
			String content = "Invalid username and password.";
			response.setContentType("text/json");
			
            response.getWriter().print(content);
//			response.sendRedirect("SignLog.jsp");
		}
		else {
			request.getSession().setAttribute("Authenticated", "YES");
			User currUser = null;
			try {
				currUser = User.get(username);
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} catch (UserNotFoundException unfe) {
				unfe.printStackTrace();
			}
			request.getSession().setAttribute("userID", currUser.getId());
			request.getSession().setAttribute("username", currUser.getUsername());
			System.out.println(request.getSession().getAttribute("username"));
		}
	}
}
