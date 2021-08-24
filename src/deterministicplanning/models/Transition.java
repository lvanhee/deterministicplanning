package deterministicplanning.models;

import finitestatemachine.Action;
import finitestatemachine.State;

public class Transition {
	
	private final State start;
	private final Action action;
	private final State end;

	public Transition(State s1, Action a, State s2) {
		this.start = s1;
		this.action = a;
		this.end = s2;
	}

	public State getStart() {
		return start;
	}

	public State getEnd() {
		return end;
	}

	public Action getAction() {
		return action;
	}

}
