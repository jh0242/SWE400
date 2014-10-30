package domainLogic;

import domain_model.Person;
import domain_model.Session;

/**
 * Initiates a friend request from one user to another
 * @author merlin
 *
 */
public class CommandToMakeFriendRequest implements Command
{

	private int userIDOfRequester;
	private String userNameOfRequestee;


	/**
	 * 
	 * @param userIDOfRequester the User ID of the user making the request
	 * @param userNameOfRequestee the User Name of the user being friended
	 */
	public CommandToMakeFriendRequest(int userIDOfRequester, String userNameOfRequestee)
	{
		this.userIDOfRequester = userIDOfRequester;
		this.userNameOfRequestee = userNameOfRequestee;
		
	}
	
	/**
	 * 
	 * @see Command#execute()
	 */
	@Override
	public void execute()
	{
		Person p = Session.getInstance().getPerson();
		if (p.getID() == userIDOfRequester) {
			p.newOutgoingFriendRequest(userNameOfRequestee);
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
