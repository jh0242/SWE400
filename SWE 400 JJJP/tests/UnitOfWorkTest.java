import static org.junit.Assert.*;

import org.junit.Test;

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
	 */
	@Test
	public void testCreation() {
		UnitOfWork<Integer> UOW = new UnitOfWork<>();
		
		assertNotNull(UOW);
	}
	
	/**
	 * Test to make sure that registering objects works AND
	 * that you can't register the same object as new and dirty.
	 */
	@Test
	public void testAddBlocking() {
		UnitOfWork<Integer> UOW = new UnitOfWork<>();
		Integer a = 1;
		Integer b = 2;
		Integer c = 3;
		Integer d = 4;
		
		UOW.registerNew(a);
		UOW.registerDirty(b);
		UOW.registerClean(c);
		UOW.registerRemoved(d);
		
		assertTrue(UOW.alreadyRegistered(a));
		assertTrue(UOW.alreadyRegistered(b));
		assertTrue(UOW.alreadyRegistered(c));
		assertTrue(UOW.alreadyRegistered(d));
		
		assertFalse(UOW.registerNew(b));
		assertFalse(UOW.registerDirty(a));
		assertFalse(UOW.registerClean(d));
		assertFalse(UOW.registerRemoved(c));
	}
}
