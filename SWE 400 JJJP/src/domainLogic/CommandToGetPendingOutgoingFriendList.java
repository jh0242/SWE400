package domainLogic;
import java.util.ArrayList;
import domain_model.FriendRequest;
import domain_model.Person;
import domain_model.Session;

/**
 * Cause the list of pending friend requests from this user to other users to be fetched
 * from the domain model (may or may not cause reading from the DB depending on
 * the state of the domain model)
 * 
 * @author merlin
 *
 */
public class CommandToGetPendingOutgoingFriendList implements Command
{
	private int userID;
	private ArrayList<FriendRequest> invitesSent;

	/**
	 * The userID of the current user
	 * 
	 * @param userID
	 *            unique
	 */
	public CommandToGetPendingOutgoingFriendList(int userID)
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
			invitesSent = p.getFriendRequestsOutgoing();
		}

	}

	/**
	 * A list of the friends associated with the given user
	 * 
	 * @see Command#getResult()
	 */
	@Override
	public String getResult()
	{
		if (invitesSent == null) return null;
		
		String ret = "";
		for (FriendRequest x : invitesSent) {
			if (!ret.equals("")) {
				ret = ret + " ";
			}
			ret = ret + x.getReceiver();
		}
		return ret;
	}

}
