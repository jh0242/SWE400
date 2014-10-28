import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import domain_model.Friend;
import domain_model.FriendRequest;
import domain_model.Person;


/**
 * @author Patrick Joseph Flanagan
 *
 */
public class PersonTest
{

	/**
	 * Test the getters and setters for sanity.
	 */
	@Test
	public void testGettersSetters()
	{
		Person p = new Person(42);
		p.setDisplayName("Fred");
		assertEquals("Fred", p.getFullname());
		p.setPassword("hunter2");
		assertEquals("hunter2", p.getPassword());
		assertEquals(42, p.getID());
	}
	
	/**
	 * Test the lazy load functionality of getting the friends array.
	 */
	@Test
	public void testGetFriends() {
		Person p = new Person(0);
		ArrayList<Friend> f = p.getFriends();
		assertNotNull(f);
		assertEquals(f.size(), 0);
	}
	
	/**
	 * Test adding friends.
	 * Special functionality: should fail if this friend already exists,
	 * should fail if this friend is yourself.
	 */
	@Test
	public void testAddFriend() {
		Person p1 = new Person(0);
		Friend p2 = new Friend("Dave");
		assertTrue(p1.addFriend(p2));
		assertFalse(p1.addFriend(p2));
	}
	
	/**
	 * Test deleting friends.
	 * Special functionality: should fail if this friend is not on your friendslist,
	 * should fail if this friend is yourself.
	 */
	@Test
	public void testDeleteFriend() {
		Person p1 = new Person(0);
		Friend p2 = new Friend("Dave");
		assertTrue(p1.addFriend(p2));
		assertTrue(p1.deleteFriend(p2));
		assertFalse(p1.deleteFriend(p2));
	}
	
	/**
	 * Test friend confirmation.
	 * TODO: test will change when we have people being loaded
	 * from the database.
	 */
	@Test
	public void testConfirmFriendRequest() {
		Person p1 = new Person(0);
		p1.receiveFriendRequest("JavaLord");
		assertTrue(p1.hasFriendRequest("JavaLord"));
		p1.confirmFriendRequest("JavaLord");
		assertFalse(p1.hasFriendRequest("JavaLord"));
	}
	
	/**
	 * Test deny friend request.
	 * TODO: test will change when we have people being loaded
	 * from the database.
	 */
	@Test
	public void testDenyFriendRequest() {
		Person p1 = new Person(0);
		p1.receiveFriendRequest("JavaLord");
		assertTrue(p1.hasFriendRequest("JavaLord"));
		assertTrue(p1.denyFriendRequest("JavaLord"));
		assertFalse(p1.denyFriendRequest("JavaLord"));
	}
	
	/**
	 * Test the formatting of the toString
	 */
	@Test
	public void testToString() {
		Person p1 = new Person(1, "JavaLord", "hunter2", "The Lord of Java");
		String s = p1.toString();
		assertTrue(s.equals("JavaLord:hunter2:The Lord of Java"));
	}
	
	/**
	 * Tests the lazyloaded getters.
	 * In absence of a database, it should at least never return null.
	 */
	@Test
	public void testLazyGetters() {
		Person p1 = new Person(0);
		ArrayList<FriendRequest> x;
		x = p1.getFriendRequests();
		assertNotNull(x);
		x = null;
		x = p1.getFriendRequestsOutgoing();
		assertNotNull(x);
		
	}

}
