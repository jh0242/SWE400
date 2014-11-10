package domain_model;

/**
 * @author Patrick Flanagan
 * A friend request has a sender and receiver.
 * It's an extremely simple mapping of username -> username.
 *
 */
public class FriendRequest extends DomainObject
{
	private String senderUsername;
	private String senderDisplayName;
	private String receiverUsername;
	private String receiverDisplayName;
	
	/**
	 * @param s Sender
	 * @param r Receiver
	 */
	public FriendRequest(String s, String r) {
		senderUsername = s;
		receiverUsername = r;
	}
	
	/**
	 * @param sUsername Sender Username
	 * @param sDisplayname Sender Displayname
	 * @param rUsername Receiver Username
	 * @param rDisplayname Receiver Displayname
	 */
	public FriendRequest(String sUsername, String sDisplayname, String rUsername, String rDisplayname) {
		senderUsername = sUsername;
		senderDisplayName = sDisplayname;
		receiverUsername = rUsername;
		receiverDisplayName = rDisplayname;
	}
	
	/**
	 * Getter for sender
	 * @return sender
	 */
	public String getSender() {
		return senderUsername;
	}
	
	/**
	 * Getter for sender's display name
	 * @return senderDisplayName
	 */
	public String getSenderDisplayName() {
		return senderDisplayName;
	}
	
	/**
	 * Getter for receiver
	 * @return receiver
	 */
	public String getReceiver() {
		return receiverUsername;
	}
	
	/**
	 * Getter for receiver
	 * @return receiverDisplayName
	 */
	public String getReceiverDisplayName() {
		return receiverDisplayName;
	}
	
	/**
	 * senderUsername:senderDisplayName:receiverUsername:receiverDisplayName
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return senderUsername + ":" + senderDisplayName + ":" + receiverUsername + ":" + receiverDisplayName;
	}

	/**
	 * @param fullname Set the receiver display name to this string.
	 */
	public void setReceiverDisplayName(String fullname) 
	{
		this.receiverDisplayName = fullname;
	}

	/**
	 * @param fullname Set the sender display name to this string.
	 */
	public void setSenderDisplayName(String fullname) 
	{
		this.senderDisplayName = fullname;
	}
}
