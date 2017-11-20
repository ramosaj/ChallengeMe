package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;

import db.User;

public class SearchUserThread implements Callable<Vector<User>> {
	private List<User> allUser;
	private String searchItem;
	public SearchUserThread(List<User> allUser, String searchItem) {
		this.allUser = allUser;
		this.searchItem = searchItem;
	}

	@Override
	public Vector<User> call() throws Exception {
		Vector<User> retval = new Vector<>();
		for(User user : this.allUser) {
			if (user.getName().contains(this.searchItem)) {
				retval.add(user);
				continue;
			}
			if (user.getUsername().contains(this.searchItem)) {
				retval.add(user);
				continue;
			} 
			if (user.getBio() != null) {
				if (user.getBio().contains(this.searchItem)) {
					retval.add(user);
					continue;
				}
			}
		}
		return retval;
	}
	
	
}
