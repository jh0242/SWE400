package domain_model;

import java.util.ArrayList;

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
	private ArrayList<DomainObject> cleanRegister = new ArrayList<>();
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
		if (cleanRegister.contains(something)) registered = true;
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
	 */
	public boolean registerClean(DomainObject something) {
		boolean success = false;
		if (!alreadyRegistered(something)) {
			 cleanRegister.add(something);
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
		
	}
}
