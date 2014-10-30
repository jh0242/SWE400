import static org.junit.Assert.*;

import org.junit.Test;

import domainLogic.CommandToGetPendingIncomingFriendList;
import domain_model.Person;
import domain_model.Session;


/**
 * @author Patrick Flanagan
 *
 */
public class CommandToGetPendingIncomingFriendListTest
{

	/**
	 * Test this command's functionality.
	 */
	@Test
	public void test()
	{
		Person p = new Person(0);
		Session.getInstance().setPerson(p);
		p.receiveFriendRequest("first");
		p.receiveFriendRequest("second");
		CommandToGetPendingIncomingFriendList c = new CommandToGetPendingIncomingFriendList(0);
		c.execute();
		assertEquals(c.getResult(), "first, second");
	}

}
