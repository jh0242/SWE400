package domainLogic;
/**
 * Holds enough information about a user to have them in a friend list
 * @author merlin
 *
 */
public class Friend
{

	private String userName;
	private String displayName;
	
	private Friend(String userName, String displayName)
	{
		this.userName = userName;
		this.displayName = displayName;
	}

	/**
	 * Get the friend's user name
	 * @return the user name of the friend's login credentials
	 */
	public String getUserName()
	{
		return userName;
	}

	/**
	 * Get the user's display name
	 * @return the name the user has chosen to be known by
	 */
	public String getDisplayName()
	{
		return displayName;
	}
}
