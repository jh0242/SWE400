package domainLogic;

import domain_model.Person;
import domain_model.Session;

/**
 * Reject a friend request from one user to another
 * @author merlin
 *
 */
public class CommandToRejectFriendRequest implements Command
{

	private int userIDOfRequestee;
	private String userNameOfRequester;

	/**
	 * 
	 * @param userIDOfRequestee the User ID of the user accepting the request
	 * @param userNameOfRequester the User Name of the user who initiated the friend request
	 */
	public CommandToRejectFriendRequest(int userIDOfRequestee, String userNameOfRequester)
	{
		this.userIDOfRequestee = userIDOfRequestee;
		this.userNameOfRequester = userNameOfRequester;
		
	}
	
	/**
	 * 
	 * @see Command#execute()
	 */
	@Override
	public void execute()
	{
		Person p = Session.getInstance().getPerson();
		if (p.getID() == userIDOfRequestee) {
			p.denyFriendRequest(userNameOfRequester);
		}

	}

	/**
	 * Nothing needs to be retrieved from this command
	 * @see Command#getResult()
	 */
	@Override
	public Object getResult()
	{
		return null;
	}

}
