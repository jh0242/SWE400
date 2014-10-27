import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.junit.Test;

import data_gateway.DataBaseConnection;
import data_gateway.PersonGateway;

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
		assertFalse(DataBaseConnection.getInstance().getConnection().isClosed());
		DataBaseConnection.getInstance().closeConnection();
	}
}
