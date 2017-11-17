package util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import db.Challenge;
import db.User;

public class Sort {
	
	static void sortUserByDate(List<User> users) 
	{
		Collections.sort(users, new Comparator<User>() {
			@Override
			public int compare(User u1, User u2) {
				return u1.getCreateAtDate().compareTo(u2.getCreateAtDate());
			}
		});
	}
	
	static void sortChallengeByDate(List<Challenge> challenges)
	{
		Collections.sort(challenges, new Comparator<Challenge>() {
			@Override
			public int compare(Challenge c1, Challenge c2) {
				return c1.getCreateAtDate().compareTo(c2.getCreateAtDate());
			}
		});
	}
}
