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

import static massahud.ai.eightpuzzle.Direction.DOWN;
import static massahud.ai.eightpuzzle.Direction.LEFT;
import static massahud.ai.eightpuzzle.Direction.RIGHT;
import static massahud.ai.eightpuzzle.Direction.UP;

import java.util.ArrayList;
import java.util.List;

import massahud.ai.solver.InvalidAction;
import massahud.ai.solver.InvalidState;

/**
 *
 * @author Geraldo Massahud
 *
 */
public class Puzzle8 {

    public static final int ROWS = 3;
    public static final int COLS = 3;

    private final StringBuilder state;

    private Integer blankStatePos;

    public Puzzle8(String state) {
        verifyState(state);
        this.state = new StringBuilder(state);
        blankStatePos = state.indexOf("0");
    }

    private void verifyState(String state) {

        if (state == null) {
            throw new InvalidState(state);
        }
        if (state.length() != 9) {
            throw new InvalidState(state);
        }

        if (!(state.contains("0")
                && state.contains("1")
                && state.contains("2")
                && state.contains("3")
                && state.contains("4")
                && state.contains("5")
                && state.contains("6")
                && state.contains("7")
                && state.contains("8"))) {
            throw new InvalidState(state);
        }

        if (state.matches(".*[^0-8].*")) {
            throw new InvalidState(state);
        }
    }

    public String getState() {
        return state.toString();
    }

    public final int getStatePos(int piece) {
        if (piece == 0) {
            return blankStatePos;
        }
        return state.indexOf(String.valueOf(piece));
    }

    public int getCol(int piece) {
        return getColFromStatePos(getStatePos(piece));
    }

    public int getRow(int piece) {
        return getRowFromStatePos(getStatePos(piece));
    }

    private int getRowFromStatePos(int statePos) {
        return statePos / COLS;
    }

    private int getColFromStatePos(int statePos) {
        return statePos % COLS;
    }

    void move(Direction direction) {
        if (!getPossibleActions().contains(direction)) {
            throw new InvalidAction(direction);
        }
        switch (direction) {
            case LEFT:
                state.setCharAt(blankStatePos, state.charAt(blankStatePos - 1));
                state.setCharAt(blankStatePos - 1, '0');
                blankStatePos = blankStatePos - 1;
                break;
            case RIGHT:
                state.setCharAt(blankStatePos, state.charAt(blankStatePos + 1));
                state.setCharAt(blankStatePos + 1, '0');
                blankStatePos = blankStatePos + 1;
                break;
            case UP:
                state.setCharAt(blankStatePos, state.charAt(blankStatePos - COLS));
                state.setCharAt(blankStatePos - COLS, '0');
                blankStatePos = blankStatePos - COLS;
                break;
            case DOWN:
                state.setCharAt(blankStatePos, state.charAt(blankStatePos + COLS));
                state.setCharAt(blankStatePos + COLS, '0');
                blankStatePos = blankStatePos + COLS;
                break;
        }

    }

    public List<Direction> getPossibleActions() {
        List<Direction> actions = new ArrayList<>(4);
        final int row = getRowFromStatePos(blankStatePos);
        final int col = getColFromStatePos(blankStatePos);

        if (row > 0) {
            actions.add(UP);
        }
        if (row < ROWS - 1) {
            actions.add(DOWN);
        }

        if (col > 0) {
            actions.add(LEFT);
        }
        if (col < COLS - 1) {
            actions.add(RIGHT);
        }
        return actions;
    }
    
    @Override
    public String toString() {
    	StringBuilder builder = new StringBuilder();
    	
    	builder.append(state.substring(0, 3)).append("\n");
    	builder.append(state.substring(3, 6)).append("\n");
    	builder.append(state.substring(6, 9));
    	
    	return builder.toString();
    }
}
