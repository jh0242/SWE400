import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data_gateway.PersonGateway;
import domainLogic.CommandToCreateUser;
import domainLogic.CommandToSelectUser;


/**
 * Tests the functionality of CommandToSelectUser
 * @author Group3
 */
public class CommandToSelectUserTest {

private String firstUserName;
private String secondUserName;
	
	/**
	 * Creates a unique user name for the test user in this class.
	 */
	@Before
	public void createFirstUser()
	{
		firstUserName = ((int) (Math.random() * 100000)) + "";
		secondUserName = ((int) (Math.random() * 100000)) + "";
	}
	
	/**
	 * Removes the user from the table after we are done testing.
	 */
	@After
	public void removeUser()
	{
		PersonGateway.removeByUserName(firstUserName);
		PersonGateway.removeByUserName(secondUserName);
	}
	
	/**
	 * Tests that CommandToCreateUser's execute method actually creates 
	 * two new users when called.
	 */
	@Test
	public void testCreateUsers()
	{
		CommandToCreateUser command1 = new CommandToCreateUser(firstUserName, "pass1", "FirstUserDisplay");
		assertEquals("null", command1.getResult());
		command1.execute();
		assertTrue(PersonGateway.userNameIsInTable(firstUserName));
		assertEquals(firstUserName + ":pass1:FirstUserDisplay", command1.getResult());
		
		CommandToCreateUser command2 = new CommandToCreateUser(secondUserName, "pass2", "SecondUserDisplay");
		assertEquals("null", command2.getResult());
		command2.execute();
		assertTrue(PersonGateway.userNameIsInTable(secondUserName));
		assertEquals(secondUserName + ":pass2:SecondUserDisplay", command2.getResult());
	}
	
	/**
	 * Tests that CommandToSelectUser initializes with the given username and displayname.
	 */
	@Test
	public void testSelectUsers()
	{
		CommandToSelectUser command = new CommandToSelectUser(firstUserName, "pass1");
		assertEquals(firstUserName, command.getUserName());
		assertEquals("pass1", command.getPassword());
	}
}
