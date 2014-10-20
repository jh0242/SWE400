package data_gateway;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//Friend Gateway
public class FriendGateway 
{
	// Executes the insertion of a row in FRIENDS with the corresponding userIDs
	public boolean insertFriend(int userIDa, int userIDb) throws SQLException
	{
		if (!areFriends(userIDa,userIDb))
		{
			String insertFriends = new String("INSERT INTO FRIENDS (UserIDa,UserIDb) VALUES (?,?)");
			PreparedStatement stmt = DataBase.getInstance().getConnection().prepareStatement(insertFriends);
			stmt.setInt(1, userIDa);
			stmt.setInt(2, userIDb);
			stmt.executeUpdate();
			return true;
		}
		return false;
	}

	// Executes the removal of a FRIENDS row where both COLUMNS contain either of the
	// corresponding userIDs
	public boolean removeFriendship(int userIDa, int userIDb) throws SQLException
	{
		if (areFriends(userIDa,userIDb))
		{
			String removeFriends = new String("DELETE FROM FRIENDS WHERE "
					+ "( UserIDa = '" + userIDa + " AND UserIDb = '" + userIDb + "') OR "
					+ "( UserIDa = '" + userIDb + " AND UserIDb = '" + userIDa + "');");
			PreparedStatement stmt = DataBase.getInstance().getConnection().prepareStatement(removeFriends);
			stmt.executeUpdate();
			return true;
		}
		return false;
	}

	//Returns Object of Friends related to userID
	public Object getFriends(int userID) throws SQLException
	{
		String getFriends = new String("SELECT * FROM FRIENDS WHERE "
				+ "( UserIDa = '" + userID + " OR UserIDb = '" + userID + "');");
		PreparedStatement stmt = DataBase.getInstance().getConnection().prepareStatement(getFriends);
		Object results = stmt.executeQuery();
		return results;
	}
	
	//Checks if there is a friendship between two users in database
	public boolean areFriends(int userIDa,int userIDb) throws SQLException
	{
		String checkAreFriends = new String("SELECT * FROM FRIENDS WHERE "
				+ "( UserIDa = '" + userIDa + " AND UserIDb = '" + userIDb + "') OR "
				+ "( UserIDa = '" + userIDb + " AND UserIDb = '" + userIDa + "');");
		PreparedStatement stmt = DataBase.getInstance().getConnection().prepareStatement(checkAreFriends);
		boolean result = stmt.execute();
		return result;
	}
}
