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
	
	private PersonMapper(){}
	static Map<String,Person> users = new HashMap<String,Person>();
	/**
	 * Returns the singleton instance of PersonMapper
	 * @return PersonMapper the singleton instance of PersonMapper.
	 */
	public static PersonMapper getInstance(){
		if(personMapper == null){
			personMapper = new PersonMapper();
		}
		return personMapper;
	}
	
	/**
	 * Gets the person object out of the HashMap if it exists.  If it doesn't
	 * exist, it loads the Person.
	 * @param userName the username of the person to be retrieved
	 * @param password the password for the associated username
	 * @return return Loaded Domain Object :: Person, or create New Domain Object
	 * 		-- returns null if user Does not Exist
	 */
	public Person getPerson(String userName,String password)
	{
		if(checkUserLoaded(userName)){
			return users.get(userName);
		}else{
			return loadUser(userName,password);
		}
	}
	
	/**
	 * Loads the Person with the given username and password and returns
	 * that Person.
	 * @param userName the username of the user to be loaded
	 * @param password the associated password
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
	 * Checks if the user is loaded in the hash map
	 * @param userName the username of the user
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
	 * @param userName the username of the user to be removed
	 * @param password the associated password
	 * Removes User from Domain and Database
	 * returns True if operation Succeeds, False if not
	 * @return true if the removal was successful, false if otherwise
	 */
	public boolean removeUser(String userName,String password)
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
	 * Removes the key from the hash map
	 * @param userName the key to be removed from the hash map
	 * @return true if the removal was successful, false otherwise
	 */
	public boolean removeUserFromHashMap(String userName)
	{
		if (users.containsKey(userName))
		{
			users.remove(userName);
			return true;
		}
		return false;
	}
	
	/**
	 * Attempts to update the display name of a given user.
	 * @param userName the username of the user to be updated.
	 * @param password the associated password
	 * @param display the new display name
	 * @return true on success and false if user does not exist
	 */
	public boolean updateDisplayName(String userName,String password, String display)
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
	 */
	public boolean persistUpdates()
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
	public String findDisplayName(String target) 
	{
		return PersonGateway.getDisplayName(target);
	}
}