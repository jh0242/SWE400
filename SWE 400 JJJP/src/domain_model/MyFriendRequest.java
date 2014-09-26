package domain_model;

import java.util.Vector;

/**
 * Handles friend requests for a user, decides if that friendship 
 * is accepted (stored in Friend database table and removed from the
 * FriendRequest database table) or if that friendship is denied  
 * (removed from the FriendRequest database table)  
 * 
 * Author: John Terry
 * Class: SWE 400
 * Date: 9-25-2014
 */

public class MyFriendRequest {
	
	private int userID;
	
	/**
	 * Allows the user to send a friend request to another user
	 * @param idUserA
	 * @param idUserB
	 */
	void sendFriendRequest(int otherUsersID){
			otherUsersID = userID;
	}
		
	/**
	 * Allows the user to confirm a friend request from another user
	 * @param idUserA
	 * @param idUserB
	 */
	void confirmFriendRequest(int idUserA, int idUserB){
			idUserA = userID;
	}
		
	/**
	 * Allows the user to deny a friend request from another user
	 * @param idUserA
	 * @param idUserB
	 */
	void denyFriendRequest(int idUserA, int idUserB){
			idUserA = userID;
	}
		
	/** 
	 * Returns a list of friend requests from a particular user
	 * @param userID
	 */
	void getFriendRequests(int userID){
			this.userID = userID;
			
	}
		
	/**
	 * Returns a list of friends from a particular user
	 * @param userID
	 */
	void getFriendsList(int userID){
			this.userID = userID;
	}
		
	/**
	 * Deletes a friend from the friends list of a particular user
	 * @param idUserA
	 * @param idUserB
	 */
	void deleteFriend(int idUserA, int idUserB){
			idUserA = userID;		// the user
	}
	
	/**
	 * Updates a user's list of friends
	 * @param userID
	 * @return 
	 */
	void updateFriendsList(int userID){
			this.userID = userID;
	}
	
}
