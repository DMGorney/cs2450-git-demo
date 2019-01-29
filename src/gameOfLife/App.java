/**
* Assignment A09 Team Assignment
* Program: App
* Programmer: Brooke Horrocks
* Team Project: Daniel Gorney, Tyrel Carter, Jamie Batchelor, Brooke Horrocks
* Date: Jul 19, 2018
*/
package gameOfLife;


// This is a modified file for the git demonstration.

//MODIFYING THIS FILE TO TRIGGER GIT TRACKING

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 * The App class represents the application window for Conway's Game of Life.
 * Here is where the window is created with an interactive grid. Users will be
 * able to mark cells as either dead or alive. Then, users will be able to activate
 * the grid with controls like play, pause, step to the next generation, randomize and reset.
 * Users will also be able to save and load their created grids in files of .gol format.
 * @author Brooke
 *
 */
public class App extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private boolean paused = true;
	private Grid grid;
	private GridRenderer renderer;
	private SaveManager saveMan;
	private Timer timer = new Timer(1, this);
	Scanner scan = new Scanner(System.in);

	/**
	 * Launch the application.
	 * @param args A string array containing the command line arguments.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame that contains control panel with game controls
	 * and grid panel with interactive grid.
	 */
	public App() {

		/**
		 * Create instance of Grid with size set to 60,
		 * instance of Gridrenderer with grid passed,
		 * instance of SaveManager with grid passed.
		 */
		grid = new Grid(60);
		renderer = new GridRenderer(grid);
		saveMan = new SaveManager(grid);

		/**
		 * Configure window frame and create panel containing buttons
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 0, 1100, 700);
		JPanel btnPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) btnPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		btnPanel.setBackground(Color.DARK_GRAY);
		getContentPane().add(btnPanel, BorderLayout.NORTH);

		/**
		 * Create inner panel and save/load buttons and add to button panel
		 */
		JPanel saveLoadPanel = new JPanel();
		saveLoadPanel.setBackground(Color.DARK_GRAY);
		btnPanel.add(saveLoadPanel);
		JButton btnSave = new JButton("Save");
		btnSave.setBackground(Color.LIGHT_GRAY);

		/**
		 * Pause game, create save file and successful alert dialog boxes, and add save
		 * button to save/load panel
		 */
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paused = true;
				String Save = JOptionPane.showInputDialog("Enter file name to save: ");
				saveMan.save(grid, Save);
				JOptionPane.showMessageDialog(btnPanel, "Successfully Saved.","Alert",JOptionPane.WARNING_MESSAGE);
			}

		});
		saveLoadPanel.add(btnSave);

		JButton btnLoad = new JButton("Load");
		btnLoad.setBackground(Color.LIGHT_GRAY);

		/**
		 * Pause game, load file and repaint to the screen, and add load button to
		 * save/load panel
		 */
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					paused = true;
					String Load = JOptionPane.showInputDialog("Enter file name to load: ");
					grid.setCurrent(saveMan.load(Load).getCurrent());
					renderer.renderGrid(grid.getCurrent());
					repaint();
			}
		});
		saveLoadPanel.add(btnLoad);

		/**
		 * Create space between save/load panel and control panel
		 */
		Component rigidArea = Box.createRigidArea(new Dimension(240, 20));
		btnPanel.add(rigidArea);

		/**
		 * Create inner panel containing control panel, play button, pause button,
		 * step button, randomize button and add to button panel
		 */
		JPanel controlPanel = new JPanel();
		controlPanel.setBackground(Color.DARK_GRAY);
		btnPanel.add(controlPanel);

		JButton btnPlay = new JButton("Play");
		btnPlay.setBackground(Color.LIGHT_GRAY);

		/**
		 * Un-pause game, loop grid
		 */
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (paused == true) {
					paused = false;
				}

				renderer.renderGrid(grid.getCurrent());
				getContentPane().repaint();
			}
		});
		controlPanel.add(btnPlay);

		JButton btnPause = new JButton("Pause");
		btnPause.setBackground(Color.LIGHT_GRAY);

		/**
		 * Pause game
		 */
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paused = true;
			}
		});
		controlPanel.add(btnPause);

		JButton btnStep = new JButton("Step");
		btnStep.setBackground(Color.LIGHT_GRAY);

		/**
		 * Step the grid to next generation of cells
		 */
		btnStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paused = true;
				grid.nextGeneration();
				renderer.renderGrid(grid.getCurrent());
				repaint();
			}
		});
		controlPanel.add(btnStep);

		JButton btnRandomize = new JButton("Randomize");

		/**
		 * Randomize cells in the grid
		 */
		btnRandomize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paused = true;
				grid.randomize();
				renderer.renderGrid(grid.getCurrent());
				getContentPane().repaint();
			}
		});
		btnRandomize.setBackground(Color.LIGHT_GRAY);
		controlPanel.add(btnRandomize);

		/**
		 * Create space between control panel and reset panel
		 */
		Component rigidArea_1 = Box.createRigidArea(new Dimension(280, 20));
		btnPanel.add(rigidArea_1);

		/**
		 * Create inner panel containing reset panel, reset button and add to button panel
		 */
		JPanel resetPanel = new JPanel();
		resetPanel.setBackground(Color.DARK_GRAY);
		btnPanel.add(resetPanel);

		JButton btnReset = new JButton("Reset");
		btnReset.setBackground(Color.LIGHT_GRAY);

		/**
		 * Reset grid, pause game
		 */
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paused = true;
				grid.reset();
				renderer.renderGrid(grid.getCurrent());
				getContentPane().repaint();
			}
		});
		resetPanel.add(btnReset);

		/**
		 * Create grid panel to contain game
		 */
		JPanel gridPanel = new JPanel();
		gridPanel.setBackground(Color.GRAY);
		getContentPane().add(gridPanel, BorderLayout.CENTER);
		gridPanel.add(renderer);

		/**
		 * Start the Timer, which will redraw the window every X milliseconds.
		 * (X can be configured up at the top of the file, where the class's fields are declared.
		 * Timer is initialized up there. Note: 1000 milliseconds = 1 second.)
		 */
		timer.start();
	}

	/**
	 * Every X milliseconds, the Timer object will 'tick', activating the below function.
	 *
	 * X (how often it 'ticks') can be configured at the top of the file, in the fields section.
	 * (See Timer initialization. Line 35.)
	 *
	 * Every 'tick', checks to see if the App is paused or not.
	 * If paused = true, then nothing else happens.
	 * If paused = false, then the 'core game loop' happens:
	 * 	(1) The grid updates to the next generation.
	 * 	(2) The renderer draws the visual representation of the updated grid.
	 *
	 *	(3) Finally, the screen is repainted(). (Refreshes the graphics on screen.)
	 */
	@Override
	public void actionPerformed(ActionEvent ev) {
		if(ev.getSource()==timer){
	    	if (!paused) {
	    		grid.nextGeneration();
	    		renderer.renderGrid(grid.getCurrent());
	    		repaint();
	    	} else {
	    		//Do nothing, because the App is paused.
	    	}
	    }

	}

}
