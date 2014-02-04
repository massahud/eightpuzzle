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

package massahud.ai.eightpuzzle;

import massahud.ai.solver.EvaluationFunction;
import massahud.ai.solver.SearchNode;

/**
 *
 * @author Geraldo Massahud
 * 
 */
public class ManhattanDistanceHeuristic implements EvaluationFunction<Direction, String> {
    private Puzzle8 goal;
    
    public ManhattanDistanceHeuristic(final String goal) {
        this.goal = new Puzzle8(goal);
    }
    
    public ManhattanDistanceHeuristic(final Puzzle8 goal) {
        this.goal = new Puzzle8(goal.getState());
    }
    
    @Override
    public int evaluate(SearchNode<Direction, String> searchNode) {
        final String state = searchNode.getState();
        final Puzzle8 puzzle = new Puzzle8(state);
        int distance = 0;
        for (int i = 1; i < state.length(); i++) {
            distance += Math.abs(goal.getCol(i) - puzzle.getCol(i)) + Math.abs(goal.getRow(i) - puzzle.getRow(i));            
        }        
        return distance;
    }
    
}
