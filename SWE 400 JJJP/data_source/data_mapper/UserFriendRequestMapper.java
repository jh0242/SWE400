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
	
	// Hash Map that stores the incoming friend requests for the user
	private static Map<Integer, List<FriendRequest>> incomingFriendRequestsList = new HashMap<Integer,List<FriendRequest>>();
	// Hash Map that stores the outgoing friend requests for the user
	private static Map<Integer, List<FriendRequest>> outgoingFriendRequestsList = new HashMap<Integer,List<FriendRequest>>();
	
	/**
	 * Returns the list outgoing friend requests for the user in the domain layer and in the database
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<FriendRequest> getAllOutgoingFriendRequests(Person user)
	{	
		if(!outgoingFriendRequestsList.containsKey(user.getID()))
		{
			try{
				ArrayList<FriendRequest> outgoingList = new ArrayList<FriendRequest>();
				ResultSet results = UserFriendRequestGateway.findOutgoingFriendRequests(user.getID());
				while(results.next())
				{
					FriendRequestOutgoing friendRequest;
					if(results.getInt(1)!= user.getID())
					{	
						friendRequest = new FriendRequestOutgoing(UserFriendRequestMapper.getSenderName(results.getInt(1), user), outgoingList);
						friendRequest.setID(results.getInt(1));
						
					}else
					{
						friendRequest = new FriendRequestOutgoing(UserFriendRequestMapper.getSenderName(results.getInt(2), user), outgoingList);
						friendRequest.setID(results.getInt(2));
					}
					outgoingList.add(friendRequest);
				}
				outgoingFriendRequestsList.put(user.getID(), outgoingList);
			}catch (SQLException e){
				System.out.println("Outgoing Friend Requests Could Not Be Reached");
			}
		}
		return (ArrayList<FriendRequest>) outgoingFriendRequestsList.get(user.getID());
	}
	
	/**
	 * Returns a list of incoming friend requests for the user in the domain layer and in the database
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<FriendRequest> getAllIncomingFriendRequests(Person user)
	{	
		if(!incomingFriendRequestsList.containsKey(user.getID()))
		{
			try{
				ArrayList<FriendRequest> incomingList = new ArrayList<FriendRequest>();
				ResultSet results = UserFriendRequestGateway.findIncomingFriendRequests(user.getID());
				while(results.next())
				{
					FriendRequestIncoming friendRequest;
					if(results.getInt(1)!= user.getID())
					{	
						friendRequest = new FriendRequestIncoming(UserFriendRequestMapper.getReceiverName(results.getInt(1), user), incomingList);
						friendRequest.setID(results.getInt(1));
						
					}else
					{
						friendRequest = new FriendRequestIncoming(UserFriendRequestMapper.getReceiverName(results.getInt(2), user), incomingList);
						friendRequest.setID(results.getInt(2));
					}
					incomingList.add(friendRequest);
				}
				incomingFriendRequestsList.put(user.getID(), incomingList);
			}catch (SQLException e){
				System.out.println("Incoming Friend Requests Could Not Be Reached");
			}
		}
		return (ArrayList<FriendRequest>) incomingFriendRequestsList.get(user.getID());
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
			if(!user.getFriendRequestsOutgoing().isEmpty()){
				for(int i=0;i<user.getFriends().size();i++)
				{
					if(user.getFriendRequestsOutgoing().get(i).getID()==friendRequestID)
					{
						user.getFriendRequestsOutgoing().remove(i);
					}
				}
			}
			if(outgoingFriendRequestsList.containsKey(user.getID()))
			{
				ArrayList<FriendRequest> list = (ArrayList<FriendRequest>) outgoingFriendRequestsList.get(user.getID());
				for(int i=0;i<list.size();i++)
				{
					if(list.get(i).getID() == friendRequestID)
					{
						list.remove(i);
					}
				}
				outgoingFriendRequestsList.put(user.getID(), list);
			}
			return true;
		} catch (SQLException e){
			System.out.println("Could Not Remove Outgoing Friend Request");			
		}
		return false;
	}
	
	/**
	 * Removes the incoming friend request from the domain layer and the database
	 * @param user
	 * @param friendRequestID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean removeIncomingFriendRequest(Person user, int friendRequestID)
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
			if(incomingFriendRequestsList.containsKey(user.getID()))
			{
				ArrayList<FriendRequest> list = (ArrayList<FriendRequest>) incomingFriendRequestsList.get(user.getID());
				for(int i=0;i<list.size();i++)
				{
					if(list.get(i).getID() == friendRequestID)
					{
						list.remove(i);
					}
				}
				incomingFriendRequestsList.put(user.getID(), list);
			}
			return true;
		} catch (SQLException e){
			System.out.println("Could Not Remove Incoming Friend Request");			
		}
		return false;
	}
	
	/**
	 * Inserts the incoming friend request from the domain layer and the database
	 * @param user
	 * @param friendRequestID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean insertOutgoingFriendRequest(Person user, int friendRequestID)
	{
		try{
			UserFriendRequestGateway.insertFriendRequest(user.getID(),friendRequestID);
			if(!user.getFriendRequestsOutgoing().contains(friendRequestID)){
				for(int i=0;i<user.getFriendRequestsOutgoing().size();i++)
				{
					if(user.getFriendRequestsOutgoing().get(i).getID()==friendRequestID)
					{
						user.getFriendRequestsOutgoing().add(i, (FriendRequest) outgoingFriendRequestsList);
					}
				}
			}
			if(incomingFriendRequestsList.containsKey(user.getID()))
			{
				ArrayList<FriendRequest> list = (ArrayList<FriendRequest>) outgoingFriendRequestsList.get(user.getID());
				for(int i=0;i<list.size();i++)
				{
					if(list.get(i).getID() == friendRequestID)
					{
						list.add(i, (FriendRequest) outgoingFriendRequestsList);
					}
				}
				outgoingFriendRequestsList.put(user.getID(), list);
			}
			return true;
		} catch (SQLException e){
			System.out.println("Could Not Insert Outgoing Friend Request");			
		}
		return false;
	}
	
	
	/**
	 * Inserts the incoming friend request from the domain layer and the database
	 * @param user
	 * @param friendRequestID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean insertIncomingFriendRequest(Person user, int friendRequestID)
	{
		try{
			UserFriendRequestGateway.insertFriendRequest(user.getID(),friendRequestID);
			if(!user.getFriendRequests().contains(friendRequestID)){
				for(int i=0;i<user.getFriendRequests().size();i++)
				{
					if(user.getFriendRequests().get(i).getID()==friendRequestID)
					{
						user.getFriendRequests().add(i, (FriendRequest) incomingFriendRequestsList);
					}
				}
			}
			if(incomingFriendRequestsList.containsKey(user.getID()))
			{
				ArrayList<FriendRequest> list = (ArrayList<FriendRequest>) incomingFriendRequestsList.get(user.getID());
				for(int i=0;i<list.size();i++)
				{
					if(list.get(i).getID() == friendRequestID)
					{
						list.add(i, (FriendRequest) incomingFriendRequestsList);
					}
				}
				incomingFriendRequestsList.put(user.getID(), list);
			}
			return true;
		} catch (SQLException e){
			System.out.println("Could Not Insert Incoming Friend Request");			
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
		}else if(outgoingFriendRequestsList.containsKey(person.getID())){
			ArrayList<FriendRequest> list = (ArrayList<FriendRequest>) outgoingFriendRequestsList.get(person.getID());
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
	
	/**
	 * Checks that the receiverID exists in the User object, friendsList object and the database
	 * @param receiverID
	 * @param person
	 * @return
	 * @throws SQLException
	 */
	public static String getReceiverName(int receiverID,Person person) throws SQLException
	{
		if(!person.getFriendRequests().isEmpty())
		{
			for(int i=0;i<person.getFriendRequests().size();i++)
			{
				if(person.getFriendRequests().get(i).getID()==receiverID)
				{
					return person.getFriendRequests().get(i).getReceiver();
				}
			}
		}else if(incomingFriendRequestsList.containsKey(person.getID())){
			ArrayList<FriendRequest> list = (ArrayList<FriendRequest>) incomingFriendRequestsList.get(person.getID());
			for(int i=0;i<list.size();i++)
			{
				if(list.get(i).getID()==receiverID)
				{
					return list.get(i).getSender();
				}
			}
		}		
		ResultSet result = PersonGateway.getUserName(receiverID);
		return result.getString(1);
	}
	
}
