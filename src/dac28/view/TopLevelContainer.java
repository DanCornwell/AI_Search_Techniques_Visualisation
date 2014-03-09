package dac28.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import dac28.controller.AlgorithmController;
import dac28.controller.CreateController;
import dac28.controller.DualCreateController;
import dac28.controller.DualTreeController;
import dac28.controller.SingleTreeController;
import dac28.controller.TreeController;
import dac28.model.SearchAlgorithm;
import dac28.model.Tree;

/**
 * The top level container that holds the rest of the displays.
 * 
 * @author Dan Cornwell
 *
 */
class TopLevelContainer implements ActionListener {

	/**
	 * The algorithm display being used.
	 */
	private AlgorithmDisplay algorithmDisplay;
	/**
	 * The second algorithm display used in the dual search.
	 */
	private AlgorithmDisplay dualAlgorithmDisplay;
	/**
	 * The tree display being used.
	 */
	private TreeDisplay treeDisplay;
	/**
	 * Menu items that the user can interact with.
	 */
	private JMenuItem newSearch,newDualSearch,quit,about,legend;
	/**
	 * Base frame for the application.
	 */
	private JFrame base;
	/**
	 * Height of the displays.
	 */
	private int HEIGHT = 500;
	/**
	 * Width of the displays.
	 */
	private int width = 700;

	TopLevelContainer() {
		initialiseBase();
	}

	/**
	 * Initialises the graphical user interface.
	 */
	private void initialiseBase() {

		// Create the base frame
		base = new JFrame("Search Algorithm Visualiser");
		base.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the menu items
		JMenuBar menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setBackground(new Color(154, 165, 127));
		menuBar.setPreferredSize(new Dimension(width, 30));
		JMenu file = new JMenu("File");
		newSearch = new JMenuItem("New Search...");
		newSearch.addActionListener(this);
		newDualSearch = new JMenuItem("New Dual Search...");
		newDualSearch.addActionListener(this);
		file.add(newSearch);
		file.add(newDualSearch);
		file.addSeparator();
		quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		file.add(quit);
		JMenu help = new JMenu("Help");
		about = new JMenuItem("About");
		about.addActionListener(this);
		legend = new JMenuItem("Legend");
		legend.addActionListener(this);
		help.add(about);
		help.add(legend);

		// Adds menus to the menu bar
		menuBar.add(file);
		menuBar.add(help);

		// Assign stuff onto the base frame
		base.setJMenuBar(menuBar);

		initialiseSingleDisplay();
	}


	/**
	 * Initialises the graphical user interface for a single search.
	 */
	private void initialiseSingleDisplay() {

		width = 700;
		addDisplays();
		showBase();
	}

	/**
	 * Initialises the graphical user interface for a dual search.
	 */
	private void initialiseDualDisplay() {

		width = 1000;
		addDualDisplays(); 
		showBase();
	}

	private void showBase() {
		// Display the window.
		base.setMinimumSize(new Dimension(width,HEIGHT));
		base.pack();
		base.setVisible(true);
		base.setResizable(false);
		base.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// New search, creates a controller and takes a user input for the goal node and their choices
		// for the tree and algorithm. Loops if input is invalid.
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

			// Add new displays
			addDisplays();

			// Create a tree and algorithm controller with the user supplied information.

			Tree tree = controller.getTreeCreator().getTree(goal);
			SearchAlgorithm algorithm = controller.getAlgorithmCreator().getSearchAlgorithm(tree);
			initialiseSingleDisplay();
			
			TreeController treeController = new SingleTreeController(algorithm,tree,treeDisplay);

			new AlgorithmController(treeController,algorithm,algorithmDisplay);

		}

		else if(e.getSource() == newDualSearch) {
			Object[] options = {"Confirm", "Cancel"};

			DualCreateController controller = new DualCreateController();

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

			// Add new displays
			addDualDisplays();

			// Create a tree and algorithm controller with the user supplied information.

			Tree tree = controller.getTreeCreator().getTree(goal);
			SearchAlgorithm algorithm1 = controller.getAlgorithm1Creator().getSearchAlgorithm(tree);
			SearchAlgorithm algorithm2 = controller.getAlgorithm2Creator().getSearchAlgorithm(tree);
			initialiseDualDisplay();
			// Controllers for the first algorithm
			SearchAlgorithm[] searchAlgorithms = {algorithm1,algorithm2};
			TreeController treeController = new DualTreeController(searchAlgorithms,tree,treeDisplay);
			new AlgorithmController(treeController,algorithm1,dualAlgorithmDisplay);
			new AlgorithmController(treeController,algorithm2,algorithmDisplay);
		}

