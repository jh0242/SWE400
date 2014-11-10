package data_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data_gateway.FriendGateway;
import data_gateway.UserFriendRequestGateway;
import domain_model.DomainObject;
import domain_model.Friend;
import domain_model.FriendRequest;
import domain_model.Person;

/**
 * Mapper class that connects the SQl commands in the FriendRequestGateway
 * to the FriendRequest and Person class in the domain layer
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
	 * Checks to make sure that there is an instance of a UserFriendRequestMapper
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
	private static Map<String, List<FriendRequest>> OutgoingFriendRequestsList = new HashMap<String, List<FriendRequest>>();
	
	/**
	 * The Hash Map that stores the incoming friend request list.  The keys are the usernames
	 * and they map to the list of friend requests that they are receiving.
	 */
	private static Map<String, List<FriendRequest>> IncomingFriendRequestsList = new HashMap<String, List<FriendRequest>>();

	/**
	 * Returns the list of outgoing friend requests for the user in the domain
	 * layer and in the database
	 * 
	 * @param user the user that needs outgoing friend requests loaded
	 * @return ArrayList<String> the list of users that have been sent friend requests from this user
	 */
	public static ArrayList<FriendRequest> getAllOutgoingFriendRequests(Person user)
	{
		clear();
		loadFriendRequestsList(user);
		ArrayList<FriendRequest> list = new ArrayList<FriendRequest>();
		list.addAll(OutgoingFriendRequestsList.get(user.getUsername()));
		return list;
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
		clear();
		loadFriendRequestsList(user);
		ArrayList<FriendRequest> list = new ArrayList<FriendRequest>();
		list.addAll(IncomingFriendRequestsList.get(user.getUsername()));
		return list;
	}
	
	/**
	 * Loads this Data Mapper with the list of friend requests associated with the
	 * given user in the parameter
	 * 
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
			{
				String requestee = results.getString(results.findColumn("Requestee"));
				String requesteeDisplay = results.getString(results.findColumn("RequesteeDisplayName"));
				FriendRequest fr = new FriendRequest(user.getUsername(), user.getFullname(), requestee, requesteeDisplay);
				OutgoingFriendRequestsList.get(user.getUsername()).add(fr);
				IncomingFriendRequestsList.put(requestee, new ArrayList<FriendRequest>());
				IncomingFriendRequestsList.get(requestee).add(fr);
			}
			while (results2.next())
			{
				String requester = results2.getString(results2.findColumn("Requester"));
				String requesterDisplay = results2.getString(results2.findColumn("RequesterDisplayName"));
				FriendRequest fr = new FriendRequest(requester, requesterDisplay, user.getUsername(), user.getFullname());
				IncomingFriendRequestsList.get(user.getUsername()).add(fr);
				OutgoingFriendRequestsList.put(requester, new ArrayList<FriendRequest>());
				OutgoingFriendRequestsList.get(requester).add(fr);
			}
		} catch (SQLException e)
		{
			System.out.println("Error in loadFriendRequestsList!");
			e.printStackTrace();
		}
	}

	/**
	 * Removes the outgoing friend request from the domain layer and the
	 * database
	 * @param sender the person that sent the friend request
	 * @param receiver the person that is the intended receiver of the request
	 */
	public static void removeFriendRequest(String sender, String receiver)
	{
		UserFriendRequestGateway.removeFriendRequest(sender, receiver);
		Iterator<FriendRequest> it = OutgoingFriendRequestsList.get(sender).iterator();
		FriendRequest fr = null;
		boolean removed = false;
		while (!removed && it.hasNext())
		{
			fr = it.next();
			if (fr.getReceiver().equals(receiver))
			{
				OutgoingFriendRequestsList.get(sender).remove(fr);
				removed = true;
			}
		}
		it = IncomingFriendRequestsList.get(receiver).iterator();
		removed = false;
		while (!removed && it.hasNext())
		{
			fr = it.next();
			if (fr.getSender().equals(sender))
			{
				IncomingFriendRequestsList.get(receiver).remove(fr);
				removed = true;
			}
		}
	}

	/**
	 * Inserts the friend request into the domain layer and the database
	 * 
	 * @param user the user sending the friend request
	 * @param fr the friend request we are attempting to add
	 * @return true if the request was successfully created or false if not
	 */
	public static boolean insertFriendRequest(Person user, FriendRequest fr)
	{
		if (!OutgoingFriendRequestsList.containsKey(user.getUsername()) && !IncomingFriendRequestsList.containsKey(user.getUsername()))
			loadFriendRequestsList(user);
		
		if (!friendRequestExists(fr))
		{
			UserFriendRequestGateway.insertFriendRequest(user.getUsername(), fr.getReceiver(), user.getFullname(), fr.getReceiverDisplayName());
			if (!OutgoingFriendRequestsList.containsKey(user.getUsername()))
				OutgoingFriendRequestsList.put(user.getUsername(), new ArrayList<FriendRequest>());
			OutgoingFriendRequestsList.get(user.getUsername()).add(fr);
			IncomingFriendRequestsList.put(fr.getReceiver(), new ArrayList<FriendRequest>());
			IncomingFriendRequestsList.get(fr.getReceiver()).add(fr);
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the friend request is present in the either the OutgoingFriendRequestList 
	 * or the IncomingFriendRequestList, if friend request is present exists returns true
	 * 
	 * @param fr the FriendRequest we are checking if existing in the hash map
	 * @return exists true if it exists, false otherwise
	 */
	public static boolean friendRequestExists(FriendRequest fr)
	{
		boolean exists = false;
		if (OutgoingFriendRequestsList.containsKey(fr.getSender()))
			for (FriendRequest f: OutgoingFriendRequestsList.get(fr.getSender()))
				if ((f.getSender() == fr.getSender() && f.getReceiver() == fr.getReceiver()) || 
						(f.getReceiver() == fr.getSender() && f.getSender() == fr.getReceiver()))
					exists = true;
		if (IncomingFriendRequestsList.containsKey(fr.getSender()))
			for (FriendRequest f: IncomingFriendRequestsList.get(fr.getSender()))
				if ((f.getSender() == fr.getSender() && f.getReceiver() == fr.getReceiver()) || 
						(f.getReceiver() == fr.getSender() && f.getSender() == fr.getReceiver()))
					exists = true;
			
		return exists;
	}

	/**
	 * Clears the Outgoing and Incoming hash maps.
	 */
	public static void clear() 
	{
		OutgoingFriendRequestsList.clear();
		IncomingFriendRequestsList.clear();
	}

	/**
	 * Updates the display name of the given user in the PENDINGFRIENDREQUESTS table
	 * and in the hash maps.
	 * @param username the user that is being updated
	 * @param fullname the new display name
	 */
	public void updateDisplayname(String username, String fullname) 
	{
		UserFriendRequestGateway.updateDisplayName(username, fullname);
		Iterator<String> it = OutgoingFriendRequestsList.keySet().iterator();
		while (it.hasNext())
		{
			for (FriendRequest fr: OutgoingFriendRequestsList.get(it.next()))
				if (fr.getReceiver().equals(username))
					fr.setReceiverDisplayName(fullname);
		}
		it = IncomingFriendRequestsList.keySet().iterator();
		while (it.hasNext())
		{
			for (FriendRequest fr: IncomingFriendRequestsList.get(it.next()))
				if (fr.getSender().equals(username))
					fr.setSenderDisplayName(fullname);
		}
	}
}
