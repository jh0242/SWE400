package data_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data_gateway.UserFriendRequestGateway;
import domain_model.FriendRequest;
import domain_model.Person;

/**
 * Mapper for the FriendRequestGateway
 * 
 * @author John Terry
 * @author Joshua McMillen
 */

public class UserFriendRequestMapper
{

	private static UserFriendRequestMapper userFriendRequestMapper;

	private UserFriendRequestMapper()
	{
	}

	/**
	 * Checks to make sure that there is an instance of a UserFriendRequest
	 * Mapper
	 * 
	 * @return UserFriendRequestMapper the singleton instance of this class
	 */
	public static UserFriendRequestMapper getInstance()
	{
		if (userFriendRequestMapper == null)
		{
			userFriendRequestMapper = new UserFriendRequestMapper();
		}
		return userFriendRequestMapper;
	}

	/**
	 * The Hash Map that stores the outgoing friend request list.  The keys are the usernames
	 * and they map to the list of friend requests they have sent.
	 */
	public static Map<String, List<FriendRequest>> OutgoingFriendRequestsList = new HashMap<String, List<FriendRequest>>();
	
	/**
	 * The Hash Map that stores the outgoing friend request list.  The keys are the usernames
	 * and they map to the list of friend requests they have been sent.
	 */
	public static Map<String, List<FriendRequest>> IncomingFriendRequestsList = new HashMap<String, List<FriendRequest>>();

	/**
	 * Returns the list outgoing friend requests for the user in the domain
	 * layer and in the database
	 * 
	 * @param user the user that needs outgoing friend requests loaded
	 * @return ArrayList<String> the list of users that have been sent friend requests from this user
	 */
	public static ArrayList<FriendRequest> getAllOutgoingFriendRequests(Person user)
	{
		if (!OutgoingFriendRequestsList.containsKey(user.getUsername()))
			loadFriendRequestsList(user);
		return (ArrayList<FriendRequest>) OutgoingFriendRequestsList.get(user.getUsername());
	}

	/**
	 * Returns the list incoming friend requests for the user in the domain
	 * layer and in the database
	 * 
	 * @param user the user that needs incoming friend requests loaded
	 * @return ArrayList<String> the list of users that have sent the user a friend request
	 */
	public static ArrayList<FriendRequest> getAllIncomingFriendRequests(Person user)
	{
		if (!IncomingFriendRequestsList.containsKey(user.getUsername()))
			loadFriendRequestsList(user);
		return (ArrayList<FriendRequest>) IncomingFriendRequestsList.get(user.getUsername());
	}
	
	/**
	 * Loads this Data Mapper with the list of friend requests associated with the
	 * given user in the parameter
	 * @param user the user that needs their friend requests loaded
	 */
	public static void loadFriendRequestsList(Person user)
	{
		OutgoingFriendRequestsList.put(user.getUsername(), new ArrayList<FriendRequest>());
		IncomingFriendRequestsList.put(user.getUsername(), new ArrayList<FriendRequest>());
		ResultSet results = UserFriendRequestGateway.findOutgoingFriendRequests(user.getUsername());
		ResultSet results2 = UserFriendRequestGateway.findIncomingFriendRequests(user.getUsername());
		try
		{
			while (results.next())
				OutgoingFriendRequestsList.get(user.getUsername()).add(
						new FriendRequest(user.getUsername(), user.getFullname(), results.getString(results.findColumn("Requestee")), results.getString(results.findColumn("RequesteeDisplayName"))));
			while (results2.next())
				IncomingFriendRequestsList.get(user.getUsername()).add(
						new FriendRequest(results2.getString(results2.findColumn("Requester")), results2.getString(results2.findColumn("RequesterDisplayName")), user.getUsername(), user.getFullname()));
		} catch (SQLException e)
		{
			System.out.println("Error in loadFriendRequestsList!");
			e.printStackTrace();
		}
	}

	/**
	 * Removes the outgoing friend request from the domain layer and the
	 * database
	 * 
	 * @param user the user that sent the friend request
	 * @param friendUsername the name of the user that was sent a friend request
	 */
	public static void removeOutgoingFriendRequest(Person user, String friendUsername)
	{
		UserFriendRequestGateway.removeFriendRequest(user.getUsername(), friendUsername);
		OutgoingFriendRequestsList.get(user.getUsername()).remove(friendUsername);
	}

	/**
	 * Inserts the friend request into the domain layer and the database
	 * @param user the user sending the friend request
	 * @param fr the friend request we are attempting to add
	 * @return true if the request was successfully created or false if not
	 */
	public static boolean insertFriendRequest(Person user, FriendRequest fr)
	{
		if (!OutgoingFriendRequestsList.containsKey(user.getUsername()) || !IncomingFriendRequestsList.containsKey(user.getUsername()))
			loadFriendRequestsList(user);
		
		if (!OutgoingFriendRequestsList.get(user.getUsername()).contains(fr.getReceiver()) &&
				!IncomingFriendRequestsList.get(user.getUsername())
				.contains(fr.getReceiver()))
		{
			UserFriendRequestGateway.insertFriendRequest(user.getUsername(), fr.getReceiver(), user.getFullname(), fr.getReceiverDisplayName());
			OutgoingFriendRequestsList.get(user.getUsername()).add(fr);
			IncomingFriendRequestsList.get(user.getUsername()).add(fr);
			return true;
		}
		return false;
	}
}
