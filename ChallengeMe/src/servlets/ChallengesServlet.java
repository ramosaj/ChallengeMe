package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import db.Challenge;
import db.Database;
import db.User;
import util.Serializer;

@WebServlet("/challenges/*")
public class ChallengesServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final Connection dbConnection = Database.getConnection(true);
		
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
				challengeId = splits[1];
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
		
		if (extractor.challengeId == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		// GET /challenges/:username/:challenge
		if (extractor.toBeQueried == null) {
			getChallenge(extractor.username, extractor.challengeId, request, response);
		}
		// TODO: other url mappings
		else {
			if(extractor.toBeQueried.equals("interested"))
			{
				listInterestedChallenges(extractor.challengeId, request, response);
			}
			if(extractor.toBeQueried.equals("completed"))
			{
				listCompletedChallenges(extractor.challengeId, request, response);
			}
		}
	}
	
	protected void listInterestedChallenges (long challenge, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		JsonArray usersJSON = new JsonArray();
		
		try {
			// get user challenges
			List<User> interestedUsers = Challenge.get(challenge).getInterestedUsers();
			for (User user : interestedUsers) {
				JsonElement userJSON = Serializer.getUserJSON(user);
				usersJSON.add(userJSON);
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		JsonElement payload = usersJSON;
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		out.print(payload.toString());
		out.flush();
	}
	protected void listCompletedChallenges (long challenge, HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		JsonArray usersJSON = new JsonArray();
		
		try {
			// get user challenges
			List<User> interestedUsers = Challenge.get(challenge).getCompletedUsers();
			for (User user : interestedUsers) {
				JsonElement userJSON = Serializer.getUserJSON(user);
				usersJSON.add(userJSON);
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		JsonElement payload = usersJSON;
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
}
