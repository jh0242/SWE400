package domain_model;

import java.util.Vector;

/**
 * @author Patrick Joseph Flanagan
 *
 */
public class Person
{
	String name;	 // Non-unique full user name.
	String password; // Passwords aren't protected. We're Sony now.
	// This field is lazy-loaded and a null value indicates that it hasn't been loaded.
	private Vector<Person> friends;

	/**
	 * Getter / name field.
	 * @return String name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Setter / name field.
	 * @param name This user's name. Not assumed to be unique.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Getter / password field.
	 * @return String password, unprotected.
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Setter / password field.
	 * @param password The user's password, unprotected.
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Get the friends vector.
	 * Never returns null. Should create it if it's null.
	 * TODO Should load from the database!
	 * @return Vector of Persons, never null, but possibly empty ( :( )
	 */
	public Vector<Person> getFriends()
	{
		// This is where loading should occur.
		// TODO: actually make it load from somewhere.
		if (friends == null) {
			friends = new Vector<>();
		}
		return friends;
	}
}
