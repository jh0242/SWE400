import static org.junit.Assert.*;

import org.junit.Test;

import domainLogic.CommandToMakeFriendRequest;
import domain_model.Person;
import domain_model.Session;


/**
 * @author Patrick Flanagan
 *
 */
public class CommandToMakeFriendRequestTest
{

	/**
	 * Test this command.
	 */
	@Test
	public void test()
	{
		Person p = new Person(0);
		Session.getInstance().setPerson(p);
		CommandToMakeFriendRequest c = new CommandToMakeFriendRequest(0, "test");
		c.execute();
		assertNull(c.getResult());
		assertEquals(p.getFriendRequestsOutgoing().size(), 1);
	}

}
