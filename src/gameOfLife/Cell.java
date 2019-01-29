/* assignment: A09 Game of Life
program: Cell
Team Project: Daniel Gorney, Tyrel Carter, Jamie Batchelor, Brooke Horrocks
author: Brooke Horrocks
created: Jul 17, 2018
*/

package gameOfLife;

import java.io.Serializable;

/**
 * Represents a Cell in the Grid
 * @author Brooke
 */
public class Cell implements Serializable {

	private static final long serialVersionUID = 1L;
	private CellState currentState;
	
	/**
	 * Constructor Cell
	 * Default CellState set to DEAD
	 */
	public Cell() {
		currentState = CellState.DEAD;
	}
	
	/**
	 * Returns the current state of a Cell as either DEAD or ALIVE from CellState Enum.
	 * @return currentState
	 */
	public CellState getState () {
		return currentState;
	}
	
	/**
	 * Method: live
	 * Set currentState to ALIVE
	 */
	public void live() {
		currentState = CellState.ALIVE;
	}
	
	/**
	 * Method: die
	 * Set currentState to DEAD
	 */
	public void die() {
		currentState = CellState.DEAD;
	}
	
}
