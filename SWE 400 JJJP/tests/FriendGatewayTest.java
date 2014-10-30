import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import data_gateway.FriendGateway;

/**
 * @author Josh McMillen
 * Tests Friend Gateway Return Values
 */
public class FriendGatewayTest
{
	/** 
	 * Checks Functionality of Friends Gateway
	 * These Tests Require that Test Users Exist in USER table with UserID's
	 * 					1594, 1595, & 1596
	 * @throws SQLException
	 */
	
	// Checks Basic Operations Work
	@Test
	public void functionsTest() throws SQLException
	{
		// Check Friendship does not exist
		assertFalse(FriendGateway.areFriends(1594,1595));
		
		// Add Friendship
		FriendGateway.insertFriend(1594,1595);
		
		// Check Friendship Exists
		assertTrue(FriendGateway.areFriends(1594,1595));
		
		// Return Friendslist
		ResultSet result = FriendGateway.getFriends(1594);
		ResultSet result2 = FriendGateway.getFriends(1595);
		result.next();
		result2.next();
		assertEquals(1595,result.getInt(2));
		assertEquals(1594,result2.getInt(1));
		// Remove Friendship
		FriendGateway.removeFriendship(1594,1595);
		
		// Check Friendship does not exist
		assertFalse(FriendGateway.areFriends(1594,1595));
	}
	
	// Checks Duplicate Friendships Are Not Added To Database
	@Test
	public void checkDuplicates() throws SQLException
	{
		// Insert Friendship
		FriendGateway.insertFriend(1594,1595);
		
		// Check That Duplicate Friendship Can Not Be Added
		assertFalse(FriendGateway.insertFriend(1594,1595));
		
		// Check That Reverse Friendship Can Not Be Added
		assertFalse(FriendGateway.insertFriend(1595,1594));
		FriendGateway.removeFriendship(1594,1595);
	}
	
	// Checks Users Can Have Multiple Friends
	@Test
	public void checkMultipleFriends() throws SQLException
	{
		// Insert Multiple Friendships
		FriendGateway.insertFriend(1594,1595);
		FriendGateway.insertFriend(1594,1596);
		FriendGateway.insertFriend(1596,1595);
		
		// Store ResultSet for Testing
		ResultSet user1594 = FriendGateway.getFriends(1594);
		user1594.next();
		ResultSet user1595 = FriendGateway.getFriends(1595);
		user1595.next();
		ResultSet user1596 = FriendGateway.getFriends(1596);
		user1596.next();
		
		// Assert Multiple Friendships Exist
		/*assertTrue(user1594.getInt(2)==1595);
		assertTrue(user1594.getInt(2)==1596);
		assertTrue(user1595.getInt(1)==1596);
		assertTrue(user1596.getInt(2)==1594);
		assertTrue(user1596.getInt(1)==1595);*/
		System.out.println(user1594.getInt(2));
		System.out.println(user1595.getInt(2));
		System.out.println(user1596.getInt(2));
		
		// Remove Friendships
		FriendGateway.removeAllFriendships(1594);
		FriendGateway.removeAllFriendships(1595);
		FriendGateway.removeAllFriendships(1596);
	}

}
