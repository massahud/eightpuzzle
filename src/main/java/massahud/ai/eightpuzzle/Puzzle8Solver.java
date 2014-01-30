/*
 * The MIT License
 *
 * Copyright 2014 t0067421.
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import massahud.ai.solver.SearchNode;
import massahud.ai.solver.Solver;

/**
 *
 * @author Geraldo Massahud
 *
 */
public class Puzzle8Solver extends Solver<Direction, String> {

    public static void main(String[] args) {
        final Puzzle8Solver solver = new Puzzle8Solver();

        String goalState = "123456780";
        char[] pieces = goalState.toCharArray();
        shuffle(pieces);
        String initialState = String.valueOf(pieces);
        Puzzle8 goal = new Puzzle8(goalState);
        Puzzle8 initial = new Puzzle8(initialState);
        
        System.out.println("Initial:");
        System.out.println(initial);
        
        System.out.println("Goal:");
        System.out.println(goal);
                
        List<SearchNode<Direction, String>> solution = solver.solve(new SearchNode<Direction, String>(null, null, initialState), new ManhattanDistanceHeuristic(goal), new Puzzle8Expander(), new Puzzle8GoalEvaluator(goalState));
        
        if (solution.isEmpty()) {
            System.out.println("No solution");
            System.out.println(solver.getNumberOfVisitedStates() + " states visited");
        } else {
            System.out.println("Found solution with " + solution.size() + " steps");
            System.out.println(solver.getNumberOfVisitedStates() + " states visited");
            printSolution(solution);
        }
        
        
    }

    private static void shuffle(char[] array) {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            int pos = random.nextInt(array.length);
            char o = array[pos];
            array[pos] = array[i];
            array[i] = o;
        }
    }

    private static void printSolution(List<SearchNode<Direction, String>> solution) {
        final Puzzle8 puzzle = new Puzzle8(solution.get(0).getState());        
        for (SearchNode<Direction, String> node : solution) {
            if (node.getFromAction() != null) {
                puzzle.move(node.getFromAction());
                System.out.println(node.getFromAction());
            }            
            System.out.println(puzzle);            
        }
    }
    
    
}
