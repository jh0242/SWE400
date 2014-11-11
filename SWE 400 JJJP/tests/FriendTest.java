import static org.junit.Assert.*;

import org.junit.Test;

import domain_model.Friend;


/**
 * Tests the functionality of the Friend class.
 * @author jh0242
 */
public class FriendTest 
{

	/**
	 * Tests that a new instance of Friend initializes with the correct
	 * variables.
	 */
	@Test
	public void testInitialization() 
	{
		Friend f = new Friend("bob");
		assertEquals("bob", f.getUserName());
		assertNull(f.getDisplayName());
		f = new Friend("bob", "display");
		assertEquals("bob", f.getUserName());
		assertEquals("display", f.getDisplayName());
	}

	/**
	 * Tests that setDisplayName updates the displayName to 
	 * the correct String.
	 */
	@Test
	public void testSetDisplayName()
	{
		Friend f = new Friend("bob", "display");
		assertEquals("display", f.getDisplayName());
		f.setDisplayName("newDisplay");
		assertEquals("newDisplay", f.getDisplayName());
	}
	
	/**
	 * Tests that the toString method returns the string with order
	 * username:displayname.
	 */
	@Test
	public void testToString()
	{
		Friend f = new Friend("bob", "display");
		assertEquals("bob:display", f.toString());
	}
}
