/* assignment: A09 Game of Life
Team Project: Daniel Gorney, Tyrel Carter, Jamie Batchelor, Brooke Horrocks
program: SaveManager
author: Tyrel Carter
created: July 19, 2018
*/

/**
 * 
 */
package gameOfLife;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;

/**
 * Save Manager that handles saving/loading the state of the Grid from Conway's Game of Life, writing/reading from .gol (Game Of Life) files.
 * 
 * @author Tyrel Carter
 *
 */
public class SaveManager {

	/**
	 * The Grid to be managed.
	 */
	private Grid grid;
	
	/**
	 * Constructs and initializes a SaveManager that will manage saving/loading the provided Grid.
	 * @param currentGrid The Grid to be managed.
	 */
	public SaveManager(Grid currentGrid) {
		grid = currentGrid;
	}
	
	/**
	 * Saves the provided Grid to a .gol (Game Of Life) file.
	 * 
	 * Saving is done via Serialization of the Grid.
	 * 
	 * @param grid The Grid to be saved.
	 * @param fileName The name of the .gol file to save to.
	 */
	public void save(Grid grid,String fileName) {
		try (ObjectOutputStream output= new ObjectOutputStream(new FileOutputStream(fileName + ".gol"))){
    		
	    	output.writeObject(grid);
	    	
	    	} catch (IOException e) {
				System.out.println(e);
			}
	}
	
	/**
	 * Loads the saved Grid from the provided .gol (Game Of Life) file.
	 * 
	 * Loading is done via De-Serialization of the .gol file.
	 * 
	 * @param fileName The name of the .gol file to load.
	 * @return The Grid that is loaded.
	 */
	public Grid load(String fileName) {

		try (ObjectInputStream input= new ObjectInputStream(new FileInputStream(fileName + ".gol"))){
    		
        	return (Grid) input.readObject();
        	
        	} catch (IOException | ClassNotFoundException e) {
    			System.out.println(e);
    		
			}
		return null;
	}
	
	
	
	
}
