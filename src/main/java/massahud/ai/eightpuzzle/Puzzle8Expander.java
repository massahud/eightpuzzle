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

import java.util.ArrayList;
import java.util.List;
import massahud.ai.solver.Expander;
import massahud.ai.solver.SearchNode;

/**
 *
 * @author Geraldo Massahud
 * 
 * Informações SVN
 * @version $Revision: $:
 * @author  Última modificação por $Author: $:
 * @date    $Date: $:
 */
class Puzzle8Expander implements Expander<Direction, String> {

    @Override
    public List<SearchNode<Direction, String>> expand(SearchNode<Direction, String> node) {
        Puzzle8 puzzle = new Puzzle8(node.getState());
        List<Direction> possibleActions = puzzle.getPossibleActions();
        
        List<SearchNode<Direction, String>> expansion = new ArrayList<>(possibleActions.size());
        for (Direction action : possibleActions) {
            puzzle.move(action);
            if (!puzzle.getState().equals(node.getState())) {
                expansion.add(new SearchNode<>(node, action, puzzle.getState()));
            }
            puzzle.move(action.getReverse());
        }
        return expansion;
    }

}
