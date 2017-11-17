package util;

import java.sql.SQLException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import db.Challenge;
import db.User;

public class Serializer
{

	/**
	 * Serialize a `Challenge` object into a JsonElement for serving API.
	 * 
	 * @param challenge
	 * @return
	 * @throws SQLException
	 */
	public static JsonElement getChallengeJSON (Challenge challenge, boolean includeOwner)
	throws SQLException
	{
		Long createdAt = null;
		if (challenge.getCreateAtDate() != null) {
			createdAt = challenge.getCreateAtDate().getTime();
		}
		
		JsonObject challengeJSON = new JsonObject();
		challengeJSON.addProperty("id", challenge.getId());
		challengeJSON.addProperty("name", challenge.getTitle());
		challengeJSON.addProperty("description", challenge.getDescription());
		challengeJSON.addProperty("url", String.format("/ChallengeMe/challenges/%s/%s", challenge.getUser().getName(), challenge.getId()));
		challengeJSON.addProperty("interestedCount", challenge.getInterestedUsers().size());
		challengeJSON.addProperty("interestedUrl", String.format("/ChallengeMe/challenges/%s/%s/interested", challenge.getUser().getName(), challenge.getId()));
		challengeJSON.addProperty("completedCount", challenge.getCompletedUsers().size());
		challengeJSON.addProperty("completedUrl", String.format("/ChallengeMe/challenges/%s/%s/completed", challenge.getUser().getName(), challenge.getId()));
		challengeJSON.addProperty("createdAt", createdAt);

		JsonArray categoriesJSON = new JsonArray();
		for (String category : challenge.getCategories()) {
			categoriesJSON.add(new JsonPrimitive(category));
		}
		challengeJSON.add("categories", categoriesJSON);
		
		// include owner
		if (includeOwner) {
			challengeJSON.add("owner", getUserJSON(challenge.getUser()));
		}
		
		return challengeJSON;
	}
	
	/**
	 * Serialize a `User` object into a JsonElement for serving API.
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public static JsonElement getUserJSON (User user)
	throws SQLException
	{
		Long createdAt = null;
		if (user.getCreateAtDate() != null) {
			createdAt = user.getCreateAtDate().getTime();
		}
		
		JsonObject userJSON = new JsonObject();
		userJSON.addProperty("id", user.getId());
		userJSON.addProperty("username", user.getUsername());
		userJSON.addProperty("url", String.format("/ChallengeMe/users/%s", user.getUsername()));
		userJSON.addProperty("name", user.getName());
		userJSON.addProperty("avatarUrl", ""); // TODO
		userJSON.addProperty("bio", user.getBio());
		userJSON.addProperty("challengesCount", user.getChallenges().size());
		userJSON.addProperty("challengesUrl", String.format("/ChallengeMe/users/%s/challenges", user.getUsername()));
		userJSON.addProperty("interestedCount", user.getInterestedChallenges().size());
		userJSON.addProperty("interestedUrl", String.format("/ChallengeMe/users/%s/interested", user.getUsername()));
		userJSON.addProperty("completedCount", user.getCompletedChallenges().size());
		userJSON.addProperty("completedUrl", String.format("/ChallengeMe/users/%s/completed", user.getUsername()));
		userJSON.addProperty("createdAt", createdAt);
		
		return userJSON;
	}
}
