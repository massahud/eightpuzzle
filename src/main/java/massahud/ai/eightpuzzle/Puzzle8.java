/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package massahud.ai.eightpuzzle;

import java.util.ArrayList;
import java.util.List;
import static massahud.ai.eightpuzzle.Direction.*;

/**
 *
 * @author Geraldo Massahud
 *
 * Informações SVN
 * @version $Revision: $:
 * @author Última modificação por $Author: $:
 * @date $Date: $:
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

}
