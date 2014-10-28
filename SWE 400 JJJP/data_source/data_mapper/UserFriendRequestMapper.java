package data_mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import data_gateway.DataBaseConnection;
import data_gateway.UserFriendRequestGateway;

/**
 * Mapper for the FriendRequestGateway
 * @author John Terry
 *
 */

public class UserFriendRequestMapper {
	
	ArrayList<Integer> friendRequests;
	//Map<Integer,Integer> friendRequestsMap = new HashMap<Integer, Integer>();
	
	/**
	 * Inserts a FriendRequest into the database and the domain layer	
	 * @param userA
	 * @param userB
	 * @return
	 * @throws SQLException
	 */
	public boolean insertFriendRequest(int userID, int friendRequestID) throws SQLException{
		UserFriendRequestGateway.insertFriendRequest(userID,friendRequestID);
		//friendRequestsMap.add(friendRequestID);
		//friendRequestsMap.add(friendRequestID);
		if (friendRequests != null && isValidFriendRequestIDInsert(userID, friendRequestID)){
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
	public boolean removeFriendRequest(int userID, int friendRequestID) throws SQLException{
		UserFriendRequestGateway.removeFriendRequest(userID,friendRequestID);
		friendRequests.remove(friendRequestID);
		if (friendRequests == null && !isValidFriendRequestIDInsert(userID, friendRequestID)){
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
	public boolean findOutgoingFriendRequests(int userID, int friendRequestID) throws SQLException{
		UserFriendRequestGateway.findOutgoingFriendRequests(userID);
		if (friendRequests.contains(friendRequestID) && isValidFriendRequestID(userID, friendRequestID)){
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
	public boolean findIncomingFriendRequests(int userID, int friendRequestID) throws SQLException{
		UserFriendRequestGateway.findIncomingFriendRequests(friendRequestID);
		if (friendRequests.contains(userID) && isValidFriendRequestID(userID, friendRequestID)){
			return true;
		} 
		return false;
	}
	
	/**
	 * Checks if the friendship of both users is listed in the PENDINGFRIENDREQUESTS table 
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	private static boolean isValidFriendRequestIDInsert(int userIDA, int userIDB) throws SQLException
	{
		String checkUserIDA = new String("SELECT * FROM PENDINGFRIENDREQUESTS where UserIDA = '" + userIDA + "' and UserIDB = '" + userIDB + "';");
		PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(checkUserIDA);
		ResultSet rs = stmt.executeQuery(checkUserIDA);
		if (rs.next())
			return false; // the userIDA is not located in the table
		return true;
	}
	
	/**
	 * Checks if the friendship of both users is listed in the PENDINGFRIENDREQUESTS table 
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	private static boolean isValidFriendRequestID(int userIDA, int userIDB) throws SQLException
	{
		String checkFriendship = new String("SELECT * FROM PENDINGFRIENDREQUESTS where UserIDA = '" + userIDA + "' and UserIDB = '" + userIDB + "';");
//		String checkFriendship = new String("SELECT * FROM PENDINGFRIENDREQUESTS WHERE "
//				+ "( UserIDA = '" + userIDA + "' AND UserIDB = '" + userIDB + "') OR "
//				+ "( UserIDA = '" + userIDB + "' AND UserIDB = '" + userIDA + "');");
		PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(checkFriendship);
		boolean result = stmt.execute();
		return result;
	}
}
