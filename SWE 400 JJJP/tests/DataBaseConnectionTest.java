import static org.junit.Assert.assertFalse;

import java.sql.SQLException;
import org.junit.Test;

import data_gateway.DataBaseConnection;

/**
 * 
 * @author Joshua Helman
 *
 */
public class DataBaseConnectionTest 
{
	/**
	 * @throws SQLException SQL errors are handled elsewhere.
	 */
	@Test
	public void testInitializeConnection() throws SQLException 
	{
		assertFalse(DataBaseConnection.getInstance().getConnection().isClosed());
		DataBaseConnection.getInstance().closeConnection();
	}
}
