package domainLogic;
import domain_model.Person;

/**
 * Creates a new user in the system
 * 
 * @author merlin
 * 
 */
public class CommandToCreateUser implements Command
{

	private String userName;

	private String password;

	private String displayName;

	/**
	 * Create a command that will add a new user to the system
	 * 
	 * @param userName
	 *            the name of the user's login credentials
	 * @param password
	 *            that password of the user's login credentials
	 * @param displayName
	 *            the name by which the user wants to be referred
	 */
	public CommandToCreateUser(String userName, String password,
			String displayName)
	{
		this.userName = userName;
		this.password = password;
		this.displayName = displayName;
	}

	/**
	 * @see Command#execute()
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}
	
	/**
	 * Get the display name this user is supposed to get
	 * @return the display name
	 */
	public String getDisplayName()
	{
		return displayName;
	}

	/**
	 * Get the password this user will need to enter to log in
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * This should return the appropriate Person object from the domain model.
	 * Null if the credentials of the user were invalid (userName not unique)
	 * 
	 * @see Command#getResult()
	 */
	@Override
	public Person getResult()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get the username this use will need to enter to log in
	 * @return the user name
	 */
	public String getUserName()
	{
		return userName;
	}

}
