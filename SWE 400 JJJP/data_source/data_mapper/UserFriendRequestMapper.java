package data_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data_gateway.UserFriendRequestGateway;
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
	 * The Hash Map that stores the friend friend request list
	 */
	public static Map<String, List<String>> FriendRequestsList = new HashMap<String, List<String>>();

	/**
	 * Returns the list outgoing friend requests for the user in the domain
	 * layer and in the database
	 * 
	 * @param user the user that needs outgoing friend requests loaded
	 * @return ArrayList<String> the list of users that have been sent friend requests from this user
	 */
	public static ArrayList<String> getAllOutgoingFriendRequests(Person user)
	{
		if (!FriendRequestsList.containsKey(user.getUsername()))
			loadFriendRequestsList(user);
		return (ArrayList<String>) FriendRequestsList.get(user.getUsername());
	}

	/**
	 * Returns the list incoming friend requests for the user in the domain
	 * layer and in the database
	 * 
	 * @param user the user that needs incoming friend requests loaded
	 * @return ArrayList<String> the list of users that have sent the user a friend request
	 */
	public static ArrayList<String> getAllIncomingFriendRequests(Person user)
	{
		ArrayList<String> incomingFriendRequests = new ArrayList<String>();
		String name = user.getUsername();
		Iterator<String> list = FriendRequestsList.keySet().iterator();
		while (list.hasNext())
		{
			String key = list.next();
			if (!key.equals(name) && FriendRequestsList.get(key).contains(name))
				incomingFriendRequests.add(key);
		}
		return incomingFriendRequests;
	}
	
	/**
	 * Loads this Data Mapper with the list of friend requests associated with the
	 * given user in the parameter
	 * @param user the user that needs their friend requests loaded
	 */
	public static void loadFriendRequestsList(Person user)
	{
		FriendRequestsList.put(user.getUsername(), new ArrayList<String>());
		ResultSet results = UserFriendRequestGateway.findOutgoingFriendRequests(user.getUsername());
		try
		{
			while (results.next())
				FriendRequestsList.get(user.getUsername()).add(results.getString(results.findColumn("Requestee")));
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
		FriendRequestsList.get(user.getUsername()).remove(friendUsername);
	}

	/**
	 * Inserts the friend request into the domain layer and the database
	 * @param user the user sending the friend request
	 * @param friendRequestUsername the user name of the user receiving the request
	 * @return true if the request was successfully created or false if not
	 */
	public static boolean insertFriendRequest(Person user, String friendRequestUsername)
	{
		if (!FriendRequestsList.containsKey(user.getUsername()))
			loadFriendRequestsList(user);
		
		if (!FriendRequestsList.get(user.getUsername()).contains(friendRequestUsername)
				&& (!FriendRequestsList.containsKey(friendRequestUsername) || !FriendRequestsList.get(friendRequestUsername).contains(user.getUsername())))
		{
			UserFriendRequestGateway.insertFriendRequest(user.getUsername(), friendRequestUsername);
			FriendRequestsList.get(user.getUsername()).add(friendRequestUsername);
			return true;
		}
		return false;
	}
}
