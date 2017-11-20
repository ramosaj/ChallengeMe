package servlets;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SearchRedirect")
public class SearchRedirect extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String query = request.getParameter("q");	
		String url = "Results.jsp?q=" + URLEncoder.encode(query, "UTF-8");
		
		session.setAttribute("query", query);
		request.getRequestDispatcher(url).forward(request, response);
	}

}