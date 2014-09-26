import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import domain_model.Activity;

/**
 * 
 * @author jh0242
 *
 */
public class ActivityTest 
{
	Activity act;
	
	/**
	 * Initializes act before each test case.
	 */
	@Before
	public void initialize()
	{
		act = new Activity("Running", "10221984", 1111, 400);
	}
	
	/**
	 * Tests that Activity initializes to the correct values.
	 */
	@Test
	public void testInitialization()
	{
		assertEquals("Running", act.getName());
		assertEquals("10221984", act.getDate());
		assertEquals(1111, act.getTime());
		assertEquals(400, act.getCaloriesBurned());
	}
	
	/**
	 * Tests that setName sets the Activity's new name correctly.
	 */
	@Test
	public void testSetName()
	{
		act.setName("Jogging");
		assertEquals("Jogging", act.getName());
	}
	
	/**
	 * Tests that setDate sets the Activity's new date correctly.
	 */
	@Test
	public void testSetDate()
	{
		act.setDate("10222014");
		assertEquals("10222014", act.getDate());
		act.setDate("01052014");
		assertEquals("01052014", act.getDate());
	}
	
//May not be needed.  May be handled in the presentation layer.
//	/**
//	 * Tests that setDate will not set a date that has a month over the
//	 * value 12.
//	 */
//	@Test
//	public void testSetBadDate()
//	{
//		act.setDate("91202014");
//		assertEquals("10221984", act.getDate());
//		act.setDate("19202014");
//		assertEquals("19202014", act.getDate());
//	}
	
	/**
	 * Tests that setTime sets the Activity's new time correctly.
	 */
	@Test
	public void testSetTime()
	{
		act.setTime(2222);
		assertEquals(2222, act.getTime());
	}
	
	/**
	 * Tests that setCaloriesBurned sets the Activity's new calories burned correctly.
	 */
	@Test
	public void testSetCaloriesBurned()
	{
		act.setCaloriesBurned(500);
		assertEquals(500, act.getCaloriesBurned());
	}
}
