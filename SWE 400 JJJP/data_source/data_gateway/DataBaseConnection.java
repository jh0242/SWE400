package data_gateway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Creates a connection to the database for the gateways.
 * @author jh0242
 */
public class DataBaseConnection 
{
	private static DataBaseConnection db = null;

	/**
	 * The destination where the database is held.
	 */
	public static final String LOCATION = "jdbc:mysql://lsagroup3.cbzhjl6tpflt.us-east-1.rds.amazonaws.com/fitness3";
	/**
	 * The login name to get access to the database.
	 */
	public static final String LOGIN_NAME = "lsagroup3";
	/**
	 * The password associated with the login name.
	 */
	public static final String PASSWORD = "lsagroup3";

	private Connection conn = null;
	
	private DataBaseConnection()
	{
		try
		{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException sqle)
		{
			// m_log.addMessageToLog(sqle);
		}
		try
		{
			getConnection();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the instance of the DataBaseConnection.
	 * @return DataBaseConnection
	 */
	public static DataBaseConnection getInstance()
	{
		if (db == null)
			db = new DataBaseConnection();
		return db;
	}


	/**
	 * If there is no connection to the database it will create one. If there is
	 * a connection already established it will return that connection.
	 * @return Connection
	 * @throws SQLException 
	 */
	public Connection getConnection() throws SQLException
	{
		if (conn == null || conn.isClosed())
		{
			conn = DriverManager.getConnection(LOCATION, LOGIN_NAME, PASSWORD);
		}
		return conn;
	}

	/**
	 * Closes the connection to the database.
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException
	{
		if (conn != null)
		{
			conn.close();
		}
	}
}
