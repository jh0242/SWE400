package domain_model;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *  This class is a gateway that isolates all of the 
 *  SQL commands to access the FriendRequest database 
 *  
 *  John Terry
 *	SWE 400, LSA Group JJJP
 *	October 3, 2014
 */

public class UserFriendRequestGateway {
	
	int userID;
	String userName;
	
	private Connection conn = null;
	
	/**
	 * Pulls all friend requests from a particular userID
	 */
	public void findUserFriendRequests(int user){
		String findSQL = new String("SELECT * FROM Friend_Request WHERE UserA='" + user + "';");
		//PreparedStatement stmt = conn.prepareStatement(findSQL);
		
	}
	
	/**
	 * Inserts a friend request to the table when the user 
	 * either sends or receives a friend request 
	 */
	public void insertFriendRequest(int userA, String userB){
		
	}
	
	/**
	 * Removes friend request from the table when
	 * either the friendship is accepted or rejected
	 */
	public void removeFriendRequest(int user){
		
	}
	
}