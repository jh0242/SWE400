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
	
	@Test
	public void testGateway() throws SQLException
	{
		PersonGateway gate = new PersonGateway();
		assertTrue(gate.insert("userA", "123", "newdisplay"));
		assertTrue(gate.insert("userB", "123", "newdisplay"));
//		assertTrue(gate.remove(gate.))
	}

//	@Test
//	public void testUniqueUserIDs() throws InterruptedException, BrokenBarrierException
//	{
//		new MockThread().run();
//	}
	
	private class MockThread extends Thread
	{
		final CyclicBarrier gate = new CyclicBarrier(10);
		
		public void run()
		{
			ArrayList<Thread> list = new ArrayList<Thread>();
			
			for (int i = 0; i < 10; i++)
			{
				Thread thread = new Thread(){
						public void run()
						{
							try
							{
								gate.await();
								PersonGateway.insert((Math.random() * 100000) + "", "123", "display");
							} catch (SQLException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InterruptedException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (BrokenBarrierException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}};
				list.add(thread);
			}
			
			for (int i = 0; i < list.size(); i++)
			{
				list.get(i).run();
			}
			
			try
			{
				gate.await();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
