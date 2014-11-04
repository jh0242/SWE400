import static org.junit.Assert.*;

import org.junit.Test;

import domainLogic.CommandToRejectFriendRequest;
import domain_model.Person;
import domain_model.Session;


/**
 * @author Patrick Flanagan
 *
 */
public class CommandToRejectFriendRequestTest
{

	/**
	 * 
	 */
	@Test
	public void test()
	{
		Person a = new Person(1, "Testman", "Tester", "hunter2");
		Session.getInstance().setPerson(a); // This is our session guy.
		a.receiveFriendRequest("Creepyguy"); // This guy sounds bad, let's not accept this
		assertEquals(1, a.getFriendRequests().size()); // Make sure it's in the list though
		CommandToRejectFriendRequest c = new CommandToRejectFriendRequest(1, "Creepyguy"); // User OBVIOUSLY wants to deny a request from Creepyguy
		c.execute(); // Get that guy out of here
		assertEquals(0, a.getFriendRequests().size());
	}

}
