package deterministicplanning.models;

import java.util.Set;

import finitestatemachine.Action;
import finitestatemachine.State;

public interface WorldModel<S extends State,A extends Action> {

	Set<S> getStates();

	S getConsequenceOfPlaying(S s, A a);

	Double getRewardFor(S s, A a);

	Set<A> getActionsFrom(S s);

}
