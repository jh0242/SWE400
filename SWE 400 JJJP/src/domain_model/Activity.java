package domain_model;

/**
 * 
 * @author Joshua Helman
 *
 */
public class Activity
{
	private String name, date;
	private int time, caloriesBurned;
	
	public Activity(String name, String date, int time, int caloriesBurned)
	{
		this.name = name;
		this.date = date;
		this.time = time;
		this.caloriesBurned = caloriesBurned;
	}
	
	/**
	 * Gets the name of the Activity.
	 * @return String name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets this Activity's name to name.
	 * @param String name
	 */
	public void setName(String newName)
	{
		this.name = newName;
	}

	/**
	 * Gets the date the Activity was created (MM/DD/YY).
	 * @return String date
	 */
	public String getDate() 
	{
		return date;
	}
	
	/**
	 * Sets this Activity's date to newDate (MM/DD/YY).
	 * @param String date
	 */
	public void setDate(String newDate)
	{
		// Checks that the value entered for month is valid ie. 1-12.  May not be needed.
//		if (newDate.charAt(0) != '0' && newDate.charAt(0) != '1'
//				&& (newDate.charAt(1) == '0' || newDate.charAt(1) == '1' || newDate.charAt(1) == '2'))
//			return;
		this.date = newDate;
	}
	
	/**
	 * Gets the time that the Activity took to complete.
	 * @return int time
	 */
	public int getTime()
	{
		return time;
	}
	
	/**
	 * Sets this Activity's time to newTime
	 * @param int time
	 */
	public void setTime(int newTime)
	{
		this.time = newTime;
	}

	/**
	 * Gets the amount of calories burned during the Activity.
	 * @return int caloriesBurned
	 */
	public int getCaloriesBurned()
	{
		return caloriesBurned;
	}
	
	/**
	 * Sets this Activity's caloriesBurned to newCaloriesBurned.
	 * @param int caloriesBurned
	 */
	public void setCaloriesBurned(int newCaloriesBurned)
	{
		this.caloriesBurned = newCaloriesBurned;
	}
}
