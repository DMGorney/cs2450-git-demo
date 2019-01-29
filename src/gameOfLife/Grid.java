/* assignment: A09 Game of Life
program: Grid
Team Project: Daniel Gorney, Tyrel Carter, Jamie Batchelor, Brooke Horrocks
author: Daniel Gorney
created: Jul 17, 2018
*/

package gameOfLife;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


/**
 * Represents a grid of Cells that follow the rules of Conway's Game of Life.
 * 
 * All spaces in the grid contain either an Alive Cell or a Dead Cell. From one generation
 * to the next those cells live or die depending on how many living neighbors (both orthogonally
 * and diagonally) they have, according to the following rules:
 * 
 * If Alive:
 * --If Lonely (lessthan 2 living neighbors), then die.
 * --If Overcrowded (morethan 3 living neighbors), then die.
 * --If exactly 2 or 3 living neighbors, then survive.
 * 
 * If Dead:
 * --Be "born" (become Alive) if there are at least 3 living neighbors.
 * 
 * Grid can be of any size, but is always square.
 * 
 * Grid provides methods to calculate next generation of Cells (according to the rules of Conway's Game
 * of Life), to randomly populate the Grid with living/dead Cells, and to reset the Grid by killing all Cells.
 * 
 * @author DMGorney
 *
 */
