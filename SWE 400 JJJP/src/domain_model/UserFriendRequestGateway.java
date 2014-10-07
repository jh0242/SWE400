package domain_model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  This class is a gateway that isolates all of the 
 *  SQL commands to access the FriendRequest database 
 *  
 *  John Terry
 *	SWE 400, LSA Group JJJP
 *	October 3, 2014
 */

public class UserFriendRequestGateway {
	
	int myUserID;
	int otherUserID;
	String userName;
	
	String returnData = new String("SELECT * FROM Friend_Request WHERE UserA='" + myUserID + "';");
	String insertData = new String("INSERT INTO Friend_Request WHERE UserB='" + otherUserID + "';");
	
	private Connection conn = null;
	
	/**
	 * Pulls all friend requests from a particular userID
	 * @throws SQLException 
	 */
	public boolean findUserFriendRequests(int user) throws SQLException{
		String findSQL = new String("SELECT * FROM Friend_Request WHERE UserA='" + user + "';");
		PreparedStatement stmt = conn.prepareStatement(findSQL);
		ResultSet rs = stmt.executeQuery(findSQL);

		if (!rs.next())
			return false; //the userID is not in the table
		
		//use scanner to keep on getting the next one
		
		stmt = conn.prepareStatement(returnData);
		
		stmt.executeUpdate();
		return true;
		
	}
	
	/**
	 * Inserts a friend request to the table when the user 
	 * either sends or receives a friend request 
	 */
	public void insertFriendRequest(int userA, String userB){
		String insertSQL = new String("SELECT * FROM Friend_Request WHERE UserA='" + userB + "';");
	}
	
	/**
	 * Removes friend request from the table when
	 * either the friendship is accepted or rejected
	 */
	public void removeFriendRequest(int user){
		
	}
	
}