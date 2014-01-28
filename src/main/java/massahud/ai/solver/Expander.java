package massahud.ai.solver;

import java.util.List;

/**
 * Function object that expands a SearchNode
 * @author massahud
 *
 * @param <Action> action type
 * @param <State> state type
 */
public interface Expander<Action, State> {
	/**
	 * Expands the SearchNode, returning a list of expanded SearchNodes
	 * @param node node to be expanded
	 * @return list of expanded nodes
	 */
	public List<SearchNode<Action,State>> expand(SearchNode<Action, State> node);
}
