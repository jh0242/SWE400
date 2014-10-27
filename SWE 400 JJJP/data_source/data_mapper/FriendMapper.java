package data_mapper;

import java.sql.SQLException;
import java.util.ArrayList;

import data_gateway.FriendGateway;
/**
 * @author Joshua McMillen
 *
 */
public class FriendMapper
{
	ArrayList<String> friends;
	boolean completeList = false;
	
	/**
	 * Get complete List of User's Friends
	 * @param userID
	 * @return
	 */
	public String getAllFriends(int userID)
	{		
		if(!completeList){
			try{
				friends = FriendGateway.getFriends(userID);
				completeList = true;
			}catch (SQLException e){
				System.out.println("Friends Could Not Be Reached");
			}
		}return giveList();
	}
	
	/**
	 * Removes Friend from both database and domain	
	 * @param userID
	 * @param friend
	 */
	public void removeFriend(int userID, int friendID)
	{
		try{
			FriendGateway.removeFriendship(userID,friendID);
			if(friends != null){
				friends.remove(friendID);
			}
		} catch (SQLException e){
			System.out.println("Could Not Remove Friend");
		}
	}
	
	/**
	 * Check if Friend has been loaded already
	 * @param userID
	 * @return
	 */
	public boolean checkMap(String userID)
	{
		if(friends.contains(userID)){
			return true;
		}return false;
	}
	
	//Return friends as comma seperated list
	private String giveList()
	{
		String allFriends = "";
		for(int i = 0; i < friends.size(); i++){
			allFriends += friends.get(i) + ",";
		}
		return allFriends.substring(0,allFriends.length()-1);
	}
}
