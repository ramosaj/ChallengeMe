package tests;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import db.*;
import junit.framework.TestCase;

class DatabaseTest extends TestCase {

	@Test
	void testDBConnection() throws SQLException {
		Database db = new Database();
		Connection conn = db.getConnection(true);
		assertEquals("dbo", conn.getSchema());
	}

	@Test
	void testAddUser() {
//		User user = new User()
//		Challenge challenge = new Challenge();
	}
}
