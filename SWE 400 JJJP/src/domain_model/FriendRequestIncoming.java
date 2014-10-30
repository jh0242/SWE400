package domain_model;

import java.util.ArrayList;

public class FriendRequestIncoming {

	private ArrayList<FriendRequest> receiver;
	private String userName;
	
	/**
	 * Constructor for Outgoing Friend Requests using ArrayLists 
	 * @param 
	 * @param incomingList
	 */
	public FriendRequestIncoming(String userName, ArrayList<FriendRequest> incomingList) {
		this.userName = userName;
		receiver = incomingList;
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
	public ArrayList<FriendRequest> getReceiver() {
		return receiver;
	}
}
