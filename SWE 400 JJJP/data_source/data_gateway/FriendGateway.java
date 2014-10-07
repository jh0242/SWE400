package data_gateway;
//Author: Joshua McMillen
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FriendGateway 
{
	private Connection conn = null;
	private String insertData = new String("INSERT INTO FRIENDS (UserID_A,UserID_B) VALUES (?,?)");
	
	// Creates an instance of a friendship.
	public boolean createFriendship(int userID_A, int userID_B) throws SQLException
	{
		if ( checkUserExists(userID_A) && checkUserExists(userID_B) ) {
			String checkFriendship = new String("SELECT * FROM FRIENDS WHERE USERID_A ="+userID_A+"AND "
					+ "USERID_B = "+userID_B+";");
			PreparedStatement stmt = conn.prepareStatement(checkFriendship);
			ResultSet rs = stmt.executeQuery(checkFriendship);
			
			// Check if Friendship Already Exists
			// If So, Kill
			if (rs.next()){
				return false;
			}
			
			// Execute Insertion of New Friendship
			stmt = conn.prepareStatement(insertData);
			stmt.setInt(1, userID_A);
			stmt.setInt(2, userID_B);
			stmt.executeUpdate();	
			
			return true;
		} return false;
	}
	
	public boolean remove ( int userID_A , int userID_B ) throws SQLException
	{
		String checkFriendship = new String("SELECT * FROM FRIENDS WHERE USERID_A ="+userID_A+"AND "
				+ "USERID_B = "+userID_B+";");
		PreparedStatement stmt = conn.prepareStatement(checkFriendship);
		ResultSet rs = stmt.executeQuery(checkFriendship);
		
		// Check if userID's exist in Friendship to remove
		// If Not, Kill
		if (!rs.next())
		{
			return false;
		}
		
		String removeFriendship = new String ( "DELETE FROM FRIENDS WHERE USERID_A ="+userID_A+"AND "
				+ "USERID_B = "+userID_B+";" );
		stmt = conn.prepareStatement(removeFriendship);
		stmt.executeUpdate();
		
		return true;
	}
	//Returns All Users
	public Object findAllFriends(int userID) throws SQLException
	{
		if(checkUserExists(userID))
		{
			String getAllFriendships = new String("SELECT * FROM FRIENDS WHERE USERID_A ="+userID+"OR "
					+ "USERID_B = "+userID+";");
			PreparedStatement stmt = conn.prepareStatement(getAllFriendships);
			Object obj = stmt.executeUpdate();
			
			return obj;
		}return null;
	}
	//gets all of the friends for a particular user based on the userID.
	public void findAllRequests(int userID)
	{
		
	}
	
	// Check that UserID is valid
	private boolean checkUserExists(int userID) throws SQLException{
		String checkFriendship = new String("SELECT * FROM PERSON WHERE USERID ="+userID+";");
		PreparedStatement stmt = conn.prepareStatement(checkFriendship);
		ResultSet rs = stmt.executeQuery(checkFriendship);
		
		// Check if User Exists
		// If Not, Kill
		if (!rs.next()){
			return false;
		} return true;
	}
}
