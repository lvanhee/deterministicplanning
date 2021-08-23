package deterministicplanning.mains;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

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
 * This example is intended to show a minimal example for 
 * 1-creating a deterministic world model
 * 2-generating a plan to a given goal state
 * 
 * This code is intended for students seeking to develop stronger IT skills and seeking to produce more qualitative code.
 * Challenge yourself in the forum to see how to engineer some code as good as possible :)
 * 
 * With a basic implementation and a simple heuristic, a problem with 1.000.000 states can be handled in a few seconds. Can you beat it? 
 * 
 */
public class MainForAiDeveloppers {
	private enum MinimalMainState{START, GOAL, GOOD_FIRST_STEP, BAD_FIRST_STEP};
	private enum MinimalMainAction{FORWARD,STAND,LEFT,RIGHT};
	
	private static final State start = StringStateImpl.newInstance("start");
	private static final State goal = StringStateImpl.newInstance("goal");
	
	public static void main(String[] args)
	{
	/**
	 * 1- Creating a deterministic world model
	 */
	WorldModel<State,Action> wm = generateWorldModel();
	
	
	/**
	 * 2- Creating computing the result
	 * Write your own function instead of Planning.resolve.
	 * 
	 */
	final int horizon = 5;
	PlanningOutcome result = Planning.resolve(wm,start, goal, horizon);
	System.out.println(result);
	
	
	}

	private static WorldModel<State,Action> generateWorldModel() {
		Function<MinimalMainState, Set<MinimalMainAction>> actions = x->{
			Set<MinimalMainAction> res = new HashSet<>();
			res.add(STAND);
			switch (x) {
			case START: res.add(LEFT);res.add(RIGHT);break;
			case 
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + x);
			}
		}
		FunctionBasedWorldModelImpl.newInstance()
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
