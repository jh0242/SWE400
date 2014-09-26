import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

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
		p.setName("Fred");
		assertEquals("Fred", p.getName());
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
		Vector<Person> f = p.getFriends();
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
		Person p2 = new Person(1);
		assertTrue(p1.addFriend(p2));
		assertFalse(p1.addFriend(p2));
		assertFalse(p1.addFriend(p1));
	}
	
	/**
	 * Test friend confirmation.
	 * TODO: test will change when we have people being loaded
	 * from the database.
	 */
	@Test
	public void testConfirmFriendRequest() {
		Person p1 = new Person(0);
		Person p2 = new Person(1);
		p1.getFriendRequests();
		p1.friendRequests.add(1);
		assertTrue(p1.friendRequests.contains(1));
		p1.confirmFriendRequest(1);
		assertFalse(p1.friendRequests.contains(1));
	}

}
