package domain_model;

/**
 * @author Patrick Joseph Flanagan
 * Details the basic things anyone who wants to be a person needs to have.
 * 
 */
public interface PersonShell
{
	/**
	 * @return Full name, e.g.: John Doe
	 */
	public String getFullname();
	/**
	 * @return Unique username, e.g.: xXxCodeLord1988xXx
	 */
	public String getUsername();
}
