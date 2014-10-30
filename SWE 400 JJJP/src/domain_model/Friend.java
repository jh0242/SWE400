package domain_model;


/**
 * @author Patrick Joseph Flanagan
 * A friend is similar to a person, but less detailed;
 * they just hold some information that our domain user needs
 * to interact with them.
 *
 */
public class Friend extends DomainObject
{
	String displayName;
	
	/**
	 * Simple constructor.
	 * @param uname Unique username.
	 */
	public Friend(String displayName) {
		this.displayName = displayName;
	}
	
	/** (non-Javadoc)
	 * @see domain_model.PersonShell#getFullname()
	 */
	public String getFullname()
	{
		return this.displayName;
	}
	
	public void setFullName(String displayName)
	{
		this.displayName = displayName;
	}

	/** (non-Javadoc)
	 * @see domain_model.PersonShell#getUsername()
	 */
	/*public String getUsername()
	{
		// TODO Auto-generated method stub
		return this.userName;
	}*/

}
