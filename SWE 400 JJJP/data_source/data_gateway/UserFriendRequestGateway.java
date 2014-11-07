package data_gateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is a gateway that isolates all of the SQL commands to access the
 * FriendRequest Table in the database
 * 
 * John Terry SWE 400, LSA Group JJJP October 3, 2014
 */

public class UserFriendRequestGateway
{
	/**
	 * Pulls all outgoing friend requests for a particular user based on their
	 * userID
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet findOutgoingFriendRequests(String user)
	{
		String findData = new String("SELECT * FROM PENDINGFRIENDREQUESTS WHERE Requester='" + user + "';");
		PreparedStatement stmt;
		ResultSet outgoingFriendRequests = null;
		try 
		{
			stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(findData);
			outgoingFriendRequests = stmt.executeQuery();
		} catch (SQLException e) 
		{
			System.out.println("Error with MySQL syntax in findOutGoingFriendRequests!");
			e.printStackTrace();
		}
		return outgoingFriendRequests;
	}

	/**
	 * Pulls all incoming friend requests for a particular user based on their
	 * userID
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet findIncomingFriendRequests(String user)
	{
		String findData = new String("SELECT * FROM PENDINGFRIENDREQUESTS WHERE Requestee='" + user + "';");
		PreparedStatement stmt;
		ResultSet incomingFriendRequests = null;
		try 
		{
			stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(findData);
		incomingFriendRequests = stmt.executeQuery();
		} catch (SQLException e) 
		{
			System.out.println("Error with MySQL syntax in findIncomingFriendRequests!");
			e.printStackTrace();
		}
		return incomingFriendRequests;
	}

	/**
	 * Inserts a friend request to the table when the user either sends or
	 * receives a friend request
	 * 
	 * @param requester
	 * @param requestee
	 * @return
	 * @throws SQLException
	 */
	public static boolean insertFriendRequest(String requester, String requestee, String requesterDisplayName, String requesteeDisplayName)
	{
		try
		{
			if (PersonGateway.userNameIsInTable(requestee) && PersonGateway.userNameIsInTable(requester) && !isPendingFriendRequest(requester, requestee))
			{
				String insertData = new String("INSERT INTO PENDINGFRIENDREQUESTS(Requester, Requestee, RequesterDisplayName, RequesteeDisplayName) VALUES (?,?,?,?)");
				PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(insertData);
				stmt.setString(1, requester);
				stmt.setString(2, requestee);
				stmt.setString(3, requesterDisplayName);
				stmt.setString(4, requesteeDisplayName);
				stmt.executeUpdate();
				return true;
			}
		} catch (SQLException e)
		{
			System.out.println("Error with MySQL syntax in insertFriendRequest!");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Removes friend request from the table when either the friendship is
	 * accepted or rejected
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public static boolean removeFriendRequest(String requester, String requestee)
	{

		if (PersonGateway.userNameIsInTable(requester) & isPendingFriendRequest(requester, requestee))
		{
			String removeData = new String("DELETE FROM PENDINGFRIENDREQUESTS WHERE " + "( Requester = '" + requester + "' AND Requestee = '" + requestee + "') OR " + "( Requester = '" + requestee
					+ "' AND Requestee = '" + requester + "');");
			PreparedStatement stmt;
			try
			{
				stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(removeData);
				stmt.executeUpdate();
			} catch (SQLException e)
			{
				System.out.println("Error with MySQL syntax in removeFriendRequest!");
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	/**
	 * Checks if the friendship of both users is listed in the
	 * PENDINGFRIENDREQUESTS table
	 * 
	 * @param userID
	 * @return
	 * @throws SQLException
	 */
	private static boolean isPendingFriendRequest(String requester, String requestee)
	{
		String checkFriendRequestIDS = new String("SELECT * FROM PENDINGFRIENDREQUESTS where Requester = '" + requester + "' and Requestee = '" + requestee + "';");
		PreparedStatement stmt;
		try
		{
			stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(checkFriendRequestIDS);

			ResultSet rs = stmt.executeQuery(checkFriendRequestIDS);
			if (!rs.next())
				return false;
		} catch (SQLException e)
		{
			System.out.println("Error with MySQL syntax in isPendingFriendRequest method!");
			e.printStackTrace();
		}
		return true;
	}
}