package dac28.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import dac28.controller.TreeController;
import dac28.model.DepthFirstSearchCreator;
import dac28.model.SearchAlgorithm;
import dac28.model.Tree;

/**
 * The top level container that holds the rest of the displays.
 * 
 * @author Dan Cornwell
 *
 */
class TopLevelContainer {

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
	private int height = 500;
	/**
	 * Width of the displays.
	 */
	private int width = 700;

	private JButton step,auto,reset,pause,skip,undo;

	TopLevelContainer() {
		initialiseBase();
	}

	/**
	 * Initialises the graphical user interface items.
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
		newSearch.addActionListener(new ActionListener(){
			// New search, creates a controller and takes a user input for the goal node and their choices
			// for the tree and algorithm. Loops if input is invalid.
			@Override
			public void actionPerformed(ActionEvent e) {
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

				// Create a tree and algorithm with the user supplied information. Return if null.
				Tree tree = controller.getTreeCreator().getTree(goal);
				if(tree==null) return;
				SearchAlgorithm algorithm = controller.getAlgorithmCreator().getSearchAlgorithm(tree);
				if(algorithm==null) return;

				// If we are using a stack use an AlgorithmDisplayStack instance. Else queue so AlgorithmDisplay.
				if(controller.getAlgorithmCreator().getClass() == DepthFirstSearchCreator.class) {
					algorithmDisplay = new AlgorithmDisplayStack();
				}
				else {
					algorithmDisplay = new AlgorithmDisplay();
				}
				// initialise the single display
				initialiseSingleDisplay();

				// Put algorithm into an array to allow display to set this algorithm
				SearchAlgorithm[] algorithms = {algorithm};
				// Create a new tree controller
				TreeController treeController = new TreeController(algorithms,tree,treeDisplay);
				// Create a new algorithm controller
				new AlgorithmController(treeController,algorithm,algorithmDisplay);

			}
		});
		newDualSearch = new JMenuItem("New Dual Search...");
		newDualSearch.addActionListener(new ActionListener(){
			// Dual search. Works similar to the single search except creates an algorithm display either side of
			// the tree and provides a tree display that allows 2 algorithm to work on it.
			@Override
			public void actionPerformed(ActionEvent e) {
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

				// Create a tree and 2 algorithms with the user supplied information. Return if null.
				Tree tree = controller.getTreeCreator().getTree(goal);
				if(tree==null) return;
				SearchAlgorithm algorithm1 = controller.getAlgorithm1Creator().getSearchAlgorithm(tree);
				if(algorithm1==null) return;
				SearchAlgorithm algorithm2 = controller.getAlgorithm2Creator().getSearchAlgorithm(tree);
				if(algorithm2==null) return;

				// If we are using a stack use an AlgorithmDisplayStack instance. Else queue so AlgorithmDisplay.
				if(controller.getAlgorithm1Creator().getClass() == DepthFirstSearchCreator.class) {
					algorithmDisplay = new AlgorithmDisplayStack();
				}
				else {
					algorithmDisplay = new AlgorithmDisplay();
				}
				if(controller.getAlgorithm2Creator().getClass() == DepthFirstSearchCreator.class) {
					dualAlgorithmDisplay = new AlgorithmDisplayStack();
				}
				else {
					dualAlgorithmDisplay = new AlgorithmDisplay();
				}
				// initialise the dual display
				initialiseDualDisplay();

				// put algorithms into an array to allow display to set them
				SearchAlgorithm[] searchAlgorithms = {algorithm1,algorithm2};
				// Create new tree controller
				TreeController treeController = new TreeController(searchAlgorithms,tree,treeDisplay);
				// Create new algorithm controller for algorithm 1
				new AlgorithmController(treeController,algorithm1,algorithmDisplay);
				// Create new algorithm controller for algorithm 2
				new AlgorithmController(treeController,algorithm2,dualAlgorithmDisplay);
				
				toggleDualButtons();
			}
		});
		file.add(newSearch);
		file.add(newDualSearch);
		file.addSeparator();
		quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?","Quitting Application",JOptionPane.YES_NO_OPTION);
				if(option == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}	
		});
		file.add(quit);
		JMenu help = new JMenu("Help");
		about = new JMenuItem("About");
		about.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		legend = new JMenuItem("Legend");
		legend.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new LegendPanel().showLegend();
			}
		});
		help.add(about);
		help.add(legend);

		// Adds menus to the menu bar
		menuBar.add(file);
		menuBar.add(help);

		// Assign stuff onto the base frame
		base.setJMenuBar(menuBar);

		// Set algorithmDisplay here so we can draw a blank algorithmDisplay
		algorithmDisplay = new AlgorithmDisplay();

		// Initialise a single display - this will be blank
		initialiseSingleDisplay();
	}


	/**
	 * Initialises the graphical user interface for a single search.
	 */
	private void initialiseSingleDisplay() {

		width = 700;
		height = 500;
		addDisplays();
		showBase();
	}

