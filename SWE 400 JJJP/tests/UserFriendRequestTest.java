import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data_gateway.PersonGateway;
import data_gateway.UserFriendRequestGateway;


public class UserFriendRequestTest 
{
	private String requester, requestee;
	
	/**
	 * Creates two unique users for the tests in this class and inserts them
	 * into the USER table.
	 */
	@Before
	public void create()
	{
		requester = ((int) (Math.random() * 100000)) + "";
		requestee = ((int) (Math.random() * 100000)) + "";
		PersonGateway.insert(requester, "password", "display");
		PersonGateway.insert(requestee, "password", "display");
	}
	
	/**
	 * Removes the two unique users created for testing purposes.
	 */
	@After
	public void remove()
	{
		PersonGateway.removeByUserName(requester);
		PersonGateway.removeByUserName(requestee);
	}
	
	/**
	 * Tests that insertFriendRequest can add a valid friend request relationship
	 * into PENDINGFRIENDREQUESTS and that trying to add the exact same row into
	 * the table will not be valid and the method will not add the row and return false.
	 */
	@Test
	public void testInsertFriendRequest()
	{
		assertTrue(UserFriendRequestGateway.insertFriendRequest(requester, requestee));
		assertFalse(UserFriendRequestGateway.insertFriendRequest(requester, requestee));
		UserFriendRequestGateway.removeFriendRequest(requester, requestee);
	}
	
	/**
	 * Tests that trying to add a friend request relationship will not happen if
	 * the user name does not exist in the USER table.
	 */
	@Test
	public void testInvalidInsertFriendRequest()
	{
		assertFalse(UserFriendRequestGateway.insertFriendRequest(requester, "false"));
		assertFalse(UserFriendRequestGateway.insertFriendRequest("false", requestee));
		assertFalse(UserFriendRequestGateway.insertFriendRequest("false", "false"));
	}
	
	/**
	 * Tests that removeFriendRequest removes a friend request relationship whenever
	 * the relationship exists in the PENDINGFRIENDREQUESTS table.  If the
	 * relationship does not exist, then it is detected and the method returns
	 * false to denote that the relationship did not exist.
	 */
	@Test
	public void testRemoveFriendRequest()
	{
		UserFriendRequestGateway.insertFriendRequest(requester, requestee);
		assertTrue(UserFriendRequestGateway.removeFriendRequest(requester, requestee));
		assertFalse(UserFriendRequestGateway.removeFriendRequest(requester, requestee));
	}
}
