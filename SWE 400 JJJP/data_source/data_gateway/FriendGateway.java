package data_gateway;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import domain_model.Friend;

/**
 * @author Joshua McMillen
 */
public class FriendGateway 
{
	/**
	 * Executes the insertion of a row in FRIENDS with the corresponding userIDs
	 * @param userNameA the username of the first user.
	 * @param userNameB the username of the other user.
	 * @param userDisplayNameA the display name of the first user.
	 * @param userDisplayNameB the display name of the other user.
	 * @return true if the insert was successful, false if otherwise
	 */
	public static boolean insertFriend(String userNameA, String userNameB, String userDisplayNameA, String userDisplayNameB)
	{
		if (!areFriends(userNameA,userNameB))
		{
			String insertFriends = new String("INSERT INTO FRIENDS (UserNameA,UserNameB,UserDisplayNameA,UserDisplayNameB) VALUES (?,?,?,?)");
			PreparedStatement stmt;
			try
			{
				stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(insertFriends);
				stmt.setString(1, userNameA);
				stmt.setString(2, userNameB);
				stmt.setString(3, userDisplayNameA);
				stmt.setString(4, userDisplayNameB);
				stmt.executeUpdate();
			} catch (SQLException e)
			{
				System.out.println("Error with MySQL syntax in insertFriend!");
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	/**
	 * Executes the removal of a FRIENDS row where both COLUMNS contain either of the corresponding userIDs
	 * @param userNameA userId of requesting User
	 * @param userNameB userID of friend User
	 * @return true if the removal was successful, false otherwise
	 */
	public static boolean removeFriendship(String userNameA, String userNameB)
	{
		try
		{
			if (areFriends(userNameA,userNameB))
			{
				String removeFriends = new String("DELETE FROM FRIENDS WHERE "
						+ "( UserNameA = '" + userNameA + "' AND UserNameB = '" + userNameB + "') OR "
						+ "( UserNameA = '" + userNameB + "' AND UserNameB = '" + userNameA + "');");
				PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(removeFriends);
				stmt.executeUpdate();
				return true;
			}
		} catch (SQLException e)
		{
			System.out.println("Error with MySQL syntax in removeFriendship!");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Executes the removal of All Friendships for a user
	 * @param userName the user we are removing from the FRIENDS table.
	 * @return True upon completion of removal of Friendships
	 */
	public static boolean removeAllFriendships(String userName)
	{
		String removeFriends = new String("DELETE FROM FRIENDS WHERE "
				+ "( UserNameA = '" + userName + "' OR UserNameB = '" + userName + "');");
		PreparedStatement stmt;
		try
		{
			stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(removeFriends);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			System.out.println("Error with MySQL syntax in removeAllFriendShips!");
			e.printStackTrace();
		}
		return true;	
	}

	/**
	 * Returns Object of Friends related to userID
	 * @param userName the User whom we are getting friends for.
	 * @return ResultSet the record of friend relationships between
	 * the given userName and other users.
	 */
	public static ResultSet getFriends(String userName)
	{
		String getFriends = new String("SELECT * FROM FRIENDS WHERE "
				+ "( UserNameA = '" + userName + "' OR UserNameB = '" + userName + "');");
		PreparedStatement stmt;
		ResultSet friends = null;
		try
		{
			stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(getFriends);
			friends = stmt.executeQuery();
		} catch (SQLException e)
		{
			System.out.println("Error with MySQL syntax in getFriends!");
			e.printStackTrace();
		}		
		return friends;
	}
		
	/**
	 * Checks if there is a friendship between two users in database
	 * @param userNameA the user name of a specific user
	 * @param userNameB the user name of the other user
	 * @return true if there is a friendship, false otherwise
	 */
	public static boolean areFriends(String userNameA, String userNameB)
	{
		String checkAreFriends = new String("SELECT UserNameA FROM FRIENDS WHERE "
				+ "( UserNameA = '" + userNameA + "' AND UserNameB = '" + userNameB + "') OR "
				+ "( UserNameA = '" + userNameB + "' AND UserNameB = '" + userNameA + "');");
		PreparedStatement stmt;
		try
		{
			stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(checkAreFriends);
			ResultSet result = stmt.executeQuery();
			if(!result.isBeforeFirst())
				return false;
		} catch (SQLException e)
		{
			System.out.println("Error with MySQL syntax in areFriends!");
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Updates the display name of the associated username for any row in
	 * the FRIENDS table.
	 * @param username the username of the Person.
	 * @param fullname the new display name of the Person.
	 */
	public static void updateDisplayName(String username, String fullname) 
	{
		String updateDisplay = new String("update FRIENDS set UserDisplayNameA=? where UserNameA=?");
		String updateDisplay2 = new String("update FRIENDS set UserDisplayNameB=? where UserNameB=?");
		PreparedStatement stmt;
		try
		{
			stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(updateDisplay);
			stmt.setString(1, fullname);
			stmt.setString(2, username);
			stmt.executeUpdate();
			stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(updateDisplay2);
			stmt.setString(1, fullname);
			stmt.setString(2, username);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			System.out.println("Error with MySQL syntax in areFriends!");
			e.printStackTrace();
		}
	}
}
