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

import static massahud.ai.eightpuzzle.Direction.*;
import static org.fest.assertions.Assertions.assertThat;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author Geraldo Massahud
 */
public class Puzzle8Test {

    public Puzzle8Test() {
    }

    @DataProvider
    public Object[][] states() {
        return new Object[][]{
            {"123405678"},
            {"012345678"},
            {"876543210"},};
    }
    
    @DataProvider
    public Object[][] invalidStates() {
        return new Object[][]{
            {"01234567"}, // less pieces
            {"011234567"}, // duplicated piece 
            {"112345678"}, // duplicated + no blank
            {"012345679"}, // invalid value
            {null},
            {""}
        }; 
    }

    @Test(dataProvider = "states")
    public void shouldStartWithGivenState(String state) {
        final Puzzle8 puzzle = new Puzzle8(state);
        assertThat(puzzle.getState()).isEqualTo(state);
    }
    
    @Test(dataProvider = "invalidStates", expectedExceptions = InvalidState.class)
    public void shouldThrowInvalidStateExceptionWithInvalidState(String state) {
        final Puzzle8 puzzle = new Puzzle8(state);
    }

    @Test
    public void shouldReturnCorrectPieceColAndRow() {
        final Puzzle8 puzzle = new Puzzle8("876543210");
        assertThat(puzzle.getRow(8)).isEqualTo(0);
        assertThat(puzzle.getCol(8)).isEqualTo(0);
        assertThat(puzzle.getRow(7)).isEqualTo(0);
        assertThat(puzzle.getCol(7)).isEqualTo(1);
        assertThat(puzzle.getRow(6)).isEqualTo(0);
        assertThat(puzzle.getCol(6)).isEqualTo(2);

        assertThat(puzzle.getRow(5)).isEqualTo(1);
        assertThat(puzzle.getCol(5)).isEqualTo(0);
        assertThat(puzzle.getRow(4)).isEqualTo(1);
        assertThat(puzzle.getCol(4)).isEqualTo(1);
        assertThat(puzzle.getRow(3)).isEqualTo(1);
        assertThat(puzzle.getCol(3)).isEqualTo(2);

        assertThat(puzzle.getRow(2)).isEqualTo(2);
        assertThat(puzzle.getCol(2)).isEqualTo(0);
        assertThat(puzzle.getRow(1)).isEqualTo(2);
        assertThat(puzzle.getCol(1)).isEqualTo(1);
        assertThat(puzzle.getRow(0)).isEqualTo(2);
        assertThat(puzzle.getCol(0)).isEqualTo(2);
    }

    @Test
    public void shouldReturnCorrectPieceStatePosition() {
        final Puzzle8 puzzle = new Puzzle8("876543210");
        assertThat(puzzle.getStatePos(8)).isEqualTo(0);
        assertThat(puzzle.getStatePos(7)).isEqualTo(1);
        assertThat(puzzle.getStatePos(6)).isEqualTo(2);
        assertThat(puzzle.getStatePos(5)).isEqualTo(3);
        assertThat(puzzle.getStatePos(4)).isEqualTo(4);
        assertThat(puzzle.getStatePos(3)).isEqualTo(5);
        assertThat(puzzle.getStatePos(2)).isEqualTo(6);
        assertThat(puzzle.getStatePos(1)).isEqualTo(7);
        assertThat(puzzle.getStatePos(0)).isEqualTo(8);
    }

    @Test
    public void shouldMoveLeft() {
        Puzzle8 puzzle = new Puzzle8("120345678");
        puzzle.move(Direction.LEFT);
        assertThat(puzzle.getState()).isEqualTo("102345678");
        puzzle.move(Direction.LEFT);
        assertThat(puzzle.getState()).isEqualTo("012345678");

        puzzle = new Puzzle8("123450678");
        puzzle.move(Direction.LEFT);
        assertThat(puzzle.getState()).isEqualTo("123405678");
        puzzle.move(Direction.LEFT);
        assertThat(puzzle.getState()).isEqualTo("123045678");

        puzzle = new Puzzle8("123456780");
        puzzle.move(Direction.LEFT);
        assertThat(puzzle.getState()).isEqualTo("123456708");
        puzzle.move(Direction.LEFT);
        assertThat(puzzle.getState()).isEqualTo("123456078");

    }
    
    @Test
    public void shouldMoveRight() {
        Puzzle8 puzzle = new Puzzle8("012345678");
        puzzle.move(Direction.RIGHT);
        assertThat(puzzle.getState()).isEqualTo("102345678");
        puzzle.move(Direction.RIGHT);
        assertThat(puzzle.getState()).isEqualTo("120345678");

        puzzle = new Puzzle8("123045678");
        puzzle.move(Direction.RIGHT);
        assertThat(puzzle.getState()).isEqualTo("123405678");
        puzzle.move(Direction.RIGHT);
        assertThat(puzzle.getState()).isEqualTo("123450678");

        puzzle = new Puzzle8("123456078");
        puzzle.move(Direction.RIGHT);
        assertThat(puzzle.getState()).isEqualTo("123456708");
        puzzle.move(Direction.RIGHT);
        assertThat(puzzle.getState()).isEqualTo("123456780");
    }

    @Test(expectedExceptions = InvalidAction.class)
    public void shouldNotMoveLeft() {
        Puzzle8 puzzle = new Puzzle8("123456078");
        puzzle.move(Direction.LEFT);
    }
    
    @Test(expectedExceptions = InvalidAction.class)
    public void shouldNotMoveRight() {
        Puzzle8 puzzle = new Puzzle8("120345678");
        puzzle.move(Direction.RIGHT);
    }

    @Test
    public void shouldReturnTwoPossibleActionsOnCorners() {
        Puzzle8 puzzle = new Puzzle8("012345678");
        assertThat(puzzle.getPossibleActions()).hasSize(2).containsOnly(RIGHT, DOWN);
        puzzle = new Puzzle8("120345678");
        assertThat(puzzle.getPossibleActions()).hasSize(2).containsOnly(LEFT, DOWN);
        puzzle = new Puzzle8("123456078");
        assertThat(puzzle.getPossibleActions()).hasSize(2).containsOnly(RIGHT, UP);
        puzzle = new Puzzle8("123456780");
        assertThat(puzzle.getPossibleActions()).hasSize(2).containsOnly(LEFT, UP);
    }
    
    @Test
    public void shouldReturnThreePossibleActionsFromEdges() {
        Puzzle8 puzzle = new Puzzle8("102345678");
        assertThat(puzzle.getPossibleActions()).hasSize(3).contains(LEFT, RIGHT, DOWN);
        puzzle = new Puzzle8("123045678");
        assertThat(puzzle.getPossibleActions()).hasSize(3).contains(RIGHT, UP, DOWN);
        puzzle = new Puzzle8("123450678");
        assertThat(puzzle.getPossibleActions()).hasSize(3).contains(LEFT, UP, DOWN);
        puzzle = new Puzzle8("123456708");
        assertThat(puzzle.getPossibleActions()).hasSize(3).contains(LEFT, RIGHT, UP);
    }
    
    @Test
    public void shouldReturnFourPossibleActionsOnMiddle() {
        Puzzle8 puzzle = new Puzzle8("123405678");
        assertThat(puzzle.getPossibleActions()).hasSize(4).contains(LEFT, RIGHT, UP, DOWN);
    }    

}
