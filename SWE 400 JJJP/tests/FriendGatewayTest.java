import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data_gateway.FriendGateway;
import data_gateway.PersonGateway;

/**
 * @author Josh McMillen
 * Tests Friend Gateway Return Values
 */
public class FriendGatewayTest
{
	private String userA, userB;
	
	/**
	 * Creates a userA and userB for each test method in
	 * this class and inserts them into the USER table
	 * to satisfy the foreign key restraints in FRIENDS
	 * table.
	 */
	@Before()
	public void create()
	{
		userA = ((int) (Math.random() * 100000)) + "";
		userB = ((int) (Math.random() * 100000)) + "";
		PersonGateway.insert(userA, "password", "display");
		PersonGateway.insert(userB, "password", "display");
	}
	
	/**
	 * Remove the friendship relationships and users
	 * that are used for testing purposes only.
	 */
	@After
	public void remove()
	{
		FriendGateway.removeAllFriendships(userA);
		PersonGateway.removeByUserName(userA);
		PersonGateway.removeByUserName(userB);
	}
	
	/**
	 * Tests that insertFriend will successfully insert the
	 * friendship of the two users specified by user names in
	 * the parameters and return true if the insert was successful
	 * or false if the insert was unsuccessful.  If unsuccessful,
	 * the friendship already exists.
	 */
	@Test
	public void testInsertFriend()
	{
		assertTrue(FriendGateway.insertFriend(userA, userB));
		assertFalse(FriendGateway.insertFriend(userA, userB));
		assertFalse(FriendGateway.insertFriend(userB, userA));
	}
	
	/**
	 * Tests that areFriends returns true if the friendship of
	 * the two user names are in the table or false if they
	 * are not in the table.
	 */
	@Test
	public void testAreFriends()
	{
		assertFalse(FriendGateway.areFriends(userA, userB));
		FriendGateway.insertFriend(userA, userB);
		assertTrue(FriendGateway.areFriends(userA, userB));
	}
	
	/**
	 * Tests that removeFriendship will remove the friendship of
	 * the two users specified in the parameters and return true
	 * if it was successful or false if it was unsuccessful.
	 */
	@Test
	public void testRemoveFriendship()
	{
		assertFalse(FriendGateway.areFriends(userA, userB));
		assertFalse(FriendGateway.removeFriendship(userA, userB));
		FriendGateway.insertFriend(userA, userB);
		assertTrue(FriendGateway.areFriends(userA, userB));
		assertTrue(FriendGateway.removeFriendship(userA, userB));
		assertFalse(FriendGateway.areFriends(userA, userB));
	}
	
	/**
	 * Tests that removeAllFriendships removes all of the friendships
	 * associated with the user name passed in the parameter.  It
	 * should remove all friendships where the user is in column 1 or 2
	 * of any row.
	 */
	@Test
	public void testRemoveAllFriendships()
	{
		String userC = ((int) (Math.random() * 100000)) + "";
		PersonGateway.insert(userC, "password", "display");
		FriendGateway.insertFriend(userA, userB);
		FriendGateway.insertFriend(userA, userC);
		assertTrue(FriendGateway.removeAllFriendships(userA));
		assertFalse(FriendGateway.areFriends(userA, userB));
		assertFalse(FriendGateway.areFriends(userA, userC));
		PersonGateway.removeByUserName(userC);
	}
	
	/**
	 * Tests that getFriends returns the ResultSet that contains
	 * all of the friends associated with the user name passed
	 * into the parameter.
	 */
	@Test
	public void testGetFriends()
	{
		String userC = ((int) (Math.random() * 100000)) + "";
		PersonGateway.insert(userC, "password", "display");
		FriendGateway.insertFriend(userA, userB);
		FriendGateway.insertFriend(userC, userA);
		ResultSet rs = FriendGateway.getFriends(userA);
		try
		{
			while (rs.next())
				assertTrue((rs.getString(1).equals(userA) && rs.getString(2).equals(userB)) || (rs.getString(1).equals(userC) && rs.getString(2).equals(userA)));
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FriendGateway.removeAllFriendships(userA);
		PersonGateway.removeByUserName(userC);
	}
}