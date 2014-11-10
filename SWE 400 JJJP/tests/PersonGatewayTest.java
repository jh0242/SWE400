
import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data_gateway.DataBaseConnection;
import data_gateway.PersonGateway;

/**
 * @author JJJP
 * Tests for the PersonGateway.
 *
 */
public class PersonGatewayTest 
{
	private String userName;
	
	/**
	 * Used to retrieve the user id for the specific user created for
	 * each test in this class.  ONLY FOR TEST PURPOSES.
	 * @return int, the UserID for the user we are testing with.
	 * @throws SQLException
	 */
	private int retrieveUserID() throws SQLException
	{
		String retrieveUser = new String("SELECT * FROM USER where UserName = '" + userName + "';");
		PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(retrieveUser);
		ResultSet rs = stmt.executeQuery(retrieveUser);
		rs.next();
		return rs.getInt(rs.findColumn("UserID"));
	}
	
	/**
	 * Used to retrieve the current displayName of the specific user created
	 * for each test in this class. ONLY FOR TEST PURPOSES.
	 * @return String, the current display name of the user.
	 * @throws SQLException
	 */
	private String getCurrentDisplayName() throws SQLException
	{
		String retrieveUser = new String("SELECT * FROM USER where UserName = '" + userName + "';");
		PreparedStatement stmt = DataBaseConnection.getInstance().getConnection().prepareStatement(retrieveUser);
		ResultSet rs = stmt.executeQuery(retrieveUser);
		if (rs.next())
			return rs.getString(rs.findColumn("DisplayName"));
		return "not here";
	}
	
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
	 * @throws SQLException if an error happens within the SQL commands
	 */
	@After
	public void removeUser() throws SQLException
	{
		PersonGateway.removeByUserName(userName);
	}
	
	/**
	 * Tests that inserting a user into the USER table using the insert
	 * method in PersonGateway works as intended and that trying to insert
	 * a user into the USER table with the same userName as another user
	 * will not be inserted.
	 * @throws SQLException if an error happens within the SQL commands
	 */
	@Test
	public void testInsertUser() throws SQLException 
	{
		assertTrue(PersonGateway.insert(userName, "password", "displayName"));
		assertFalse(PersonGateway.insert(userName, "password", "displayName"));
	}
	
	/**
	 * Tests that removing a user from the USER table using the remove
	 * method in PersonGateway works as intended.
	 * @throws SQLException if an error happens within the SQL commands
	 */
	@Test
	public void testRemoveUser() throws SQLException
	{
		PersonGateway.insert(userName, "password", "displayName");
		assertTrue(PersonGateway.removeByUserName(userName));
	}
	
	/**
	 * Tests that selectUser returns the correct ResultSet to be used by
	 * PersonDataMapper.
	 * @throws SQLException if an error happens within the SQL commands
	 */
	@Test
	public void testSelectUser() throws SQLException
	{
		assertNull(PersonGateway.selectUser(userName, "password"));
		PersonGateway.insert(userName, "password", "displayName");
		ResultSet rs = PersonGateway.selectUser(userName, "password");
		assertEquals(rs.getString(rs.findColumn("UserName")), userName);
		assertEquals(rs.getString(rs.findColumn("Password")), "password");
	}
	
	/**
	 * Tests that after we create a user it has a specific display name.
	 * Then we test that the the updateDisplayName method in PersonGateway
	 * will successfully update the display name of the test user in the table.
	 * Then we test that the test user has the correct, updated display name.
	 * Also tests that if the UserID is not in the table, the updateDisplayName
	 * method will return false.
	 * @throws SQLException if an error happens within the SQL commands
	 */
	@Test
	public void testUpdateUser() throws SQLException
	{
		PersonGateway.insert(userName, "password", "displayName");
		assertEquals("displayName", getCurrentDisplayName());
		assertTrue(PersonGateway.updateDisplayName(retrieveUserID(), "newDisplayName"));
		assertEquals("newDisplayName", getCurrentDisplayName());
		assertFalse(PersonGateway.updateDisplayName(999999999, "newDisplayName"));
	}
	
	/**
	 * Tests that trying to get the id of a user that doesn't exist will return
	 * -1.
	 */
	@Test
	public void testGetID()
	{
		assertEquals(-1, PersonGateway.getID("false", "false"));
		PersonGateway.insert(userName, "password", "displayName");
		int id = PersonGateway.getID("userA", "123");
		assertFalse(id == -1);
	}
}
