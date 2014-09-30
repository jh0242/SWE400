package domain_model;
/* @author Josh McMillen */
public class Goal
{
	private int goalID;
	private long dateStart;
	private long dateEnd;
	private int goal;
	private boolean completed;
	
	public Goal(int goalID,long dateStart,long dateEnd,int goal,boolean completed)
	{
		this.setGoalID(goalID);
		this.setDateStart(dateStart);
		this.setDateEnd(dateEnd);
		this.setGoal(goal);
		this.setCompleted(completed);
	}

	public int getGoalID() {
		return goalID;
	}

	public void setGoalID(int goalID) {
		this.goalID = goalID;
	}

	public long getDateStart() {
		return dateStart;
	}

	public void setDateStart(long dateStart) {
		this.dateStart = dateStart;
	}

	public long getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(long dateEnd) {
		this.dateEnd = dateEnd;
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
