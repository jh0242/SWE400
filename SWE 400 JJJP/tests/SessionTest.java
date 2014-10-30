import static org.junit.Assert.*;

import org.junit.Test;

import domain_model.Person;
import domain_model.Session;

/**
 * @author Patrick Joseph Flanagan
 *
 */
public class SessionTest
{
	/**
	 * Test creation of session objects.
	 */
	@Test
	public void testSessionCreation() {
		Session s = Session.getInstance();
		Person p = s.getPerson();
		p = new Person(1337);
		s.setPerson(p);
		assertNotNull(s.getPerson());
		assertEquals(s.getPerson().getID(), 1337);
	}
}
