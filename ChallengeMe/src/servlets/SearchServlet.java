package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import db.Challenge;
import db.User;
import threads.SearchChallengeThread;
import threads.SearchUserThread;
import util.Serializer;

/**
 * Servlet implementation class tempServlet
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private List<User> searchedUsers;
	private List<Challenge> searchedChallenges;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String searchItem = request.getParameter("q");
		
		try {
			List<Challenge> allChallenge = Challenge.getAll();
			List<User> allUser = User.getAll();
			
			Callable<Vector<Challenge>> challengeCall = new SearchChallengeThread(allChallenge, searchItem);
			Callable<Vector<User>> userCall = new SearchUserThread(allUser, searchItem);
			
			FutureTask<Vector<Challenge>> searchChallengeTask = new FutureTask<>(challengeCall);
			Thread searchChallengeThread = new Thread(searchChallengeTask);
			searchChallengeThread.start();
			
			FutureTask<Vector<User>> searchUserTask = new FutureTask<>(userCall);
			Thread searchUserThread = new Thread(searchUserTask);
			searchUserThread.start();
			
			searchedUsers = searchUserTask.get();
			searchedChallenges = searchChallengeTask.get();
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		catch (ExecutionException ee) {
			ee.printStackTrace();
		}

		// serialize!
		JsonArray challengesJSON = new JsonArray();
		JsonArray usersJSON = new JsonArray();
		
		try {
			// serialize all challenges into the array
			for (Challenge challenge : searchedChallenges) {
				JsonElement challengeJSON = Serializer.getChallengeJSON(challenge, true);
				challengesJSON.add(challengeJSON);
			}
			
			// get users
			for (User user : searchedUsers) {
				JsonElement userJSON = Serializer.getUserJSON(user);
				usersJSON.add(userJSON);
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		
		JsonObject payload = new JsonObject();
		payload.add("users", usersJSON);
		payload.add("challenges", challengesJSON);
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		out.print(payload.toString());
		out.flush();
	}

}
