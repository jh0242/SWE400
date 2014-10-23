package domain_model;

/**
 * @author Patrick Joseph Flanagan
 * A friend is similar to a person, but less detailed;
 * they just hold some information that our domain user needs
 * to interact with them.
 *
 */
public class Friend extends DomainObject implements PersonShell
{
	String userName;
	String displayName;
	
	/**
	 * Simple constructor.
	 * @param uname Unique username.
	 */
	public Friend(String uname) {
		this.userName = uname;
	}
	
	/**
	 * Constructor
	 * @param uname Username
	 * @param fname Full name
	 */
	public Friend(String uname, String fname) {
		this.userName = uname;
		this.displayName = fname;
	}

	/** (non-Javadoc)
	 * @see domain_model.PersonShell#getFullname()
	 */
	@Override
	public String getFullname()
	{
		// TODO Auto-generated method stub
		return this.displayName;
	}

	/** (non-Javadoc)
	 * @see domain_model.PersonShell#getUsername()
	 */
	@Override
	public String getUsername()
	{
		// TODO Auto-generated method stub
		return this.userName;
	}

}
