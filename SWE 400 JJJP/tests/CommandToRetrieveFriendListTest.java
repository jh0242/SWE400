import static org.junit.Assert.*;

import org.junit.Test;

import domainLogic.CommandToRetrieveFriendList;
import domain_model.Friend;
import domain_model.Person;
import domain_model.Session;


public class CommandToRetrieveFriendListTest {

	/**
	 * Tests the CommandToRetrieveFriendList by adding a friend to 
	 * a person's arraylist friends
	 */
	@Test
	public void test()
	{
		Person p = new Person(0, "TestUserName", "testpassword123", "TestDisplayName");
		Friend f = new Friend("NewFriendUserName", "NewFriendDisplayName");
		Session.getInstance().setPerson(p);
		p.addFriend(f);
		CommandToRetrieveFriendList c = new CommandToRetrieveFriendList(0);
		c.execute();
		assertEquals("NewFriendUserName", "NewFriendDisplayName", c.getResult());
	}
}
