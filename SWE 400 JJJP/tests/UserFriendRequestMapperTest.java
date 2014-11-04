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
		PersonGateway.removeByUserName(requester);
		PersonGateway.removeByUserName(requestee);
	}
	
	@Test
	public void testLoadFriendRequestsList()
	{
		assertEquals(0, UserFriendRequestMapper.getAllOutgoingFriendRequests(person).size());
		UserFriendRequestGateway.insertFriendRequest(requester, requestee);
		UserFriendRequestMapper.loadFriendRequestsList(person);
		assertEquals(1, UserFriendRequestMapper.);
	}
	
	@Test
	public void testInsert()
	{
		assertTrue(UserFriendRequestMapper.insertFriendRequest(person, requestee));
		assertFalse(UserFriendRequestMapper.insertFriendRequest(person, requestee));
	}
}
