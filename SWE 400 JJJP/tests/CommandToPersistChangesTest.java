import static org.junit.Assert.*;

import org.junit.Test;

import domainLogic.CommandToModifyUser;
import domainLogic.CommandToPersistChanges;
import domain_model.Person;
import domain_model.Session;


/**
 * Tests the functionality of CommandToPersistChanges.
 * @author group3
 */
public class CommandToPersistChangesTest 
{
	/**
	 * Test the functionality CommandToPersistChanges
	 */
	@Test
	public void test()
	{
		Person p = new Person(0, "test", "testpwd", "testdisplay");
		CommandToModifyUser c = new CommandToModifyUser(p.getID(), "nottest"); // It's still test
		Session.getInstance().setPerson(p);
		assertEquals(Session.getInstance().getPerson().getFullname(), "testdisplay");
		
		CommandToPersistChanges persist = new CommandToPersistChanges();
		c.execute();
		persist.execute();
		assertEquals(Session.getInstance().getPerson().getFullname(), "nottest");
	}
}
