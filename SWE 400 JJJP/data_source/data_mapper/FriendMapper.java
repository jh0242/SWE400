package data_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	/**
	 * Get complete List of User's Friends
	 * @param userID
	 * @return
	 */
	public static boolean getAllFriends(Person user)
	{		
		try{
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
				user.addFriend(friend);
			}
		}catch (SQLException e){
			System.out.println("Friends Could Not Be Reached");
			return false;
		}
		return true;
	}
	
	/**
	 * Removes Friend from both database and domain	
	 * @param userID
	 * @param friend
	 */
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
			return true;
		} catch (SQLException e){
			System.out.println("Could Not Remove Friend");			
		}
		return false;
	}
	
	public static String getFriendName(int friendID,Person person) throws SQLException
	{
		for(int i=0;i<person.getFriends().size();i++)
		{
			if(person.getFriends().get(i).getID()==friendID)
			{
				return person.getFriends().get(i).getFullname();
			}
		}
		ResultSet result = PersonGateway.getUserName(friendID);
		return result.getString(1);
	}
}
