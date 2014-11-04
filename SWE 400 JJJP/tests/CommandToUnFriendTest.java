import static org.junit.Assert.*;

import org.junit.Test;

import domainLogic.CommandToUnFriend;
import domain_model.Friend;
import domain_model.Person;
import domain_model.Session;


/**
 * @author Patrick Joseph Flanagan
 * Test the Command to UnFriend.
 *
 */
public class CommandToUnFriendTest
{

	/**
	 * Test the basic functionality.
	 */
	@Test
	public void test()
	{
		Person a = new Person(1, "Antisocialdude", "hunter2", "Anti Social Dude");
		Friend f = new Friend("UnassumingGuy");
		a.addFriend(f);
		Session.getInstance().setPerson(a);
		assertEquals(1, a.getFriends().size());
		CommandToUnFriend c = new CommandToUnFriend(1, "UnassumingGuy");
		c.execute();
		assertEquals(0, a.getFriends().size());
	}

}
