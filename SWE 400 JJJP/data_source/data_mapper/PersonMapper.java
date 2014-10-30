package data_mapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import data_gateway.PersonGateway;
import domain_model.Person;
/**
 * @author Josh McMillen
 * Person Mapper
 */
public class PersonMapper
{
	Map<String,Person> users = new HashMap<String,Person>();
	
	/**
	 * @param userName
	 * @param password
	 * @return return Loaded Domain Object :: Person, or create New Domain Object
	 * 		-- returns null if user Does not Exist
	 * @throws SQLException
	 */
	public Person getPerson(String userName,String password) throws SQLException
	{
		if(checkUserLoaded(userName)){
			return users.get(userName);
		}else{
			return loadUser(userName,password);
		}
	}
	
	/**
	 * @param userName
	 * @param password
	 * @return New Domain Object :: Person
	 * @throws SQLException
	 */
	private Person loadUser(String userName,String password) throws SQLException
	{
		ResultSet record = PersonGateway.selectUser(userName,password);
		Person loadedUser = new Person(record.getInt("UserID"));
		loadedUser.setDisplayName(record.getString("DisplayName"));
		loadedUser.setPassword(record.getString("Password"));
		loadedUser.setUsername("UserName");
		users.put(userName, loadedUser);
		return loadedUser;
	}
	
	/**
	 * @param userName
	 * @return true if User is Loaded into HashMap
	 */
	private boolean checkUserLoaded(String userName){
		if(users.containsKey(userName))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * @param userName
	 * @param password
	 * Removes User from Domain and Database
	 * returns True if operation Succeeds, False if not
	 * @throws SQLException 
	 */
	public boolean removeUser(String userName,String password) throws SQLException
	{
		if(users.containsKey(userName)){
			if(users.get(userName).getPassword() == password){
				users.remove(userName);
			}
		}
		if(PersonGateway.selectUser(userName, password)!=null)
		{
			PersonGateway.removeByUserName(userName);
			return true;
		}
		return false;
	}
	
	/**
	 * @param userName
	 * @param password
	 * @param display
	 * @return true on success and false if user does not exist
	 * @throws SQLException
	 */
	public boolean updateDisplayName(String userName,String password, String display) throws SQLException
	{
		if(checkUserLoaded(userName))
		{
			if(users.get(userName).getPassword() == password)
			{
				users.get(userName).setDisplayName(display);
				users.get(userName).setUpdated(true);
			} return true;
		}else
		{
			if(PersonGateway.selectUser(userName, password)!= null)
			{
				Person loadedUser = loadUser(userName,password);
				loadedUser.setDisplayName(display);
				users.put(userName, loadedUser);
				users.get(userName).setUpdated(true);
				return true;
			} return false;
		}
	}
	
	/**
	 * Persists updates to database
	 * @return true upon completion
	 * @throws SQLException
	 */
	public boolean persistUpdates() throws SQLException
	{
		for(Entry<String, Person> entry : users.entrySet())
		{
			if(entry.getValue().isUpdated()){
				PersonGateway.updateDisplayName(entry.getValue().getID(),entry.getValue().getFullname());
			}
		}
		return true;
	}
}
