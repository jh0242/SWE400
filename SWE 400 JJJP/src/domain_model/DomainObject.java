package domain_model;

/**
 * @author Patrick Joseph Flanagan
 *  This is the Layer Supertype pattern as described in the literature.
 *  Its purpose is to unify common properties of the domain model's objects.
 *
 */
public class DomainObject
{
	protected int id; // A unique ID. This should be unique among all DomainObjects across all sessions.
	/**
	 * @return The DomainObject's private ID.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Setter for ID. Be careful with this. It's assumed to be unique.
	 * @param id unique integer
	 */
	public void setID(int id){
		this.id = id;
	}
}
