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
		Person p = new Person(0, "man", "123", "mrman");
		Session.getInstance().setPerson(p);
		p.receiveFriendRequest("first", "mrfirst");
		p.receiveFriendRequest("second", "mrsecond");
		CommandToGetPendingIncomingFriendList c = new CommandToGetPendingIncomingFriendList(0);
		c.execute();
		assertEquals("mrfirst,mrsecond", c.getResult());
	}

}
