package deterministicplanning.mains;

import deterministicplanning.models.WorldModel;
import deterministicplanning.models.pedagogy.ListbasedNongenericWorldModel;
import deterministicplanning.solvers.Planning;
import deterministicplanning.solvers.PlanningOutcome;
import finitestatemachine.Action;
import finitestatemachine.State;
import finitestatemachine.impl.StringActionImpl;
import finitestatemachine.impl.StringStateImpl;

/**
 * 
 * @author loisv
 * 
 * This example is intended to show the simplest possible code for 
 * 1-creating a deterministic world model
 * 2-generating a plan to a given goal state 
 * 
 * The code is intended to have the least requirements in terms of IT skills, 
 * but those willing to develop as CS expert can/should seek to make much better code.
 * Challenge yourself in the forum to see how to engineer some code as good as possible :)
 */
public class MainMinimalIt {

	private static final State start = StringStateImpl.newInstance("start");
	private static final State goal = StringStateImpl.newInstance("goal");

	public static void main(String[] args)
	{
		/**
		 * 1- Creating a deterministic world model
		 */
		WorldModel<State,Action> wm = generateWorldModel();

		final int horizon = 5;

		/**
		 * 2- Creating computing the result
		 */
		PlanningOutcome result = Planning.resolve(wm,start, goal, horizon);
		System.out.println(result);	
	}

	private static WorldModel<State,Action> generateWorldModel() {
		State goodFirstStep = StringStateImpl.newInstance("good_first_step");
		State badFirstStep = StringStateImpl.newInstance("bad_first_step");


		Action forward = StringActionImpl.newInstance("forward");
		Action standStill = StringActionImpl.newInstance("stand_still");
		Action left = StringActionImpl.newInstance("left");
		Action right = StringActionImpl.newInstance("right");
		ListbasedNongenericWorldModel model = new ListbasedNongenericWorldModel();
		model.addTransition(start,left,goodFirstStep);
		model.addTransition(goodFirstStep,forward,goal);
		model.addTransition(start,right,badFirstStep);
		model.addTransition(start, standStill, start);
		model.addTransition(goal, standStill, goal);
		model.addTransition(badFirstStep, standStill, badFirstStep);
		model.addTransition(goodFirstStep, standStill, goodFirstStep);

		return model;
	}

}
