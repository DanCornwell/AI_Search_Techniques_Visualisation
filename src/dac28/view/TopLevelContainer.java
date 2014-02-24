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

import dac28.controller.AlgorithmController;
import dac28.controller.CreateController;
import dac28.controller.TreeController;
import dac28.model.SearchAlgorithm;
import dac28.model.Tree;

class TopLevelContainer implements ActionListener {

	private AlgorithmDisplay algorithmDisplay;
	private TreeDisplay treeDisplay;
	private JMenuItem newSearch,quit,about;

	TopLevelContainer() {
		algorithmDisplay = new AlgorithmDisplay();
		treeDisplay = new TreeDisplay();
		initialiseBase();
	}

	/**
	 * Initialises the graphical user interface.
	 */
	private void initialiseBase() {

		// Width and Height of the application
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
		newSearch = new JMenuItem("New Search...");
		newSearch.addActionListener(this);
		file.add(newSearch);
		file.addSeparator();
		quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		file.add(quit);
		JMenu help = new JMenu("Help");
		about = new JMenuItem("About");
		about.addActionListener(this);
		help.add(about);

		// Adds menus to the menu bar
		menuBar.add(file);
		menuBar.add(help);

		// Assign stuff onto the base frame
		base.setJMenuBar(menuBar);
		base.getContentPane().add(treeDisplay.initialiseTreePanel(WIDTH,HEIGHT),BorderLayout.WEST);
		base.getContentPane().add(algorithmDisplay.initialiseAlgorithmPanel(WIDTH, HEIGHT),BorderLayout.EAST);

		// Display the window.
		base.setMinimumSize(new Dimension(WIDTH,HEIGHT));
		base.pack();
		base.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newSearch) {
			Object[] options = {"Confirm", "Cancel"};

			CreateController controller = new CreateController();

			int goal = 0;

			while(true) {

				int result = JOptionPane.showOptionDialog(null, controller.getCreateDialog(), 
						"Algorithm and Tree Chooser",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

				if(result == JOptionPane.OK_OPTION) {

					try {
						goal = Integer.parseInt(controller.getGoal());
						break;
					}
					catch(NumberFormatException error) {
						Object[] ok = {"Ok"};
						JOptionPane.showOptionDialog(null,"The value entered for the goal was not an integer.\n" +
								"Please enter a valid integer.", 
								"Goal Value Error",JOptionPane.YES_OPTION,
								JOptionPane.ERROR_MESSAGE, null, ok, ok[0]);
					}
				}
				else {
					return;
				}

			}

			Tree tree = controller.getTreeCreator().getTree(goal);
			SearchAlgorithm algorithm = controller.getAlgorithmCreator().getSearchAlgorithm(tree);

			TreeController treeController = new TreeController(algorithm,tree,treeDisplay);

			new AlgorithmController(treeController,algorithm,algorithmDisplay);

		}

		else if(e.getSource() == quit) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?","Quitting Application",JOptionPane.YES_NO_OPTION);
			if(option == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}

		else if(e.getSource() == about) {

		}

	}

}
