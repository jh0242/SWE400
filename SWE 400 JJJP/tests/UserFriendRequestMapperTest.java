import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data_gateway.PersonGateway;
import data_gateway.UserFriendRequestGateway;
import data_mapper.UserFriendRequestMapper;
import domain_model.FriendRequest;
import domain_model.Person;


/**
 * Tests the functionality of the UserFriendRequestMapper class.
 * @author jh0242
 */
public class UserFriendRequestMapperTest
{
	private String requester, requestee;
	private Person person, person2;
	private FriendRequest fr;
	
	/**
	 * Inserts two users into the USER table for test
	 * purposes.  Also initializes the variables needed for
	 * tests.
	 */
	@Before
	public void create()
	{
		requester = ((int) (Math.random() * 100000)) + "";
		requestee = ((int) (Math.random() * 100000)) + "";
		person = new Person(PersonGateway.getID(requester, "password"), requester, "password", "display");
		person2 = new Person(PersonGateway.getID(requestee, "password"), requestee, "password", "display");
		PersonGateway.insert(requester, "password", "display");
		PersonGateway.insert(requestee, "password", "display");
		fr = new FriendRequest(requester, "display", requestee, "display");
	}
	
	/**
	 * Removes friend requests and users from their associated
	 * tables when finished testing.
	 */
	@After
	public void remove()
	{
		UserFriendRequestGateway.removeFriendRequest(requester, requestee);
		UserFriendRequestGateway.removeFriendRequest(requestee, requester);
		PersonGateway.removeByUserName(requester);
		PersonGateway.removeByUserName(requestee);
	}
	
	/**
	 * Tests that getInstance() returns an instance
	 * of UserFriendRequestMapper.
	 */
	@Test
	public void testGetInstance()
	{
		assertTrue(UserFriendRequestMapper.getInstance() instanceof UserFriendRequestMapper);
	}
	
	/**
	 * Tests that getAllIncomingFriendRequests retrieves the correct users that
	 * have sent the specified user a friend request.
	 */
	@Test
	public void testGetAllIncomingFriendRequests()
	{
		assertTrue(UserFriendRequestMapper.insertFriendRequest(person, fr));
		assertEquals(requester, UserFriendRequestMapper.getAllIncomingFriendRequests(person2).get(0).getSender());
		UserFriendRequestMapper.removeFriendRequest(person.getUsername(), fr.getReceiver());
		fr = new FriendRequest(requestee, "display", requester, "display");
		UserFriendRequestMapper.insertFriendRequest(person2, fr);
		assertEquals(requestee, UserFriendRequestMapper.getAllIncomingFriendRequests(person).get(0).getSender());
	}
	
	/**
	 * Tests that loadFriendRequestsList will load all of the user friend requests
	 * that have been sent to a user and the friend requests that the user has sent.
	 */
	@Test
	public void testLoadFriendRequestsList()
	{
		assertEquals(0, UserFriendRequestMapper.getAllOutgoingFriendRequests(person).size());
		assertTrue(UserFriendRequestMapper.insertFriendRequest(person, fr));
		assertFalse(UserFriendRequestMapper.insertFriendRequest(person, fr));
		assertEquals(requestee, UserFriendRequestMapper.getAllOutgoingFriendRequests(person).get(0).getReceiver());
		UserFriendRequestMapper.removeFriendRequest(person.getUsername(), fr.getReceiver());
		fr = new FriendRequest(requestee, "display", requester, "display");
		UserFriendRequestMapper.insertFriendRequest(person2, fr);
		assertEquals(requester, UserFriendRequestMapper.getAllOutgoingFriendRequests(person2).get(0).getReceiver());
	}

	/**
	 * Tests that insertFriendRequest successfully inserts a friend request into the database
	 * and if a user attempts to add the same friend request it will not succeed.  Also, if
	 * a user attempts to send a friend request to a user that has already sent them a friend
	 * request, it will be unsuccessful and return false.
	 */
	@Test
	public void testInsert()
	{
		assertTrue(UserFriendRequestMapper.insertFriendRequest(person, fr));
		assertFalse(UserFriendRequestMapper.insertFriendRequest(person, fr));
		fr = new FriendRequest(requestee, "display", requester, "display");
		assertFalse(UserFriendRequestMapper.insertFriendRequest(person2, fr));
	}
	
	/**
	 * Tests that removeOutgoingFriendRequest removes the request that a user has sent
	 * to another user.
	 */
	@Test
	public void testRemoveOutgoingFriendRequest()
	{
		assertTrue(UserFriendRequestMapper.insertFriendRequest(person, fr));
		assertEquals(requestee, UserFriendRequestMapper.getAllOutgoingFriendRequests(person).get(0).getReceiver());
		UserFriendRequestMapper.removeFriendRequest(person.getUsername(), fr.getReceiver());
		assertEquals(0, UserFriendRequestMapper.getAllOutgoingFriendRequests(person).size());
	}
	
	/**
	 * Tests that getAllOutgoingFriendRequests returns the correct list of users that the
	 * given user has sent friend requests to.
	 */
	@Test
	public void testGetAllOutgoingFriendRequests()
	{
		assertEquals(0, UserFriendRequestMapper.getAllOutgoingFriendRequests(person).size());
		UserFriendRequestMapper.insertFriendRequest(person, fr);
		assertEquals(1, UserFriendRequestMapper.getAllOutgoingFriendRequests(person).size());
		UserFriendRequestMapper.removeFriendRequest(person.getUsername(), fr.getReceiver());
		assertEquals(0, UserFriendRequestMapper.getAllOutgoingFriendRequests(person).size());
	}
}
