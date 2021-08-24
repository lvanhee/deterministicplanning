package deterministicplanning.solvers.planningoutcomes;

import deterministicplanning.models.Plan;
import finitestatemachine.Action;
import finitestatemachine.State;

public class SuccessfulPlanningOutcome<S extends State, A extends Action> implements PlanningOutcome{
	private final Plan<S,A> p;
	
	private SuccessfulPlanningOutcome(Plan<S,A> p) {this.p = p;}

	public static<S extends State, A extends Action> SuccessfulPlanningOutcome<S, A> newInstance(Plan<S, A> plan) {
		return new SuccessfulPlanningOutcome<>(plan);
	};
	
	public String toString()
	{
		return p.toString();
	}

	public Plan<S, A> getPlan() {
		return p;
	}

}
