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

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 
 * @author Geraldo Massahud
 * 
 */
public class Solver<Action, State> {

	private static final int QUEUE_INITIAL_CAPACITY = 11;

	/**
	 * 
	 * @param initialState
	 * @param evaluationFunction
	 * @param expander
	 * @param goalEvaluator
	 * @return
	 */
	public List<SearchNode<Action, State>> solve(
			SearchNode<Action, State> initialState,
			final EvaluationFunction<Action, State> evaluationFunction,
			final Expander<Action, State> expander,
			final GoalEvaluator<Action, State> goalEvaluator) {

		if (initialState == null) {
			throw new IllegalArgumentException("initialState can not be null");
		}
		final PriorityQueue<SearchNode<Action, State>> fringe = createPriorityQueue(evaluationFunction);

		fringe.add(initialState);

		SearchNode<Action, State> node = fringe.poll();
		while (node != null) {
			if (goalEvaluator.satisfyGoal(node)) {
				return generateSolution(node);
			} else {
				fringe.addAll(expander.expand(node));
			}			
			node = fringe.poll();			
		}		
		return Collections.emptyList();		
	}

	private List<SearchNode<Action, State>> generateSolution(
			SearchNode<Action,State> goalState) {
		LinkedList<SearchNode<Action, State>> solution = new LinkedList<>();
		SearchNode<Action,State> node = goalState;

		while (node != null) {
			solution.push(node);
			node = node.getFromNode();
		}
		return solution;
	}

	/**
	 * Creates a PriorityQueue with a comparator based on the evaluation
	 * function
	 * 
	 * @param evaluationFunction
	 * @return
	 */
	private PriorityQueue<SearchNode<Action, State>> createPriorityQueue(
			final EvaluationFunction<Action, State> evaluationFunction) {

		return new PriorityQueue<>(QUEUE_INITIAL_CAPACITY,
				new Comparator<SearchNode<Action, State>>() {
					@Override
					public int compare(SearchNode<Action, State> s1,
							SearchNode<Action, State> s2) {
						Integer cost1 = s1.getCost();
						Integer cost2 = s2.getCost();

						// set the nodes costs if they are not set yet
						if (cost1 == null) {
							cost1 = evaluationFunction.evaluate(s1);
							s1.setCost(cost1);
						}

						if (cost2 == null) {
							cost2 = evaluationFunction.evaluate(s2);
							s2.setCost(cost2);
						}
						return cost1 - cost2;
					}
				});

	}
}
