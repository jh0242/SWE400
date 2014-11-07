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
	 * @param userName the requested user name for the new user.  Must be unique.
	 * @param password the requested password for the new user.
	 * @param displayName the requested display name for the new user.
	 * @return boolean true if the insert succeeded or false if the insert did not succeed.
	 * @throws SQLException
	 */
	public static boolean insert(String userName, String password, String displayName)
	{
		try 
		{
			if (!userNameIsInTable(userName))
			{
				String insertData = new String("INSERT INTO USER (UserName,Password,DisplayName) VALUES (?,?,?)");
				PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(insertData);
				stmt.setString(1, userName);
				stmt.setString(2, password);
				stmt.setString(3, displayName);
				stmt.executeUpdate();
				return true;
			}
		} catch (SQLException e) 
		{
			System.out.println("Error with MySQL syntax in insert!");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Gets the id of the user with the associated userName and password.
	 * @param userName of the user being requested.
	 * @param password of the user being requested.
	 * @return the user's unique id number if the userName and password is valid, else -1.
	 * @throws SQLException
	 */
	public static int getID(String userName, String password)
	{
		String selectUser = new String("SELECT * FROM USER where UserName = '" + userName + "' AND Password = '" + password + "';");
		PreparedStatement stmt;
		ResultSet rs;
		int id = -1;
		try 
		{
			stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(selectUser);
			rs = stmt.executeQuery(selectUser);
			if (!rs.next())
				return id;
			id = rs.getInt(rs.findColumn("UserID"));
		} catch (SQLException e) 
		{
			System.out.println("Error with MySQL syntax in getID!");
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * Retrieves the row from the database where UserName and Password match the
	 * corresponding parameters.  If the UserName does not exist or the password
	 * is incorrect for the corresponding userName, return null.
	 * @param userName the userName of the user trying to log in.
	 * @param password the password of the user trying to log in.
	 * @return rs the row of the corresponding user or null if the userName does not exist
	 * or the password is incorrect.
	 * @throws SQLException
	 */
	public static ResultSet selectUser(String userName, String password)
	{
		String selectUser = new String("SELECT * FROM USER where UserName = '" + userName + "' AND Password = '" + password + "';");
		PreparedStatement stmt;
		ResultSet rs = null;
		try
		{
			stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(selectUser);
			rs = stmt.executeQuery(selectUser);
			if (!rs.next())
				return null;
		} catch (SQLException e)
		{
			System.out.println("Error with MySQL syntax in selectUser!");
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Checks that the userName is in the table.  If the userName is in the table, that row
	 * is removed from the table.  If the userName is not in the table, return false to alert
	 * of an unsuccessful remove due to an invalid userName.
	 * @param userName the unique userName of the User to be removed.
	 * @return boolean whether or not the remove was successful.
	 * @throws SQLException
	 */
	public static boolean removeByUserName(String userName)
	{
		try {
			if (userNameIsInTable(userName))
			{
				String removeUser = new String("DELETE FROM USER where UserName = '" + userName + "';");
				PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(removeUser);
				stmt.executeUpdate();
				return true;
			}
		} catch (SQLException e) 
		{
			System.out.println("Error with MySQL syntax in removeByUserName!");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Checks that the userID is in the database.  If the userID is in the database, the displayName
	 * in that row is updated to newDisplayName and return true.  If the userID is not in the database,
	 * return false to alert of an unsuccessful update due to an invalid userID.
	 * @param userID the id of the user making the request
	 * @param newDisplayName the new display name requested by the user
	 * @return boolean true if the update is successful, false if the update is unsuccessful
	 * @throws SQLException
	 */
	public static boolean updateDisplayName(int userID, String newDisplayName)
	{
		if (userIDisInTable(userID))
		{
			String removeUser = new String("UPDATE USER set DisplayName = '" + newDisplayName + "' where UserID = '" + userID + "';");
			PreparedStatement stmt;
			try
			{
				stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(removeUser);
				stmt.executeUpdate();
			} catch (SQLException e)
			{
				System.out.println("Error with MySQL syntax in updateDisplayName!");
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	/**
	 * Checks if the User is in the table by using the id of the User you are
	 * working with.  If the User is in the table, return true.  If the User
	 * is not in the table, return false.
	 * @param userID the ID of the user we are looking for.
	 * @return true if the User is in the table, false if the User is not in the table.
	 * @throws SQLException
	 */
	private static boolean userIDisInTable(int userID)
	{
		String checkValidUserID = new String("SELECT * FROM USER where UserID = '" + userID + "';");
		PreparedStatement stmt;
		ResultSet rs = null;
		try
		{
			stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(checkValidUserID);
			rs = stmt.executeQuery(checkValidUserID);
			if (!rs.next())
				return false;
		} catch (SQLException e)
		{
			System.out.println("Error with MySQL syntax in userIDisInTable!");
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Checks if the User is in the table by using the userName of the User you are
	 * working with.  If the User is in the table, return true.  If the User
	 * is not in the table, return false.
	 * @param userName the userName of the User we are looking for.
	 * @return true if the User is in the table, false if the User is not in the table.
	 * @throws SQLException
	 */
	public static boolean userNameIsInTable(String userName)
	{
		String checkUniqueUserName = new String("SELECT * FROM USER where UserName = '" + userName + "';");
		PreparedStatement stmt;
		ResultSet rs;
		try 
		{
			stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(checkUniqueUserName);
			rs = stmt.executeQuery(checkUniqueUserName);
			if (!rs.next())
				return false;
		} catch (SQLException e) 
		{
			System.out.println("Error with MySQL syntax in userNameIsInTable!");
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Gets the displayName of the target user in the USER table.
	 * @param target the UserName of the row to be used
	 * @return the DisplayName of the target row
	 */
	public static String getDisplayName(String target) 
	{
		String checkUniqueUserName = new String("SELECT * FROM USER where UserName = '" + target + "';");
		PreparedStatement stmt;
		ResultSet rs;
		String displayName = "";
		try 
		{
			stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(checkUniqueUserName);
			rs = stmt.executeQuery(checkUniqueUserName);
			if (!rs.next())
				return "nonexistent person";
			displayName = rs.getString("DisplayName");
		} catch (SQLException e) 
		{
			System.out.println("Error with MySQL syntax in userNameIsInTable!");
			e.printStackTrace();
		}
		return displayName;
	}
}