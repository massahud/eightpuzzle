/*
 * The MIT License
 *
 * Copyright 2014 Geraldo Massahud.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package massahud.ai.solver;

import java.util.Objects;

/**
 * A SearchNode, contains the previous SearchNode from where the action should be applied to get into this SearchNode state
 *   
 * @author massahud
 *
 * @param <Action> action type
 * @param <State> state type
 */
public class SearchNode<Action, State> {
	
	private final State state;
	private final Action fromAction;
	private final SearchNode<Action, State> fromNode;	
	private Integer cost;

	
	/**
	 * Constructs the search node
	 * 
	 * @param fromNode the previous SearchNode
	 * @param fromAction the action applied on the previous SearchNode to get on this SearchNode state
	 * @param state the current state
	 */
	public SearchNode(SearchNode fromNode, Action fromAction, State state) {
		if (state == null) {
			throw new IllegalArgumentException("Can not have a null state.");
		}
		if (fromAction == null && fromNode != null) {
			throw new IllegalArgumentException("Can not have a previous node without an applied action.");
		}
		if (fromAction != null && fromNode == null) {
			throw new IllegalArgumentException("Can not have an action without a previous not to apply it.");
		}
		
		this.state = state;
		this.fromNode = fromNode;
		this.fromAction = fromAction;
	}	
	
	/**
	 * The previous SearchNode
	 * @return the previous SearchNode or null, if it is a root SearchNode
	 */
	public SearchNode<Action, State> getFromNode() {
		return fromNode;
	}
	
	/**
	 * The action to get in this SearchNode state
	 * @return the action applied on the previous SearchNode to get into this SearchNode state, or null if it is a root SearchNode
	 */
	public Action getFromAction() {
		return fromAction;
	}
	
	/**
	 * The problem state of this SearchNode
	 * @return the problem state of this SearchNode
	 */
	public State getState() {
		return state;
	}
	
	/**
	 * SearchNode cost, if it is null it means only that this SearchNode was not evaluated yet.
	 * @return cost from the evaluation function, or null if the SearchNode was not evaluated yet
	 */
	public Integer getCost() {
		return this.cost;
	}
	
	/**
	 * Sets the SearchNode cost, the Solver sets this cost when it evaluates the SearchNode. If it is already set the solver 
	 * won't use the evaluation function on this node. 
	 * @param cost
	 */
	public void setCost(Integer cost) {
		this.cost = cost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hashCode(state);
		result = prime * result + Objects.hashCode(fromAction);
		result = prime * result + Objects.hashCode(fromNode);
		return result;
	}

	@Override
	public boolean equals(Object obj) {		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
				
		@SuppressWarnings("unchecked")
		SearchNode<Action, State> other = (SearchNode<Action, State>) obj;
		
		return Objects.equals(getState(), other.getState())
				&& Objects.equals(getFromAction(), other.getFromAction())
				&& Objects.equals(getFromNode(), other.getFromNode());		
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SearchNode[");
		builder.append("state=").append(state);
		builder.append(", fromAction=").append(fromAction);
		builder.append(", cost=").append(cost);
		if (fromNode != null) {
			builder.append(", fromNode.state=").append(fromNode.state);
		} else {
			builder.append(", fromNode=null");
		}
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
