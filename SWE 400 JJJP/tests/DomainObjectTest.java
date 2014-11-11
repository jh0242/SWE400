import static org.junit.Assert.*;

import org.junit.Test;

import domain_model.DomainObject;


/**
 * Tests the functionality of DomainObject.
 * @author jh0242
 */
public class DomainObjectTest 
{
	/**
	 * Tests that setID updates the id correctly.
	 */
	@Test
	public void testSetId() 
	{
		DomainObject d = new DomainObject();
		assertEquals(0, d.getID());
		d.setID(101);
		assertEquals(101, d.getID());
	}
}
