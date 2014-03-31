package dac28.controller;

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
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import dac28.model.Node;
import dac28.model.Tree;
import dac28.model.TreeCreator;

/**
 * Controller to create a dual search.
 * 
 * @author Dan Cornwell
 *
 */
public class DualCreateController extends CreateController {

	/**
	 * Combo box relating to the 2nd algorithm.
	 */
	private JComboBox<String> dualAlgorithmOptions = new JComboBox<String>(ALGORITHMS);

	/**
	 * Returns a dialog window allowing the user to choose the search.
	 * 
	 * @return dialog window
	 */
	@Override
	public JPanel getCreateDialog() {

		JPanel panel = new JPanel();
		// Box Layout so items are set vertically
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

		// width of the window
		final int WIDTH = 400;
		// height of the window
		final int HEIGHT = 550;

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
				char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
				int index = 0;
				for(JTextField field: nodeValues) {
					field.setText(String.valueOf(alphabet[index++]).toUpperCase());
					// avoid null pointer by reseting the index
					if(index==alphabet.length-1) index = 0;
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
		goalChoice.setPreferredSize(new Dimension(WIDTH/4,HEIGHT/15));
		goalChoice.add(new JLabel("Enter value of the goal node: "));
		goal.setPreferredSize(new Dimension(30,20));
		goalChoice.add(goal);

		// Adds the tree options and label
		JPanel treeChoices = new JPanel();
		treeChoices.setPreferredSize(new Dimension(WIDTH/4,HEIGHT/15));
		treeChoices.add(new JLabel("Select Search Tree: ",JLabel.RIGHT));
		treeChoices.add(treeOptions);
		treeOptions.setSelectedIndex(0);
		// Call repaint on the tree whenever an item in the combo box is selected
		treeOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NUMBER.setSelected(true);
				treeDiagram.repaint();
			}
		});

		// Adds the first search choices and label
		JPanel searchChoices1 = new JPanel();
		searchChoices1.setPreferredSize(new Dimension(WIDTH/4,HEIGHT/15));
		searchChoices1.add(new JLabel("Select First Search Algorithm: ",JLabel.RIGHT));
		searchChoices1.add(algorithmOptions);
		algorithmOptions.setSelectedIndex(0);
		algorithmOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// If the algorithm options chosen are the same, change them
				if(algorithmOptions.getSelectedIndex() == dualAlgorithmOptions.getSelectedIndex()) {
					if(dualAlgorithmOptions.getSelectedIndex() == 0) {
						dualAlgorithmOptions.setSelectedIndex(1);
					}
					else dualAlgorithmOptions.setSelectedIndex(0);
				}
				// Call repaint on the tree if uniform cost search is chosen (to add path cost inputs)
				if(algorithmOptions.getSelectedIndex() == Arrays.asList(ALGORITHMS).indexOf("UniformCostSearch")) {
					treeDiagram.repaint();
				}
			}
		});

		// Adds the second search choices and label
		JPanel searchChoices2 = new JPanel();
		searchChoices2.setPreferredSize(new Dimension(WIDTH/4,HEIGHT/15));
		searchChoices2.add(new JLabel("Select Second Search Algorithm: ",JLabel.RIGHT));
		searchChoices2.add(dualAlgorithmOptions);
		dualAlgorithmOptions.setSelectedIndex(1);
		dualAlgorithmOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// If the algorithm options chosen are the same, change them
				if(algorithmOptions.getSelectedIndex() == dualAlgorithmOptions.getSelectedIndex()) {
					if(algorithmOptions.getSelectedIndex() == 0) {
						algorithmOptions.setSelectedIndex(1);
					}
					else algorithmOptions.setSelectedIndex(0);
				}
				// Call repaint on the tree if uniform cost search is chosen (to add path cost inputs)
				if(dualAlgorithmOptions.getSelectedIndex() == Arrays.asList(ALGORITHMS).indexOf("UniformCostSearch")) {
					treeDiagram.repaint();
				}
			}
		});

		// Tree drawing panel
		treeDiagram = new TreeDiagram();
		treeDiagram.setLayout(null);
		treeDiagram.setPreferredSize(new Dimension(WIDTH-20,3*(HEIGHT/4)-10));
		treeDiagram.setBorder(BorderFactory.createLineBorder(Color.black));
		treeDiagram.setBackground(Color.white);

		// add all the panels to the main panel and then return it
		panel.add(goalChoice);
		panel.add(treeChoices);
		panel.add(searchChoices1);
		panel.add(searchChoices2);
		panel.add(treeDiagram);
		panel.add(nodeNamesPanel);

		return panel;

	}

	/**
	 * Returns the unique id of the 2nd selected algorithm.
	 * 
	 * @return Integer representing the algorithm's ID
	 */
	public int getAlgorithm2UID() {

		String selectedItem = (String)dualAlgorithmOptions.getSelectedItem();

		int id = 0;

		while(id <= ALGORITHMS.length-1 && !ALGORITHMS[id].equals(selectedItem)) {
			id++;
		}

		return id;

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
			drawTree(g);
		}

		/**
		 * Draws the tree onto the TREE_DIAGRAM panel
		 * 
		 * @param g - graphics
		 */
		private void drawTree(Graphics g) {

			Tree tree = TreeCreator.getInstance().getTree(getTreeUID(), "", new LinkedList<String>());
			if(tree==null) {
				g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
				g.drawString("Tree not found", (this.getWidth()/3)+15, this.getHeight()/2);
				return;
			}

			// HashMap holding with a point key and point value. Used to draw lines between these 2 points.
			Map<Point,Point> lineCoords = new HashMap<Point,Point>();
			// Holds the line connection coordinates of parent nodes.
			LinkedList<Point> parentCoords = new LinkedList<Point>();
			// Clear the text fields list
			nodeValues.clear();
			pathValues.clear();
			// Remove text fields from tree diagram
			treeDiagram.removeAll();

			// The maximum depth of the tree.
			final int TREE_DEPTH = tree.getTreeDepth();
			// Size of the boxes to be drawn
			int boxsize = 30;
			// The font size
			int fontSize = 8;

			// The shrink ratio for the font size
			final double shrinkRatio = 5.0/6;

			// While the boxes are too big either horizontally or vertically, shrink the box size
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

			// Draws the root node, with its value inside it. 
			g.drawRect(ROOT_X_POS, ROOT_Y_POS, boxsize, boxsize);		
			// Create the text field box for the root node
			int defaultValue = 0;
			JTextField rootValue = new JTextField(String.valueOf(defaultValue++));
			rootValue.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
			rootValue.setBounds(ROOT_X_POS+10, ROOT_Y_POS+5, 20, 20);
			rootValue.setBorder(null);
			nodeValues.add(rootValue);
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
						// Create the text field box for the child node
						JTextField childValue = new JTextField(String.valueOf(defaultValue++));
						childValue.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
						childValue.setBounds(xPos+10, yPos+5, 20, 20);
						childValue.setBorder(null);
						nodeValues.add(childValue);

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
							if(algorithmOptions.getSelectedItem().equals("UniformCostSearch")
									|| dualAlgorithmOptions.getSelectedItem().equals("UniformCostSearch")) {
								JTextField lineValue = new JTextField("1");
								lineValue.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
								lineValue.setBounds(((childCoord.x+parentCoord.x)/2), (childCoord.y+parentCoord.y)/2, 15, 15);
								pathValues.add(lineValue);
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

