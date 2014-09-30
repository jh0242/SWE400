package domain_model;

import java.util.Hashtable;
import java.util.Map;

/* @author Josh McMillen */
public class GoalList
{
	Map<Integer,Goal> goalList;
	
	public GoalList()
	{
		goalList = new Hashtable<Integer,Goal>();
	}
	
	public void addGoal(Goal goal)
	{
		goalList.put(goal.getGoalID(), goal);
	}
	
	public void removeGoal(int goalID)
	{
		goalList.remove(goalID);
	}
	
	public Goal getGoal(int goalID)
	{
		return goalList.get(goalID);
	}
	
	public void updateGoal(Goal goal)
	{
		goalList.put(goal.getGoalID(), goal);
	}
}
