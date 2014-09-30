package domain_model;

import java.util.Vector;

/**
 * @author Patrick Joseph Flanagan
 * @author John Terry
 *
 */
public class Person
{
	int id;			 // Unique identifier.
	String name;	 // Non-unique full user name.
	String password; // Passwords aren't protected. We're Sony now.
	// This field is lazy-loaded and a null value indicates that it hasn't been loaded.
	private Vector<Person> friends;
	public Vector<Integer> friendRequests; // Just User's ID

	
	/**
	 * Person constructor.
	 * @param id Unique identifying ID number. We don't want two people with the same ID, ever.
	 */
	public Person(int id) {
		this.id = id;
	}
	
	/**
	 * Getter for id.
	 * @return ID number.
	 */
	public int getID() {
		return this.id;
	}
	
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
	 * Is used to lazyload things.
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
	
	/**
	 * Add a new friend to the friendslist in this person.
	 * @param f A person instance. Will fail if it's this person.
	 * @return true on success, false on failure. Will fail if the friend is already in this list.
	 */
	public boolean addFriend(Person f) {
		boolean status = false;
		if (friends == null) getFriends(); // Lazy load if it hasn't been lazyloaded yet.
		if (!friends.contains(f) && this != f) {
			friends.add(f);
			status = true;
		}
		return status;
	}
	
	/**
	 * Removes a friend from the friendslist in this person.
	 * @param f A person instance. Will fail if it's this person.
	 * @return true on success, false on failure. Will fail if the friend is not on this list.
	 */
	public boolean deleteFriend(Person f) {
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
	 * @return
	 */
	public Vector<Integer> getFriendRequests() {
		// TODO this ought to be a database load operation
		if (friendRequests == null) {
			friendRequests = new Vector<Integer>();
		}
		return friendRequests;
	}
	
	/**
	 * This adds this userID to the otherUserID's pending friend request table 
	 * TODO
	 * @param otherUserID
	 * @return
	 */
	public boolean sendFriendRequest(Integer otherUserID) {
		return false;
	}
	
	/**
	 * This add's the otherUserID to this userID's friends' list and removes 
	 * this association from the pending friend request table
	 * @param otherUserID
	 * @return
	 */
	public boolean confirmFriendRequest(Person f, Integer otherUserID) {
		boolean status = false;
		if(friendRequests == null) getFriendRequests(); // Lazy load if it hasn't been lazyloaded yet.
		if (friendRequests.contains(otherUserID)) {
			
			addFriend(f);
			friendRequests.remove(otherUserID);
			status = true;
			
			//addFriend(person f) Need to get person from database.
		}
		return status;
	}
	
	/**
	 * Removes a friend request from the otherUserID with this userID, this removes 
	 * this association from the pending friend request table	  
	 * @param otherUserID
	 * @return
	 */
	public boolean denyFriendRequest(Integer otherUserID){
		boolean status = false;
		if (friendRequests == null) getFriendRequests(); // Lazy load if it hasn't been lazyloaded yet.
		if (friendRequests.contains(otherUserID)) {
			friendRequests.remove(otherUserID);
			status = true;
		}
		return status;
	}
	
}
