/*
Assignment: gameoflife
Team Project: Daniel Gorney, Tyrel Carter, Jamie Batchelor, Brooke Horrocks
Program: CellAvatar.java
Written by: Jamie
Date: Jul 19, 2018
*/
package gameOfLife;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * The graphical representation of a single Cell.
 * 
 * Paints a 10x10 colored square on the screen that reflects the state of the Cell
 * the CellAvatar is representing: draws a Black square for a Cell that is ALIVE, draws a White
 * square for a Cell that is DEAD.
 * 
 * CellAvatars respond to being clicked on by toggling their color (White/Black) and informing the Grid
 * that it needs to toggle the ALIVE/DEAD state of the Cell represented by the CellAvatar.
 * 
 * CellAvatar keeps track of which (Column, Row) coordinate from the Grid that it represents.
 * 
 * @author Jamie Batchelor
 *
 */
public class CellAvatar extends JPanel {
	
	/**
	 * cellColor will be either White or Black: White for a Cell that is
	 * DEAD and Black for a Cell that is ALIVE.
	 */
	private Color cellColor;
	/**
	 * The (column, row) coordinate in the Grid that this CellAvatar resides at.
	 */
	private int columnRepresented;
	private int rowRepresented;

	/**
	 * Draws the 10x10 square that represents a Cell.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(this.cellColor);
		g.fillRect(0, 0, 10, 10);
		}
	
	/**
	 * Toggles the CellAvatar's color between White/Black.
	 */
	public void toggleColor() {
		if (this.cellColor == Color.BLACK) {
			this.cellColor = Color.WHITE;
		}
		else {
			this.cellColor = Color.BLACK;
		}
	}
	
	/**
	 * @return The CellAvatar's current color.
	 */
	public Color getColor() {
		return cellColor;
	}
	
	/**
	 * @return The column from the (column, row) Grid coordinate this CellAvatar resides at.
	 */
	public int getColumn() {
		return columnRepresented;
	}
	
	/**
	 * @return The row from the (column, row) Grid coordinate this CellAvatar resides at.
	 */
	public int getRow() {
		return rowRepresented;
	}
	
	/**
	 * Constructs and initializes a CellAvatar that represents a Cell located at
	 * (col, row) coordinate in the Grid provided.
	 * 
	 * CellAvatar color defaults to White (representing the default DEAD state of Cells).
	 * 
	 * COnfigures click-handler that toggles the ALIVE/DEAD state of the represented Cell and
	 * toggles the color of the CellAvatar to match.
	 * 
	 * @param grid The Grid containing the Cell that this CellAvatar represents.
	 * @param col Column from (col, row) coordinate in the Grid.
	 * @param row Row from (col, row) coordinate in the Grid.
	 */
	public CellAvatar(Grid grid, int col, int row) {
		super();
		this.cellColor = Color.WHITE;
		this.columnRepresented = col;
		this.rowRepresented = row;
		
		this.addMouseListener(new MouseAdapter()  
		{  
			@Override
		    public void mouseClicked(MouseEvent e)  
		    {  
				CellAvatar avatarClickedOn = (CellAvatar) e.getSource();
				avatarClickedOn.toggleColor();
				avatarClickedOn.repaint();
				grid.toggleCell(avatarClickedOn.getColumn(), avatarClickedOn.getRow());
		    }  
		}); 
	}

}
