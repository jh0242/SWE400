import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data_gateway.PersonGateway;
import data_gateway.UserFriendRequestGateway;


/**
 * Tests the functionality of the UserFriendRequestGateway.
 * @author jh0242
 */
public class UserFriendRequestGatewayTest 
{
	private String requester, requestee, displayName = "display";
	
	/**
	 * Creates two unique users for the tests in this class and inserts them
	 * into the USER table.
	 */
	@Before
	public void create()
	{
		requester = ((int) (Math.random() * 100000)) + "";
		requestee = ((int) (Math.random() * 100000)) + "";
		PersonGateway.insert(requester, "password", displayName);
		PersonGateway.insert(requestee, "password", displayName);
	}
	
	/**
	 * Removes the two unique users created for testing purposes.
	 */
	@After
	public void remove()
	{
		UserFriendRequestGateway.removeFriendRequest(requester, requestee);
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
		assertTrue(UserFriendRequestGateway.insertFriendRequest(requester, requestee, displayName, displayName));
		assertFalse(UserFriendRequestGateway.insertFriendRequest(requester, requestee, displayName, displayName));
	}
	
	/**
	 * Tests that trying to add a friend request relationship will not happen if
	 * the user name does not exist in the USER table.
	 */
	@Test
	public void testInvalidInsertFriendRequest()
	{
		assertFalse(UserFriendRequestGateway.insertFriendRequest(requester, "false", displayName, displayName));
		assertFalse(UserFriendRequestGateway.insertFriendRequest("false", requestee, displayName, displayName));
		assertFalse(UserFriendRequestGateway.insertFriendRequest("false", "false", displayName, displayName));
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
		UserFriendRequestGateway.insertFriendRequest(requester, requestee, displayName, displayName);
		assertTrue(UserFriendRequestGateway.removeFriendRequest(requester, requestee));
		assertFalse(UserFriendRequestGateway.removeFriendRequest(requester, requestee));
	}
	
	/**
	 * Tests that findOutGoingFriendRequests
	 */
	@Test
	public void testFindOutGoingFriendRequests()
	{
		assertFalse(checkResultSet(UserFriendRequestGateway.findOutgoingFriendRequests(requester)));
		UserFriendRequestGateway.insertFriendRequest(requester, requestee, displayName, displayName);
		assertTrue(checkResultSet(UserFriendRequestGateway.findOutgoingFriendRequests(requester)));
	}
	
	/**
	 * Tests that findIncomingFriendRequests correctly retrieves the
	 * friend requests that are sent to a user.
	 */
	@Test
	public void testFindIncomingFriendRequests()
	{
		assertFalse(checkResultSet(UserFriendRequestGateway.findIncomingFriendRequests(requestee)));
		UserFriendRequestGateway.insertFriendRequest(requester, requestee, displayName, displayName);
		assertTrue(checkResultSet(UserFriendRequestGateway.findIncomingFriendRequests(requestee)));
		
	}
	
	private boolean checkResultSet(ResultSet rs)
	{
		try 
		{
			return rs.next();
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
