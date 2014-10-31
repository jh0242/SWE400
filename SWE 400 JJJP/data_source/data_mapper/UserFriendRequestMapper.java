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
import domain_model.FriendRequestIncoming;
import domain_model.FriendRequestOutgoing;
import domain_model.Person;

/**
 * Mapper for the FriendRequestGateway
 * 
 * @author John Terry
 * @author Joshua McMillen
 */

public class UserFriendRequestMapper {
	
	private static UserFriendRequestMapper userFriendRequestMapper;
	
	private UserFriendRequestMapper(){}
	
	/**
	 * Checks to make sure that there is an instance of a UserFriendRequest Mapper
	 * @return
	 */
	public static UserFriendRequestMapper getInstance()
	{
		if(userFriendRequestMapper == null)
		{
			userFriendRequestMapper = new UserFriendRequestMapper();
		}
		return userFriendRequestMapper;
	}
	
	// Hash Map that stores the friend request list
	private static Map<Integer, List<FriendRequest>> FriendRequestsList = new HashMap<Integer,List<FriendRequest>>();
	
	/**
	 * Returns the list outgoing friend requests for the user in the domain layer and in the database
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<FriendRequest> getAllFriendRequests(Person user) throws SQLException
	{	
		if(!FriendRequestsList.containsKey(user.getID()))
		{
			ArrayList<FriendRequest> List = new ArrayList<FriendRequest>();
			ResultSet results = UserFriendRequestGateway.findOutgoingFriendRequests(user.getID());
			while(results.next())
			{
				FriendRequest friendRequest;
				if(results.getInt(1)!= user.getID())
				{	
					friendRequest = new FriendRequest(UserFriendRequestMapper.getSenderName(results.getInt(1), user), List);
					friendRequest.setID(results.getInt(1));
					
				}else
				{
					friendRequest = new FriendRequest(UserFriendRequestMapper.getSenderName(results.getInt(2), user), List);
					friendRequest.setID(results.getInt(2));
				}
				List.add(friendRequest);
			}
				FriendRequestsList.put(user.getID(), List);
		
		return (ArrayList<FriendRequest>) FriendRequestsList.get(user.getID());
		}
	}
	
	/**
	 * Removes the outgoing friend request from the domain layer and the database
	 * @param user
	 * @param friendRequestID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean removeOutgoingFriendRequest(Person user, int friendRequestID)
	{
		try{
			UserFriendRequestGateway.removeFriendRequest(user.getID(),friendRequestID);
			if(!user.getFriendRequests().isEmpty()){
				for(int i=0;i<user.getFriends().size();i++)
				{
					if(user.getFriendRequests().get(i).getID()==friendRequestID)
					{
						user.getFriendRequests().remove(i);
					}
				}
			}
			if(FriendRequestsList.containsKey(user.getID()))
			{
				ArrayList<FriendRequest> list = (ArrayList<FriendRequest>) FriendRequestsList.get(user.getID());
				for(int i=0;i<list.size();i++)
				{
					if(list.get(i).getID() == friendRequestID)
					{
						list.remove(i);
					}
				}
				FriendRequestsList.put(user.getID(), list);
			}
			return true;
		} catch (SQLException e){
			System.out.println("Could Not Remove Outgoing Friend Request");			
		}
		return false;
	}
	
	/**
	 * Inserts the friend request into the domain layer and the database
	 * @param user
	 * @param friendRequestID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean insertFriendRequest(Person user, int friendRequestID)
	{
		try{
			UserFriendRequestGateway.insertFriendRequest(user.getID(),friendRequestID);
			if(!user.getFriendRequestsOutgoing().contains(friendRequestID)){
				for(int i=0;i<user.getFriendRequestsOutgoing().size();i++)
				{
					if(user.getFriendRequestsOutgoing().get(i).getID()==friendRequestID)
					{
						user.getFriendRequestsOutgoing().add(i, (FriendRequest) FriendRequestsList);
					}
				}
			}
			if(FriendRequestsList.containsKey(user.getID()))
			{
				ArrayList<FriendRequest> list = (ArrayList<FriendRequest>) FriendRequestsList.get(user.getID());
				for(int i=0;i<list.size();i++)
				{
					if(list.get(i).getID() == friendRequestID)
					{
						list.add(i, (FriendRequest) FriendRequestsList);
					}
				}
				FriendRequestsList.put(user.getID(), list);
			}
			return true;
		} catch (SQLException e){
			System.out.println("Could Not Insert Outgoing Friend Request");			
		}
		return false;
	}
	
	/**
	 * Checks that the senderID exists in the User object, friendsList object and the database
	 * @param senderID
	 * @param person
	 * @return
	 * @throws SQLException
	 */
	public static String getSenderName(int senderID,Person person) throws SQLException
	{
		if(!person.getFriendRequests().isEmpty())
		{
			for(int i=0;i<person.getFriendRequests().size();i++)
			{
				if(person.getFriendRequests().get(i).getID()==senderID)
				{
					return person.getFriendRequests().get(i).getSender();
				}
			}
		}else if(FriendRequestsList.containsKey(person.getID())){
			ArrayList<FriendRequest> list = (ArrayList<FriendRequest>) FriendRequestsList.get(person.getID());
			for(int i=0;i<list.size();i++)
			{
				if(list.get(i).getID()==senderID)
				{
					return list.get(i).getSender();
				}
			}
		}		
		ResultSet result = PersonGateway.getUserName(senderID);
		return result.getString(1);
	}
}
