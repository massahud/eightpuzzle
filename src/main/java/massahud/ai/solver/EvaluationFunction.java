package massahud.ai.solver;

import java.util.Comparator;

/**
 * The evaluation function of a SearchNode, implementation is problem specific
 * @author massahud
 *
 * @param <Action> type of the problem actions
 * @param <State> type of the problem states
 */
public interface EvaluationFunction<Action, State> {
	
	/**
	 * Evaluates a SearchNode cost
	 * @param searchNode
	 * @return
	 */
	public int evaluate(SearchNode<Action, State> searchNode);

}
