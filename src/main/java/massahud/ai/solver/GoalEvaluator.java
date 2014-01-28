package massahud.ai.solver;

public interface GoalEvaluator<Action, State> {
	
	public boolean satisfyGoal(SearchNode<Action, State> searchNode);

}
