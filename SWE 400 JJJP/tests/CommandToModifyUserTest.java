import static org.junit.Assert.*;

import org.junit.Test;

import domainLogic.CommandToModifyUser;
import domain_model.Person;
import domain_model.Session;


/**
 * @author Patrick Flanagan
 *
 */
public class CommandToModifyUserTest
{

	/**
	 * Test the functionality CommandToModifyUser of
	 */
	@Test
	public void test()
	{
		Person p = new Person(0, "test", "testpwd", "testdisplay");
		CommandToModifyUser c = new CommandToModifyUser(p.getID(), "nottest"); // It's still test
		Session.getInstance().setPerson(p);
		assertEquals(Session.getInstance().getPerson().getFullname(), "testdisplay");
		c.execute();
		assertEquals(Session.getInstance().getPerson().getFullname(), "nottest");
	}

}
