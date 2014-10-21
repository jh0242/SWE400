//Author: Joshua McMillen
package data_gateway;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain_model.Friend;

//Friend Gateway
public class FriendGateway 
{
	// Executes the insertion of a row in FRIENDS with the corresponding userIDs
	public static boolean insertFriend(String userIDa, String userIDb) throws SQLException
	{
		if (!areFriends(userIDa,userIDb))
		{
			String insertFriends = new String("INSERT INTO FRIENDS (UserIDa,UserIDb) VALUES (?,?)");
			PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(insertFriends);
			stmt.setString(1, userIDa);
			stmt.setString(2, userIDb);
			stmt.executeUpdate();
			return true;
		}
		return false;
	}

	// Executes the removal of a FRIENDS row where both COLUMNS contain either of the
	// corresponding userIDs
	public static boolean removeFriendship(String userIDa, String userIDb) throws SQLException
	{
		if (areFriends(userIDa,userIDb))
		{
			String removeFriends = new String("DELETE FROM FRIENDS WHERE "
					+ "( UserIDa = '" + userIDa + " AND UserIDb = '" + userIDb + "') OR "
					+ "( UserIDa = '" + userIDb + " AND UserIDb = '" + userIDa + "');");
			PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(removeFriends);
			stmt.executeUpdate();
			return true;
		}
		return false;
	}

	//Returns Object of Friends related to userID
	public static ArrayList<String> getFriends(String userID) throws SQLException
	{
		String getFriends = new String("SELECT * FROM FRIENDS WHERE "
				+ "( UserIDa = '" + userID + " OR UserIDb = '" + userID + "');");
		PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(getFriends);
		ResultSet results = stmt.executeQuery();
		ArrayList<String> friends =	null;
		while(results.next())
		{
			if(results.getString(1)!=userID)
			{
				friends.add(results.getString(1));
			}else
			{
				friends.add(results.getString(2));
			}
		}
		return friends;
	}
	
	//Checks if there is a friendship between two users in database
	public static boolean areFriends(String userIDa,String userIDb) throws SQLException
	{
		String checkAreFriends = new String("SELECT * FROM FRIENDS WHERE "
				+ "( UserIDa = '" + userIDa + " AND UserIDb = '" + userIDb + "') OR "
				+ "( UserIDa = '" + userIDb + " AND UserIDb = '" + userIDa + "');");
		PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(checkAreFriends);
		boolean result = stmt.execute();
		return result;
	}
}
