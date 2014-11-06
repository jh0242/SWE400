import static org.junit.Assert.*;

import org.junit.Test;

import domain_model.DomainObject;
import domain_model.UnitOfWork;


/**
 * @author Patrick Joseph Flanagan
 * Test class for the Unit of Work.
 *
 */
public class UnitOfWorkTest
{
	/**
	 * 
	 * @author Patrick Joseph Flanagan
	 * This super simple domain object is just used for testing.
	 * Its IDs only need to be unique on a per-test basis.
	 */
	protected class TestDomainObject extends DomainObject {
		TestDomainObject(int newID) {
			this.id = newID;
		}
	}
	/**
	 * 
	 */
	@Test
	public void testCreation() {
		UnitOfWork UOW = new UnitOfWork();
		
		assertNotNull(UOW);
	}
	
	/**
	 * Test to make sure that registering objects works AND
	 * that you can't register the same object as new and dirty.
	 */
	@Test
	public void testAddBlocking() {
		UnitOfWork UOW = new UnitOfWork();
		
		//TestDomainObject t1 = new TestDomainObject(1);
		TestDomainObject t2 = new TestDomainObject(2);
		TestDomainObject t3 = new TestDomainObject(3);
		TestDomainObject t4 = new TestDomainObject(4);
		//assertTrue(UOW.registerClean(t1));
		assertTrue(UOW.registerDirty(t2));
		assertTrue(UOW.registerNew(t3));
		assertTrue(UOW.registerRemoved(t4));
		
		// Erroneous add. This should fail.
		assertFalse(UOW.registerNew(t4));
	}
}
