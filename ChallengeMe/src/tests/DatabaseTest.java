package tests;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.Database;
import db.User;
import db.User.UserNotFoundException;

class DatabaseTest {
	private User user;
	private static Database db;
	private static Connection conn;
	private long userId;
	
	
	
    @BeforeAll
    static void initAll() {
		db = new Database();
		conn = Database.getConnection(true);
    }

    @BeforeEach
    void init() {
    }

    @Test
    void connectionTest() {
		try {
			assertEquals("dbo", conn.getSchema());
		} catch (SQLException e) {
			fail("cannot connect with server");
			e.printStackTrace();
		}
    }

    @Test
    void userTest() {
    		try {
		    	long userId = User.add("junit", "password", "UnitTest", "I'm unit test", "testUrl");
		    	assertNotNull(userId);
		    	this.userId = userId;
		    	this.user = User.get("junit");
    		} catch (SQLException e) {
			e.printStackTrace();
			fail("Error in addUser: " + e.getMessage());
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			fail("Error in getUser: " + e.getMessage());
		}
    		
    		
    		try {
    			User.update(this.userId, "updatedUserName", "updatedName", "updatedBio", "updatedUrl");
    			assertNotNull(User.get(this.userId));
    		} catch (SQLException e) {
    			e.printStackTrace();
    			fail("Error in updateUser: " + e.getMessage());
    		} catch (UserNotFoundException e) {
    			e.printStackTrace();
    			fail("Error in getUser: " + e.getMessage());
    		}
    		
    		try {
    			User user = User.get(this.userId);
    			assertEquals(user.getName(), "updatedName");
    			assertEquals(user.getUsername(), "updatedUserName");
    			assertEquals(user.getBio(), "updatedBio");
    			assertEquals(user.getAvatarURL(), "updatedUrl");
    		} catch (UserNotFoundException e) {
			e.printStackTrace();
			fail("Error in getUser by id NOTFOUND: " + e.getMessage());
    		} catch (SQLException e) {
    			e.printStackTrace();
    			fail("Error in getUser by id: " + e.getMessage());
    		}
    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
    }
    

}