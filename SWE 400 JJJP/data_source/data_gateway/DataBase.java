package data_gateway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DataBase 
{
	private static DataBase db = null;

	public static final String LOCATION = "jdbc:mysql://db.cs.ship.edu:3306/csc371-18";
	public static final String LOGIN_NAME = "jh0242";
	public static final String PASSWORD = "when616mudd645";

	private DataSource dataSource = null;

	private Connection conn = null;
	
	private DataBase()
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
	
	public static DataBase getInstance()
	{
		if (db == null)
			db = new DataBase();
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
