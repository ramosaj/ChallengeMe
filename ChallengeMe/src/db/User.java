package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Checklist
 * ---------
 * [X] `add` static method for creating a User
 * [X] `update` static method for updating a User
 * [X] `save` method for creating/updating an existing User
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
	private String password;
	private String name;
	private String bio;
	private String avatarURL;
	
	
	private List<Challenge> challenges;
	private List<Challenge> completedChallenges;
	private List<Challenge> interestedChallenges;
	private Date createAt;
	
	public static class UserNotFoundException extends Exception {
		private static final long serialVersionUID = -7465057662586463032L;
	}

	public User (Long id, String username, String password, String name, String bio, String avatarURL)
	{
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.bio = bio;
		this.avatarURL = avatarURL;
	}
	
	public User (Long id, String username, String password, String name, String bio, String avatarURL, List<Challenge> challenges, List<Challenge> completedChallenges,  List<Challenge> interestedChallenges)  
	{
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.bio = bio;
		this.avatarURL = avatarURL;
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
	
	public String getPassword ()
	{
		return password;
	}

	public void setPassword (String password)
	{
		this.password = password;
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
	
	public void setAvatarURL(String avatarURL) 
	{
		this.avatarURL = avatarURL;
	}
	
	public String getAvatarURL() 
	{
		return avatarURL;
	}
	
	public Date getCreateAtDate() 
	{
		return createAt;
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
		if (id == null) {
			// adding a challenge
			long userId = User.add(username, password, name, bio, avatarURL);
			// update the id
			id = userId;
		}
		else {
			User.update(id, username, name, bio, avatarURL);
		}
		createAt = new Date();
		assert(id != null);
	}
	
	/**
	 * Get a user by his or her id.
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static User get (Long id)
	throws SQLException, UserNotFoundException
	{
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM ? WHERE id=?");
		ps.setString(1, TBL_NAME);
		ps.setLong(2, id);
		
		ResultSet rs = ps.executeQuery();

		if (!rs.next()) {
			// user not found, throw an error
			throw new UserNotFoundException();
		}
		else {
			return User.getFromResultSet(rs, LAZY_LOAD);
		}
	}
	
	/**
	 * Get a user by his or her username.
	 * 
	 * @param username
	 * @return
	 * @throws SQLException
	 */
	public static User get (String username)
	throws SQLException, UserNotFoundException
	{
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM ? WHERE username=?");
		ps.setString(1, TBL_NAME);
		ps.setString(2, username);
		
		ResultSet rs = ps.executeQuery();
		if (!rs.next()) {
			// user not found, throw an error
			throw new UserNotFoundException();
		}
		else {
			return User.getFromResultSet(rs, LAZY_LOAD);
		}
	}
	
	/**
	 * Add a new user into the database.
	 * 
	 * NOTE: Does not check for whether the user exists or not.
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @param bio
	 * @return
	 * @throws SQLException
	 */
	public static long add (String username, String password, String name, String bio, String avatarURL)
	throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement("INSERT INTO ? (username, password, name, bio, avatarURL) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, TBL_NAME);
		ps.setString(2, username);
		ps.setString(3, password);
		ps.setString(4, name);
		ps.setString(5, bio);
		ps.setString(6, avatarURL);
				
		int userCreated = ps.executeUpdate();
		assert (userCreated == 0 || userCreated == 1);
		
		if (userCreated == 0) {
			throw new SQLException("Failed to create challenge.");
		}
		
		Long userId = ps.getGeneratedKeys().getLong(1);
		return userId;
	}
	
	public static void update (Long userId, String username, String name, String bio, String avatarURL)
	throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement("UPDATE ? SET username=?, name=?, bio=?, avatarURL=?, WHERE userId=?");
		ps.setString(1, TBL_NAME);
		ps.setString(2, username);
		ps.setString(3, name);
		ps.setString(4, bio);
		ps.setString(5, avatarURL);
		ps.setLong(6, userId);
		int usersUpdated = ps.executeUpdate();
		assert (usersUpdated == 0 || usersUpdated == 1);
	}
	
	protected static User getFromResultSet (ResultSet rs, boolean lazyLoad)
	throws SQLException
	{
		Long id = rs.getLong("id");
		String username = rs.getString("username");
		String password = rs.getString("password");
		String name = rs.getString("name");
		String bio = rs.getString("bio");
		String avatarURL = rs.getString("avatarURL");
		User user = null;
		
		if (lazyLoad) {
			user = new User(id, username, password, name, bio, avatarURL);
		}
		else {
	  		List<Challenge> challenges = getChallenges(id);
	 		List<Challenge> completedChallenges = getCompletedChallenges(id);
	 		List<Challenge> interestedChallenges = getInterestedChallenges(id);
	 		user = new User(id, username, password, name, bio, avatarURL, challenges, completedChallenges, interestedChallenges);
		}
		
		return user;
	}
	
	/**
	 * Change the password of a user.
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	public static void changePassword (Long userId, String password)
	throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement("UPDATE ? SET password=? WHERE id=?");
		ps.setString(1, TBL_NAME);
		ps.setString(4, password);
		
		int usersUpdated = ps.executeUpdate();
		assert (usersUpdated == 0 || usersUpdated == 1);
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
	
	public static boolean presentInterest (Long userId, Long challengeId)
	throws SQLException
	{
		// don't do anything if user is already interested in the challenge
		if (checkInterest(userId, challengeId)) {
			return false;
		}
		
		PreparedStatement ps = connection.prepareStatement("INSERT INTO ? (userId, challengeId) VALUES (?, ?)");
		ps.setString(1, InterestedChallenge.TBL_NAME);
		ps.setLong(2, userId);
		ps.setLong(3, challengeId);
		
		int interestedPresented = ps.executeUpdate();
		assert (interestedPresented == 0 || interestedPresented == 1);
		return (interestedPresented == 1);
	}
	
	public static boolean deleteInterest (Long userId, Long challengeId)
	throws SQLException
	{
		// don't do anything if user is already interested in the challenge
		if (!checkInterest(userId, challengeId)) {
			return false;
		}
		
		PreparedStatement ps = connection.prepareStatement("DELETE FROM ? WHERE userId=? AND challengeId=?");
		ps.setString(1, InterestedChallenge.TBL_NAME);
		ps.setLong(2, userId);
		ps.setLong(3, challengeId);
		
		int interestDeleted = ps.executeUpdate();
		assert (interestDeleted == 0 || interestDeleted == 1);
		return (interestDeleted == 1);
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
	
	public static boolean markAsComplete (Long userId, Long challengeId)
	throws SQLException
	{
		// don't do anything if user is already interested in the challenge
		if (checkCompletion(userId, challengeId)) {
			return false;
		}
		
		PreparedStatement ps = connection.prepareStatement("INSERT INTO ? (userId, challengeId) VALUES (?, ?)");
		ps.setString(1, CompletedChallenge.TBL_NAME);
		ps.setLong(2, userId);
		ps.setLong(3, challengeId);
		
		int markedComplete = ps.executeUpdate();
		assert (markedComplete == 0 || markedComplete == 1);
		return (markedComplete == 1);
	}
	
	public static boolean markAsIncomplete (Long userId, Long challengeId)
	throws SQLException
	{
		// don't do anything if user is already interested in the challenge
		if (!checkCompletion(userId, challengeId)) {
			return false;
		}
		
		PreparedStatement ps = connection.prepareStatement("DELETE FROM ? WHERE userId=? AND challengeId=?");
		ps.setString(1, CompletedChallenge.TBL_NAME);
		ps.setLong(2, userId);
		ps.setLong(3, challengeId);
		
		int markedIncomplete = ps.executeUpdate();
		assert (markedIncomplete == 0 || markedIncomplete == 1);
		return (markedIncomplete == 1);
	}
	
}