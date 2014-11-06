package domain_model;

/**
 * @author Patrick Flanagan
 * A friend request has a sender and receiver.
 * It's an extremely simple mapping of username -> username.
 *
 */
public class FriendRequest extends DomainObject
{
	private String sender;
	private String receiver;
	
	/**
	 * @param s Sender
	 * @param r Receiver
	 */
	public FriendRequest(String s, String r) {
		sender = s;
		receiver = r;
	}
	
	/**
	 * Getter for sender
	 * @return sender
	 */
	public String getSender() {
		return sender;
	}
	
	/**
	 * Getter for receiver
	 * @return receiver
	 */
	public String getReceiver() {
		return receiver;
	}
}
