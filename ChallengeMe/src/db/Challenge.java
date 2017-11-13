package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Checklist
 * ---------
 * [X] `save` for updating
 * [X] include categories
 * [ ] implement `replaceCategories` static method
 * [ ] include created time
 * [ ] include updated time
 * 
 * @author Eddo W. Hintoso
 * @since November 11, 2017
 */
public class Challenge
{	
	public static final String TBL_NAME = "challenges";
	
	// Lazy loading is so that for more expensive queries such as
	// finding the users who completed a challenge or are interested
	// in a challenge, such queries for a Challenge record doesn't hit
	// the database until explicitly requested
	protected static final Boolean LAZY_LOAD = true;
	
	private static Connection connection = Database.getConnection(true);
	
	private Long id;
	private String title;
	private String description;
	private List<String> categories;
	
	private List<User> completedUsers;
	private List<User> interestedUsers;

	private User user;
	
	// for creating 
	public Challenge (User user, String title, String description, List<String> categories)
	{
		this.user = user;
		this.title = title;
		this.description = description;
		this.categories = categories;
	}
	
	// for getting an existing challenge, lazy-loaded
	public Challenge (Long id, User user, String title, String description, List<String> categories)
	{
		this.id = id;
		this.user = user;
		this.title = title;
		this.description = description;
		this.categories = categories;
	}
	
	// for getting an existing challenge without lazy-loading
	public Challenge (Long id, User user, String title, String description, List<String> categories, List<User> completedUsers, List<User> interestedUsers)  
	{
		this.id = id;
		this.user = user;
		this.title = title;
		this.description = description;
		this.categories = categories;
		
		this.completedUsers = completedUsers;
		this.interestedUsers = interestedUsers;
	}
	
	public Long getId ()
	{
		return id;
	}

	public String getTitle ()
	{
		return title;
	}

	public void setTitle (String title)
	{
		this.title = title;
	}

	public String getDescription ()
	{
		return description;
	}

	public void setDescription (String description)
	{
		this.description = description;
	}
	
	public List<String> getCategories ()
	{
		return categories;
	}
	
	public void setCategories (List<String> categories)
	{
		this.categories = categories;
	}

	public User getUser ()
	{
		return user;
	}

	public List<User> getCompletedUsers ()
	throws SQLException
	{
		if (this.completedUsers == null) {
			this.completedUsers = Challenge.getCompletedUsers(this.id);
		}
		return this.completedUsers;
	}
	
	public List<User> getInterestedUsers ()
	throws SQLException
	{
		if (this.interestedUsers == null) {
			this.interestedUsers = Challenge.getInterestedUsers(this.id);
		}
		return this.interestedUsers;
	}
	
	/**
	 * Create or update a challenge.
	 * 
	 * Pattern to create
	 * -----------------
	 * Challenge challenge = new Challenge(user, "title", "description");
	 * challenge.save();
	 * 
	 * NOTE: the `id` parameter above is set to null so it could be auto-generated
	 * It is strongly recommended to not manually create an `id` for the challenge.
	 * 
	 * Pattern to update
	 * -----------------
	 * Challenge challenge = Challenge.get(123456789);
	 * challenge.setTitle("new title");
	 * challenge.setDescription("it's a mystery");
	 * challenge.save();
	 * 
	 * @throws SQLException
	 */
	public void save ()
	throws SQLException
	{
		if (id == null) {
			// adding a challenge
			long challengeId = Challenge.add(user, title, description);
			// update the id
			id = challengeId;
		}
		else {
			Challenge.update(id, title, description);
		}
		
		// challenge could still be updated if categories failed to be replaced
		// violates atomicity, but whatever
		Challenge.replaceCategories(id, categories);
		
		assert(id != null);
	}
	
	/**
	 * Get a challenge by their unique id.
	 * 
	 * @param id
	 * @return a `Challenge` object that may or may not be lazy loaded.
	 * @throws SQLException
	 */
	public static Challenge get (Long id)
	throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM ? WHERE id=?");
		ps.setString(1, TBL_NAME);
		ps.setLong(2, id);
		
