/* This class implements the JDBC features of Java. It is used to establish a connection between a mySQL database and to manipulate
 * that database in certain ways. It is primarily implemented inside the TwitterManager class but also implemented in and called upon by 
 * certain JSP pages which make up the web-interface  */
package SentimentApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class JDBC {
	public static void main(String[] args) {
		
	}
	
	// creates a connection between the database
	public static Connection databaseConnect () {
		Connection conn = null;
		
		
		try {
			// details of the database to be connected to
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionUrl = "jdbc:mysql://127.7.1.2:3306/twitterontap";
			String connectionUser = "admin22N7Ms4";
			String connectionPassword = "qhwUED8YW7il";
			conn = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return conn;
			
		}
	
	// sends data to database according to the (String) SQL statement provided as a parameter
	public static void sendToDatabase (String query, Connection conn) throws SQLException {
		Statement stmt = null;
		int update = 0;
		
		stmt = conn.createStatement();
		update = stmt.executeUpdate(query);
		
	}
	
	// this method clears the tables which store positive and negative tweets before each instance of a search by a user
	public static void clearTables (Connection conn) throws SQLException {
		Statement stmt = null;
		int update = 0;
		
		stmt = conn.createStatement();
		update = stmt.executeUpdate("DELETE FROM positivetweets");
		update = stmt.executeUpdate("DELETE FROM negativetweets");
		
	}
    
	/* this method retrieves certain things from a database according to the parameters provided. So, when examples of positive and negative
	 *  tweets are being displayed on screen on the web-interface, this method will return tweet content, user names, user pictures (etc...) from
	 *  the necessary table */
	public static String getFromDatabase (String query, String column, int whichRow, Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		stmt = conn.createStatement();
		rs = stmt.executeQuery(query); // creates a ResultSet object which is essentially a virtual SQL table created by the supplied SQL statement
		boolean isTrue= true;
		
		
		for (int i =0; i < whichRow; i++) {
			 isTrue =rs.next();
		}
		
		/* when examples of positive and negative twets are displayed on screen in the web-interface, 7 positive and 7 negative tweets are selected from the
		 * database. This piece of code ensures that if there are less that 7 tweets in either table, an error will not occur at runtime */
		if (isTrue == false ) { 
			String exitRow = " ";
			return exitRow;
		}
        
		
		String output = rs.getString(column);
		// same a above... to avoid errors at runtime
		if (output == null) {
			String emptyRow = " ";
			return emptyRow;
		}
		return output;
	}
		
	}


