module deterministicplanning {
	requires finitestatemachine;
	requires markov;
	
	exports deterministicplanning.models;
	exports deterministicplanning.solvers;
	exports deterministicplanning.solvers.planningoutcomes;
}