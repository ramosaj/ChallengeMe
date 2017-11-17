package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import db.User;

public class SearchUserThread implements Callable<List<User>> {
	private List<User> allUser;
	private String searchItem;
	public SearchUserThread(List<User> allUser, String searchItem) {
		this.allUser = allUser;
		this.searchItem = searchItem;
	}

	@Override
	public List<User> call() throws Exception {
		List<User> retval = new ArrayList<>();
		for(User user : this.allUser) {
			if (user.getName().contains(this.searchItem)) {
				retval.add(user);
			} else if (user.getUsername().contains(this.searchItem)) {
				retval.add(user);
			} else if (user.getBio().contains(this.searchItem)) {
				retval.add(user);
			}
		}
		return retval;
	}
	
	
}
