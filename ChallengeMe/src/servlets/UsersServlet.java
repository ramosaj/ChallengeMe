package servlets;

import db.User;
import db.Challenge;
import db.Database;

import util.Serializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

/**
 * @author Eddo W. Hintoso
 * @since November 11, 2017
 */
@WebServlet("/users/*")
public class UsersServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final Connection dbConnection = null; // Database.getConnection(true);
		
	private static final Set<String> validQueryables = new HashSet<String>(Arrays.asList(new String[] {"interested", "completed", "challenges"}));
	
	/**
	 * A inner helper class to extract parameters from a requested URL paths.
	 * 
	 * @author Eddo W. Hintoso
	 * @since November 11, 2017
	 */
	private class PathExtractor
	{
		protected String username, toBeQueried, ownername;
		protected Long challengeId;
		
		public PathExtractor (String path)
		{
			if (path == null || path.equals("/")) return;
			
			String[] splits = path.split("/");
			
			if (splits.length >= 2) {
				username = splits[1];
			}
			if (splits.length >= 3) {
				toBeQueried = splits[2];
			}
			if (splits.length >= 4) {
				ownername = splits[3];
			}
			if (splits.length >= 5) {
				challengeId = Long.parseLong(splits[4]);
			}
		}
	}
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		// url mapping
		String pathInfo = request.getPathInfo();
		PathExtractor extractor = new PathExtractor(pathInfo);
		
		if (extractor.username == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		// GET /users/:username
		if (extractor.toBeQueried == null) {
			getUser(extractor.username, request, response);
		}
		else if (extractor.toBeQueried.equals("interested")) {
			// GET /users/:username/interested
			if (extractor.ownername == null && extractor.challengeId == null) {
				listInterestedChallenges(extractor.username, request, response);
			}
			// GET /users/:username/interested/:owner/:challenge
			else if (extractor.ownername != null && extractor.challengeId != null) {
				checkInterest(extractor.username, extractor.ownername, extractor.challengeId, request, response);
			}
			// invalid 
			else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		else if (extractor.toBeQueried.equals("completed")) {
			// GET /users/:username/completed
			if (extractor.ownername == null && extractor.challengeId == null) {
				listCompletedChallenges(extractor.username, request, response);
			}
			// GET /users/:username/completed/:owner/:challenge
			else if (extractor.ownername != null && extractor.challengeId != null) {
				checkCompletion(extractor.username, extractor.ownername, extractor.challengeId, request, response);
			}
			// invalid 
			else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		else if (extractor.toBeQueried.equals("challenges")) {
			// GET /users/:username/challenges
			if (extractor.ownername == null && extractor.challengeId == null) {
				listChallenges(extractor.username, request, response);
			}
			// invalid
			else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
		else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	protected void doPost (HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		// url mapping
		String pathInfo = request.getPathInfo();
		PathExtractor extractor = new PathExtractor(pathInfo);
		
		// POST /users/:username/challenges
		if (extractor.toBeQueried != null
				&& extractor.toBeQueried.equals("challenges")
				&& extractor.ownername == null
				&& extractor.challengeId == null) {
			createChallenge(extractor.username, request, response);
		}
		else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	/**
	 * GET /users/:username
	 * 
	 * Get a single user.
	 * 
	 * @param username
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getUser (String username, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		JsonElement payload = null;
		try {
			payload = Serializer.getUserJSON(User.get(username));
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		out.print(payload.toString());
		out.flush();
	}
	
	/**
	 * GET /users/:username/interested
	 * 
	 * List all challenges that interest a user.
	 * 
	 * @param username
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void listInterestedChallenges (String username, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		Integer limit = Integer.parseInt(request.getParameter("limit"));
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		// for fast development, do not program in sort or limit in queries yet
		
		JsonArray challengesJSON = new JsonArray();
		
		try {
			// get user challenges
			List<Challenge> interestedChallenges = User.get(username).getInterestedChallenges();
			for (Challenge challenge : interestedChallenges) {
				JsonElement challengeJSON = Serializer.getChallengeJSON(challenge, true);
				challengesJSON.add(challengeJSON);
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		JsonElement payload = challengesJSON;
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		out.print(payload.toString());
		out.flush();
	}
	
	/**
	 * GET /users/:username/interested/:owner/:challenge
	 * 
	 * Check if a user is interested in a challenge.
	 * 
	 * @param username
	 * @param ownername
	 * @param challengeId
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void checkInterest (String username, String ownername, Long challengeId, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		try {
			User user = User.get(username);
			boolean interested = User.checkInterest(user.getId(), challengeId);
			
			response.setStatus((interested) ? HttpServletResponse.SC_NO_CONTENT : HttpServletResponse.SC_NOT_FOUND);
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * GET /users/:username/completed
	 * 
	 * List all challenges that a user has completed.
	 * 
	 * @param username
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void listCompletedChallenges (String username, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		Integer limit = Integer.parseInt(request.getParameter("limit"));
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		// for fast development, do not program in sort or limit in queries yet
		
		JsonArray challengesJSON = new JsonArray();
		
		try {
			// get user challenges
			List<Challenge> interestedChallenges = User.get(username).getCompletedChallenges();
			for (Challenge challenge : interestedChallenges) {
				JsonElement challengeJSON = Serializer.getChallengeJSON(challenge, true);
				challengesJSON.add(challengeJSON);
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		JsonElement payload = challengesJSON;
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		out.print(payload.toString());
		out.flush();
	}
	
	/**
	 * GET /users/:username/completed/:owner/:challenge
	 * 
	 * Check if a user has completed a challenge.
	 * 
	 * @param username
	 * @param ownername
	 * @param challengeId
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void checkCompletion (String username, String ownername, Long challengeId, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		try {
			User user = User.get(username);
			boolean interested = User.checkCompletion(user.getId(), challengeId);
			
			response.setStatus((interested) ? HttpServletResponse.SC_NO_CONTENT : HttpServletResponse.SC_NOT_FOUND);
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	/**
	 * List all user challenges.
	 * 
	 * GET /users/:username/challenges
	 * 
	 * @param username
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void listChallenges (String username, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		Integer limit = Integer.parseInt(request.getParameter("limit"));
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		// for fast development, do not program in sort or limit in queries yet
		
		JsonArray challengesJSON = new JsonArray();
		
		try {
			// get user challenges
			List<Challenge> challenges = User.get(username).getChallenges();
			for (Challenge challenge : challenges) {
				JsonElement challengeJSON = Serializer.getChallengeJSON(challenge, false);
				challengesJSON.add(challengeJSON);
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		JsonElement payload = challengesJSON;
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		out.print(payload.toString());
		out.flush();
	}
	
	/**
	 * Create a challenge.
	 * 
	 * POST /usrs/:username/challenges
	 * 
	 * @param username
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void createChallenge (String username, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		// get request body
		BufferedReader br = request.getReader();
		String requestBodyString = br.lines().collect(Collectors.joining(System.lineSeparator()));
		JsonObject requestBody = new JsonParser().parse(requestBodyString).getAsJsonObject();
		
		// extract values from request body
		String title = requestBody.get("title").getAsString();
		String description = requestBody.get("description").getAsString();
		List<String> categories = new ArrayList<>();
		JsonArray categoriesJSONArray = requestBody.get("categories").getAsJsonArray();
		for (JsonElement c : categoriesJSONArray) {
			categories.add(c.getAsString());
		}
		
		JsonElement createdChallengeJSON = new JsonObject();
		try {
			User user = User.get(username);
			Challenge challenge = new Challenge(user, title, description, categories);
			createdChallengeJSON = Serializer.getChallengeJSON(challenge, false);
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		JsonElement payload = createdChallengeJSON;
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_CREATED);
		// TODO: get location header uri
		response.setHeader("Location", "");
		out.print(payload.toString());
		out.flush();
	}

}