	/**
	 * Initialises the graphical user interface for a dual search.
	 */
	private void initialiseDualDisplay() {

		width = 1000;
		height = 600;
		addDualDisplays(); 
		showBase();
	}

	/**
	 * Displays the frame. Calls this method after the frame has been resized (possibly for dual search).
	 */
	private void showBase() {

		base.setPreferredSize(new Dimension(width,height));
		base.pack();
		base.setVisible(true);
		base.setResizable(false);
		base.setLocationRelativeTo(null);
	}

	/**
	 * Adds single displays onto the base frame.
	 */
	private void addDisplays() {

		base.getContentPane().removeAll();
		treeDisplay = new TreeDisplaySingleAlgorithm();
		base.getContentPane().add(treeDisplay.initialiseTreePanel(width/2,height-30),BorderLayout.WEST);
		base.getContentPane().add(algorithmDisplay.initialiseAlgorithmPanel(width/2,height-30),BorderLayout.EAST);

	}

	/**
	 * Adds dual displays onto the base frame.
	 */
	private void addDualDisplays() {

		final int MASTER_BUTTON_HEIGHT = 100;

		base.getContentPane().removeAll();
		treeDisplay = new TreeDisplayDualAlgorithms();
		base.getContentPane().add(algorithmDisplay.initialiseAlgorithmPanel(width/3, height-30-MASTER_BUTTON_HEIGHT),BorderLayout.WEST);
		base.getContentPane().add(treeDisplay.initialiseTreePanel(width/3,height-30-MASTER_BUTTON_HEIGHT));
		base.getContentPane().add(dualAlgorithmDisplay.initialiseAlgorithmPanel(width/3,height-30-MASTER_BUTTON_HEIGHT),BorderLayout.EAST);
		// add the master button panel
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(width,MASTER_BUTTON_HEIGHT));
		p.setBackground(Color.yellow);
		step = new JButton("Step");
		step.addActionListener(new DualButtonListener(){
			@Override
			public void buttonLogic() {
				algorithmDisplay.step.doClick();
				dualAlgorithmDisplay.step.doClick();
			}		
		});
		auto = new JButton("Auto");
		auto.addActionListener(new DualButtonListener(){
			@Override
			public void buttonLogic() {

				algorithmDisplay.auto.doClick();
				dualAlgorithmDisplay.auto.doClick();
			}
		});
		undo = new JButton("Undo");
		undo.addActionListener(new DualButtonListener(){
			@Override
			public void buttonLogic() {
				algorithmDisplay.undo.doClick();
				dualAlgorithmDisplay.undo.doClick();
			}
		});
		pause = new JButton("Pause");
		pause.addActionListener(new DualButtonListener(){
			@Override
			public void buttonLogic() {
				algorithmDisplay.pause.doClick();
				dualAlgorithmDisplay.pause.doClick();
			}
		});
		skip = new JButton("Skip");
		skip.addActionListener(new DualButtonListener(){
			@Override
			public void buttonLogic() {
				algorithmDisplay.skip.doClick();
				dualAlgorithmDisplay.skip.doClick();
			}
		});
		reset = new JButton("Reset");
		reset.addActionListener(new DualButtonListener(){
			@Override
			public void buttonLogic() {
				algorithmDisplay.reset.doClick();
				dualAlgorithmDisplay.reset.doClick();
			}
		});
		p.add(step);
		p.add(auto);
		p.add(undo);
		p.add(pause);
		p.add(skip);
		p.add(reset);

		toggleDualButtons();

		base.getContentPane().add(p,BorderLayout.SOUTH);

	}

	private abstract class DualButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			buttonLogic();

			toggleDualButtons();

		}

		protected abstract void buttonLogic();

	}

	private void toggleDualButtons() {
		// Enable/disable buttons were appropriate
		step.setEnabled(algorithmDisplay.step.isEnabled() || dualAlgorithmDisplay.step.isEnabled());
		auto.setEnabled(algorithmDisplay.auto.isEnabled() && dualAlgorithmDisplay.auto.isEnabled());
		undo.setEnabled(algorithmDisplay.undo.isEnabled() || dualAlgorithmDisplay.undo.isEnabled());
		pause.setEnabled(algorithmDisplay.pause.isEnabled() && dualAlgorithmDisplay.pause.isEnabled());
		skip.setEnabled(algorithmDisplay.skip.isEnabled() && dualAlgorithmDisplay.skip.isEnabled());
		reset.setEnabled(algorithmDisplay.reset.isEnabled() && dualAlgorithmDisplay.reset.isEnabled());

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
			goalNode.setBackground(Color.green);
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
			newItem.setBackground(new Color(51,255,255));
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