		else if(e.getSource() == quit) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?","Quitting Application",JOptionPane.YES_NO_OPTION);
			if(option == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}

		else if(e.getSource() == about) {
		}

		else if(e.getSource() == legend) {
			new LegendPanel().showLegend();
		}

	}

	/**
	 * Adds single displays onto the base frame.
	 */
	private void addDisplays() {

		base.getContentPane().removeAll();
		treeDisplay = new TreeDisplay();
		algorithmDisplay = new AlgorithmDisplay();
		base.getContentPane().add(treeDisplay.initialiseTreePanel(width/2,HEIGHT-30),BorderLayout.WEST);
		base.getContentPane().add(algorithmDisplay.initialiseAlgorithmPanel(width/2,HEIGHT-30),BorderLayout.EAST);

	}
	
	/**
	 * Adds dual displays onto the base frame.
	 */
	private void addDualDisplays() {

		base.getContentPane().removeAll();
		treeDisplay = new DualTreeDisplay();
		algorithmDisplay = new AlgorithmDisplay();
		dualAlgorithmDisplay = new AlgorithmDisplay();
		base.getContentPane().add(dualAlgorithmDisplay.initialiseAlgorithmPanel(width/3, HEIGHT-30),BorderLayout.WEST);
		base.getContentPane().add(treeDisplay.initialiseTreePanel(width/3,HEIGHT-30));
		base.getContentPane().add(algorithmDisplay.initialiseAlgorithmPanel(width/3,HEIGHT-30),BorderLayout.EAST);

	}

	/**
	 * Returns the legend panel.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class LegendPanel {

		/**
		 * Border for the legend boxes.
		 */
		private final Border BOX_BORDER = BorderFactory.createLineBorder(Color.black);
		/**
		 * Size for the legend boxes.
		 */
		private final Dimension BOX_SIZE = new Dimension(20,20);
		/**
		 * Size for the panel that all legend data is inside.
		 */
		private final Dimension PANEL_SIZE = new Dimension(270,30);
		/**
		 * The default colour.
		 */
		private final Color DEFAULT = Color.white;

		private JDialog showLegend() {

			JDialog legend = new JDialog(base,"Legend",false);
			legend.setLayout(new FlowLayout());

			// Creates all the boxes in the legend.
			JLabel currentNode = new JLabel();
			currentNode.setBorder(BOX_BORDER);
			currentNode.setPreferredSize(BOX_SIZE);
			currentNode.setBackground(Color.yellow);
			currentNode.setOpaque(true);
			JLabel currentNodeInfo = new JLabel(" - Current node being examined in the algorithm");
			JLabel goalNode = new JLabel();
			goalNode.setBorder(BOX_BORDER);
			goalNode.setPreferredSize(BOX_SIZE);
			goalNode.setBackground(Color.red);
			goalNode.setOpaque(true);
			JLabel goalNodeInfo = new JLabel(" - Goal node the algorithm searches for");
			JLabel head = new JLabel();
			head.setBorder(BOX_BORDER);
			head.setPreferredSize(BOX_SIZE);
			head.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(139, 0, 255)));
			JLabel headInfo = new JLabel(" - Head of queue / Top of stack");
			JLabel newItem = new JLabel();
			newItem.setBorder(BOX_BORDER);
			newItem.setPreferredSize(BOX_SIZE);
			newItem.setBackground(Color.green);
			newItem.setOpaque(true);
			JLabel newItemInfo = new JLabel(" - New element to the list");

			// Creates panels and adds the boxes to them.
			JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p1.setPreferredSize(PANEL_SIZE);
			p1.setBackground(DEFAULT);
			p1.add(currentNode);
			p1.add(currentNodeInfo);
			JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p2.setPreferredSize(PANEL_SIZE);
			p2.setBackground(DEFAULT);
			p2.add(goalNode);
			p2.add(goalNodeInfo);
			JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p3.setPreferredSize(PANEL_SIZE);
			p3.setBackground(DEFAULT);
			p3.add(head);
			p3.add(headInfo);
			JPanel p4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p4.setPreferredSize(PANEL_SIZE);
			p4.setBackground(DEFAULT);
			p4.add(newItem);
			p4.add(newItemInfo);

			// Add the panels to the legend.
			legend.getContentPane().setBackground(DEFAULT);
			legend.setSize(300,200);
			legend.add(p1);
			legend.add(p2);
			legend.add(p3);
			legend.add(p4);
			legend.setVisible(true);
			legend.setLocationRelativeTo(base);

			return legend;

		}
	}

}
