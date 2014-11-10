package data_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data_gateway.FriendGateway;
import domain_model.Friend;
import domain_model.Person;
/**
 * @author Joshua McMillen
 * Checks against Domain and Database to return data requested about a user's friends
 */
public class FriendMapper
{
		
	private static FriendMapper friendMapper;
	private static Map<String, List<Friend>> friendsList = new HashMap<String,List<Friend>>();
	
	private FriendMapper()
	{
		
	}
	
	/**
	 * @return instance of friendMapper
	 */
	public static FriendMapper getInstance()
	{
		if(friendMapper == null)
		{
			friendMapper = new FriendMapper();
		}
		return friendMapper;
	}	
	
	/**
	 * Get complete List of User's Friends
	 * @param user Unique user object from PersonMapper
	 * @return ArrayList of Friends
	 */
	public ArrayList<Friend> getAllFriends(Person user)
	{
		if(!friendsList.containsKey(user.getUsername()))
		{
			try{
				ArrayList<Friend> list = new ArrayList<Friend>();
				ResultSet results = FriendGateway.getFriends(user.getUsername());
				while(results.next())
				{
					Friend friend;
					if(results.getString(results.findColumn("UserNameA")).equals(user.getUsername()))
					{	
						friend = new Friend(results.getString(results.findColumn("UserNameB")), results.getString(results.findColumn("UserDisplayNameB")));
						
					}else
					{
						friend = new Friend(results.getString(results.findColumn("UserNameA")), results.getString(results.findColumn("UserDisplayNameA")));
					}
					list.add(friend);
				}
				friendsList.put(user.getUsername(), list);
			}catch (SQLException e){
				System.out.println("Friends Could Not Be Reached");
			}
		}
		return (ArrayList<Friend>) friendsList.get(user.getUsername());
	}
	
	/**
	 * Removes Friend from both database and domain	
	 * @param user unique user object from PersonMapper
	 * @param friendUserName requested id of Friend
	 * @return true upon completion of try, false if not 
	 */
	public boolean removeFriend(Person user, String friendUserName)
	{
		
		if(!user.getFriends().isEmpty()){
			for(int i=0;i<user.getFriends().size();i++)
			{
				if(user.getFriends().get(i).getUserName().equals(friendUserName))
				{
					user.getFriends().remove(i);
				}
			}
		}
		if(friendsList.containsKey(user.getUsername()))
		{
			ArrayList<Friend> list = (ArrayList<Friend>) friendsList.get(user.getUsername());
			for(int i=0;i<list.size();i++)
			{
				if(list.get(i).getUserName().equals(friendUserName))
				{
					list.remove(i);
				}
			}
			friendsList.put(user.getUsername(), list);
		}
		return FriendGateway.removeFriendship(user.getUsername(),friendUserName);
	}
	
	/** 
	 * Stores a given Friend into appropriate locations in Domain and Database
	 * @param person Unique User from PersonMapper
	 * @param friend Unique Friend from with Unique Friend from User's FriendList
	 * @return True upon completion, False if DB Insert Fails
	 */
	public static boolean saveFriend(Person person, Friend friend)
	{
		person.addFriend(friend);
		ArrayList<Friend> list = person.getFriends();
		if(!list.contains(friend))
		{
			list.add(friend);
			friendsList.put(person.getUsername(), list);
			if (!friendsList.containsKey(friend.getUserName()))
				friendsList.put(friend.getUserName(), new ArrayList<Friend>());
			friendsList.get(friend.getUserName()).add(friend);
		}			
		return FriendGateway.insertFriend(person.getUsername(),friend.getUserName(), person.getFullname(), friend.getDisplayName());
	}

	public static boolean removeUserFromHashMap(String userName) 
	{
		Map<String, List<Friend>> map = friendsList;
		if (friendsList.containsKey(userName))
		{
			friendsList.remove(userName);
			return true;
		}
		return false;
	}

	public void updateDisplayName(String username, String fullname) 
	{
		FriendGateway.updateDisplayName(username, fullname);
		Iterator<String> it = friendsList.keySet().iterator();
		while (it.hasNext())
		{
			for (Friend fr: friendsList.get(it.next()))
				if (fr.getUserName().equals(username))
					fr.setDisplayName(fullname);
		}
	}
}