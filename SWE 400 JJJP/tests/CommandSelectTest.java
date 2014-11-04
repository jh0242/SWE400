import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import data_gateway.PersonGateway;
import domainLogic.CommandToCreateUser;
import domainLogic.CommandToSelectUser;


/**
 * @author Patrick Flanagan
 * Testing the CommandToSelectUser command.
 *
 */
public class CommandSelectTest
{

	/**
	 * Test the select user command. Sort of requires the create user command to have a pristine database.
	 */
	@Test
	public void testCommand()
	{
		CommandToCreateUser create = new CommandToCreateUser("Selectman", "sel123", "Select");
		CommandToSelectUser sel = new CommandToSelectUser("Selectman", "sel123");
		create.execute();
		sel.execute();
		assertNotNull(sel.getResult());
		assertEquals(sel.getResult(), "Selectman");
	}
	
	/**
	 * Fix the database to a pristine state.
	 */
	@After
	public void cleanUp() {
		PersonGateway.removeByUserName("Selectman");
	}

}
