package data_gateway;

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
	
	/**
	 * Pulls all outgoing friend requests for a particular user based on their userID
	 * @throws SQLException 
	 */
	public boolean findOutgoingFriendRequests(int user) throws SQLException{
		if (isValidUserID(user))
		{
			String findData = new String("SELECT * FROM Friend_Request WHERE UserA='" + user + "';");
			PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(findData);
			stmt.executeUpdate();
			return true;
		}
		return false;	
	}
	
	/**
	 * Pulls all incoming friend requests for a particular user based on their userID
	 * @throws SQLException 
	 */
	public boolean findIncomingFriendRequests(int user) throws SQLException{
		if (isValidUserID(user))
		{
			String findData = new String("SELECT * FROM Friend_Request WHERE UserB='" + user + "';");
			PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(findData);
			stmt.executeUpdate();
			return true;
		}
		return false;	
	}
	
	/**
	 * Inserts a friend request to the table when the user 
	 * either sends or receives a friend request 
	 * @throws SQLException 
	 */
	public boolean insertFriendRequest(int userA, int userB) throws SQLException
	{
		if (isValidUserID(userB))
		{
			String insertData = new String("INSERT INTO Friend_Request(userID, userID) VALUES (?,?)");
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
	 * @throws SQLException 
	 */
	public boolean removeFriendRequest(int user) throws SQLException
	{
		if (isValidUserID(user))
		{
			String removeData = new String("DELETE FROM Friend_Request where user_id = '" + user + "';");
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
	private boolean isValidUserID(int userID) throws SQLException
	{
		String checkUserID = new String("SELECT * FROM PERSON where user_id = '" + userID + "';");
		PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(checkUserID);
		ResultSet rs = stmt.executeQuery(checkUserID);
		if (!rs.next())
			return false; // the userID is not located in the table
		return true;
	}
}