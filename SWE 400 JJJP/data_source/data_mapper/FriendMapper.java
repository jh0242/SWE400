package data_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data_gateway.FriendGateway;
import data_gateway.PersonGateway;
import domain_model.Friend;
import domain_model.Person;
/**
 * @author Joshua McMillen
 *
 */
public class FriendMapper
{
	
	private static FriendMapper friendMapper;
	
	private FriendMapper(){}
	
	public static FriendMapper getInstance()
	{
		if(friendMapper == null)
		{
			friendMapper = new FriendMapper();
		}
		return friendMapper;
	}
	
	private static Map<Integer, List<Friend>> friendsList = new HashMap<Integer,List<Friend>>();
	
	/**
	 * Get complete List of User's Friends
	 * @param userID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Friend> getAllFriends(Person user)
	{	
		if(!friendsList.containsKey(user.getID()))
		{
			try{
				ArrayList<Friend> list = new ArrayList<Friend>();
				ResultSet results = FriendGateway.getFriends(user.getID());
				while(results.next())
				{
					Friend friend;
					if(results.getInt(1)!= user.getID())
					{	
						friend = new Friend(FriendMapper.getFriendName(results.getInt(1), user));
						friend.setID(results.getInt(1));
						
					}else
					{
						friend = new Friend(FriendMapper.getFriendName(results.getInt(2), user));
						friend.setID(results.getInt(2));
					}
					list.add(friend);
				}
				friendsList.put(user.getID(), list);
			}catch (SQLException e){
				System.out.println("Friends Could Not Be Reached");
			}
		}
		return (ArrayList<Friend>) friendsList.get(user.getID());
	}
	
	/**
	 * Removes Friend from both database and domain	
	 * @param userID
	 * @param friend
	 */
	@SuppressWarnings("unchecked")
	public static boolean removeFriend(Person user, int friendID)
	{
		try{
			FriendGateway.removeFriendship(user.getID(),friendID);
			if(!user.getFriends().isEmpty()){
				for(int i=0;i<user.getFriends().size();i++)
				{
					if(user.getFriends().get(i).getID()==friendID)
					{
						user.getFriends().remove(i);
					}
				}
			}
			if(friendsList.containsKey(user.getID()))
			{
				ArrayList<Friend> list = (ArrayList<Friend>) friendsList.get(user.getID());
				for(int i=0;i<list.size();i++)
				{
					if(list.get(i).getID() == friendID)
					{
						list.remove(i);
					}
				}
				friendsList.put(user.getID(), list);
			}
			return true;
		} catch (SQLException e){
			System.out.println("Could Not Remove Friend");			
		}
		return false;
	}
	
	/**
	 * Checks User Object, then FriendsList object, then Database for friendID
	 * @param friendID
	 * @param person
	 * @return
	 * @throws SQLException
	 */
	public static String getFriendName(int friendID,Person person) throws SQLException
	{
		if(!person.getFriends().isEmpty())
		{
			for(int i=0;i<person.getFriends().size();i++)
			{
				if(person.getFriends().get(i).getID()==friendID)
				{
					return person.getFriends().get(i).getDisplayName();
				}
			}
		}else if(friendsList.containsKey(person.getID())){
			ArrayList<Friend> list = (ArrayList<Friend>) friendsList.get(person.getID());
			for(int i=0;i<list.size();i++)
			{
				if(list.get(i).getID()==friendID)
				{
					return list.get(i).getDisplayName();
				}
			}
		}		
		ResultSet result = PersonGateway.getUserName(friendID);
		return result.getString(1);
	}
}