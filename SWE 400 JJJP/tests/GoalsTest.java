import static org.junit.Assert.*;

import org.junit.Test;

import domain_model.Goal;
import domain_model.GoalList;


public class GoalsTest {

	@Test
	public void testInitialization() {
		GoalList glist = new GoalList();
		Goal g = new Goal(10, 100, 88, 7,false);
		glist.addGoal(g);
		assertEquals(g.getGoalID(),glist.getGoal(g.getGoalID()).getGoalID());
		assertEquals(g.getDateEnd(),glist.getGoal(g.getGoalID()).getDateEnd());
		assertEquals(g.getDateStart(),glist.getGoal(g.getGoalID()).getDateStart());
		assertEquals(g.getGoal(),glist.getGoal(g.getGoalID()).getGoal());
		assertFalse(glist.getGoal(g.getGoalID()).isCompleted());
		g.setCompleted(true);
		assertTrue(glist.getGoal(g.getGoalID()).isCompleted());
		
	}

}
