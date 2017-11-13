package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Checklist
 * ---------
 * [ ] `add` static method for creating a User
 * [ ] `update` static method for updating a User
 * [ ] `save` method for creating/updating an existing User
 * [ ] include created time
 * [ ] include updated time
 * 
 * @author hintoso
 * @since November 11, 2017
 */
public class User
{
	public static final String TBL_NAME = "users";
	protected static final Boolean LAZY_LOAD = true;
	
	private static Connection connection = Database.getConnection(true);
	
	private Long id;
	private String username;
	private String name;
	private String bio;
	
	private List<Challenge> challenges;
	private List<Challenge> completedChallenges;
	private List<Challenge> interestedChallenges;
	
	public User (Long id, String username, String name, String bio)
	{
		this.id = id;
		this.username = username;
		this.name = name;
		this.bio = bio;
	}
	
	public User (Long id, String username, String name, String bio, List<Challenge> challenges, List<Challenge> completedChallenges,  List<Challenge> interestedChallenges)  
	{
		this.id = id;
		this.username = username;
		this.name = name;
		this.bio = bio;
		this.challenges = challenges;
		this.completedChallenges = completedChallenges;
		this.interestedChallenges = interestedChallenges;
	}
	
	public Long getId ()
	{
		return id;
	}
	
	public String getUsername ()
	{
		return username;
	}

	public void setUsername (String username)
	{
		this.username = username;
	}

	public String getName ()
	{
		return name;
	}

	public void setName (String name)
	{
		this.name = name;
	}

	public String getBio ()
	{
		return bio;
	}

	public void setBio (String bio)
	{
		this.bio = bio;
	}

	public List<Challenge> getChallenges ()
	throws SQLException
	{
		if (this.challenges == null) {
			this.challenges = User.getChallenges(this.id);
		}
		return this.challenges;
	}
	
	public List<Challenge> getCompletedChallenges ()
	throws SQLException
	{
		if (this.completedChallenges == null) {
			this.completedChallenges = User.getCompletedChallenges(this.id);
		}
		return this.completedChallenges;
	}
	
	public List<Challenge> getInterestedChallenges ()
	throws SQLException
	{
		if (this.interestedChallenges == null) {
			this.interestedChallenges = User.getInterestedChallenges(this.id);
		}
		return this.interestedChallenges;
	}
	
	/**
	 * Update or create a new user.
	 * 
	 * @throws SQLException
	 */
	public void save ()
	throws SQLException
	{
		
	}
	
	/**
	 * Get a user by his or her id.
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static User get (Long id)
	throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM ? WHERE id=?");
		ps.setString(1, TBL_NAME);
		ps.setLong(2, id);
		
		ResultSet rs = ps.executeQuery();
		return User.getFromResultSet(rs, LAZY_LOAD);
	}
	
	/**
	 * Get a user by his or her username.
	 * 
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public static User get (String username)
	throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM ? WHERE username=?");
		ps.setString(1, TBL_NAME);
		ps.setString(2, username);
		
		ResultSet rs = ps.executeQuery();
		return User.getFromResultSet(rs, LAZY_LOAD);
	}
	
	
	protected static User getFromResultSet (ResultSet rs, boolean lazyLoad)
	throws SQLException
	{
		Long id = rs.getLong("id");
		String username = rs.getString("username");
		String name = rs.getString("name");
		String bio = rs.getString("bio");
		
		User user = null;
		
		if (lazyLoad) {
			user = new User(id, username, name, bio);
		}
		else {
	  		List<Challenge> challenges = getChallenges(id);
	 		List<Challenge> completedChallenges = getCompletedChallenges(id);
	 		List<Challenge> interestedChallenges = getInterestedChallenges(id);
	 		user = new User(id, username, name, bio, challenges, completedChallenges, interestedChallenges);
		}
		
		return user;
	}
	
	private static List<Challenge> getChallenges (Long userId)
	throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM ? WHERE userId=?");
		ps.setString(1, Challenge.TBL_NAME);
		ps.setLong(2, userId);
		
		ResultSet rs = ps.executeQuery();
		List<Challenge> challenges = new ArrayList<>(); 
		while (rs.next()) {
			challenges.add(Challenge.getFromResultSet(rs, Challenge.LAZY_LOAD));
		}
		return challenges;
	}
	
	private static List<Challenge> getCompletedChallenges (Long userId)
	throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM ? WHERE userId=?");
		ps.setString(1, CompletedChallenge.TBL_NAME);
		ps.setLong(2, userId);
		
		ResultSet rs = ps.executeQuery();
		List<Challenge> completedChallenges = new ArrayList<>(); 
		while (rs.next()) {
			Long challengeId = rs.getLong("challengeId");
			completedChallenges.add(Challenge.get(challengeId));
		}
		return completedChallenges;
	}
	
	private static List<Challenge> getInterestedChallenges (Long userId)
	throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM ? WHERE userId=?");
		ps.setString(1, InterestedChallenge.TBL_NAME);
		ps.setLong(2, userId);
		
		ResultSet rs = ps.executeQuery();
		List<Challenge> completedChallenges = new ArrayList<>(); 
		while (rs.next()) {
			Long challengeId = rs.getLong("challengeId");
			completedChallenges.add(Challenge.get(challengeId));
		}
		return completedChallenges;
	}
	
	public static boolean checkInterest (Long userId, Long challengeId)
	throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM ? WHERE userId=? AND challengeId=?");
		ps.setString(1, InterestedChallenge.TBL_NAME);
		ps.setLong(2, userId);
		ps.setLong(3, challengeId);
		
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
	
	public static boolean checkCompletion (Long userId, Long challengeId)
	throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM ? WHERE userId=? AND challengeId=?");
		ps.setString(1, CompletedChallenge.TBL_NAME);
		ps.setLong(2, userId);
		ps.setLong(3, challengeId);
		
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}
	
}