
import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data_gateway.DataBaseConnection;
import data_gateway.PersonGateway;

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
	 * @throws SQLException
	 */
	@After
	public void removeUser() throws SQLException
	{
		PersonGateway.removeByUserName(userName);
	}
	
	/**
	 * Tests that inserting a user into the USER table using the insert
	 * method in PersonGateway works as intended.
	 * @throws SQLException
	 */
	@Test
	public void testInsertUser() throws SQLException 
	{
		assertTrue(PersonGateway.insert(userName, "password", "displayName"));
	}
	
	/**
	 * Tests that removing a user from the USER table using the remove
	 * method in PersonGateway works as intended.
	 * @throws SQLException
	 */
	@Test
	public void testRemoveUser() throws SQLException
	{
		PersonGateway.insert(userName, "password", "displayName");
		assertTrue(PersonGateway.removeByUserName(userName));
	}
	
	/**
	 * Tests that after we create a user it has a specific display name.
	 * Then we test that the the updateDisplayName method in PersonGateway
	 * will successfully update the display name of the test user in the table.
	 * Then we test that the test user has the correct, updated display name.
	 * @throws SQLException
	 */
	@Test
	public void testUpdateUser() throws SQLException
	{
		PersonGateway.insert(userName, "password", "displayName");
		assertEquals("displayName", getCurrentDisplayName());
		assertTrue(PersonGateway.updateDisplayName(retrieveUserID(), "newDisplayName"));
		assertEquals("newDisplayName", getCurrentDisplayName());
	}
}
