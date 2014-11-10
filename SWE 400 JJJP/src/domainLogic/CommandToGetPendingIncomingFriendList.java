package domainLogic;

import java.util.ArrayList;

import domain_model.FriendRequest;
import domain_model.Person;
import domain_model.Session;

/**
 * Cause the list of friend requests from other user to this user to be fetched
 * from the domain model (may or may not cause reading from the DB depending on
 * the state of the domain model)
 * 
 * @author merlin
 *
 */
public class CommandToGetPendingIncomingFriendList implements Command
{

	private int userID;
	private ArrayList<FriendRequest> incomingRequests;

	/**
	 * The userID of the current user
	 * 
	 * @param userID
	 *            unique
	 */
	public CommandToGetPendingIncomingFriendList(int userID)
	{
		this.userID = userID;
	}

	/**
	 * 
	 * @see Command#execute()
	 */
	@Override
	public void execute()
	{
		Person p = Session.getInstance().getPerson();
		if (p.getID() == userID) {
			incomingRequests = p.getFriendRequests();
		}
	}

	/**
	 * A comma-separated list of the friends associated with the given user
	 * 
	 * @see Command#getResult()
	 */
	@Override
	public String getResult()
	{
		String ret = "";
		boolean addSpace = false;
		for (FriendRequest x : incomingRequests) {
			if (addSpace) {
				ret = ret + ",";
			}
			ret = ret + x.getSenderDisplayName();
			addSpace = true;
		}
		System.out.println(" PendingIncomingFL: " + ret);
		return ret;
	}

	/**
	 * For testing purposes - to check that the constructor correctly remembered
	 * the userID of the requestor
	 * 
	 * @return the userID that was given to the constructor
	 */
	public int getUserID()
	{
		return userID;
	}

}
