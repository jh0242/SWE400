package domain_model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Patrick Joseph Flanagan
 * @author John Terry
 *
 */
public class Person extends DomainObject implements PersonShell
{
	String name;	 // Non-unique full name. e.g.: John Doe
	String username; // Unique username e.g.: xXxJavaLordxXx
	String password; // Passwords aren't protected. We're Sony now.
	
	/**
	 * Lazy-loaded field. Be sure to use getFriends()
	 */
	private ArrayList<PersonShell> friends;

	/**
	 * Lazy-loaded field. Be sure to use getFriendRequests()
	 */
	private ArrayList<PersonShell> friendRequests;
	
	/**
	 * Person constructor.
	 * @param id Unique identifying ID number. This needs to be unique across _all_ DomainObjects across _all_ sessions.
	 */
	public Person(int id) {
		this.id = id;
	}
	
	/**
	 * Getter / name field.
	 * @return String name
	 */
	@Override
	public String getFullname()
	{
		return name;
	}
	
	/**
	 * @return String username, assumed to be unique.
	 */
	@Override
	public String getUsername() {
		return username;
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
	 * Get the friends ArrayList.
	 * Never returns null. Should create it if it's null.
	 * Is used to lazyload things.
	 * TODO Should load from the database!
	 * @return ArrayList of Persons, never null, but possibly empty ( :( )
	 */
	public ArrayList<PersonShell> getFriends()
	{
		// This is where loading should occur.
		// TODO: actually make it load from somewhere.
		if (friends == null) {
			friends = new ArrayList<>();
		}
		return friends;
	}
	
	/**
	 * Add a new friend to the friendslist in this person.
	 * @param f A person instance. Will fail if it's this person.
	 * @return true on success, false on failure. Will fail if the friend is already in this list.
	 */
	public boolean addFriend(PersonShell f) {
		boolean status = false;
		if (!getFriends().contains(f) && this != f) {
			getFriends().add(f);
			status = true;
		}
		return status;
	}
	
	/**
	 * Removes a friend from the friendslist in this person.
	 * @param f A person instance. Will fail if it's this person.
	 * @return true on success, false on failure. Will fail if the friend is not on this list.
	 */
	public boolean deleteFriend(PersonShell f) {
		boolean status = false;
		if (friends == null) getFriends(); // Lazy load if it hasn't been lazyloaded yet.
		if (friends.contains(f) && this != f) {
			friends.remove(f);
			status = true;
		}
		return status;
	}
	
	/** Friend Request Code **/
	
	/**
	 * This returns the list of friendRequests from this userID
	 * @return friendRequests ArrayList full of PersonShells
	 */
	public ArrayList<PersonShell> getFriendRequests() {
		// TODO this ought to be a database load operation
		// Until then, it just makes an empty ArrayList.
		if (friendRequests == null) {
			friendRequests = new ArrayList<>();
		}
		return friendRequests;
	}
	
	/**
	 * This adds this userID to the otherUserID's pending friend request table 
	 * TODO
	 * @param uname The other person's unique username.
	 * @return True on success, false on failure.
	 */
	public boolean sendFriendRequest(String uname) {
		return false;
	}
	
	/**
	 * Receive a friend request from another user.
	 * @param uname Username of the person who sent the request to us.
	 */
	public void receiveFriendRequest(String uname) {
		Friend f = new Friend(uname);
		getFriendRequests().add(f);
	}
	
	/**
	 * @param uname Given username to search friend requests for
	 * @return True if this username is in the friend requests
	 */
	public boolean hasFriendRequest(String uname) {
		Iterator<PersonShell> it = friendRequests.iterator();
		boolean success = false;
		while (it.hasNext() && !success) {
			PersonShell f = it.next();
			if (f.getUsername().equals(uname)) {
				success = true;
			}
		}
		return success;
	}
	
	/**
	 * This add's the otherUserID to this userID's friends' list and removes 
	 * this association from the pending friend request table
	 * @param uname Unique username
	 * @return True if confirmation successful
	 */
	public boolean confirmFriendRequest(String uname) {
		Iterator<PersonShell> it = friendRequests.iterator();
		boolean success = false;
		while (it.hasNext() && !success) {
			PersonShell f = it.next();
			if (f.getUsername().equals(uname)) {
				// Match!
				it.remove();
				success = true;
			}	
		}
		if (success) {
			Friend f = new Friend(uname);
			getFriends().add(f);
		}
		return success;
	}
	
	/**
	 * Remove a friend request by username.
	 * @param uname Unique username.
	 * @return True if success, false otherwise.
	 */
	public boolean removeFriendRequest(String uname) {
		Iterator<PersonShell> it = friendRequests.iterator();
		boolean success = false;
		while (it.hasNext() && !success) {
			PersonShell f = it.next();
			if (f.getUsername().equals(uname)) {
				// Match!
				it.remove();
				success = true;
			}	
		}
		return success;
	}
	
	/**
	 * Removes a friend request from the otherUserID with this userID, this removes 
	 * this association from the pending friend request table	  
	 * @param uname Unique username, should exist in friendRequests
	 * @return True if a friend request was found and denied; false otherwise.
	 */
	public boolean denyFriendRequest(String uname){
		return removeFriendRequest(uname);
	}
	
}
