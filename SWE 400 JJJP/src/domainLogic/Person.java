package domainLogic;

/**
 * You all will have one of these.
 * @author merlin
 *
 */
public class Person
{

	private String userName;
	private String password;
	private String displayName;

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return userName + ":" + password + ":" + displayName;
	}
}
