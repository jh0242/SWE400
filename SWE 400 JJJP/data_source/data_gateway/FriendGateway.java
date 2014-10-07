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
	
	//creates an instance of a friendship.
	public boolean createFriendship(int userID_A, int userID_B) throws SQLException{
		String linkTwoFriends = new String("SELECT * FROM FRIENDS WHERE USERID_A ="+userID_A+"AND "
				+ "USERID_B = "+userID_B+";");
		PreparedStatement stmt = conn.prepareStatement(linkTwoFriends);
		ResultSet rs = stmt.executeQuery(linkTwoFriends);
		
		if (!rs.next()){
			return false;
		}
		
		stmt = conn.prepareStatement(insertData);
		stmt.setInt(1, userID_A);
		stmt.setInt(2, userID_B);
		stmt.executeUpdate();	
		
		return true;
	}
	
	public void delete(int userID_A, int userID_B){
		
	}
	//removes an instance of a friendship.
	public void findAllFriends(int userID){
		
	}
	//gets all of the friends for a particular user based on the userID.
	public void findAllRequests(int userID){
		
	}
	//gets all of the pending requests for a particular user based on the userID.

}
