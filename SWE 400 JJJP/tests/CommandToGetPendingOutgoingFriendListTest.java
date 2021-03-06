import static org.junit.Assert.*;

import org.junit.Test;

import domainLogic.CommandToGetPendingOutgoingFriendList;
import domain_model.Person;
import domain_model.Session;


/**
 * @author Patrick Flanagan
 * Working on record for longest test class name.
 *
 */
public class CommandToGetPendingOutgoingFriendListTest
{

	/**
	 * Test this specific command.
	 */
	@Test
	public void test()
	{
		Person p = new Person(0);
		p.nonDatabase = true;
		Session.getInstance().setPerson(p);
		p.newOutgoingFriendRequest("test");
		CommandToGetPendingOutgoingFriendList c = new CommandToGetPendingOutgoingFriendList(0);
		assertNull(c.getResult());
		c.execute();
		System.out.println(c.getResult());
		assertTrue(c.getResult().equals("nonexistent person"));
	}

}
