package dac28.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import dac28.model.Node;
import dac28.model.TextFileReader;
import dac28.model.Tree;
import dac28.model.TreeCreator;

/**
 * Controller to create new searches and trees.
 * 
 * @author Dan Cornwell
 *
 */
public class SearchCreator {

	/**
	 * The goal field the user can enter their goal value in.
	 */
	protected JTextField goal;
	/**
	 * The combo boxes for the different algorithms and trees.
	 */
	protected JComboBox<String> algorithmOptions,treeOptions;	
	/**
	 * String arrays for the algorithms and trees.
	 */
	protected final String[] ALGORITHMS, TREES;	
	/**
	 * Draws the tree panel, with text fields to set the node values.
	 */
	protected JPanel treeDiagram;
	/**
	 * List of the node input text fields.
	 */
	protected List<JTextField> nodeValues;
	/**
	 * List of the path input text fields.
	 */
	protected List<JTextField> pathValues;
	/**
	 * The width of the controller.
	 */
	protected final int WIDTH = 400;
	/**
	 * The height of the controller.
	 */
	protected final int HEIGHT = 500;
	
	/**
	 * Initialises the radio buttons and goal field.
	 */
	public SearchCreator() {

		goal = new JTextField("");

		treeDiagram = new TreeDiagram();

		ALGORITHMS = getAlgorithms();
		algorithmOptions = new JComboBox<String>(ALGORITHMS);

		TREES = getTrees();
		treeOptions = new JComboBox<String>(TREES);

		nodeValues = new LinkedList<JTextField>();

		pathValues = new LinkedList<JTextField>();

	}

	/**
	 * Returns the algorithms defined in algorithms.txt in the form of a string array.
	 *  
	 * @return string array 
	 */
	private String[] getAlgorithms() {

		return TextFileReader.getAlgorithms().toArray(new String[TextFileReader.getAlgorithms().size()]);

	}

	/**
	 * Returns the trees defined in trees.txt in the form of a string array.
	 *  
	 * @return string array 
	 */
	private String[] getTrees() {

		return TextFileReader.getTrees().toArray(new String[TextFileReader.getTrees().size()]);

	}

