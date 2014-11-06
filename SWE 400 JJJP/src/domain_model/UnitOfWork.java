package domain_model;

import java.util.ArrayList;

import data_mapper.FriendMapper;
import data_mapper.PersonMapper;
import data_mapper.UserFriendRequestMapper;

/**
 * @author Patrick Joseph Flanagan
 *
 * Tracks changes that have happened and allows us to commit them
 * all as one sudden database hit rather than lots of tiny
 * little database hits.
 * 
 * TODO: Implement commit method once we have a data mapper.
 */
public class UnitOfWork
{	
	private ArrayList<DomainObject> newRegister = new ArrayList<>();
	private ArrayList<DomainObject> dirtyRegister = new ArrayList<>();
	private ArrayList<DomainObject> removeRegister = new ArrayList<>();
	
	/**
	 * Checks to see if a given object is already registered somewhere.
	 * @param something An object this UOW covers
	 * @return True if already registered, false if not.
	 */
	public boolean alreadyRegistered(DomainObject something) {
		boolean registered = false;
		if (newRegister.contains(something)) registered = true;
		if (dirtyRegister.contains(something)) registered = true;
		if (removeRegister.contains(something)) registered = true;
		return registered;
	}
	
	/**
	 * @param something Something to register.
	 * @return True if successful, false if given something is already registered.
	 */
	public boolean registerNew(DomainObject something) {
		boolean success = false;
		if (!alreadyRegistered(something)) {
			 newRegister.add(something);
			 success = true;
		}
		else {
			success = false;
		}
		return success;
	}
	
	/**
	 * @param something Something to register.
	 * @return True if successful, false if given something is already registered.
	 */
	public boolean registerDirty(DomainObject something) {
		boolean success = false;
		if (!alreadyRegistered(something)) {
			 dirtyRegister.add(something);
			 success = true;
		}
		else {
			success = false;
		}
		return success;
	}
	
	/**
	 * @param something Something to register.
	 * @return True if successful, false if given something is already registered.
	 *   Not actually needed.
	 */
	/**public boolean registerClean(DomainObject something) {
		boolean success = false;
		if (!alreadyRegistered(something)) {
			 cleanRegister.add(something);
			 success = true;
		}
		else {
			success = false;
		}
		return success;
	}*/
	
	/**
	 * @param something Something to register.
	 * @return True if successful, false if given something is already registered.
	 */
	public boolean registerRemoved(DomainObject something) {
		boolean success = false;
		if (!alreadyRegistered(something)) {
			 removeRegister.add(something);
			 success = true;
		}
		else {
			success = false;
		}
		return success;
	}

	/**
	 * Commit the changes down to the data mappers.
	 */
	public void commit()
	{
		Person sessionPerson = Session.getInstance().getPerson();
		for (DomainObject x : newRegister) {
			
			// Friends
			if (x.getClass().equals(Friend.class)) {
				Friend f = (Friend) x;
				FriendMapper.saveFriend(sessionPerson, f);
			}
			
			// FriendRequests, outgoing only at the moment.
			else if (x.getClass().equals(FriendRequest.class)) {
				FriendRequest fr = (FriendRequest) x;
				if (fr.getSender() == sessionPerson.getUsername()) {
					UserFriendRequestMapper.insertFriendRequest(sessionPerson, fr.getReceiver());
				}
			}
		}
		
		for (DomainObject x : dirtyRegister) {
			// Person
			if (x.getClass().equals(Person.class)) {
				Person p = (Person) x;
				PersonMapper.updateDisplayName(p.getUsername(), p.getPassword(), p.getFullname());
			}
		}
		
		for (DomainObject x : removeRegister) {
			// Friend
			if (x.getClass().equals(Friend.class)) {
				Friend f = (Friend) x;
				FriendMapper.getInstance().removeFriend(sessionPerson, f.getUserName());
			}
			// Friend Request
			else if (x.getClass().equals(FriendRequest.class)) {
				FriendRequest fr = (FriendRequest) x;
				// Outgoing
				if (fr.getSender() == sessionPerson.getUsername()) {
					UserFriendRequestMapper.removeOutgoingFriendRequest(sessionPerson, fr.getReceiver());
				}
				// Incoming
				//  
			}
		}
		newRegister.clear();
		dirtyRegister.clear();
		removeRegister.clear();
	}
}
