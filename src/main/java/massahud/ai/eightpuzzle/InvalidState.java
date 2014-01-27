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
 * @author  Última modificação por $Author: $:
 * @date    $Date: $:
 */
public class InvalidState extends RuntimeException {

    private final String state;
    public InvalidState(String state) {
        this.state = state;
    }

    @Override
    public String getMessage() {
        return "Invalid state: " + state;
    }
    
    

    
}
