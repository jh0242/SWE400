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
	 * @param userIDa
	 * @param userIDb
	 * @return
	 * @throws SQLException
	 */
	public static boolean insertFriend(int userIDa, int userIDb) throws SQLException
	{
		if (!areFriends(userIDa,userIDb))
		{
			String insertFriends = new String("INSERT INTO FRIENDS (UserIDA,UserIDB) VALUES (?,?)");
			PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(insertFriends);
			stmt.setInt(1, userIDa);
			stmt.setInt(2, userIDb);
			stmt.executeUpdate();
			return true;
		}
		return false;
	}

	/**
	 * Executes the removal of a FRIENDS row where both COLUMNS contain either of the corresponding userIDs
	 * @param userIDa userId of requesting User
	 * @param userIDb userID of friend User
	 * @return
	 * @throws SQLException
	 */
	public static boolean removeFriendship(int userIDa, int userIDb) throws SQLException
	{
		if (areFriends(userIDa,userIDb))
		{
			String removeFriends = new String("DELETE FROM FRIENDS WHERE "
					+ "( UserIDA = '" + userIDa + "' AND UserIDB = '" + userIDb + "') OR "
					+ "( UserIDA = '" + userIDb + "' AND UserIDB = '" + userIDa + "');");
			PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(removeFriends);
			stmt.executeUpdate();
			return true;
		}
		return false;
	}
	
	/**
	 * Executes the removal of All Friendships for a user
	 * @param userID
	 * @return True upon completion of removal of Friendships
	 * @throws SQLException
	 */
	public static boolean removeAllFriendships(int userID) throws SQLException
	{
		String removeFriends = new String("DELETE FROM FRIENDS WHERE "
				+ "( UserIDA = '" + userID + "' OR UserIDB = '" + userID + "');");
		PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(removeFriends);
		stmt.executeUpdate();
		return true;	
	}

	/**
	 * Returns Object of Friends related to userID
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet getFriends(int userID) throws SQLException
	{
		String getFriends = new String("SELECT * FROM FRIENDS WHERE "
				+ "( UserIDA = '" + userID + "' OR UserIDB = '" + userID + "');");
		PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(getFriends);
		ResultSet friends = stmt.executeQuery();		
		return friends;
	}
		
	/**
	 * Checks if there is a friendship between two users in database
	 * @param userIDa
	 * @param userIDb
	 * @return
	 * @throws SQLException
	 */
	public static boolean areFriends(int userIDa,int userIDb) throws SQLException
	{
		String checkAreFriends = new String("SELECT UserIDA FROM FRIENDS WHERE "
				+ "( UserIDA = '" + userIDa + "' AND UserIDB = '" + userIDb + "') OR "
				+ "( UserIDA = '" + userIDb + "' AND UserIDB = '" + userIDa + "');");
		PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(checkAreFriends);
		ResultSet result = stmt.executeQuery();
		if(!result.isBeforeFirst())
		{
			return false;
		}else
		{
			return true;
		}
	}
}
