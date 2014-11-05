import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data_gateway.PersonGateway;
import data_gateway.UserFriendRequestGateway;
import data_mapper.UserFriendRequestMapper;
import domain_model.Person;


public class UserFriendRequestMapperTest
{
	private String requester, requestee;
	private Person person;
	
	@Before
	public void create()
	{
		requester = ((int) (Math.random() * 100000)) + "";
		requestee = ((int) (Math.random() * 100000)) + "";
		person = new Person(PersonGateway.getID(requester, "password"), requester, "password", "display");
		PersonGateway.insert(requester, "password", "display");
		PersonGateway.insert(requestee, "password", "display");
	}
	
	@After
	public void remove()
	{
		UserFriendRequestGateway.removeFriendRequest(requester, requestee);
		UserFriendRequestGateway.removeFriendRequest(requestee, requester);
		PersonGateway.removeByUserName(requester);
		PersonGateway.removeByUserName(requestee);
	}
	
	@Test
	public void testGetInstance()
	{
		assertTrue(UserFriendRequestMapper.getInstance() instanceof UserFriendRequestMapper);
	}
	
	@Test
	public void testLoadFriendRequestsList()
	{
		assertEquals(0, UserFriendRequestMapper.getAllOutgoingFriendRequests(person).size());
		UserFriendRequestGateway.insertFriendRequest(requester, requestee);
		UserFriendRequestMapper.loadFriendRequestsList(person);
		assertEquals(requestee, UserFriendRequestMapper.getAllOutgoingFriendRequests(person).get(0));
		UserFriendRequestMapper.removeOutgoingFriendRequest(person, requestee);
		person.setUsername(requestee);
		UserFriendRequestMapper.insertFriendRequest(person, requester);
		assertEquals(requester, UserFriendRequestMapper.getAllIncomingFriendRequests(person).get(0));
	}
	
	@Test
	public void testInsert()
	{
		assertTrue(UserFriendRequestMapper.insertFriendRequest(person, requestee));
		assertFalse(UserFriendRequestMapper.insertFriendRequest(person, requestee));
	}
	
	@Test
	public void testRemoveOutgoingFriendRequest()
	{
		UserFriendRequestGateway.insertFriendRequest(requester, requestee);
		assertEquals(requestee, UserFriendRequestMapper.getAllOutgoingFriendRequests(person).get(0));
		UserFriendRequestMapper.removeOutgoingFriendRequest(person, requestee);
		assertEquals(0, UserFriendRequestMapper.getAllOutgoingFriendRequests(person).size());
	}
	
	@Test
	public void testGetAllOutgoingFriendRequests()
	{
		assertEquals(0, UserFriendRequestMapper.getAllOutgoingFriendRequests(person).size());
		UserFriendRequestMapper.insertFriendRequest(person, requestee);
		assertEquals(1, UserFriendRequestMapper.getAllOutgoingFriendRequests(person).size());
		UserFriendRequestMapper.removeOutgoingFriendRequest(person, requestee);
		assertEquals(0, UserFriendRequestMapper.getAllOutgoingFriendRequests(person).size());
	}
}
