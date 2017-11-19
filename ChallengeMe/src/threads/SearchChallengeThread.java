package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import db.Challenge;

public class SearchChallengeThread implements Callable<List<Challenge>> {
	private List<Challenge> allChallenge;
	private String searchItem;
	public SearchChallengeThread(List<Challenge> allChallenge, String searchItem) {
		this.allChallenge = allChallenge;
		this.searchItem = searchItem;
	}

	@Override
	public List<Challenge> call() throws Exception {
		List<Challenge> retval = new ArrayList<>();
		for (Challenge challenge : this.allChallenge) {
			if (challenge.getCategories() != null) {
				System.out.println(challenge.getCategories());
				System.out.println(this.searchItem);
				for (String cat : challenge.getCategories()) {
					if (cat.toLowerCase().contains(this.searchItem)) {
						retval.add(challenge);
					}
				}
			} else if (challenge.getTitle().contains(this.searchItem)) {
				retval.add(challenge);
			} else if (challenge.getDescription().contains(this.searchItem)) {
				retval.add(challenge);
			}
		}
		return retval;
	}
	
	
}
