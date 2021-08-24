package deterministicplanning.mains;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

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

	/**
	 * First, define explicit types for states of actions. As, in this example, the set of states is bounded and
	 * known, an enum is enough. But, probably, for your models, you will need to define a new class that implements state.
	 * 
	 * @author loisv
	 *
	 */
	private enum MinimalMainState implements State {START, GOAL, GOOD_FIRST_STEP, BAD_FIRST_STEP};
	private enum MinimalMainAction implements Action{FORWARD,STAND,LEFT,RIGHT};

	public static void main(String[] args)
	{
		/**
		 * 1- Creating a deterministic world model
		 */
		WorldModel<MinimalMainState,MinimalMainAction> wm = generateWorldModel();


		/**
		 * 2- Creating computing the result
		 * Write your own function instead of Planning.resolve.
		 * 
		 */
		final int horizon = 5;
		PlanningOutcome result = Planning.resolve(wm,MinimalMainState.START, MinimalMainState.GOAL, horizon);
		System.out.println(result);
	}

	private static WorldModel<MinimalMainState,MinimalMainAction> generateWorldModel() {
		Function<MinimalMainState, Set<MinimalMainAction>> actionsPerState = 
				x->{
					Set<MinimalMainAction> res = new HashSet<>();
					res.add(MinimalMainAction.STAND);
					switch (x) {
					case START: res.add(MinimalMainAction.LEFT);res.add(MinimalMainAction.RIGHT);break;
					case GOOD_FIRST_STEP:res.add(MinimalMainAction.FORWARD); break;
					}
					return res;
				};

				BiFunction<MinimalMainState, MinimalMainAction, MinimalMainState> transition = (s,a)->
				{
					if(a.equals(MinimalMainAction.STAND))return s;
					if(s.equals(MinimalMainState.START)&&a.equals(MinimalMainAction.RIGHT))return MinimalMainState.GOOD_FIRST_STEP;
					if(s.equals(MinimalMainState.START)&&a.equals(MinimalMainAction.LEFT))return MinimalMainState.BAD_FIRST_STEP;
					if(s.equals(MinimalMainState.GOOD_FIRST_STEP)&&a.equals(MinimalMainAction.FORWARD))return MinimalMainState.GOAL;
					throw new Error();
				};
				return FunctionBasedDeterministicWorldModel.newInstance(
						Arrays.asList(MinimalMainState.values()).stream().collect(Collectors.toSet()),
						transition,
						(s,a)->-1d,
						actionsPerState);
	}

}
