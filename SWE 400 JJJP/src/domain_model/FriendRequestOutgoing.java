package domain_model;

import java.util.ArrayList;

public class FriendRequestOutgoing {

	private ArrayList<FriendRequest> sender;
	private String userName;
	
	/**
	 * Constructor for Outgoing Friend Requests using ArrayLists 
	 * @param 
	 * @param incomingList
	 */
	public FriendRequestOutgoing(String userName, ArrayList<FriendRequest> outgoingList) {
		this.userName = userName;
		sender = outgoingList;
	}

	/**
	 * Getter for userID
	 * @return The user's userName
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * Getter for sender
	 * @return sender
	 */
	public ArrayList<FriendRequest> getSender() {
		return sender;
	}
}
