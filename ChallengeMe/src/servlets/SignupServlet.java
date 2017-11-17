package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.User;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException
	{
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		Boolean userExists = null;
		try {
			userExists = User.validateUsername(username);
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			userExists = false;
		}
		
		if (userExists) {
			request.setAttribute("errmsg", "Invalid username");
			// response.sendRedirect("SignLog.jsp");
		}
		else {
			try {
				User.add(username, password, email, fname + lname, "", "");
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		
	}

}
