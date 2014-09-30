package domain_model;

import java.util.Vector;

/**
 * @author Patrick Joseph Flanagan
 *
 * @param <T> What sort of object the UOW is dealing with.
 */
public class UnitOfWork<T>
{
	
	private Vector<T> newRegister = new Vector<>();
	private Vector<T> dirtyRegister = new Vector<>();
	private Vector<T> cleanRegister = new Vector<>();
	private Vector<T> removeRegister = new Vector<>();
	
	/**
	 * Constructor!
	 */
	public UnitOfWork() {

	}
	
	/**
	 * Checks to see if a given object is already registered somewhere.
	 * @param something An object this UOW covers
	 * @return True if already registered, false if not.
	 */
	public boolean alreadyRegistered(T something) {
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
	public boolean registerNew(T something) {
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
	public boolean registerDirty(T something) {
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
	public boolean registerClean(T something) {
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
	public boolean registerRemoved(T something) {
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
}
