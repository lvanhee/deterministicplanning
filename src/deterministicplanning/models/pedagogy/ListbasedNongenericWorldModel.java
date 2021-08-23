package deterministicplanning.models.pedagogy;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import deterministicplanning.models.Transition;
import deterministicplanning.models.WorldModel;
import finitestatemachine.Action;
import finitestatemachine.State;

/**
 * 
 * @author loisv
 * 
 * This world model is built for being fully understandable while requiring the least prerequisites in terms of IT
 * knowledge.
 * 
 * All the states, actions, and transitions are represented explicitly using Java lists and the genericity is hidden
 * away by only using raw "State" and "Action" types.
 * 
 *  While it allows to work with minimal knowledge of Java/programming, this class has some limitations:
 *  -it has a low computational efficiency, both in space and memory
 *  -using it in practice involves making a lot of "casting" operations, which can/should be avoided
 *  -the statespace is necessarily finite
 *  -the object is mutable and can thus get to inconsistent behavior if altered
 *  
 *   If you want to develop a CS AI skillset, try working with the FunctionBased world model, it is 
 *   state-of-the-art quality programming.
 *
 */
public class ListbasedNongenericWorldModel implements WorldModel<State, Action>
{
	private final List<Transition> transitions = new LinkedList<>();	
	
	public void addTransition(State s1, Action a, State s2) {
		for(Transition t:transitions)
			if(t.getStart().equals(s1)&&t.getAction().equals(a))
				throw new Error("A transition for "+s1+" and "+a+" has already been defined. You should not try to define it again, as the model is deterministic.");
		transitions.add(new Transition(s1,a,s2));
	}


	@Override
	public Set<State> getStates() {
		Set<State> result = new HashSet<>();
		for(Transition t: transitions)
		{
			result.add(t.getStart());
			result.add(t.getEnd());
		}
		return result;
	}


	@Override
	public State getConsequenceOfPlaying(State s, Action a) {
		for(Transition t:transitions)
			if(t.getStart().equals(s)&&t.getAction().equals(a))
				return t.getEnd();
		throw new Error("No transition defined for:"+s+" "+a);
	}


	@Override
	public Double getRewardFor(State s, Action a) {
		return -1d;
	}


	@Override
	public Set<Action> getActionsFrom(State s) {
		Set<Action> result = new HashSet<>();
		for(Transition t: transitions)
		{
			if(t.getStart().equals(s))
				result.add(t.getAction());
		}
		return result;
	}
	
}