	/**
	 * Returns a dialog window allowing the user to choose the search.
	 * 
	 * @return dialog window
	 */
	private final JPanel getCreateDialog() {

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

		panel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		// Node name buttons
		// final button since it is used within the tree changer ActionListener
		final JRadioButton NUMBER = new JRadioButton("Numeric");
		NUMBER.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int name = 0;
				for(JTextField field: nodeValues) {
					field.setText(String.valueOf(name++));
					field.setEditable(false);
				}
			}
		});
		JRadioButton alpha = new JRadioButton("Alphabetic");
		alpha.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				int index = 1;
				for(JTextField field: nodeValues) {

					// Iterates through the alphabet, such that the values are
					// A,B,...,Z,AA,AB,...AZ,BA etc...
					String value = "";					
					if (index<27){
						value += Character.toString((char)(index+96));
					} else {
						if (index%26==0) {
							value += (Character.toString((char) ((index/26)-1+96))+Character.toString((char)((index%26)+1+96)));
						} else {
							value += (Character.toString((char) ((index/26)+96))+Character.toString((char)((index%26)+96)));
						}
					}
					field.setText(String.valueOf(value).toUpperCase());
					index++;
					field.setEditable(false);
				}			
			}
		});
		JRadioButton custom = new JRadioButton("Custom");
		custom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(JTextField field: nodeValues) {
					field.setEditable(true);
				}	
			}
		});
		ButtonGroup nodeNames = new ButtonGroup();
		nodeNames.add(NUMBER);
		nodeNames.add(alpha);
		nodeNames.add(custom);
		NUMBER.setSelected(true); 
		JPanel nodeNamesPanel = new JPanel();
		nodeNamesPanel.setPreferredSize(new Dimension(WIDTH/4,HEIGHT/12));
		nodeNamesPanel.add(NUMBER);
		nodeNamesPanel.add(alpha);
		nodeNamesPanel.add(custom);

		// Adds the goal input box and label
		JPanel goalChoice = new JPanel();
		goalChoice.setPreferredSize(new Dimension(WIDTH/4,HEIGHT/12));
		goalChoice.add(new JLabel("Enter value of the goal node: "));
		goal.setPreferredSize(new Dimension(30,20));
		goalChoice.add(goal);

		// Adds the tree options and label
		JPanel treeChoices = new JPanel();
		treeChoices.setPreferredSize(new Dimension(WIDTH/4,HEIGHT/12));
		treeChoices.add(new JLabel("Select Search Tree: ",JLabel.RIGHT));
		treeChoices.add(treeOptions);
		treeOptions.setSelectedIndex(0);	


		// Call repaint on the tree whenever an item in the combo box is selected
		treeOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// 'Reset' the random tree if it is selected
				if(treeOptions.getSelectedItem().equals("TreeRandom")) 
					TreeCreator.getInstance().putTree(treeOptions.getSelectedIndex(),"TreeRandom");
				
				NUMBER.doClick();
				// Clear the text fields list
				nodeValues.clear();
				pathValues.clear();
				// Remove text fields from tree diagram
				treeDiagram.removeAll();
				treeDiagram.repaint();
			}
		});

		// Tree drawing panel
		treeDiagram.setLayout(null);
		treeDiagram.setPreferredSize(new Dimension(WIDTH-20,3*(HEIGHT/4)-10));
		treeDiagram.setBorder(BorderFactory.createLineBorder(Color.black));
		treeDiagram.setBackground(Color.white);

		// Add panels onto main panel and returns it
		panel.add(goalChoice);
		panel.add(treeChoices);
		panel.add(addSearchChoices());
		panel.add(treeDiagram);
		panel.add(nodeNamesPanel);

		return panel;

	}

	/**
	 * Returns a panel with a combo box of all the search algorithms.
	 * Controllers that use multiple searches should override this method
	 * and return a panel with all the search options.
	 * 
	 * @return a panel with the search combo boxes
	 */
	protected JPanel addSearchChoices() {

		// Adds the search choices and label
		JPanel searchChoices = new JPanel();
		searchChoices.setPreferredSize(new Dimension(WIDTH/4,HEIGHT/12));
		searchChoices.add(new JLabel("Select Search Algorithm: ",JLabel.RIGHT));
		searchChoices.add(algorithmOptions);
		algorithmOptions.setSelectedIndex(0);
		// Call repaint on the tree if uniform cost search is chosen (to add path cost inputs)
		algorithmOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(algorithmOptions.getSelectedIndex() != Arrays.asList(ALGORITHMS).indexOf("UniformCostSearch")) {
					// Clear any path values
					pathValues.clear();
					// Remove text fields from tree diagram
					treeDiagram.removeAll();
				}
				treeDiagram.repaint();
			}
		});

		return searchChoices;
	}

	/**
	 * Displays a dialog box allowing the user to choose their search algorithms and trees.
	 * Validates the user input, returning warning dialog if there is a problem
	 * 
	 * @return true if user clicks OK, false if they hit cancel
	 */
	final boolean getUserInput() {
		
		Object[] options = {"Confirm", "Cancel"};

		// boolean used to loop the controller on invalid input
		boolean validInput = false;

		JPanel dialog = getCreateDialog();

		while(!validInput) {

			int result = JOptionPane.showOptionDialog(null, dialog, 
					"Algorithm and Tree Chooser",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

			if(result == JOptionPane.OK_OPTION) {

				List<String> warnings = new LinkedList<String>();
				for(String value: getNodeValues()) {
					if(value.trim().equals("")) {
						warnings.add("A node name was blank. A default value will be used instead.\n");
						break;
					}

				}
				if(getGoal().trim().equals("")) warnings.add("The goal value was blank.\n");
				else if(!getNodeValues().contains(getGoal())) 
					warnings.add("The goal value was not found in the tree.\n");

				for(String value: getPathValues()) {

					try {
						Integer.parseInt(value); 
					}
					catch(NumberFormatException numberError) {
						warnings.add("A path cost value was not an integer. Default value of 1 will be used instead.");
						break;
					}
				}

				// If warnings exist display them and let user decide whether to continue or not
				if(!warnings.isEmpty()) {

					String warningString = "";
					for(String s: warnings) {
						warningString+=(s+"\n");

					}

					Object[] warningOptions = {"Yes","Back"};

					int warningResult = JOptionPane.showOptionDialog(null,"The following warnings were raised: " +
							"\n\n\n".concat(warningString).concat("\nAre you sure you wish to continue?"), 
							"Application Warning",JOptionPane.YES_OPTION,
							JOptionPane.WARNING_MESSAGE, null, warningOptions, warningOptions[0]);


					if(warningResult == JOptionPane.OK_OPTION) {
						validInput = true;
					}

				}
				else validInput = true;
			}

			else {
				return false;
			}

		}
		return true;
		
	}
	
	/**
	 * Returns the the unique ID of the selected tree, which is its index within the button group.
	 * 
	 * @return Integer representing the tree's ID
	 */
	protected final int getTreeUID() {
		
		return treeOptions.getSelectedIndex();

	}

	/**
	 * Returns the unique id of the selected algorithm, which is its index within the button group.
	 * 
	 * @return Integer representing the algorithm's ID
	 */
	protected final int getAlgorithmUID() {

		return algorithmOptions.getSelectedIndex();

	}

	/**
	 * Returns the value that is in the goal text field.
	 * 
	 * @return string representing the value entered
	 */
	protected final String getGoal() {
		return goal.getText();
	}

	/**
	 * Returns the string values in the node text fields.
	 * 
	 * @return queue of string values for the nodes
	 */
	protected final Queue<String> getNodeValues() {
		Queue<String> values = new LinkedList<String>();

		for(JTextField field: nodeValues) {

			values.add(field.getText().trim());

		}

		return values;
	}

	/**
	 * Returns the string values in the path text fields.
	 * 
	 * @return queue of string values for the paths
	 */
	protected final Queue<String> getPathValues() {
		Queue<String> values = new LinkedList<String>();
		for(JTextField field: pathValues) {
			values.add(field.getText());
		}
		return values;
	}

	/**
	 * Returns whether uniform cost search is selected.
	 * Controllers with multiple algorithms should check each algorithm
	 * with or operators.
	 * 
	 * @return true if an algorithm has selected uniform cost search, false otherwise
	 */
	protected boolean usingUniformCostSearch() {

		return (algorithmOptions.getSelectedItem().equals("UniformCostSearch"));
	}

	/**
	 * Returns true if the algorithm selected should use a stack display. 
	 * Add or statements in the same form of the ones below if new algorithms with stacks are implemented.
	 * If there is more than one search being used create another method like this one for the another button group.
	 * 
	 * @return true if the selected search matches one that uses a stack, false otherwise
	 */
	protected final boolean algorithmUsingStack() {

		return algorithmOptions.getSelectedItem().equals("DepthFirstSearch") 
				|| algorithmOptions.getSelectedItem().equals("IterativeDeepeningSearch");
	}

	/**
	 * Panel that draws a visualisation for the trees.
	 * 
	 * @author Dan Cornwell
	 * 
	 */
	private class TreeDiagram extends JPanel {
	
		/**
		 * Random serial number.
		 */
		private static final long serialVersionUID = -323925685273143109L;

		/**
		 * Paints the JPanel. Calls the draw tree method.
		 */
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Tree tree = TreeCreator.getInstance().getTree(getTreeUID(), new LinkedList<String>());
			if(tree==null) {
				g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
				g.drawString("Tree not found", (this.getWidth()/3)+15, this.getHeight()/2);
				return;
			}

			// HashMap holding with a point key and point value. Used to draw lines between these 2 points.
			Map<Point,Point> lineCoords = new HashMap<Point,Point>();
			// Holds the line connection coordinates of parent nodes.
			LinkedList<Point> parentCoords = new LinkedList<Point>();
			// The maximum depth of the tree.
			final int TREE_DEPTH = tree.getTreeDepth();
			// Size of the boxes to be drawn
			int boxsize = 30;
			// The font size
			int fontSize = 10;
			// a default value used when naming new nodes
			int defaultValue = 0;
			// The shrink ratio for the font size
			final double shrinkRatio = 5.0/6;

			// whether they're are some node values to use, if false then they will be created
			boolean isNodeValuesEmpty = nodeValues.isEmpty();
			// whether they're are some path values to use, if false then they will be created
			boolean isPathValuesEmpty = pathValues.isEmpty();

			// While the boxes are too big either horizontally or vertically, shrink the box size
			// This makes sure the entire tree is drawn within the panel
			if(TREE_DEPTH != 0 && tree.getTreeWidth() != 0) {
				while((this.getHeight()/TREE_DEPTH)-10 < boxsize) {
					boxsize -= 5;
					fontSize = (int)Math.round(fontSize * (shrinkRatio));
				}
				while((this.getWidth()/tree.getTreeWidth())-10 < boxsize) {
					boxsize -=5;
					fontSize = (int)Math.round(fontSize * (shrinkRatio));
				}
				g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
			}

			
			// The x position of the root node.
			final int ROOT_X_POS = (this.getWidth()/2)-(boxsize/2);
			// The y position of the root node.
			final int ROOT_Y_POS = (this.getHeight()/TREE_DEPTH)/4;
			
			// Draws the root node box
			g.drawRect(ROOT_X_POS, ROOT_Y_POS, boxsize, boxsize);

			// if no node values exist create them
			if(isNodeValuesEmpty) {
				// Create the text field box for the root node
				JTextField rootValue = new JTextField(String.valueOf(defaultValue++));
				rootValue.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
				rootValue.setBounds(ROOT_X_POS+10, ROOT_Y_POS+5, 20, 20);
				rootValue.setBorder(null);
				rootValue.setBackground(Color.white);
				nodeValues.add(rootValue);
			}

			// The line connection point of the root node. This will be the bottom centre of the box.
			Point rootPoint = new Point(ROOT_X_POS+(boxsize/2),ROOT_Y_POS+boxsize);

			// A list of nodes representing parents.
			LinkedList<Node> parents = new LinkedList<Node>();
			// A list of nodes representing children.
			LinkedList<Node> children = new LinkedList<Node>();

			// Adds the root node to the parent list.
			parents.add(tree.getRoot());
			// Adds the root node line connection point n times, where n is the size of its children list.
			for(int i=0;i<tree.getRoot().getChildren().size();i++) {
				parentCoords.add(rootPoint);
			}

			// Integer representing the node level we are on. Root is considered to be level 0.
			int nodeLevel = 1;

			// While elements exist within parents list.
			while(!parents.isEmpty()) {

				// Remove all elements from parent list and add their children to the children list.
				while(!parents.isEmpty()) {
					children.addAll(parents.remove().getChildren());
				}

				// If the children list is not empty (i.e parents were not all leaf nodes).
				if(!children.isEmpty()) {

					// Get the number of nodes that will be on this level, given by the size of the children list.
					final int NODES_ON_LEVEL = children.size();

					// For all the children on this level.
					for(int i=0;i<NODES_ON_LEVEL;i++) {

						// Gives the nodes x position, using math to give visually pleasing spacing.
						int xPos = (this.getWidth()/(NODES_ON_LEVEL+1)) + (i*(this.getWidth()/(NODES_ON_LEVEL+1))) - (boxsize/2);
						
						// Get the nodes y position by multiplying the node level with the 
						// amount of space each level takes in relation to the max height.
						int yPos = nodeLevel*(this.getHeight()/TREE_DEPTH);
						
						// Draw the nodes with their values inside them.
						g.drawRect(xPos, yPos, boxsize, boxsize);	

						// if no node values exist create them
						if(isNodeValuesEmpty) {
							// Create the text field box for the child node
							JTextField childValue = new JTextField(String.valueOf(defaultValue++));
							childValue.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
							childValue.setBounds(xPos+10, yPos+5, 20, 20);
							childValue.setBackground(Color.white);
							childValue.setBorder(null);
							nodeValues.add(childValue);
						}

						// The child line connection point. This will be the top middle of the box.
						Point childCoord = new Point(xPos+(boxsize/2),yPos);

						// If there are parent coordinates in the list
						if(!parentCoords.isEmpty()) {
							// Get the first parent coordinate.
							Point parentCoord = parentCoords.remove();
							// Add this coordinate with the child's coordinate to the line coordinates HashMap.
							// Note that childCoord is the key, since a parent can have many children but
							// a child can only have one parent.
							lineCoords.put(childCoord, parentCoord);

							// If we are using uniform cost search then add text field allowing path cost
							if(usingUniformCostSearch()) {
								if(isPathValuesEmpty) {
									JTextField lineValue = new JTextField("1");
									lineValue.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
									lineValue.setBounds(((childCoord.x+parentCoord.x)/2), (childCoord.y+parentCoord.y)/2, 15, 15);
									pathValues.add(lineValue);
								}
							}	

							// For the size of this child's children list, add its parent coordinate to the 
							// parent coordinates list.
							for(int j=0;j<children.get(i).getChildren().size();j++) {
								// Parent coordinate is its child coordinate but with the box size added to the 
								// y coordinate. AKA the top centre point.
								Point newParentCoord = new Point(childCoord.x,childCoord.y+boxsize);
								parentCoords.add(newParentCoord);
							}
						}	
					}
				}

				// Add all the children to the parents list.
				for(Node newParents: children) {
					parents.add(newParents);
				}
				// Clear the children list.
				children.clear();
				// Increment the node level.
				nodeLevel++;
			}

			// Draw all the lines within the line coordinates HashMap
			for(Entry<Point, Point> lines: lineCoords.entrySet()) {
				g.drawLine(lines.getKey().x, lines.getKey().y, lines.getValue().x, lines.getValue().y);
			}

			// Add all text fields
			for(JTextField textFields: nodeValues) {
				textFields.setEditable(false);
				treeDiagram.add(textFields);
			}
			for(JTextField pathFields: pathValues) {
				treeDiagram.add(pathFields);
			}

		}

	}

}
