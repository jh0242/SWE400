package domain_model;

import java.util.ArrayList;
import java.util.Iterator;

import data_mapper.FriendMapper;

/**
 * @author Patrick Joseph Flanagan
 * @author John Terry
 * @author Josh McMillen
 */
public class Person extends DomainObject
{
	String displayName; // Pretty non-unique display name.
	String userName;    // Unique username e.g.: xXxJavaLordxXx
	String password;    // Passwords aren't protected. We're Sony now.
	boolean updated;
	/**
	 * Lazy-loaded field. Be sure to use getFriends()
	 */
	private ArrayList<Friend> friends;

	/**
	 * Lazy-loaded field. Be sure to use getFriendRequests()
	 */
	private ArrayList<FriendRequest> friendRequests;
	/**
	 * Lady-loaded field. Be sure to use getFriendRequestsOutgoing()
	 */
	private ArrayList<FriendRequest> friendRequestsOutgoing;
	
	/**
	 * Person constructor.
	 * In a production sort of environment, you should really use the full constructor
	 * with password, username, and displayname all included.
	 * This is primarily to take the burden off testing.
	 * @param id Unique identifying ID number. This needs to be unique across _all_ DomainObjects across _all_ sessions.
	 */
	public Person(int id) {
		this.id = id;
		updated = false;
	}
	
	/**
	 * A more verbose constructor where you can specify username, displayname, and password immediately.
	 * @param id Unique ID
	 * @param username Unique username, no spaces
	 * @param displayname Non-unique displayname
	 * @param password sekrit password
	 */
	public Person(int id, String username, String password, String displayname) {
		this.id = id;
		this.userName = username;
		this.displayName = displayname;
		this.password = password;
	}
	
	/**
	 * @return Getter for updated boolean
	 */
	public boolean isUpdated()
	{
		return updated;
	}

	/**
	 * 
	 * @param updated Setter for updated boolean
	 */
	public void setUpdated(boolean updated)
	{
		this.updated = updated;
	}
	
	/**
	 * Getter / name field.
	 * @return String name
	 */
	public String getFullname()
	{
		return displayName;
	}
	
	/**
	 * @return String username, assumed to be unique.
	 */
	public String getUsername() {
		return userName;
	}

	/**
	 * Setter.
	 * @param name Name to set userName field to.
	 */
	public void setUsername(String name) {
		this.userName = name;
	}
	