		ResultSet rs = ps.executeQuery();
		return Challenge.getFromResultSet(rs, LAZY_LOAD);
	}
	
	/**
	 * Adds a new challenge into the database.
	 * 
	 * @param username
	 * @param title
	 * @param description
	 * @return the id for the created challenge record
	 * @throws SQLException
	 */
	public static long add (User user, String title, String description)
	throws SQLException
	{
		Long userId = user.getId();
		
		PreparedStatement ps = connection.prepareStatement("INSERT INTO ? VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, TBL_NAME);
		ps.setLong(2, userId);
		ps.setString(3, title);
		ps.setString(4, description);
				
		int challengeCreated = ps.executeUpdate();
		assert (challengeCreated == 0 || challengeCreated == 1);
		
		if (challengeCreated == 0) {
			throw new SQLException("Failed to create challenge.");
		}
		
		Long challengeId = ps.getGeneratedKeys().getLong(1);
		return challengeId;
	}
	
	/**
	 * Update a challenge record in the database.
	 * 
	 * The columns/attributes allowed to be modified are:
	 * - `title`
	 * - `description`
	 * 
	 * @param challengeId
	 * @param title
	 * @param description
	 */
	public static void update (Long challengeId, String title, String description)
	throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement("UPDATE ? SET title=?, description=? WHERE id=?");
		ps.setString(1, TBL_NAME);
		ps.setString(2, title);
		ps.setString(3, description);
		ps.setLong(4, challengeId);
		
		int challengesUpdated = ps.executeUpdate();
		assert (challengesUpdated == 0 || challengesUpdated == 1);
	}
	
	/**
	 * Replace a challenge's categories in the database.
	 * 
	 * @param challengeId
	 * @param categories
	 */
	public static void replaceCategories (Long challengeId, List<String> categories)
	throws SQLException
	{
		// TODO
	}

	/**
	 * Get a Challenge object from a ResultSet.
	 * 
	 * @param rs
	 * @param lazyLoad
	 * @return
	 * @throws SQLException
	 */
	protected static Challenge getFromResultSet (ResultSet rs, boolean lazyLoad)
	throws SQLException
	{
		Long id = rs.getLong("id");
		Long userId = rs.getLong("userId");
		String title = rs.getString("title");
		String description = rs.getString("description");
		
		User user = User.get(userId);
		// TODO: get categories
		List<String> categories = new ArrayList<>();
		
		Challenge challenge = null;
		
		if (lazyLoad) {
			challenge = new Challenge(id, user, title, description, categories);
		}
		else {
			List<User> completedUsers = getCompletedUsers(id);
			List<User> interestedUsers = getInterestedUsers(id);
			challenge = new Challenge(id, user, title, description, categories, completedUsers, interestedUsers);
		}
		
		return challenge;
	}
	
	private static List<User> getCompletedUsers (Long challengeId)
	throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM ? WHERE challengeId=?");
		ps.setString(1, CompletedChallenge.TBL_NAME);
		ps.setLong(2, challengeId);
		
		ResultSet rs = ps.executeQuery();
		List<User> completedUsers = new ArrayList<>(); 
		while (rs.next()) {
			Long userId = rs.getLong("userId");
			completedUsers.add(User.get(userId));
		}
		return completedUsers;
	}
	
	private static List<User> getInterestedUsers (Long challengeId)
	throws SQLException
	{
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM ? WHERE challengeId=?");
		ps.setString(1, InterestedChallenge.TBL_NAME);
		ps.setLong(2, challengeId);
		
		ResultSet rs = ps.executeQuery();
		List<User> completedUsers = new ArrayList<>(); 
		while (rs.next()) {
			Long userId = rs.getLong("userId");
			completedUsers.add(User.get(userId));
		}
		return completedUsers;
	}
}
