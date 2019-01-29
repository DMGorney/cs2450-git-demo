/* assignment: A09 Game of Life
Team Project: Daniel Gorney, Tyrel Carter, Jamie Batchelor, Brooke Horrocks
program: GridRenderer
author: Jamie Batchelor
created: Jul 17, 2018
*/

package gameOfLife;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A renderer that handles drawing a visual representation (consisting of Black and White 10x10 squares) of a Grid containing Cells that follow the rules of Conway's Game of Life.
 * 
 * @author Jamie Batchelor
 *
 */
public class GridRenderer extends JPanel {
	/**
	 * The collection of CellAvatars that serve as visual representations of the Cells in the Grid.
	 */
	private CellAvatar[][] avatars;

	/**
	 * Constructs and initializes a GridRenderer that will render the given Grid.
	 * @param grid The Grid to be rendered.
	 */
	public GridRenderer(Grid grid) {
		setLayout(new GridLayout(grid.getCurrent().length, grid.getCurrent().length, 0, 0));
		avatars = new CellAvatar[grid.getCurrent().length][grid.getCurrent().length];
		for (int i = 0; i < grid.getCurrent().length; i++) {
			for (int j =0; j < grid.getCurrent().length; j++) {
				avatars[i][j] = new CellAvatar(grid, i, j);
				avatars[i][j].setOpaque(true);
				avatars[i][j].setPreferredSize(new Dimension(10,10));
				add(avatars[i][j]);
				
			}
		}
		renderGrid(grid.getCurrent());
	}	
	
	/**
	 * Renders the provided Grid, drawing one CellAvatar for every Cell in the grid.
	 * @param currentGrid The grid to be rendered.
	 */
	public void renderGrid(Cell[][] currentGrid) {
		for (int i = 0; i < currentGrid.length; i++) {
			for (int j =0; j < currentGrid.length; j++) {
				if (currentGrid[i][j].getState() == CellState.ALIVE) {
					if(avatars[i][j].getColor() == Color.WHITE) { 
						avatars[i][j].toggleColor();
					}
				} else if (currentGrid[i][j].getState() == CellState.DEAD) {
					if(avatars[i][j].getColor() == Color.BLACK) { 
						avatars[i][j].toggleColor();
					}
				}
				
			}
		}
	}
}
