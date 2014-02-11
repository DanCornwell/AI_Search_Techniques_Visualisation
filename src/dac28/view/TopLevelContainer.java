package dac28.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

class TopLevelContainer implements ActionListener {

	private AlgorithmDisplay algorithmDisplay;
	
	public TopLevelContainer() {
		algorithmDisplay = new AlgorithmDisplay();
		initialiseBase();
	}
	
	/**
	 * Initialises the graphical user interface.
	 */
	private void initialiseBase() {

		final int WIDTH = 700;
		final int HEIGHT = 500;

		// Create the base frame
		JFrame base = new JFrame("Search Algorithm Visualiser");
		base.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the menu items
		JMenuBar menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setBackground(new Color(154, 165, 127));
		menuBar.setPreferredSize(new Dimension(WIDTH, 30));
		JMenu file = new JMenu("File");
		JMenuItem newSearch = new JMenuItem("New Search...");
		newSearch.addActionListener(this);
		file.add(newSearch);
		file.addSeparator();
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		file.add(quit);
		JMenu help = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(this);
		help.add(about);
		
		// Adds menus to the menu bar
		menuBar.add(file);
		menuBar.add(help);

		// Assign stuff onto the base frame
		base.setJMenuBar(menuBar);
		base.getContentPane().add(TreeDisplay.initialiseTree(WIDTH,HEIGHT),BorderLayout.WEST);
		base.getContentPane().add(algorithmDisplay.initialiseAlgorithm(WIDTH, HEIGHT),BorderLayout.EAST);

		// Display the window.
		base.setMinimumSize(new Dimension(WIDTH,HEIGHT));
		base.pack();
		base.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("New Search...")) {

		}
		if(e.getActionCommand().equals("Quit")) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?","Quitting Application",JOptionPane.YES_NO_OPTION);
			if(option == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}

	}

}
