package data_gateway;

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
	 * @throws SQLException 
	 */
	public boolean insertFriendRequest(int userA, String userB) throws SQLException
	{
		if (isValidUserName(userB))
		{
			String insertData = new String("INSERT INTO Friend_Request(userID, userID) VALUES (?,?)");
			PreparedStatement stmt = DataBase.getInstance().getConnection().prepareStatement(insertData);
			stmt.setInt(1, userA);
			stmt.setString(2, userB);
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
			PreparedStatement stmt = DataBase.getInstance().getConnection().prepareStatement(removeData);
			stmt.executeUpdate();
			return true;
		}
		return false;	
	}
	
	/**
	 * Determines if a particular user name is valid by checking if a particular user name is
	 * located in the PERSON table in the database
	 * @param userName
	 * @return
	 * @throws SQLException
	 */
	private boolean isValidUserName(String userName) throws SQLException
	{
		String checkUserName = new String("SELECT * FROM PERSON where User Name = '" + userName + "';");
		PreparedStatement stmt = DataBase.getInstance().getConnection().prepareStatement(checkUserName);
		ResultSet rs = stmt.executeQuery(checkUserName);
		if (!rs.next())
			return false; // the username is not located in the table
		return true;
		
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
		PreparedStatement stmt = DataBase.getInstance().getConnection().prepareStatement(checkUserID);
		ResultSet rs = stmt.executeQuery(checkUserID);
		if (!rs.next())
			return false; // the userID is not located in the table
		return true;
		
	}
}