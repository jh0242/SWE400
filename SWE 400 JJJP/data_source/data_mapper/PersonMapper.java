package data_mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import data_gateway.PersonGateway;
import domain_model.Person;

public class PersonMapper
{
	HashMap<Integer,Person> users = new HashMap<Integer,Person>();
	
	public Person getPerson(int userID) throws SQLException
	{
		if(checkUserLoaded(userID)){
			return users.get(userID);
		}else if(PersonGateway.userIDisInTable(userID)){
			return loadUser(userID);
		}else{
			return null;
		}
	}
	
	public Person loadUser(int userID)
	{
		ResultSet record = PersonGateway.selectUser(userID);
		Person loadedUser = new Person(userID);
		loadedUser.setDisplayName(record.getString("DisplayName"));
		loadedUser.setPassword(record.getString("Password"));
		loadedUser.setUsername("UserName");
		users.put(userID, loadedUser);
	}
	
	public boolean checkUserLoaded(int userid){
		if(users.containsKey(userid))
		{
			return true;
		}
		return false;
	}
}
