

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import data_mapper.PersonMapper;
import domain_model.Person;

public class PersonMapperTest 
{
	@Test
	public void testGetUser() throws SQLException {
		Person userA = PersonMapper.getPerson("userA", "123");
		System.out.println(userA.getUsername());
		assertEquals("userA",userA.getUsername());
		assertEquals("newdisplay",userA.getFullname());
	}
}
