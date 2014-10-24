package data_gateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PersonGateway as a RowDataGateway.
 * @author jh0242
 *
 */
public class PersonGateway
{
	/**
	 * Checks that the userName isn't already in use in the database, since
	 * userName is unique. If it is not in use, we add the new User to the
	 * PERSON table in the database and return true. If it is in use, we return
	 * false to alert the User that the userName is already in use.
	 * 
	 * @param userName, the requested user name for the new user.  Must be unique.
	 * @param password, the requested password for the new user.
	 * @param displayName, the requested display name for the new user.
	 * @return boolean, true if the insert succeeded or false if the insert did not succeed.
	 * @throws SQLException
	 */
	public static boolean insert(String userName, String password, String displayName) throws SQLException
	{
		if (isValidUserName(userName))
		{
			String insertData = new String("INSERT INTO USER (UserName,Password,DisplayName) VALUES (?,?,?)");
			PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(insertData);
			stmt.setString(1, userName);
			stmt.setString(2, password);
			stmt.setString(3, displayName);
			stmt.executeUpdate();
			return true;
		}
		return false;
	}

	/**
	 * Checks that the userID is in the database.  If the userID is in the database, that row is
	 * deleted from the database and return true.  If the userID is not in the database, return
	 * false to alert of an unsuccessful remove due to an invalid userID.
	 * @param userID, the id of the user making the request
	 * @return boolean, whether or not the 
	 * @throws SQLException
	 */
	public static boolean removeByUserID(int userID) throws SQLException
	{
		if (isValidUserID(userID))
		{
			String removeUser = new String("DELETE FROM PERSON where UserID = '" + userID + "';");
			PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(removeUser);
			stmt.executeUpdate();
			return true;
		}
		return false;
	}
	
	public static boolean removeByUserName(String userName) throws SQLException
	{
		if (isValidUserName(userName))
		{
			String removeUser = new String("DELETE FROM PERSON where UserName = '" + userName + "';");
			PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(removeUser);
			stmt.executeUpdate();
			return true;
		}
		return false;
	}

	/**
	 * Checks that the userID is in the database.  If the userID is in the database, the displayName
	 * in that row is updated to newDisplayName and return true.  If the userID is not in the database,
	 * return false to alert of an unsuccessful update due to an invalid userID.
	 * @param userID, the id of the user making the request
	 * @param newDisplayName, the new display name requested by the user
	 * @return boolean, true if the update is successful, false if the update is unsuccessful
	 * @throws SQLException
	 */
	public boolean updateDisplayName(int userID, String newDisplayName) throws SQLException
	{
		if (isValidUserID(userID))
		{
			String removeUser = new String("UPDATE PERSON where user_id = '" + userID + "';");
			PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(removeUser);
			stmt.executeUpdate();
			return true;
		}
		return false;
	}

	private static boolean isValidUserID(int userID) throws SQLException
	{
		String checkValidUserID = new String("SELECT * FROM PERSON where user_id = '" + userID + "';");
		PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(checkValidUserID);
		ResultSet rs = stmt.executeQuery(checkValidUserID);
		if (!rs.next())
			return false; // The userID is not in the database, thus the
							// specified user does not exist.
		return true;
	}

	private static boolean isValidUserName(String userName) throws SQLException
	{
		String checkUniqueUserName = new String("SELECT * FROM USER where UserName = '" + userName + "';");
		PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(checkUniqueUserName);
		ResultSet rs = stmt.executeQuery(checkUniqueUserName);
		if (!rs.next())
			return true; // the userName is not taken in the table.
		return false;
	}
}