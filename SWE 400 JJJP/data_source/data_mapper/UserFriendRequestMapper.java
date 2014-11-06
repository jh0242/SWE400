package data_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	 * @return
	 */
	public static UserFriendRequestMapper getInstance()
	{
		if (userFriendRequestMapper == null)
		{
			userFriendRequestMapper = new UserFriendRequestMapper();
		}
		return userFriendRequestMapper;
	}

	// Hash Map that stores the friend request list
	private static Map<String, List<String>> FriendRequestsList = new HashMap<String, List<String>>();

	/**
	 * Returns the list outgoing friend requests for the user in the domain
	 * layer and in the database
	 * 
	 * @param user
	 * @return
	 */
	public static ArrayList<String> getAllOutgoingFriendRequests(Person user)
	{
		if (!FriendRequestsList.containsKey(user.getUsername()))
			loadFriendRequestsList(user);
		return (ArrayList<String>) FriendRequestsList.get(user.getUsername());
	}
	
	/**
	 * Like getAllOutgoingFriendRequests, but returns the object form instead of just strings.
	 * @param user The session person
	 * @return An arraylist of FriendRequests.
	 */
	public static ArrayList<FriendRequest> getAllOutgoingFriendRequestObjects(Person user) {
		ArrayList<String> outgoingFriendRequestUsernames = getAllOutgoingFriendRequests(user);
		ArrayList<FriendRequest> fr = new ArrayList<>();
		Iterator<String> i = outgoingFriendRequestUsernames.iterator();
		while (i.hasNext()) {
			String x = i.next();
			fr.add(new FriendRequest(user.getUsername(), x));
		}
		return fr;
	}
	
	/**
	 * Like getAllIncomingFriendRequests, but returns the object form instead of just strings.
	 * @param user The session person.
	 * @return An arraylist of FriendRequests.
	 */
	public static ArrayList<FriendRequest> getAllIncomingFriendRequestObjects(Person user) {
		ArrayList<String> incomingFriendRequestUsernames = getAllIncomingFriendRequests(user);
		ArrayList<FriendRequest> fr = new ArrayList<>();
		Iterator<String> i = incomingFriendRequestUsernames.iterator();
		while (i.hasNext()) {
			String x = i.next();
			fr.add(new FriendRequest(x, user.getUsername()));
		}
		return fr;
	}

	/**
	 * Returns the list outgoing friend requests for the user in the domain
	 * layer and in the database
	 * 
	 * @param user
	 * @return
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
	 * @param user
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
	 * @param user
	 * @param friendUsername
	 * @return
	 */
	public static void removeOutgoingFriendRequest(Person user, String friendUsername)
	{
		UserFriendRequestGateway.removeFriendRequest(user.getUsername(), friendUsername);
		FriendRequestsList.get(user.getUsername()).remove(friendUsername);
	}

	/**
	 * Inserts the friend request into the domain layer and the database
	 * 
	 * @param user
	 * @param friendRequestUsername
	 * @return
	 */
	public static boolean insertFriendRequest(Person user, String friendRequestUsername)
	{
		if (!FriendRequestsList.containsKey(user.getUsername()))
			loadFriendRequestsList(user);
		
		if (!FriendRequestsList.get(user.getUsername()).contains(friendRequestUsername))
		{
			UserFriendRequestGateway.insertFriendRequest(user.getUsername(), friendRequestUsername);
			FriendRequestsList.get(user.getUsername()).add(friendRequestUsername);
			return true;
		}
		return false;
	}
}
