import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import domain_model.Person;


/**
 * @author Patrick Joseph Flanagan
 *
 */
public class PersonTest
{

	/**
	 * Test the getters and setters for sanity.
	 */
	@Test
	public void testGettersSetters()
	{
		Person p = new Person();
		p.setName("Fred");
		assertEquals("Fred", p.getName());
		p.setPassword("hunter2");
		assertEquals("hunter2", p.getPassword());
	}
	
	/**
	 * Test the lazy load functionality of getting the friends array.
	 */
	@Test
	public void testGetFriends() {
		Person p = new Person();
		Vector<Person> f = p.getFriends();
		assertNotNull(f);
		assertEquals(f.size(), 0);
	}

}
