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

import com.google.gson.Gson;

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
		System.err.println("HI in sear: " +searchItem);
		try {
			List<Challenge> allChallenge = Challenge.getAll();
			List<User> allUser = User.getAll();
		    // FutureTask is a concrete class that
		    // implements both Runnable and Future
		    FutureTask[] tasks = new FutureTask[2];
		 
		      Callable callableUser = new SearchUserThread(allUser, searchItem);
		      Callable callableChallenge = new SearchChallengeThread(allChallenge, searchItem);
		      // Create the FutureTask with Callable
		      tasks[0] = new FutureTask(callableUser);
		      tasks[1] = new FutureTask(callableChallenge);
		 
		      // As it implements Runnable, create Thread
		      // with FutureTask
		      Thread t1 = new Thread(tasks[0]);
		      t1.start();
		      
		      Thread t2 = new Thread(tasks[1]);
		      t2.start();
		  
			this.displayUser = (Vector<User>)tasks[0].get();
			this.displayChallenge = (Vector<Challenge>)tasks[1].get();
			System.err.println("SIZEMAN: "+ this.displayUser.size());
			
		} catch (SQLException e) {
			System.err.println("ERROR 1");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.err.println("ERROR 2");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			System.err.println("ERROR 2");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		for(int i =0;i<this.displayUser.size();i++)
		{
			this.displayUser.get(i).setCreateAtDate(null);
		}
		System.out.println("in cahhaha wy no change");
		for(int i =0;i<this.displayChallenge.size();i++)
		{
			System.out.println("in here wy no change");
			this.displayChallenge.get(i).setCreateAtDate(null);
			try {
				for(int j =0;j<this.displayChallenge.get(i).getCompletedUsers().size();j++)
				{
					this.displayChallenge.get(i).getCompletedUsers().get(j).setCreateAtDate(null);
				}
				for(int j =0;j<this.displayChallenge.get(i).getInterestedUsers().size();j++)
				{
					this.displayChallenge.get(i).getInterestedUsers().get(j).setCreateAtDate(null);
				}
				this.displayChallenge.get(i).getUser().setCreateAtDate(null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(displayChallenge.get(0).getCreateAtDate()==null)
		{
		}
		String sendUsers = gson.toJson(this.displayUser);
		String sendChallenges = gson.toJson(this.displayChallenge);
		request.setAttribute("userResult", sendUsers);
		request.setAttribute("challengeResult", sendChallenges);
		System.err.println("lessgoo-bmark//t");
		request.getSession().setAttribute("userResult", sendUsers);
		request.getSession().setAttribute("challengeResult", sendChallenges);
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		out.print(sendUsers+"^"+sendChallenges);
		out.flush();/*
		System.err.println("re-bmark//t");
		String url = "/ChallengeMe/Results.jsp";
		System.out.println("URL: "+url);
		request.getRequestDispatcher(url).forward(request, response);
		System.err.println("bomaarkb//00");*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
