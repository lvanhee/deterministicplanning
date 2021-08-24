package deterministicplanning.models;

import java.util.List;

import finitestatemachine.Action;
import finitestatemachine.State;
import markov.impl.PairImpl;

public class PlanImpl<S extends State, A extends Action> implements Plan<S,A> {

	private final List<PairImpl<S, A>> pairs;
	public PlanImpl(List<PairImpl<S, A>> stateActionPairs) {
		this.pairs = stateActionPairs;
	}

	public static<S extends State, A extends Action> Plan<S,A> newInstance(List<PairImpl<S,A>> stateActionPairs) {
		return new PlanImpl<S,A>(stateActionPairs);
	}
	
	public String toString()
	{
		return pairs.toString();
	}

}
