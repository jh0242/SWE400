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
	private String userName;
	private String displayName;
	
	/**
	 * Simple constructor, userName only.
	 * @param userName Unique userName; should be checked against DB.
	 */
	public Friend(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Full constructor.
	 * @param userName Unique username (Unique to DB)
	 * @param displayName Non-unique visual name.
	 */
	public Friend(String userName, String displayName) {
		this.userName = userName;
		this.displayName = displayName;
	}
	
	/**
	 * @return The user's userName
	 */
	public String getUserName() {
		return this.userName;
	}
	
	/**
	 * 
	 * @return User's displayName.
	 */
	public String getDisplayName() {
		return this.displayName;
	}
	
	/**
	 * Set (or re-set) the display name.
	 * @param displayName Fancy full-name, non-unique.
	 */
	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}
	
	/**
	 * Returns userName:displayName
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.userName + ":" + this.displayName;
		
	}
}
