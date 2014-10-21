package data_gateway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DataBaseConnection 
{
	private static DataBaseConnection db = null;

	public static final String LOCATION = "jdbc:mysql://lsagroup3.cbzhjl6tpflt.us-east-1.rds.amazonaws.com/fitness3";
	public static final String LOGIN_NAME = "lsagroup3";
	public static final String PASSWORD = "lsagroup3";

	private DataSource dataSource = null;

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
	
	public static DataBaseConnection getInstance()
	{
		if (db == null)
			db = new DataBaseConnection();
		return db;
	}


	/**
	 * If there is no connection to the database it will create one. If there is
	 * a connection already established it will return that connection.
	 * @throws SQLException 
	 */
	public Connection getConnection() throws SQLException
	{
		if (conn != null)
		{
			if (conn.isClosed())
			{
				if (dataSource != null)
				{
					conn = dataSource.getConnection();
				} else if (LOGIN_NAME != null)
				{
					conn = DriverManager.getConnection(LOCATION, LOGIN_NAME,
							PASSWORD);
				} else
				{
					conn = DriverManager.getConnection(LOCATION);
				}
			}
		} else
		{
			if (dataSource != null)
			{
				conn = dataSource.getConnection();
			} else if (LOGIN_NAME != null)
			{
				conn = DriverManager.getConnection(LOCATION, LOGIN_NAME,
						PASSWORD);
			} else
			{
				conn = DriverManager.getConnection(LOCATION);
			}
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
