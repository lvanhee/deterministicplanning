package deterministicplanning.models;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import finitestatemachine.Action;
import finitestatemachine.State;

public class FunctionBasedDeterministicWorldModel<S extends State, A extends Action> implements WorldModel<S, A>{
	private final Set<S> states;
	private final BiFunction<S, A, S> transition;
	private final BiFunction<S, A, Double> reward;
	private final Function<S, Set<A>>actionsPerState;
	
	private FunctionBasedDeterministicWorldModel(Set<S> states, BiFunction<S, A, S> transition, BiFunction<S, A, Double> reward, Function<S, Set<A>>actionsPerState) {
		this.states = states;
		this.transition = transition;
		this.reward = reward;
		this.actionsPerState = actionsPerState;
	}
	@Override
	public Set<S> getStates() {
		return states;
	}

	@Override
	public S getConsequenceOfPlaying(S s, A a) {
		return transition.apply(s, a);
	}

	@Override
	public Double getRewardFor(S s, A a) {
		return reward.apply(s,a);
	}

	@Override
	public Set<A> getActionsFrom(S s) {
		return actionsPerState.apply(s);
	}
	
	public static<S extends State, A extends Action> FunctionBasedDeterministicWorldModel<S, A> newInstance(Set<S> states, BiFunction<S, A, S> transition, BiFunction<S, A, Double> reward, Function<S, Set<A>>actionsPerState) {
		return new FunctionBasedDeterministicWorldModel<>(states, transition, reward, actionsPerState);
	}

}
