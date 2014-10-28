package data_gateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  This class is a gateway that isolates all of the SQL commands 
 *  to access the FriendRequest Table in the database 
 *  
 *  John Terry
 *	SWE 400, LSA Group JJJP
 *	October 3, 2014
 */

public class UserFriendRequestGateway {
	
	/**
	 * Pulls all outgoing friend requests for a particular user based on their userID
	 * @param user 
	 * @return 
	 * @throws SQLException 
	 */
	public static boolean findOutgoingFriendRequests(int user) throws SQLException{
		if (isValidUserID(user))
		{
			String findData = new String("SELECT * FROM PENDINGFRIENDREQUESTS WHERE UserIDA='" + user + "';");
			PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(findData);
			stmt.executeQuery();
			return true;
		}
		return false;	
	}
	
	/**
	 * Pulls all incoming friend requests for a particular user based on their userID
	 * @param user 
	 * @return 
	 * @throws SQLException 
	 */
	public static boolean findIncomingFriendRequests(int user) throws SQLException{
		if (isValidUserID(user))
		{
			String findData = new String("SELECT * FROM PENDINGFRIENDREQUESTS WHERE UserIDB='" + user + "';");
			PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(findData);
			stmt.executeQuery();
			return true;
		}
		return false;	
	}
	
	/**
	 * Inserts a friend request to the table when the user 
	 * either sends or receives a friend request 
	 * @param userA 
	 * @param userB 
	 * @return 
	 * @throws SQLException 
	 */
	public static boolean insertFriendRequest(int userA, int userB) throws SQLException
	{
		if (isValidUserID(userB) & isValidFriendRequestIDInsert(userA, userB))
		{
			String insertData = new String("INSERT INTO PENDINGFRIENDREQUESTS(UserIDA, UserIDB) VALUES (?,?)");
			PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(insertData);
			stmt.setInt(1, userA);
			stmt.setInt(2, userB);
			stmt.executeUpdate();
			return true;
		}
		return false;
	}
	
	/**
	 * Removes friend request from the table when
	 * either the friendship is accepted or rejected
	 * @param user 
	 * @return 
	 * @throws SQLException 
	 */
	public static boolean removeFriendRequest(int userA, int userB) throws SQLException
	{
		if (isValidUserID(userA) & isValidFriendRequestID(userA, userB))
		{
			String removeData = new String("DELETE FROM PENDINGFRIENDREQUESTS WHERE "
					+ "( UserIDA = '" + userA + "' AND UserIDB = '" + userB + "') OR "
					+ "( UserIDA = '" + userB + "' AND UserIDB = '" + userA + "');");
			PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(removeData);
			stmt.executeUpdate();
			return true;
		}
		return false;	
	}
	
	/**
	 * Determines if a particular user ID is valid by checking if a particular user ID is 
	 * located in the PERSON table in the database
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	private static boolean isValidUserID(int userID) throws SQLException
	{
		String checkUserID = new String("SELECT * FROM USER where UserID = '" + userID + "';");
		PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(checkUserID);
		ResultSet rs = stmt.executeQuery(checkUserID);
		if (!rs.next())
			return false; // the userID is not located in the table
		return true;
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