import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data_gateway.PersonGateway;
import domainLogic.CommandToCreateUser;


public class CommandToCreateUserTest 
{
	private String userName;
	
	/**
	 * Creates a unique user name for the test user in this class.
	 */
	@Before
	public void createUser()
	{
		userName = ((int) (Math.random() * 100000)) + "";
	}
	
	/**
	 * Removes the user from the table after we are done testing.
	 * @throws SQLException
	 */
	@After
	public void removeUser()
	{
		PersonGateway.removeByUserName(userName);
	}
	
	/**
	 * Tests that CommandToCreateUser's execute method actually creates a
	 * new user when called.
	 * @throws SQLException
	 */
	@Test
	public void testCreateUser()
	{
		CommandToCreateUser command = new CommandToCreateUser(userName, "pass", "display");
		assertNull(command.getResult());
		command.execute();
		assertTrue(PersonGateway.userNameIsInTable(userName));
		assertEquals(userName + ":pass:display", command.getResult());
	}
}
