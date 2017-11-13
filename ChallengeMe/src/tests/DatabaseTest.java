package tests;
import org.junit.jupiter.api.Test;

import db.Database;

class DatabaseTest {

	@Test
	void test() {
//		fail("Not yet implemented");
		Database db = new Database();
		db.getConnection(true);
		
	}

}
