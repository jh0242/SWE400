import static org.junit.Assert.*;

import org.junit.Test;

import data_gateway.PersonGateway;
import data_mapper.FriendMapper;
import data_mapper.PersonMapper;
import domain_model.Friend;
import domain_model.Person;

public class FriendMapperTest
{

	@Test
	public void testFrienships()
	{
		PersonMapper pm = PersonMapper.getInstance();
		PersonGateway.insert("asdfqwer", "asdfqwer", "rewqfdsa");
		Person p1 = pm.getPerson("asdfqwer", "asdfqwer");
		PersonGateway.insert("asdfqwer2", "asdfqwer2", "rewqfdsa2");
		Person p2 = pm.getPerson("asdfqwer2", "asdfqwer2");
		PersonGateway.insert("asdfqwer3", "asdfqwer3", "rewqfdsa3");
		Person p3 = pm.getPerson("asdfqwer3","asdfqwer3");
		Friend f1 = new Friend(p2.getUsername(), p2.getFullname());
		Friend f2 = new Friend(p3.getUsername(), p3.getFullname());
		FriendMapper fm = FriendMapper.getInstance();
		FriendMapper.saveFriend(p1, f1);
		assertEquals(f1,fm.getAllFriends(p1).get(0));
		fm.removeFriend(p1, "asdfqwer2");
		assertTrue(fm.getAllFriends(p1).isEmpty());
		FriendMapper.saveFriend(p1, f1);
		FriendMapper.saveFriend(p1, f2);
		assertEquals(f1,fm.getAllFriends(p1).get(0));
		assertEquals(f2,fm.getAllFriends(p1).get(1));
		fm.updateDisplayName(f1.getUserName(),"TEST");
		assertEquals("TEST",fm.getAllFriends(p1).get(0).getDisplayName());
		PersonGateway.removeByUserName("asdfqwer");
		PersonGateway.removeByUserName("asdfqwer2");
		PersonGateway.removeByUserName("asdfqwer3");
		
	}

}
