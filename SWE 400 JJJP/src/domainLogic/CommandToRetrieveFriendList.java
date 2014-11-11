package domainLogic;
import domain_model.Friend;
import domain_model.Session;

/**
 * Cause a user's friend list to be fetched from the domain model (may or may
 * not cause reading from the DB depending on the state of the domain model
 * 
 * @author merlin
 *
 */
public class CommandToRetrieveFriendList implements Command
{

	private int userID;

	/**
	 * The userID of the current user
	 * @param userID unique
	 */
	public CommandToRetrieveFriendList(int userID)
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
		if(Session.getInstance().getPerson().getID() == userID)
		{
			//Session.getInstance().getPerson().getFriends();
		}
	}

	/**
	 * A list of the friends associated with the given user
	 * @see Command#getResult()
	 */
	@Override
	public String getResult()
	{
		String ret = "";
		for (Friend x : Session.getInstance().getPerson().getFriends()) {
			if (!ret.equals("")) {
				ret = ret + ",";
			}
			ret = ret + x.getDisplayName();
		}
		return ret;
	}

}
