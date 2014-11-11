import static org.junit.Assert.*;

import org.junit.Test;

import domain_model.FriendRequest;


/**
 * Tests the functionality of FriendRequest.
 * @author jh0242
 */
public class FriendRequestTest 
{

	/**
	 * Tests that FriendRequest initializes with the correct
	 * user names and display names.
	 */
	@Test
	public void testInitialization() 
	{
		FriendRequest fr = new FriendRequest("userA", "userB");
		assertEquals("userA", fr.getSender());
		assertEquals("userB", fr.getReceiver());
		assertNull(fr.getSenderDisplayName());
		assertNull(fr.getReceiverDisplayName());
		fr = new FriendRequest("userC", "displayC", "userD", "displayD");
		assertEquals("userC", fr.getSender());
		assertEquals("userD", fr.getReceiver());
		assertEquals("displayC", fr.getSenderDisplayName());
		assertEquals("displayD", fr.getReceiverDisplayName());
	}
	
	/**
	 * Tests that setting the display names to new display names
	 * works correctly in the setReceiverDisplayName and setSenderDisplayName.
	 */
	@Test
	public void testSetters()
	{
		FriendRequest fr = new FriendRequest("userA", "displayA", "userB", "displayB");
		assertEquals("displayA", fr.getSenderDisplayName());
		assertEquals("displayB", fr.getReceiverDisplayName());
		fr.setSenderDisplayName("newDisplayA");
		fr.setReceiverDisplayName("newDisplayB");
		assertEquals("newDisplayA", fr.getSenderDisplayName());
		assertEquals("newDisplayB", fr.getReceiverDisplayName());
	}
	
	/**
	 * Tests that the toString method returns a string in the order of
	 * sender user name, sender display name, receiver user name, receiver display name.
	 */
	@Test
	public void testToString()
	{
		FriendRequest fr = new FriendRequest("userA", "displayA", "userB", "displayB");
		assertEquals("userA:displayA:userB:displayB", fr.toString());
	}
}
