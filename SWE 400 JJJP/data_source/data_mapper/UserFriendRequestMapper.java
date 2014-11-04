package data_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data_gateway.FriendGateway;
import data_gateway.PersonGateway;
import data_gateway.UserFriendRequestGateway;
import domain_model.Friend;
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

	public static void loadFriendRequestsList(Person user)
	{
		FriendRequestsList.put(user.getUsername(), new ArrayList<String>());
		ResultSet results = UserFriendRequestGateway.findOutgoingFriendRequests(user.getUsername());
		try
		{
			while (results.next())
				FriendRequestsList.get(user.getUsername()).add(results.getString(results.findColumn("UserNameB")));
		} catch (SQLException e)
		{
			System.out.println("Error in getAllOutgoingFriendRequests!");
			e.printStackTrace();
		}
	}

	/**
	 * Removes the outgoing friend request from the domain layer and the
	 * database
	 * 
	 * @param user
	 * @param friendRequestID
	 * @return
	 */
	public static boolean removeOutgoingFriendRequest(Person user, int friendRequestID)
	{
		try
		{
			UserFriendRequestGateway.removeFriendRequest(user.getID(), friendRequestID);
			if (!user.getFriendRequests().isEmpty())
			{
				for (int i = 0; i < user.getFriends().size(); i++)
				{
					if (user.getFriendRequests().get(i).getID() == friendRequestID)
					{
						user.getFriendRequests().remove(i);
					}
				}
			}
			if (FriendRequestsList.containsKey(user.getID()))
			{
				ArrayList<FriendRequest> list = (ArrayList<FriendRequest>) FriendRequestsList.get(user.getID());
				for (int i = 0; i < list.size(); i++)
				{
					if (list.get(i).getID() == friendRequestID)
					{
						list.remove(i);
					}
				}
				FriendRequestsList.put(user.getID(), list);
			}
			return true;
		} catch (SQLException e)
		{
			System.out.println("Could Not Remove Outgoing Friend Request");
		}
		return false;
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
		
//		if (!user.getFriendRequestsOutgoing().contains(friendRequestUsername))
//		{
//			for (int i = 0; i < user.getFriendRequestsOutgoing().size(); i++)
//			{
//				if (user.getFriendRequestsOutgoing().get(i).getReceiver() == friendRequestUsername)
//				{
//					user.getFriendRequestsOutgoing().add(i, (FriendRequest) FriendRequestsList);
//				}
//			}
//		}
//		if (FriendRequestsList.containsKey(user.getUsername()))
//		{
//			ArrayList<FriendRequest> list = (ArrayList<FriendRequest>) FriendRequestsList.get(user.getUsername());
//			for (int i = 0; i < list.size(); i++)
//			{
//				if (list.get(i).getReceiver() == friendRequestUsername)
//				{
//					list.add(i, (FriendRequest) FriendRequestsList);
//				}
//			}
//			FriendRequestsList.put(user.getUsername(), list);
//		}
//		return true;
//		return false;
	}

	/**
	 * Checks that the senderID exists in the User object, friendsList object
	 * and the database
	 * 
	 * @param senderID
	 * @param person
	 * @return
	 * @throws SQLException
	 */
	public static String getSenderName(int senderID, Person person)
	{
		if (!person.getFriendRequests().isEmpty())
		{
			for (int i = 0; i < person.getFriendRequests().size(); i++)
			{
				if (person.getFriendRequests().get(i).getID() == senderID)
				{
					return person.getFriendRequests().get(i).getSender();
				}
			}
		} else if (FriendRequestsList.containsKey(person.getID()))
		{
			ArrayList<FriendRequest> list = (ArrayList<FriendRequest>) FriendRequestsList.get(person.getID());
			for (int i = 0; i < list.size(); i++)
			{
				if (list.get(i).getID() == senderID)
				{
					return list.get(i).getSender();
				}
			}
		}
		ResultSet result = PersonGateway.getUsername(senderID);
		return result.getString(1);
	}
}