	/**
	 * Setter / name field.
	 * @param name This user's name. Not assumed to be unique.
	 */
	public void setDisplayName(String name)
	{
		this.displayName = name;
		Session.getInstance().getUnitOfWork().registerDirty(this);
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
	public ArrayList<Friend> getFriends()
	{
		// This is where loading should occur.
		if (friends == null) {
			friends = new ArrayList<>();
			FriendMapper.getInstance().getAllFriends(this);
		}
		return friends;
	}
	
	/**
	 * Add a new friend to the friendslist in this person.
	 * @param f A person instance. Will fail if it's this person.
	 * @return true on success, false on failure. Will fail if the friend is already in this list.
	 */
	public boolean addFriend(Friend f) {
		boolean status = false;
		if (!getFriends().contains(f)) {
			getFriends().add(f);
			status = true;
			Session.getInstance().getUnitOfWork().registerNew(f);
		}
		return status;
	}
	
	/**
	 * Removes a friend from the friendslist in this person.
	 * @param f Some friend instance.
	 * @return true on success, false on failure. Will fail if the friend is not on this list.
	 */
	public boolean deleteFriend(Friend f) {
		boolean status = false;
		if (friends == null) getFriends(); // Lazy load if it hasn't been lazyloaded yet.
		if (friends.contains(f)) {
			friends.remove(f);
			status = true;
			Session.getInstance().getUnitOfWork().registerRemoved(f);
		}
		return status;
	}
	
	/**
	 * Remove a friend by their username (which is unique)
	 * @param friendtodelete The username of a friend you want to remove.
	 * @return True if this friend was removed; false if they were not or not found.
	 */
	public boolean deleteFriendByUsername(String friendtodelete) {
		boolean status = false;
		if (friends == null) getFriends();
		Iterator<Friend> i = friends.iterator();
		while (i.hasNext() && status == false) {
			Friend x = i.next();
			if (x.getUserName().equals(friendtodelete)) {
				i.remove();
				Session.getInstance().getUnitOfWork().registerRemoved(x);
				status = true;
			}
		}
		return status;
	}
	
	/** Friend Request Code **/
	
	/**
	 * This returns the list of friendRequests from this userID
	 * This is the "incoming" list.
	 * @return friendRequests
	 */
	public ArrayList<FriendRequest> getFriendRequests() {
		if (friendRequests == null) {
			//friendRequests = UserFriendRequestMapper.getAllIncomingFriendRequestObjects(this);
			friendRequests = new ArrayList<>();
		}
		return friendRequests;
	}
	
	/**
	 * Returns the outgoing friend requests.
	 * e.g.: the ones sent by this user to somebody else.
	 * @return friendRequestsOutgoing
	 */
	public ArrayList<FriendRequest> getFriendRequestsOutgoing() {
		if (friendRequestsOutgoing == null) {
			//friendRequestsOutgoing = UserFriendRequestMapper.getAllOutgoingFriendRequestObjects(this);
			friendRequestsOutgoing = new ArrayList<>();
		}
		return friendRequestsOutgoing;
	}
	
	/**
	 * Receive a friend request from another user.
	 * @param uname Username of the person who sent the request to us.
	 */
	public void receiveFriendRequest(String uname) {
		// Sender: uname, whoever is given. Receiver: me! This object.
		boolean okayToAdd = true;
		this.getFriendRequests();
		for (FriendRequest x : this.friendRequests) {
			if (x.getSender() == uname) {
				okayToAdd = false;
			}
		}
		if (okayToAdd) {
			FriendRequest f = new FriendRequest(uname, this.userName);
			getFriendRequests().add(f);
			Session.getInstance().getUnitOfWork().registerNew(f);
		}
	}
	
	/**
	 * @param uname Given username to search friend requests for
	 * @return True if this username is in the friend requests
	 */
	public boolean hasFriendRequest(String uname) {
		Iterator<FriendRequest> it = friendRequests.iterator();
		boolean success = false;
		while (it.hasNext() && !success) {
			FriendRequest f = it.next();
			if (f.getSender().equals(uname)) {
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
		Iterator<FriendRequest> it = friendRequests.iterator();
		boolean success = false;
		while (it.hasNext() && !success) {
			FriendRequest f = it.next();
			if (f.getSender().equals(uname)) {
				// Match!
				Session.getInstance().getUnitOfWork().registerRemoved(f); // Remove this now that it has been accepted.
				it.remove();
				success = true;
			}	
		}
		if (success) {
			Friend f = new Friend(uname);
			getFriends().add(f);
			Session.getInstance().getUnitOfWork().registerNew(f);
		}
		return success;
	}
	
	/**
	 * Remove a friend request by username.
	 * @param uname Unique username.
	 * @return True if success, false otherwise.
	 */
	public boolean removeFriendRequest(String uname) {
		Iterator<FriendRequest> it = friendRequests.iterator();
		boolean success = false;
		while (it.hasNext() && !success) {
			FriendRequest f = it.next();
			if (f.getSender().equals(uname)) {
				// Match!
				Session.getInstance().getUnitOfWork().registerRemoved(f);
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
		// Intentionally no UOW operation here; it's carried out in the above method call.
	}
	
	/**
	 * Send a new friend request to someone. Put it in this person's outgoing list.
	 * @param target A person.
	 * @return Success on true.
	 */
	public boolean newOutgoingFriendRequest(String target) {
		boolean success = true;
		
		this.getFriendRequestsOutgoing();
		
		for (FriendRequest x : friendRequestsOutgoing) {
			if (x.getReceiver().equals(target)) {
				success = false;
				break;
			}
		}
		FriendRequest f = new FriendRequest(this.userName, target);
		friendRequestsOutgoing.add(f);
		Session.getInstance().getUnitOfWork().registerNew(f);
		
		return success;
	}
	
	/**
	 * Return string of their username + password + displayname.
	 */
	@Override
	public String toString()
	{
		return userName + ":" + password + ":" + displayName;
	}
	
}
