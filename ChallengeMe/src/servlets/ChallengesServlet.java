package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
import db.User;
import util.Serializer;

@WebServlet("/challenges/*")
public class ChallengesServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
		
	/**
	 * A inner helper class to extract parameters from a requested URL paths.
	 * 
	 * @author Eddo W. Hintoso
	 * @since November 11, 2017
	 */
	private class PathExtractor
	{
		protected String username, toBeQueried;
		protected Long challengeId;
		
		public PathExtractor (String path)
		{
			if (path == null || path.equals("/")) return;
			
			String[] splits = path.split("/");
			
			if (splits.length >= 2) {
				username = splits[1];
			}
			if (splits.length >= 3) {
				challengeId = Long.parseLong(splits[2]);
			}
			if (splits.length >= 4) {
				toBeQueried = splits[3];
			}
		}
	}
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		// url mapping
		String pathInfo = request.getPathInfo();
		PathExtractor extractor = new PathExtractor(pathInfo);
		
		// GET /challenges
		if (extractor.username == null || extractor.challengeId == null) {
			getAllChallenges(request, response);
			return;
		}
		
		// GET /challenges/:username/:challenge
		if (extractor.toBeQueried == null) {
			getChallenge(extractor.username, extractor.challengeId, request, response);
		}
		// GET /challenges/:username/:challenge/categories
		else if (extractor.toBeQueried.equals("categories")) {
			listCategories(extractor.username, extractor.challengeId, request, response);
		}
		// GET /challenges/:username/:challenge/interested
		else if (extractor.toBeQueried.equals("interested")) {
			getInterestedUsers(extractor.username, extractor.challengeId, request, response);
		}
		// GET /challenges/:username/:challenge/completed
		else if (extractor.toBeQueried.equals("completed")) {
			getCompletedUsers(extractor.username, extractor.challengeId, request, response);
		}
		else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	protected void doPut (HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		// url mapping
		String pathInfo = request.getPathInfo();
		PathExtractor extractor = new PathExtractor(pathInfo);
		
		if (extractor.username == null || extractor.challengeId == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		// PUT /challenges/:username/:challenge/categories
		if (extractor.toBeQueried.equals("categories")) {
			replaceCategories(extractor.username, extractor.challengeId, request, response);
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
		
		if (extractor.username == null || extractor.challengeId == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		// PATCH /challenges/:username/:challenge
		if (extractor.toBeQueried == null) {
			editChallenge(extractor.username, extractor.challengeId, request, response);
		}
		// TODO: other url mappings
		else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	/**
	 * GET /challenges
	 * 
	 * Get all challenges.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getAllChallenges (HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		Integer limit = null;
		if (request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
		}
		
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");
		// for fast development, do not program in sort or limit in queries yet
		
		JsonArray challengesJSON = new JsonArray();
		
		try {
			// get user challenges
			List<Challenge> challenges = Challenge.getAll();
			for (Challenge challenge : challenges) {
				System.out.println(challenge.getCreateAtDate());
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
	 * GET /challenges/:username/:challenge
	 * 
	 * Get a single challenge.
	 * 
	 * @param username
	 * @param challengeId
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getChallenge (String username, Long challengeId, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		JsonElement payload = null;
		try {
			payload = Serializer.getChallengeJSON(Challenge.get(challengeId), true);
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
	 * POST /challenges/:username/:challenge
	 * 
	 * Edit a challenge's title and description.
	 * 
	 * NOTE: To edit a challenge's categories, use the categories endpoint.
	 * 
	 * @param username
	 * @param challengeId
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void editChallenge (String username, Long challengeId, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		// get request body
		BufferedReader br = request.getReader();
		String requestBodyString = br.lines().collect(Collectors.joining(System.lineSeparator()));
		JsonObject requestBody = new JsonParser().parse(requestBodyString).getAsJsonObject();
		
		// extract values from request body
		String title = requestBody.get("title").getAsString();
		String description = requestBody.get("description").getAsString();

		Challenge challenge = null;
		JsonElement payload = null;
		try {
			challenge = Challenge.get(challengeId);
			challenge.setTitle(title);
			challenge.setDescription(description);
			challenge.save();
			payload = Serializer.getChallengeJSON(challenge, true);
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
	 * GET /challenges/:username/:challenge/categories
	 * 
	 * List all categories for a challenge.
	 * 
	 * @param username
	 * @param challengeId
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void listCategories (String username, Long challengeId, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		JsonArray categoriesJSON = new JsonArray();
		
		try {
			// get user challenges
			Challenge challenge = Challenge.get(challengeId);
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
	
	/**
	 * GET /challenges/:username/:challenge/interested
	 * 
	 * List all interested users of a challenge.
	 * 
	 * @param username
	 * @param challengeId
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getInterestedUsers (String username, Long challengeId, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		JsonArray interestedUsers = new JsonArray();
		
		try {
			// get user challenges
			Challenge challenge = Challenge.get(challengeId);
			for (User user : challenge.getInterestedUsers()) {
				interestedUsers.add(Serializer.getUserJSON(user));
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		JsonElement payload = interestedUsers;
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		out.print(payload.toString());
		out.flush();
	}
	
	/**
	 * GET /challenges/:username/:challenge/completed
	 * 
	 * List all users who have completed a challenge.
	 * 
	 * @param username
	 * @param challengeId
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void getCompletedUsers (String username, Long challengeId, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		JsonArray completedUsers = new JsonArray();
		
		try {
			// get user challenges
			Challenge challenge = Challenge.get(challengeId);
			for (User user : challenge.getCompletedUsers()) {
				completedUsers.add(Serializer.getUserJSON(user));
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		JsonElement payload = completedUsers;
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		out.print(payload.toString());
		out.flush();
	}
	
	/**
	 * PUT /challenges/:username/:challenge/categories
	 * 
	 * Replace all categories for a challenge.
	 * 
	 * @param username
	 * @param challengeId
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void replaceCategories (String username, Long challengeId, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		// get request body
		BufferedReader br = request.getReader();
		String requestBodyString = br.lines().collect(Collectors.joining(System.lineSeparator()));
		JsonObject requestBody = new JsonParser().parse(requestBodyString).getAsJsonObject();
		
		// extract values from request body
		List<String> categories = new ArrayList<>();
		JsonArray categoriesJSONArray = requestBody.get("names").getAsJsonArray();
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
