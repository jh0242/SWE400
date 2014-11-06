import static org.junit.Assert.*;

import org.junit.Test;

import domainLogic.CommandToAcceptFriendRequest;
import domain_model.Person;
import domain_model.Session;


/**
 * @author Patrick Flanagan
 *
 */
public class CommandToAcceptFriendRequestTest
{

	/**
	 * Test the friend request acceptance. Requires a friend request.
	 */
	@Test
	public void test()
	{
		Person p = new Person(1);
		Session.getInstance().setPerson(p);
		p.receiveFriendRequest("testman", "testmandisplayname");
		assertEquals(p.getFriends().size(), 0); // No friends yet, we hope.
		CommandToAcceptFriendRequest c = new CommandToAcceptFriendRequest(1, "testman");
		c.execute();
		assertEquals(p.getFriends().size(), 1);
	}

}
