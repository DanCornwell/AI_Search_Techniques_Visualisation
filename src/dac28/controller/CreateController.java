package dac28.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import dac28.model.BreadthFirstSearchCreator;
import dac28.model.DepthFirstSearchCreator;
import dac28.model.Node;
import dac28.model.SearchAlgorithmCreator;
import dac28.model.Tree;
import dac28.model.Tree112111Creator;
import dac28.model.Tree124Creator;
import dac28.model.Tree1354Creator;
import dac28.model.TreeCreator;

/**
 * Controller to create new searches and trees.
 * 
 * @author Dan Cornwell
 *
 */
public class CreateController {

	/**
	 * Radio buttons relating to the trees the user can choose.
	 */
	private JRadioButton tree124,tree1354,tree112111;
	/**
	 * Radio buttons relating to the searches the user can choose.
	 */
	private JRadioButton bfs,dfs;
	/**
	 * The goal field the user can enter their goal value in.
	 */
	private JTextField goal;

	/**
	 * Initialises the radio buttons and goal field.
	 */
	public CreateController() {

		tree124 = new JRadioButton("1-2-4 Tree");
		tree124.setSelected(true);	
		tree1354 = new JRadioButton("1-3-5-4 Tree");
		tree1354.setSelected(false);
		tree112111 = new JRadioButton("1-1-2-1-1-1 Tree");
		tree112111.setSelected(false);

		bfs = new JRadioButton("Breadth First Search");
		bfs.setSelected(true);
		dfs = new JRadioButton("Depth First Search");
		dfs.setSelected(false);	

		goal = new JTextField("");

	}

	/**
	 * Returns a dialog window allowing the user to choose the search.
	 * 
	 * @return dialog window
	 */
	public JPanel getCreateDialog() {

		JPanel panel = new JPanel();

		// width of the window
		final int WIDTH = 400;
		// height of the window
		final int HEIGHT = 500;

		panel.setPreferredSize(new Dimension(WIDTH,HEIGHT));

		JPanel treeChoices = new JPanel(new GridLayout(0,1));
		treeChoices.setPreferredSize(new Dimension((WIDTH/2)-10,HEIGHT/2));
		treeChoices.setBorder(BorderFactory.createLineBorder(Color.black));

		treeChoices.add(new JLabel("Enter value of the goal node: "));
		goal.setPreferredSize(new Dimension(30,20));
		treeChoices.add(goal);
		treeChoices.add(tree124);
		treeChoices.add(tree1354);
		treeChoices.add(tree112111);
		
		ButtonGroup treeGroup = new ButtonGroup();
		treeGroup.add(tree124);
		treeGroup.add(tree1354);
		treeGroup.add(tree112111);
		
		JPanel searchChoices = new JPanel(new GridLayout(0,1));
		searchChoices.setPreferredSize(new Dimension((WIDTH/2)-10,HEIGHT/2));
		searchChoices.setBorder(BorderFactory.createLineBorder(Color.black));

		searchChoices.add(bfs);
		searchChoices.add(dfs);

		ButtonGroup algorithmGroup = new ButtonGroup();
		algorithmGroup.add(bfs);
		algorithmGroup.add(dfs);

		final JPanel TREE_DIAGRAM = new TreeDiagram();
		TREE_DIAGRAM.setPreferredSize(new Dimension(WIDTH-20,(HEIGHT/2)-20));
		TREE_DIAGRAM.setBorder(BorderFactory.createLineBorder(Color.black));
		TREE_DIAGRAM.setBackground(Color.white);

		for(Enumeration<AbstractButton> buttons = treeGroup.getElements();buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					TREE_DIAGRAM.repaint();
				}
			});
		}
		
		panel.add(treeChoices);
		panel.add(searchChoices);
		panel.add(TREE_DIAGRAM);

		return panel;

	}
	
	/**
	 * Returns a tree creator, depending on which button is selected.
	 * 
	 * @return a tree creator
	 */
	public TreeCreator getTreeCreator() {

		if(tree124.isSelected()) {
			return new Tree124Creator();
		}
		else if(tree1354.isSelected()) {
			return new Tree1354Creator();
		}
		else if(tree112111.isSelected()) {
			return new Tree112111Creator();
		}

		return new Tree124Creator();
	}

	/**
	 * Returns a search algorithm creator, depending on which button is selected.
	 * 
	 * @return a search algorithm creator
	 */
	public SearchAlgorithmCreator getAlgorithmCreator() {

		if(dfs.isSelected()) {
			return new DepthFirstSearchCreator();
		}
		else if(bfs.isSelected()) {
			return new BreadthFirstSearchCreator();
		}

		return new DepthFirstSearchCreator();
	}

	/**
	 * Returns the value that is in the goal text field.
	 * 
	 * @return string representing the value entered
	 */
	public String getGoal() {
		return goal.getText();
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

		private void drawTree(Graphics g) {

			Tree tree = getTreeCreator().getTree(0);

			// HashMap holding with a point key and point value. Used to draw lines between these 2 points.
			Map<Point,Point> lineCoords = new HashMap<Point,Point>();
			// Holds the line connection coordinates of parent nodes.
			LinkedList<Point> parentCoords = new LinkedList<Point>();

			// The maximum depth of the tree.
			final int TREE_DEPTH = tree.getTreeDepth();
			// Size of the boxes to be drawn
			int boxsize = 30;
			// The font size
			int fontSize = 8;
			
			// The shrink ratio for the font size
			final double shrinkRatio = 5.0/6;
			
			// While the boxes are too big either horizontally or vertically, shrink the box size
			while((this.getHeight()/TREE_DEPTH)-10 < boxsize) {
				boxsize -= 5;
				fontSize = (int)Math.round(fontSize * (shrinkRatio));
			}
			while((this.getWidth()/tree.getTreeWidth())-10 < boxsize) {
				boxsize -=5;
				fontSize = (int)Math.round(fontSize * (shrinkRatio));
			}
			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));

			// The x position of the root node.
			final int ROOT_X_POS = (this.getWidth()/2)-(boxsize/2);
			// The y position of the root node.
			final int ROOT_Y_POS = (this.getHeight()/TREE_DEPTH)/4;

			// Draws the root node, with its value inside it. 
			g.drawRect(ROOT_X_POS, ROOT_Y_POS, boxsize, boxsize);		
			g.drawString(String.valueOf(tree.getRoot().getValue()), ROOT_X_POS+(boxsize/2), ROOT_Y_POS+(boxsize/2));

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
						g.drawString(String.valueOf(children.get(i).getValue()), xPos+(boxsize/2), yPos+(boxsize/2));

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

		}

	}
}
