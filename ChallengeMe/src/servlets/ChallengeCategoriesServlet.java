package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import db.Challenge;
import db.Database;

/**
 * DEPRECATED. DO NOT TOUCH.
 * 
 * @author Eddo W. Hintoso
 * @since November 11, 2017
 */
@WebServlet("/challenge/user/categories")
public class ChallengeCategoriesServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final Connection dbConnection = Database.getConnection(true);
		
	protected void doGet (HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		String username = request.getParameter("username");
		Long challengeId = Long.parseLong(request.getParameter("challengeId"));
		
		JsonArray categoriesJSON = new JsonArray();
		
		try {
			// get user challenges
			Challenge challenge = Challenge.get(challengeId);
			// modified by Peter
			for (String category : challenge.getCategories()) {
				categoriesJSON.add(new JsonPrimitive(category));
			}
			
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		JsonObject payload = new JsonObject();
		payload.add("names", categoriesJSON);
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		out.print(payload.toString());
		out.flush();
	}
	
	protected void doPut (HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		String username = request.getParameter("username");
		Long challengeId = Long.parseLong(request.getParameter("challengeId"));
		
		// get request body
		BufferedReader br = request.getReader();
		String requestBodyString = br.lines().collect(Collectors.joining(System.lineSeparator()));
		JsonObject requestBody = new JsonParser().parse(requestBodyString).getAsJsonObject();
		
		// extract values from request body
		List<String> categories = new ArrayList<>();
		JsonArray categoriesJSONArray = requestBody.get("categories").getAsJsonArray();
		for (JsonElement c : categoriesJSONArray) {
			categories.add(c.getAsString());
		}
		
		try {
			Challenge.replaceCategories(challengeId, categories);
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		out.print(requestBodyString);
		out.flush();
	}

}
