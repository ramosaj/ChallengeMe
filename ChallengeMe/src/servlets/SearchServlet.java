package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.Challenge;
import db.User;
import threads.SearchChallengeThread;
import threads.SearchUserThread;

/**
 * Servlet implementation class tempServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FutureTask[] tasks = new FutureTask[2];
	private List<User> displayUser;
	private List<Challenge> displayChallenge;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchItem = request.getParameter("searchItem");
		try {
			List<Challenge> allChallenge = Challenge.getAll();
			List<User> allUser = User.getAll();
			
			Callable challengeCall = new SearchChallengeThread(allChallenge, searchItem);
			Callable userCall = new SearchUserThread(allUser, searchItem);
			
			this.tasks[0] = new FutureTask(challengeCall);
			this.tasks[1] = new FutureTask(userCall);
			for(FutureTask task : this.tasks) {
				Thread t = new Thread(task);
				t.start();
			}
			this.displayUser = (List<User>)this.tasks[0].get();
			this.displayChallenge = (List<Challenge>)this.tasks[1].get();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String sendUsers = gson.toJson(this.displayUser);
		String sendChallenge = gson.toJson(this.displayChallenge);
		request.setAttribute("userResult", sendUsers);
		request.setAttribute("challengeResult", sendChallenges);
		response.sendRedirect("result.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
