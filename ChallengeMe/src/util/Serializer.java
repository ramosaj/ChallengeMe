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
		JsonObject challengeJSON = new JsonObject();
		challengeJSON.addProperty("id", challenge.getId());
		challengeJSON.addProperty("name", challenge.getTitle());
		challengeJSON.addProperty("description", challenge.getDescription());
		challengeJSON.addProperty("url", ""); // TODO
		challengeJSON.addProperty("interestedCount", challenge.getInterestedUsers().size());
		challengeJSON.addProperty("interestedUrl", ""); // TODO
		challengeJSON.addProperty("completedCount", challenge.getCompletedUsers().size());
		challengeJSON.addProperty("completedUrl", ""); // TODO
		challengeJSON.addProperty("createdAt", ""); // TODO
		challengeJSON.addProperty("updatedAt", ""); // TODO

		JsonArray categoriesJSON = new JsonArray();
		// modified by Peter
		for (String category : challenge.getCategories()) {
//			categoriesJSON.add(category);
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
		JsonObject userJSON = new JsonObject();
		userJSON.addProperty("id", user.getId());
		userJSON.addProperty("username", user.getUsername());
		userJSON.addProperty("url", ""); // TODO
		userJSON.addProperty("name", user.getName());
		userJSON.addProperty("avatarUrl", ""); // TODO
		userJSON.addProperty("bio", user.getBio());
		userJSON.addProperty("challengesCount", user.getChallenges().size());
		userJSON.addProperty("challengesUrl", ""); // TODO
		userJSON.addProperty("interestedCount", user.getInterestedChallenges().size());
		userJSON.addProperty("interestedUrl", ""); // TODO
		userJSON.addProperty("completedCount", user.getCompletedChallenges().size());
		userJSON.addProperty("completedUrl", ""); // TODO
		userJSON.addProperty("createdAt", ""); // TODO
		userJSON.addProperty("updatedAt", ""); // TODO
		
		return userJSON;
	}
}
