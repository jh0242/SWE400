import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import data_gateway.UserFriendRequestGateway;


public class UserFriendRequestTest {

	/**
	 * Tests that a friendship request can be successfully added to the 
	 * PENDINGFRIENDREQUEST table in the database
	 */
	@Test
	public void testInsertFriend() throws SQLException {
		int user1 = 1594;
		int user2 = 1595;
		
		assertTrue(UserFriendRequestGateway.insertFriendRequest(user1, user2));
		assertFalse((UserFriendRequestGateway.insertFriendRequest(user1, user2)));
		assertTrue(UserFriendRequestGateway.removeFriendRequest(user2, user1));	
	}
	
	/**
	 * Tests that a friendship request can be successfully removed from the 
	 * PENDINGFRIENDREQUEST table in the database
	 * @throws SQLException
	 */
	@Test
	public void testRemoveFriend() throws SQLException {
		int user1 = 1594;
		int user2 = 1595;
		
		assertTrue(UserFriendRequestGateway.insertFriendRequest(user1, user2));
		assertTrue(UserFriendRequestGateway.removeFriendRequest(user2, user1));	
//		assertFalse(UserFriendRequestGateway.removeFriendRequest(user2, user1));	
	}
	
	/**
	 * Tests that all outgoing friend requests for a user can be retrieved 
	 * from the PENDINGFRIENDREQUEST table in the database
	 * @throws SQLException
	 */
	@Test
	public void testFindOutgoingFriend() throws SQLException {
		int user1 = 1594;
		int user2 = 1595;
		int user3 = 1596;
		
		assertTrue(UserFriendRequestGateway.insertFriendRequest(user1, user2));
		assertTrue(UserFriendRequestGateway.insertFriendRequest(user1, user3));
		
		assertTrue(UserFriendRequestGateway.findOutgoingFriendRequests(user1));
		assertTrue(UserFriendRequestGateway.removeFriendRequest(user2, user1));
		assertTrue(UserFriendRequestGateway.removeFriendRequest(user3, user1));	
//		assertFalse(UserFriendRequestGateway.findOutgoingFriendRequests(user1));
	}
	
	/**
	 * Tests that all incoming friend requests for a user can be retrieved 
	 * from the PENDINGFRIENDREQUEST table in the database
	 * @throws SQLException
	 */
	@Test
	public void testFindIncomingFriend() throws SQLException {
		int user1 = 1594;
		int user2 = 1595;
		int user3 = 1596;
		
		assertTrue(UserFriendRequestGateway.insertFriendRequest(user1, user2));
		assertTrue(UserFriendRequestGateway.insertFriendRequest(user3, user2));
		
		assertTrue(UserFriendRequestGateway.findIncomingFriendRequests(user2));
		assertTrue(UserFriendRequestGateway.removeFriendRequest(user2, user1));	
		assertTrue(UserFriendRequestGateway.removeFriendRequest(user2, user3));
//		assertFalse(UserFriendRequestGateway.findIncomingFriendRequests(user2));
	}
	

}
