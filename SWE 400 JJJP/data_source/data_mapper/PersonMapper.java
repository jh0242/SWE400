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
	private static PersonMapper personMapper;
	
	//private static Map<String,Person> users = new HashMap<String,Person>();
	
	private PersonMapper(){}
	static Map<String,Person> users = new HashMap<String,Person>();
	
	public static PersonMapper getInstance(){
		if(personMapper == null){
			personMapper = new PersonMapper();
		}
		return personMapper;
	}
	
	/**
	 * @param userName
	 * @param password
	 * @return return Loaded Domain Object :: Person, or create New Domain Object
	 * 		-- returns null if user Does not Exist
	 * @throws SQLException
	 */
	public static Person getPerson(String userName,String password)
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
	private static Person loadUser(String userName,String password)
	{
		ResultSet record = PersonGateway.selectUser(userName,password);
		Person loadedUser = null;
		try 
		{
			loadedUser = new Person(record.getInt("UserID"));
			loadedUser.setDisplayName(record.getString("DisplayName"));
			loadedUser.setPassword(record.getString("Password"));
			loadedUser.setUsername(record.getString("UserName"));
		} catch (SQLException e) 
		{
			System.out.println("An error occurred in loadUser!");
			e.printStackTrace();
		}
		users.put(userName, loadedUser);
		return loadedUser;
	}
	
	/**
	 * @param userName
	 * @return true if User is Loaded into HashMap
	 */
	private static boolean checkUserLoaded(String userName){
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
	public static boolean removeUser(String userName,String password)
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
	
	public static boolean removeUserFromHashMap(String userName)
	{
		if (users.containsKey(userName))
		{
			users.remove(userName);
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
	public static boolean updateDisplayName(String userName,String password, String display)
	{
		boolean success = false;
		if(checkUserLoaded(userName))
		{
			if(users.get(userName).getPassword() == password)
			{
				users.get(userName).setDisplayName(display);
				users.get(userName).setUpdated(true);
			} success = true;
		}else
		{
			if(PersonGateway.selectUser(userName, password)!= null)
			{
				Person loadedUser = loadUser(userName,password);
				loadedUser.setDisplayName(display);
				users.put(userName, loadedUser);
				users.get(userName).setUpdated(true);
				success = true;
			} success = false;
		}
		PersonGateway.updateDisplayName(PersonGateway.getID(userName, password), display);
		return success;
	}
	
	/**
	 * Persists updates to database
	 * @return true upon completion
	 * @throws SQLException
	 */
	public static boolean persistUpdates()
	{
		for(Entry<String, Person> entry : users.entrySet())
		{
			if(entry.getValue().isUpdated()){
				PersonGateway.updateDisplayName(entry.getValue().getID(),entry.getValue().getFullname());
			}
		}
		return true;
	}

	/**
	 * Checks if the userName is already in the users hashmap and returns null if it is.
	 * If not, it attempts to insert the new user into the database and returns null if it
	 * is unsuccessful.  If it is successful, it adds the new Person object to the users
	 * hashmap and returns that Person object.
	 * @param userName the userName of the new user.
	 * @param password the password of the new user.
	 * @param displayName the displayNmae of the new user.
	 * @return person if the insert is successful, else return false.
	 * @throws SQLException
	 */
	public static Person insert(String userName, String password, String displayName)
	{
		if (!checkUserLoaded(userName))
		{
			if (PersonGateway.insert(userName, password, displayName))
			{
				int id = PersonGateway.getID(userName, password);
				PersonGateway.insert(userName, password, displayName);
				Person person = new Person(id);
				person.setUsername(userName);
				person.setPassword(password);
				person.setDisplayName(displayName);
				users.put(userName, person);
				return person;
			}
		}
		return null;
	}

	/**
	 * Calls PersonGateway to get the displayName of the specified
	 * target person.
	 * @param target the userName of the person we want a display name for
	 * @return the displayName of the target
	 */
	public static String findDisplayName(String target) 
	{
		String displayName;
		if (users.containsKey(target))
			displayName = users.get(target).getFullname();
		else
			displayName = PersonGateway.getDisplayName(target);
		return displayName;
	}
}