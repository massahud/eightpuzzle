/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package massahud.ai.eightpuzzle;

/**
 *
 * @author Geraldo Massahud
 *
 * Informações SVN
 * @version $Revision: $:
 * @author Última modificação por $Author: $:
 * @date $Date: $:
 */
public class InvalidAction extends RuntimeException {

    private Direction direction;

    InvalidAction(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String getMessage() {
        return "Impossible to move " + direction;
    }

    public Direction getInvalidDirection() {
        return this.direction;
    }

}
