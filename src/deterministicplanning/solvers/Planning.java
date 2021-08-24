package deterministicplanning.solvers;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import deterministicplanning.models.Plan;
import deterministicplanning.models.PlanImpl;
import deterministicplanning.models.WorldModel;
import deterministicplanning.solvers.planningoutcomes.FailedPlanningOutcome;
import deterministicplanning.solvers.planningoutcomes.PlanningOutcome;
import deterministicplanning.solvers.planningoutcomes.SuccessfulPlanningOutcome;
import finitestatemachine.Action;
import finitestatemachine.State;
import markov.MDP;
import markov.Policy;
import markov.StateProbabilityDistribution;
import markov.impl.FunctionBasedMDPImpl;
import markov.impl.PairImpl;
import markov.impl.Policies;
import markov.impl.StateProbabilityDistributionHashImpl;

public class Planning {

	/**
	 * This function turns a world model, a starting state and a goal to a plan.
	 * 
	 * The implementation of this function is based uses Markov Decision Processes (MDPs), a supermodel of classic planning. 
	 * If you do not know what MDPs are, just do not look into this function.
	 * 
	 * As an issue, this function is relatively slow (as an exercise, try assessing its complexity).
	 * If you want to develop CS skills, try implementing your own solver, like using a depth first search.
	 * Can you think of an even faster algorithm? Try implementing it and see how much faster you can get!
	 * 
	 * @param <S>
	 * @param <A>
	 * @param wm
	 * @param startingState
	 * @param goalState
	 * @param maxHorizon
	 * @return
	 */
	public static<S extends State, A extends Action> PlanningOutcome resolve(WorldModel<S,A> wm, S startingState, S goalState, int maxHorizon) {
		BiFunction<S, A, StateProbabilityDistribution<S>> distribution = (s,a)->StateProbabilityDistributionHashImpl.newInstance(wm.getConsequenceOfPlaying(s,a));
		BiFunction<S, A, Double> reward = (s,a)->wm.getRewardFor(s,a);
		MDP<S, A> mdp = FunctionBasedMDPImpl.newInstance(wm.getStates(), distribution, reward, s->wm.getActionsFrom(s));
		Policy<S, A> policy = Policies.getOptimalPolicy(mdp, maxHorizon,true);
		List<PairImpl<S,A>> res = Policies.getMostProbableTrajectoryFollowing(mdp, startingState, maxHorizon, policy);
		
		Optional<PairImpl<S, A>>firstOccurrence = res.stream().filter(x->x.getLeft().equals(goalState)).findFirst();
		if(firstOccurrence.isEmpty())
			return FailedPlanningOutcome.NO_PLAN;

		return SuccessfulPlanningOutcome.newInstance(PlanImpl.newInstance(res.subList(0, res.indexOf(firstOccurrence.get()))));
	}


}
