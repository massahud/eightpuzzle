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

import massahud.ai.solver.SearchNode;
import static org.fest.assertions.Assertions.assertThat;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author Geraldo Massahud
 */
public class ManhattanDistanceHeuristicNGTest {
    
    @DataProvider
    public Object[][] onlyCols() {
        
        return new Object[][] {
            {"012345678","102345678", 2},
            {"012345678","012534678", 4},
            {"012345768","012345678", 2},
            {"012345768","021354678", 6},
        };
        
    }
    
    @DataProvider
    public Object[][] onlyRows() {
        return new Object[][] {
            {"015345678","012342678", 2},
            {"012345678","012375648", 2},
            {"012345678","072345618", 4},
            {"012345678","072315648", 4}
        };
    }
    
    @DataProvider
    public Object[][] colsAndRows() {
        return new Object[][] {
            {"012345678","812345670", 8},
            {"012345678","412305678", 4},
            {"012345678","082345671", 6},
            {"012345678","062341578", 8} 
        };
    }
    
    @Test
    public void shouldReturnOnGoal() {
        final Puzzle8 goal = new Puzzle8("018273645");
        final ManhattanDistanceHeuristic manhattan = new ManhattanDistanceHeuristic(goal);
        assertThat(manhattan.evaluate(new SearchNode<Direction, String>(null, null, goal.getState()))).isZero();
    }
    
    @Test(dataProvider = "onlyCols")
    public void shouldReturnColumnDistance(String goal, String state, int distance) {        
        final ManhattanDistanceHeuristic manhattan = new ManhattanDistanceHeuristic(goal);
        assertThat(manhattan.evaluate(new SearchNode<Direction, String>(null, null, state))).isEqualTo(distance);
    }
    
    @Test(dataProvider = "onlyRows")
    public void shouldReturnRowDistance(String goal, String state, int distance) {
        
    }
    
    @Test(dataProvider = "colsAndRows")
    public void shouldReturnRowColumnDistance(String goal, String state, int distance) {
        
    }

  
}

