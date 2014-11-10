

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import data_gateway.PersonGateway;
import data_mapper.PersonMapper;
import domain_model.Person;

public class PersonMapperTest 
{
	@Test
	public void testGetUser() throws SQLException {
		PersonGateway.insert("pm01U", "pm01P", "pm01D");
		PersonMapper pm = PersonMapper.getInstance();
		Person userA = pm.getPerson("pm01U", "pm01P");
		assertEquals("pm01U",userA.getUsername());
		assertEquals("pm01D",userA.getFullname());
		pm.updateDisplayName("pm01U","pm01P","pm01New");
		pm.persistUpdates();
		assertEquals("pm01New",pm.findDisplayName("pm01U"));
		PersonGateway.removeByUserName("pm01U");
	}
}
