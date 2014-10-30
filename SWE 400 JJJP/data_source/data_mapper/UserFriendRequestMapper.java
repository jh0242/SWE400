package data_mapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import data_gateway.UserFriendRequestGateway;
import domain_model.Person;

/**
 * Mapper for the FriendRequestGateway
 * @author John Terry
 *
 */

public class UserFriendRequestMapper {
	
	Map<Integer, Person> users = new HashMap<Integer, Person>();
	Map<Integer, Integer> friendRequests = new HashMap<Integer, Integer>();
	
	/**
	 * Inserts a FriendRequest into the database and the domain layer	
	 * @param userA
	 * @param userB
	 * @return
	 * @throws SQLException
	 */
	public boolean insertFriendRequest(int userIDA, Person userA, int friendRequestIDB) throws SQLException{
		if(isValidUserObject(userIDA, userA) && !isValidFriendRequestObject(friendRequestIDB))
		{
			friendRequests.put(userIDA, friendRequestIDB);
			UserFriendRequestGateway.insertFriendRequest(userIDA, friendRequestIDB);
			return true;
		}
		return false;
	}
	
	/**
	 * Removes a FriendRequest from the database and the domain layer
	 * @param userID
	 * @param friendRequestID
	 * @return
	 * @throws SQLException
	 */
	public boolean removeFriendRequest(int userIDA, Person userA, int friendRequestIDB) throws SQLException{
		if(isValidUserObject(userIDA, userA) && isValidFriendRequestObject(friendRequestIDB))
		{
			friendRequests.remove(friendRequestIDB);
			UserFriendRequestGateway.removeFriendRequest(userIDA,friendRequestIDB);
			return true;
		}
		return false;
	}
	
	/**
	 * Finds the OutgoingFriendRequest(s) from the database and the domain layer 
	 * @param userID
	 * @param friendRequestID
	 * @return
	 * @throws SQLException
	 */
	public boolean findOutgoingFriendRequests(int userIDA, Person userA, int friendRequestIDB) throws SQLException{
		
		if(isValidUserObject(userIDA, userA))
		{
			friendRequests.get(userIDA);
			UserFriendRequestGateway.findOutgoingFriendRequests(userIDA);
			return true;
		}
		return false;
	}
	
	/**
	 * Finds the IncomingFriendRequest(s) from the database and the domain layer 
	 * @param userID
	 * @param friendRequestID
	 * @return
	 * @throws SQLException
	 */
	public boolean findIncomingFriendRequests(int userIDA, Person userA, int friendRequestIDB) throws SQLException{

		if(isValidFriendRequestObject(userIDA))
		{
			friendRequests.get(friendRequestIDB);
			UserFriendRequestGateway.findOutgoingFriendRequests(friendRequestIDB);
			return true;
		}
		return false;
	}
	
	/**
	 * Checks to make sure that the user object is listed in the users HashMap
	 * @param userIDA
	 * @param userA
	 * @return
	 * @throws SQLException
	 */
	private boolean isValidUserObject(int userIDA, Person userA) throws SQLException
	{
		if(users.containsKey(userIDA) && users.containsValue(userA))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Checks to make sure that the friendRequest object is listed in the friendRequests HashMap
	 * @param userIDA
	 * @return
	 * @throws SQLException
	 */
	private boolean isValidFriendRequestObject(int userIDA) throws SQLException
	{
		if(friendRequests.containsKey(userIDA))
		{
			return true;
		}
		return false;
	}
}