public class Grid implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = -1112878371680458777L;
	
	/**
	 * Multidimensional arrays used to represent the Grid.
	 */
	private Cell[][] starting;
	private Cell[][] current;
	private ArrayList<ArrayList<Boolean>> nextGenAlive;
	
	/**
	 * Constructs and initializes a Grid with the provided size (a square Grid, size*size dimensions).
	 * 
	 * All Cells are initially Dead.
	 * 
	 * @param size The length of each side of the Grid (the number of columns and row).
	 */
	public Grid(int size) {
		
		starting = new Cell[size][size];
		current = new Cell[size][size];
		nextGenAlive = new ArrayList<ArrayList<Boolean>>();
		
		for (int i = 0; i < size; i++) {
			nextGenAlive.add(new ArrayList<Boolean>());
			
			for (int j = 0; j < size; j++) {
				nextGenAlive.get(i).add(new Boolean(false));
			}
		}
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				starting[i][j] = new Cell();
				current[i][j] = new Cell();
				
			}
		}
	}
	
	/**
	 * Used to set the starting arrangement of Cells in the Grid.
	 * 
	 * @param currentGrid The Grid to be modified.
	 */
	public void setStarting(Cell[][] currentGrid) {
		starting = currentGrid;
	}
	
	/**
	 * @return The current Grid state.
	 */
	public Cell[][] getCurrent () {
		return current;
	}
	
	/**
	 * Sets the state of the Grid.
	 * 
	 * @param newGrid The new state for the Grid to be set to.
	 */
	public void setCurrent(Cell[][] newGrid) {
		for (int i = 0; i < current.length; i++) {
			for (int j = 0; j < current.length; j++) {
				if (newGrid[i][j].getState() == CellState.ALIVE) {
					current[i][j].live();
				} else {
					current[i][j].die();
				}
			}
		}
	}
	
	/**
	 * @return The starting state of the Grid.
	 */
	public Cell[][] getStarting() {
		return starting;
	}
	
	/**
	 * Causes the Cell at location (column, row) to be "born" (become Alive).
	 * 
	 * @param column Column of Cell to be born.
	 * @param row Row of Cell to be born.
	 */
	public void birthCell(int column, int row) {
		current[column][row].live();
	}
	
	/**
	 * Causes the Cell at location (column, row) to die (become Dead).
	 * 
	 * @param column Column of Cell to kill.
	 * @param row Row of Cell to kill.
	 */
	public void killCell(int column, int row) {
		current[column][row].die();
	}
	
	/**
	 * Toggles the state of the Cell at location (column, row) between Dead-Alive.
	 * 
	 * @param column Column of Cell to toggle.
	 * @param row Row of Cell to toggle.
	 */
	public void toggleCell(int column, int row) {
		if (current[column][row].getState() == CellState.ALIVE) {
			current[column][row].die();
		} else {
			current[column][row].live();
		}
	}
	
	/**
	 * Kills all of the Cells in the Grid.
	 */
	public void reset() {
		for(int i = 0; i < current.length; i++) {
			for (int j = 0; j < current.length; j++) {
				current[i][j].die();
			}
		}
	}
	
	/**
	 * Randomly populates the entire Grid with living/dead Cells.
	 */
	public void randomize() {
		Random r = new Random();
		for (Cell[] cArray : current) {
			for (Cell cell : cArray) {
				if (r.nextBoolean()) {
					cell.live();
				} else {
					cell.die();
				}
			}
		}
	}
	
	/**
	 * Advances the Grid forward one generation, killing/birthing Cells according to the rules of the Game Of Life. 
	 */
	public void nextGeneration() {
		
		//Calculate survival of next generation, all cells simultaneously
		//before changing any of them.
		for (int i = 1; i < current.length - 1; i++) {
			for (int j = 1; j < current.length - 1; j++) {
				if (checkSurvival(i, j) == true) {
					nextGenAlive.get(i).set(j, new Boolean(true));
					
					//nextGenAlive[i][j] = true;
				} else {
					nextGenAlive.get(i).set(j, new Boolean(false));
					
					//nextGenAlive[i][j] = false;
				}
			}
		}
		
		//Change the cells to match the above calculation.
		for (int i = 1; i < current.length - 1; i++) {
			for (int j = 1; j < current.length - 1; j++) {
				if (nextGenAlive.get(i).get(j) == true) {
					birthCell(i, j);
				} else {
					killCell(i, j);
				}
			}
		}
	}
	
	/**
	 * Helper function used when calculating the next generation of Cells, checks to see if a Cell should live or die.
	 * 
	 * Determination of survival/death is based on the rules of the Game of Life.
	 * 
	 * @param col Column of Cell to be checked.
	 * @param row Row of Cell to be checked.
	 * @return Whether or not the Cell should be Alive in the next generation.
	 */
	private boolean checkSurvival(int col, int row) {
		int numberOfNeighbors = 0;
		
		//Upper-left neighbor.
		if (current[col - 1][row - 1].getState() == CellState.ALIVE) {
			numberOfNeighbors++;
		}  
		//Upper neighbor.
		if (current[col - 1][row].getState() == CellState.ALIVE) {
			numberOfNeighbors++;
		}  
		//Upper-right neighbor.
		if (current[col - 1][row + 1].getState() == CellState.ALIVE) {
			numberOfNeighbors++;
		} 
		//Left neighbor.
		if (current[col][row - 1].getState() == CellState.ALIVE) {
			numberOfNeighbors++;
		} 
		//Right neighbor.
		if (current[col][row + 1].getState() == CellState.ALIVE) {
			numberOfNeighbors++;
		} 
		//Lower-left neighbor.
		if (current[col + 1][row - 1].getState() == CellState.ALIVE) {
			numberOfNeighbors++;
		} 
		//Lower neighbor.
		if (current[col + 1][row].getState() == CellState.ALIVE) {
			numberOfNeighbors++;
		} 
		//Lower-right neighbor.	
		if (current[col + 1][row + 1].getState() == CellState.ALIVE) {
			numberOfNeighbors++;
		}
		
		if(numberOfNeighbors < 2) {
			return false;
		} else if ((numberOfNeighbors == 2 || numberOfNeighbors == 3) && current[col][row].getState() == CellState.ALIVE) {
			return true;
		} else if (numberOfNeighbors == 3 && current[col][row].getState() == CellState.DEAD) {
			return true;
		} else if (numberOfNeighbors > 3) { 
			return false;
		}
		
		return false;
	}
}
