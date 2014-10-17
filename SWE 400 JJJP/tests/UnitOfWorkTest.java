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
		
		fail();
	}
}
