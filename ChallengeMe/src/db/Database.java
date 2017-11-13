package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database
{
	private static Connection connection = null;
	
	private static final String hostname = "challengeme201.database.windows.net";
	private static final Integer port = 1433;
	private static final String dbName = "ChallengeMe";
	private static final String user = "rootCSCI201L";
	private static final String password = "cgme201!";
	private static final Boolean useSSL = false;
	
	public static Connection getConnection (boolean useNewConnection)
	{
		String connectionUri = "";
		Connection cnx = null;
		
		try {
			connectionUri = String.format("jdbc:sqlserver://%s:%s;database=%s;user=%s;password=%s;useSSL=%s", hostname, port, dbName, user, password, useSSL);
			if (Database.connection == null) {
				connection = DriverManager.getConnection(connectionUri);
			}

			if (useNewConnection) {
				cnx = DriverManager.getConnection(connectionUri);
			}
			else {
				cnx = connection;
			}
		}
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return cnx;
	}
	
}