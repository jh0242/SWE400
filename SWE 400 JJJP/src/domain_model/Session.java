package domain_model;

/**
 * Represents a user's session. Each thread should have access to this
 * but threadlocal variables will mean that while the procedure is identical,
 * the data differs by thread.
 * @author Patrick Joseph Flanagan, Esq.
 *
 */
public class Session
{
	private static Session instance = null;
	private ThreadLocal<Person> person;
	private ThreadLocal<UnitOfWork> uow;
	
	/**
	 * Bare private constructor, prevents anyone from making one illegally.
	 */
	private Session() {
		this.person = new ThreadLocal<>();
		this.uow = new ThreadLocal<>();
	}
	
	/**
	 * @return Singleton session ref.
	 */
	public static Session getInstance() {
		if (instance == null) {
			instance = new Session();
		}
		return instance;
	}
	
	/**
	 * Return the thread-local person object.
	 * @return Person object.
	 */
	public Person getPerson() {
		return this.person.get();
	}
	
	/**
	 * Set the ThreadLocal Person reference.
	 * @param p A person. THE person. For this session.
	 */
	public void setPerson(Person p) {
		this.person.set(p);
	}
	
	/**
	 * Gets this thread's UnitOfWork.
	 * If it doesn't exist yet, make it and return it.
	 * @return UnitOfWork
	 */
	public UnitOfWork getUnitOfWork() {
		if (this.uow.get() == null) {
			this.uow.set(new UnitOfWork());
		}
		return this.uow.get();
	}
}
