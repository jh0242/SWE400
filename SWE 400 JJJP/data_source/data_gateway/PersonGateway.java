package data_gateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersonGateway
{
	String insertData = new String("INSERT INTO PERSON (User Name,Password,Display Name) VALUES (?,?,?)");
	
	private Connection conn = null;
	
	//May not need this if we can make sql automatically generate these.
	private int getNextUniqueID()
	{
		//returns the next unique id, an integer, for the PERSON table.
		return 0;
	}
	
	/**
	 * Checks that the userName isn't already in use in the database, since userName is unique.  If it
	 * is not in use, we add the new User to the PERSON table in the database and return true.  If it is
	 * in use, we return false to alert the User that the userName is already in use.
	 * @param userName
	 * @param password
	 * @param displayName
	 * @return boolean, whether or not the name is valid (true if the name is not in use, else false).
	 * @throws SQLException
	 */
	public boolean insert(String userName, String password, String displayName) throws SQLException
	{
		String checkUniqueUserName = new String("SELECT * FROM PERSON where User Name = '" + userName + "';");
		PreparedStatement stmt = conn.prepareStatement(checkUniqueUserName);
		ResultSet rs = stmt.executeQuery(checkUniqueUserName);
		if (!rs.next())
			return false; //the userName is not taken in the table.
		
		//rs.next will get the next row in the result set
		//type in rs.getString, rs.getInt, etc. to see the methods for retrieving information
		//from specific columns
		stmt = conn.prepareStatement(insertData);
		stmt.setString(1, userName);
		stmt.setString(2, password);
		stmt.setString(3, displayName);
		stmt.executeUpdate();
		
		return true;
	}
	
	//May not need this method since there is not a command for a user to delete their account.
	/**
	 * 
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	public boolean remove(int userID) throws SQLException
	{
		String checkValidUserID = new String("SELECT * FROM PERSON where user_id = '" + userID + "';");
		PreparedStatement stmt = conn.prepareStatement(checkValidUserID);
		ResultSet rs = stmt.executeQuery(checkValidUserID);
		if (!rs.next())
			return false; //The userID is not in the database, thus the specified
						  //user does not exist.
		
		//rs.next will get the next row in the result set
		//type in rs.getString, rs.getInt, etc. to see the methods for retrieving information
		//from specific columns
		String removeUser = new String("DELETE FROM PERSON where user_id = '" + userID + "';");
		stmt = conn.prepareStatement(removeUser);
		stmt.executeUpdate();
		
		return true;
	}
	
	public boolean updateDisplayName(int userID, String newDisplayName) throws SQLException
	{
		String checkValidUserID = new String("SELECT * FROM PERSON where user_id = '" + userID + "';");
		PreparedStatement stmt = conn.prepareStatement(checkValidUserID);
		ResultSet rs = stmt.executeQuery(checkValidUserID);
		if (!rs.next())
			return false; //The userID is not in the database, thus the specified
						  //user does not exist.
		
		//rs.next will get the next row in the result set
		//type in rs.getString, rs.getInt, etc. to see the methods for retrieving information
		//from specific columns
		String removeUser = new String("UPDATE PERSON where user_id = '" + userID + "';");
		stmt = conn.prepareStatement(removeUser);
		stmt.executeUpdate();
		
		return true;
	}
	
	public boolean findByID(int userID)
	{
		return false;
	}
	
	public boolean findByUserName(String userName)
	{
		return false;
	}
}