import static org.junit.Assert.*;

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
	@Test
	public void testInitializeConnection() throws SQLException 
	{
		DataBaseConnection connection = DataBaseConnection.getInstance();
		assertFalse(connection.getConnection().isClosed());
		connection.closeConnection();
	}

}
