package data_gateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersonGateway
{
	String insertData = new String("INSERT INTO PERSON (User Name,Password,Display Name) VALUES (?,?,?)");
	
	private Connection conn = null;
	
	private int getNextUniqueID()
	{
		//returns the next unique id, an integer, for the PERSON table.
		return 0;
	}
	
	public boolean insert(String userName, String password, String displayName) throws SQLException
	{
		String checkUniqueUserName = new String("SELECT * FROM PERSON where User Name = '" + userName + "';");
		PreparedStatement stmt = conn.prepareStatement(checkUniqueUserName);
		ResultSet rs = stmt.executeQuery(checkUniqueUserName);
		if (!rs.next())
			return false; //the userName is not taken in the table.
		
		//rs.next will get the next row in the result set
		//type in rs.getString, rs.getInt, etc. to see the methods for retrieving information
		//from specific columns
		stmt = conn.prepareStatement(insertData);
		stmt.setString(1, userName);
		stmt.setString(2, password);
		stmt.setString(3, displayName);
		stmt.executeUpdate();
		
		return true;
	}
}
