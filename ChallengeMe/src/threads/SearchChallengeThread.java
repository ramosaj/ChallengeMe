package threads;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;

import db.Challenge;

public class SearchChallengeThread implements Callable<Vector<Challenge>> {
	private List<Challenge> allChallenge;
	private String searchItem;
	public SearchChallengeThread(List<Challenge> allChallenge, String searchItem) {
		this.allChallenge = allChallenge;
		this.searchItem = searchItem;
	}

	@Override
	public Vector<Challenge> call() throws Exception {
		Vector<Challenge> retval = new Vector<>();
		outterLoop:
		for (Challenge challenge : this.allChallenge) {
			System.out.println(challenge.getDescription());
			if (challenge.getCategories() != null) {
				for (String cat : challenge.getCategories()) {
					if (cat.toLowerCase().contains(this.searchItem)) {
						retval.add(challenge);
						continue outterLoop;
					}
				}
				
			}
			if (challenge.getTitle().contains(this.searchItem)) {
				retval.add(challenge);
				continue;
			}
			if (challenge.getDescription().contains(this.searchItem)) {
				retval.add(challenge);
				continue;
			}
		}
		return retval;
	}
	
	
}
